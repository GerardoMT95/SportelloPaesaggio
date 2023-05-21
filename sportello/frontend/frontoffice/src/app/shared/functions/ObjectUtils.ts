import { BasicFile } from 'src/app/shared/models/allegati.model';
import { isNullOrUndefined } from 'util';
import { ConfigOption } from '../models/models';
import { HierarchicalOption, OptionNode, TipiEQualificazioni, UlterioriContestiPaesaggistici, Allegato } from './../models/models';

/**
 * @description Metodo che trasforma un array flat in una struttura gerarchica 
 * @param tq Array di TipiEQualificazioni oteuto dal BE
 */
export function fromTeQtoConfigOption(tq: TipiEQualificazioni[]): ConfigOption[]
{
    let co: ConfigOption[] = null;
    if (tq && tq.length > 0)
    {
        co = [];
        let lookup: Map<string, ConfigOption> = new Map();
        tq.forEach((item, index, array) =>
        {
            let obj = null;
            if (!lookup.has(item.codice))
            {
                obj = this.buildConfigoption(item);
                lookup.set(item.codice, obj);
            }
            else
                obj = lookup.get(item.codice);

            if (item.dependency != null)
            {
                let parentCodes: string[] = array.filter(elem => elem.key === item.dependency).map(m => m.codice);
                parentCodes.forEach(code => 
                {
                    if (lookup.has(code))
                    {
                        let parent = lookup.get(code);
                        parent.childName = item.raggruppamento;
                        if (item.codice != "DPR_31_2017_2")
                            parent.extraData.childStyle = "margin";
                        if (!parent.childOptions)
                            parent.childOptions = [];
                        parent.childOptions.push(obj);
                    }
                    else
                    {
                        let tmp = array.find(elem => elem.codice === code);
                        let tmpObj = buildConfigootionTQ(tmp);
                        if (item.codice != "DPR_31_2017_2")
                            tmpObj.extraData.childStyle = "margin";
                        tmpObj.childName = item.raggruppamento;
                        tmpObj.childOptions.push(obj);
                        lookup.set(code, tmpObj);
                    }
                });
            }
            else
                co.push(obj);
        });
    }
    return co;
}
function buildConfigootionTQ(tq: TipiEQualificazioni): ConfigOption
{
    let co: ConfigOption =
    {
        value: tq.codice,
        label: tq.label,
        hasDescription: tq.hastext,
        hasChildren: tq.key != null,
        name: tq.raggruppamento,
        extraData: { type: tq.stile }
    }
    return co;
}

/**
 * @description Metodo che trasforma un array flat in una struttura gerarchica
 * @param items Array di TipiEQualificazioni oteuto dal BE
 */
export function fromTeQToHierarchicalOption(items: TipiEQualificazioni[]): HierarchicalOption[]
{
    let options: HierarchicalOption[] = [];
    if(items && items.length > 0)
    {
        items.forEach(item =>
        {
            if(isNullOrUndefined(item.dependency))
            {
                let option: HierarchicalOption;
                let index = options.map(m => m.name).indexOf(item.raggruppamento)

                if (index >= 0)
                {
                    option = options[index];
                    option.options = [...option.options, buildOptionNode(item)];
                }
                else
                    option = buildOption(item);

                if (!option.children && !isNullOrUndefined(item.key))
                {
                    option.children = recursiveSearchChildren(items, item.key);
                    if (option.children.options.length > 1)
                        option.childStyle = "margin";
                }

                if(index >= 0)
                    options[index] = option;
                else 
                    options.push(option)
            }
        });
    }
    return options;
}
function recursiveSearchChildren(items: TipiEQualificazioni[], key: number): HierarchicalOption
{
    let container: HierarchicalOption = null;
    if(key != null)
    {
        let options: OptionNode[] = [];
        let childKey: number = null;
        items.forEach(item =>
        {
            if (item.dependency === key)
            {
                if (!container)
                    container = buildOption(item);
                if (item.key != null)
                    childKey = item.key;
                options.push(buildOptionNode(item));
            }
        });
        if (container != null && !isNullOrUndefined(key))
        {
            container.children = recursiveSearchChildren(items, childKey);
            if (container.children && container.children.options && container.children.options.length > 1)
                container.childStyle = "margin";
        }
        container.options = options;
    }
    return container;
}
function buildOption(item: TipiEQualificazioni): HierarchicalOption
{
    return {
        dependency: item.dependency,
        name: item.raggruppamento,
        options: [buildOptionNode(item)],
        type: item.stile,
    }
}
function buildOptionNode(tq: TipiEQualificazioni): OptionNode
{
    let node: OptionNode =
    {
        key: tq.key,
        label: tq.label,
        value: tq.codice,
        hasText: tq.hastext,
        orderIndex: tq.ordine
    }

    return node;
}

/**
 * @description trasforma un array di ulteriori contesti paesaggistici in un array di configOption 
 * @param items Oggetti da trasformare
 */
export function fromUCPToHierarchicalStruct(items: UlterioriContestiPaesaggistici[]): ConfigOption[]
{
    return recursiveBuildUCP(items, null);
}
function recursiveBuildUCP(items: UlterioriContestiPaesaggistici[], gruppo: string): ConfigOption[]
{
    let array: ConfigOption[] = null;
    if(items != null)
    {
        array = [];
        items.forEach(item =>
        {
            if(item.gruppo === gruppo)
            {
                let elem: ConfigOption = buildConfigootionUCP(item);
                elem.childOptions = recursiveBuildUCP(items, item.codice);
                if(elem.childOptions &&elem.childOptions.length > 0)
                    elem.hasChildren = true;
                array.push(elem);
            }
        });
    }
    return array;
}
function buildConfigootionUCP(item: UlterioriContestiPaesaggistici): ConfigOption
{
    return {
        label: item.label,
        value: item.codice,
        hasDescription: item.hastext,
        extraData: 
        {
            art1: item.art1,
            art2: item.art2,
            disposizioni: item.disposizioni,
            definizione: item.definizione
        }
    }
}

/**
 * @description trasforma un array di ulteriori contesti paesaggistici in un oggetto HierarchicalOption
 * @param items Oggetti da trasformare
 */
export function fromUCPToHierarchicalOption(items: UlterioriContestiPaesaggistici[]): HierarchicalOption
{
    let obj: HierarchicalOption = null;
    if(items && items.length > 0)
    {
        let opt: OptionNode[] = [];
        items.forEach((item, index) =>
        {
            opt.push({
                label: 'DICHIARAZIONI_TAB.'+item.codice,
                value: item.codice,
                hasText: false,
                key: null,
                orderIndex: index
            });
        });
        obj = 
        {
            dependency: 2,
            name: 'art142Child',
            options: opt,
            type: 'checkbox'
        }
    }
    return obj;
}

export function fromAllegatoToBasicFile(a: Allegato): BasicFile
{
    let f: BasicFile = {
        labelType: a.tipiContenuto ? a.tipiContenuto[0] : "",
        name: a.nome,
        nome: a.nome,
        data:new Date(a.data),
        type: a.tipiContenuto ? a.tipiContenuto[0] : "",
        uploadDate: new Date(a.data),
        path: a.path,
        id: a.id,
        size: a.size,
        formatoFile:null,
        idCms:null,
        checksum:a.checksum
    }
    return f;
}


export function isDataEmpty(data:any) {
    if(typeof(data) === 'object'){
        if(JSON.stringify(data) === '{}' || JSON.stringify(data) === '[]'){
            return true;
        }else if(!data){
            return true;
        }
        return false;
    }else if(typeof(data) === 'string'){
        if(!data.trim()){
            return true;
        }
        return false;
    }else if(typeof(data) === 'undefined'){
        return true;
    }else{
        return false;
    }
}

/**
 * 
 * @param data oggetto le cui chiavi verranno aggiunte in minuscolo es data.COMUNE => data.comune
 */
export function attributesLower(data:any){
    if(typeof(data) === 'object'){
        let keys=Object.keys(data);
        keys.forEach(key=>{data[key.toLowerCase()]=data[key];})
    }
}