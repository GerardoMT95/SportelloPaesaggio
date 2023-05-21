import { PraticaDelegato } from "src/app/shared/models/models";

export interface CambioOwnershipRequest{
    codicePratica:string;
    codiceFiscaleProponente:string;
    codiceSegreto:string;
}
export interface Subentro extends PraticaDelegato{
	fileNameModulo?:string;
	fileNameSollevamento?:string;
	hashModulo?:string;
	hashSollevamento?:string;

}
export interface CambioOwnershipResponse{
    esito:boolean;
    sollevamentoIncarico:boolean;
    subentro:Subentro;
}