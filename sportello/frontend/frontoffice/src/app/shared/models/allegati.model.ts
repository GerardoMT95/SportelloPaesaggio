
/**
 * @description Interfaccia generica per le tipologie di documento accettate in allegati-form
 */
export interface DocumentType
{
    type: any;
    label: string;
    multiple: boolean;
    required: boolean;
    accept?: string[];
    maxSize?: number;
}
/**
 * export class DocumentType
{
    private _type: any;
    private _label: string;
    private _multiple: boolean;
    private _accept?: string[];
    private _maxSize?: number;

    get type(): any { return this._type; }
    set type(type: any) { this._type = type; }

    get label(): string { return this._label; }
    set label(label: string) {  this._label = label; }

    get multiple(): boolean { return this._multiple; }
    set multiple(multiple: boolean) { this._multiple = multiple; }

    get accept(): string[] { return this._accept; }
    set accept(accept: string[]) { this._accept = accept; }

    get maxSize(): number {return this._maxSize; }
    set maxSize(maxSize: number) { this._maxSize = maxSize; }
}
 */

/**
 * @description classe astratta 
 */
export class BasicFile
{
    public id?: any;
    public type: any;
    public labelType: string;
    public name: string;
    public uploadDate: Date;
    public path?: string;
    public size?: number;
    public praticaId?: string;
    public descrizione?: string;
    public idIntegrazione?: number;
    public nome: string;
    public data: Date;
    //adeguamento....
    public formatoFile: string;
    public nomeFile?: string;
    public dataCaricamento?: Date;
    public pathCms?: string;
    public idCms: string;
    public checksum?: string;
    public isSigned?: boolean;
    
}

export class BFile extends BasicFile{} 