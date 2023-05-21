import { CorrispondenzaDTO ,DettaglioCorrispondenzaDTO} from "../../gestione-istanza-comunicazioni/model/corrispondenza.models";


export class RelazioneCorrispondenza
{
    idRelazione:number;
    tipoInvia?:string;
    idPratica?:string;
    corrispondenza: CorrispondenzaDTO;

}

export class RelazioneDettaglioCorrispondenza
{
    idRelazione:number;
    tipoInvia?:string;
    idPratica?:string;
    corrispondenza: DettaglioCorrispondenzaDTO;

}