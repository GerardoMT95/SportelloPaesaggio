import { FormGroup } from '@angular/forms';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { GroupType, TipoProcedimento } from './../../../shared/models/models';
import { IstanzaFascicolo } from './../configuration/tabs.const';
import { RoutesHandler } from './../models/routes-handler.models';

/**
 * @description Metodo che, sulla base di parametri come lo stato del fascicolo, la tipologia
 * di procedimento e la tipologia del gruppo con cui l'utente è loggato calcola le rotte a cui
 * tale utente non può accedere
 * @param status Stato del fascicolo
 * @param tipoProcedimento Tipologi del procedimento
 * @param tipoGruppo Tipologia del gruppo cui appartiene l'utente
 */
export function buildForbiddenRouteslist(status: StatoEnum, pratica: Fascicolo, tipoGruppo?: GroupType): RoutesHandler
{
    let tipoProcedimento = pratica.tipoProcedimento;
    let obj: RoutesHandler = new RoutesHandler();
    obj.defaultRoute = IstanzaFascicolo.tabs.presentata;
    switch (status)
    {
        case StatoEnum.InAttesaDiProtocollazione:
            obj.forbiddenRoutes =
            [
                IstanzaFascicolo.tabs.verbale,
                IstanzaFascicolo.tabs.ulterioreDocumentazione,
                IstanzaFascicolo.tabs.parere,
                IstanzaFascicolo.tabs.elaborazione,
                IstanzaFascicolo.tabs.relazioneEnte,
                IstanzaFascicolo.tabs.trasmissione
            ];
            if(tipoGruppo !== GroupType.EnteDelegato)
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.protocollazione);
            break;
        case StatoEnum.PresaInCarica:
            obj.forbiddenRoutes = 
            [
                IstanzaFascicolo.tabs.protocollazione,
                IstanzaFascicolo.tabs.verbale,
                IstanzaFascicolo.tabs.ulterioreDocumentazione,
                IstanzaFascicolo.tabs.parere,
                IstanzaFascicolo.tabs.relazioneEnte,
                IstanzaFascicolo.tabs.trasmissione
            ];
            break;
        case StatoEnum.InLavorazione:
            obj.forbiddenRoutes = 
            [
                IstanzaFascicolo.tabs.protocollazione,
                IstanzaFascicolo.tabs.trasmissione
            ];
            /* if(tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010 ||
               tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004 ||
               tipoGruppo === GroupType.Regione)
            {
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.relazioneEnte);
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.parere);
            } */
            if (!pratica.showVCL || tipoGruppo === GroupType.Regione)
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.verbale);
            if(![GroupType.EnteDelegato, GroupType.Regione].includes(tipoGruppo))
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.elaborazione);
            break;
        case StatoEnum.InTrasmissione:
            obj.forbiddenRoutes = 
            [
                IstanzaFascicolo.tabs.protocollazione,
                IstanzaFascicolo.tabs.elaborazione
            ];
            /* if (tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010 ||
                tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004 ||
                tipoGruppo === GroupType.Regione)
            {
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.relazioneEnte);
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.parere);
            } */
            if (!pratica.showVCL || tipoGruppo === GroupType.Regione)
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.verbale);
            if (![GroupType.EnteDelegato, GroupType.Regione].includes(tipoGruppo))
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.trasmissione);
            else
                obj.defaultRoute = IstanzaFascicolo.tabs.trasmissione;
            break;
        case StatoEnum.Trasmessa:
            obj.forbiddenRoutes = 
            [
                IstanzaFascicolo.tabs.protocollazione, 
                IstanzaFascicolo.tabs.elaborazione, 
            ];
            /* if (tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010 ||
                tipoProcedimento == TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004 ||
                tipoGruppo === GroupType.Regione)
            {
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.relazioneEnte);
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.parere);
            } */
            if (!pratica.showVCL || tipoGruppo === GroupType.Regione)
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.verbale);
            if ([GroupType.CommissioneLocale].includes(tipoGruppo))
                obj.forbiddenRoutes.push(IstanzaFascicolo.tabs.trasmissione);    
            break;
        case StatoEnum.Sospesa:
        case StatoEnum.Archiviata:
            obj.forbiddenRoutes =
            [
                IstanzaFascicolo.tabs.protocollazione,
                IstanzaFascicolo.tabs.elaborazione,
                IstanzaFascicolo.tabs.relazioneEnte,
                IstanzaFascicolo.tabs.parere,
                IstanzaFascicolo.tabs.verbale,
                IstanzaFascicolo.tabs.ulterioreDocumentazione,
                IstanzaFascicolo.tabs.trasmissione
            ];
            break;
    }
    return obj;
}

/**
 * @description Metodo che restituisce un booleano, il quale indica se un utente appartenente alla tipologia 
 * di gruppo passata in input al metodo può operare in scrittura o meno su un determinato path
 * @param groupType Gruppo dell'utente loggato
 * @param path Percorso per cui si vuole capire i permessi dell'utente, questi possono essere: 
 *      - protocollazione: "istanza-protocollazione",
 *      - presentata: "istanza-presentata",
 *      - localizzazione: "istanza-localizzazione",
 *      - verbale: "istanza-verbale",
 *      - relazioneEnte: "istanza-relazione-ente",
 *      - parere: "istanza-parere",
 *      - comunicazioni: "istanza-comunicazioni",
 *      - ulterioreDocumentazione: "istanza-documentazione",
 *      - elaborazione: "istanza-passa",
 *      - trasmissione: "trasmissione-provvedimento-finale"
 * @param statoPratica stato attuale della pratica
 * @returns true se ha permessi in lettura, false altrimenti. Torna null nel caso in cui venga passato un path invalido
 */
export function canOperate(groupType: GroupType, path: string, statoPratica: StatoEnum): boolean|null
{
    let _canOperate: boolean = null;
    let blockedStatus = [StatoEnum.compilaDomanda, StatoEnum.GeneraStampaDomanda,
                         StatoEnum.AllegaDocumentiSottoscritti,
                         StatoEnum.Archiviata, StatoEnum.Sospesa,
                         StatoEnum.PresaInCarica, StatoEnum.Trasmessa];
    if(!blockedStatus.includes(statoPratica))
    {
        switch (path)
        {
            case IstanzaFascicolo.tabs.protocollazione:
                //Posso operare solo se la pratica è in attesa di protocollazione e sono un ente delegato o regione
                _canOperate = statoPratica === StatoEnum.InAttesaDiProtocollazione && 
                              [GroupType.Regione, GroupType.EnteDelegato].includes(groupType);
                break;
            case IstanzaFascicolo.tabs.relazioneEnte:
            case IstanzaFascicolo.tabs.elaborazione:
            case IstanzaFascicolo.tabs.trasmissione:
                //Posso operare solo se la pratica è in lavorazione e sono un ente delegato o regione
                _canOperate = statoPratica === StatoEnum.InLavorazione &&
                              [GroupType.Regione, GroupType.EnteDelegato].includes(groupType);
                break;
            case IstanzaFascicolo.tabs.parere:
                //Posso operare solo se la pratica è in lavorazione e sono un ente delegato, 
                //regione o una soprintendenza
                _canOperate = statoPratica === StatoEnum.InLavorazione &&
                              [GroupType.Regione, GroupType.EnteDelegato, GroupType.Soprintendenza].includes(groupType);
                break;
            case IstanzaFascicolo.tabs.presentata:
            case IstanzaFascicolo.tabs.localizzazione:
            case IstanzaFascicolo.tabs.verbale:
                //Tab di sola lettura
                _canOperate = false;
                break;
            case IstanzaFascicolo.tabs.comunicazioni:
            case IstanzaFascicolo.tabs.ulterioreDocumentazione:
                //Posso operare solamente se la pratica è in lavorazione o in trasmissione a prescindere
                //dal mio gruppo
                _canOperate = [StatoEnum.InLavorazione, StatoEnum.InTrasmissione].includes(statoPratica);
                break;
        }
    }
    return _canOperate;
}

/**
 * Metodo di debug
 * @param form 
 * @param formName 
 */
export function printFormErrors(form: FormGroup, formName: string): void
{
    if (form)
    {
        Object.keys(form.controls).forEach(key =>
        {
            if (form.get(key).errors)
                console.log("Form group: ", formName, " - Campo ", key, " Errato: ", form.get(key).errors);
            if (form.get(key) instanceof FormGroup)
                printFormErrors(form.get(key) as FormGroup, key);
        });
    }

}