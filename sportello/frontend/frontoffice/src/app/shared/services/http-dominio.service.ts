import { InfoAzienda, InfoEnte, SportelloConfigBean } from './../components/model/model';
import { UlterioriContestiPaesaggistici, TipoContenuto, IpaEnte } from './../models/models';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SelectOption } from '../components/select-field/select-field.component';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { map} from 'rxjs/operators';
import { BaseResponse } from '../components/model/base-response';
import { TipiEQualificazioni, ConfigOption, HierarchicalOption } from '../models/models';
import { copyOf } from '../functions/generic.utils';
import { fromTeQtoConfigOption, fromTeQToHierarchicalOption, fromUCPToHierarchicalStruct, fromUCPToHierarchicalOption } from '../functions/ObjectUtils';
import { isNullOrUndefined } from 'util';
import { CONST } from '../constants';
import { DestinatarioComunicazioneDTO } from 'src/app/features/interfaccia-di-riepilogo-del-fascicolo/models/corrispondenza.model';

@Injectable({
  providedIn: 'root'
})
export class HttpDominioService {
  
  constructor(private httpClient: HttpClient) {}
  private baseUrl: string = CONST.WEB_RESOURCE_BASE_URL;
  private TIPI_QUALIFICAZIONI: string = this.baseUrl + "/dominio/qualificazioni";
  private ULTERIORI_CONTESTI_PAESAGGISTICI: string = this.baseUrl + "/dominio/ulterioriContestiPaesag";
  private SEZIONI_CATASTALI: string = this.baseUrl + "/dominio/sezioniCatastali";

  private URL_RECUPERA_IMPRESA: string = this.baseUrl + "/Fascicolo/recupera-impresa/"
  private URL_RECUPERA_ENTE: string = this.baseUrl + "/Fascicolo/recupera-ente/"
  private URL_TIPO_AZIENDA: string = this.baseUrl + "/public/lista-tipo-azienda";
  private GET_INDIRIZZI_MAIL_DEFAULT= this.baseUrl + "/dominio/indirizziMailDefault";
  private GET_TIPO_ENTE= this.baseUrl + "/dominio/tipo_org_ente_delegato";

  /**
   * @description Chiamata al BE per ottenere una lista di ulteriori contesti paesaggistici
   * @param date parametro opzionale nel caso in cui si voglia ottenere i dati di un particolare momento storico
   */
  public findUlterioriContestiPaesaggistici(idTipoProcedimento: number, date?: Date, sezione?: string): Observable<BaseResponse<UlterioriContestiPaesaggistici[]>>
  {
    let httpParams: HttpParams = new HttpParams().set("idTipoProcedimento", idTipoProcedimento.toString());
    if(!isNullOrUndefined(date))
      httpParams = httpParams.append("dataPratica", date.toDateString());
    if (!isNullOrUndefined(sezione))
      httpParams = httpParams.append("sezione", sezione);
    let params = {params: httpParams};
    return this.httpClient.get<BaseResponse<UlterioriContestiPaesaggistici[]>>(this.ULTERIORI_CONTESTI_PAESAGGISTICI, params);
  }

  /**
   * @description Richiama il metodo findUlterioriContestiPaesaggistici per poi mappare il payload di ritorno
   * come un array di ConfigOption usati per buildare la pagina
   * @param tipoProcedimento 
   */
  public findUlterioriContestiPaesaggisticiHierarchical(idTipoProcedimento: number, date?: Date, sezione?: string): Observable<BaseResponse<ConfigOption[]>>
  {
    return this.findUlterioriContestiPaesaggistici(idTipoProcedimento, date, sezione).pipe(map(response =>
    {
      let myResponse: BaseResponse<ConfigOption[]> = copyOf(response, ["payload"]);
      if (response.payload)
        myResponse.payload = fromUCPToHierarchicalStruct(response.payload);
      return myResponse;
    }));
  }

  /**
   * @description Richiama il metodo findUlterioriContestiPaesaggistici per poi mappare il payload di ritorno
   * come un oggetto HierarchicalOption
   * @param tipoProcedimento 
   */
  public findUlterioriContestiPaesaggisticiHierarchical2(idTipoProcedimento: number, sezione: string, date?: Date): Observable<BaseResponse<HierarchicalOption>>
  {
    return this.findUlterioriContestiPaesaggistici(idTipoProcedimento, date, sezione).pipe(map(response =>
    {
      let myResponse: BaseResponse<HierarchicalOption> = copyOf(response, ["payload"]);
      if (response.payload)
        myResponse.payload = fromUCPToHierarchicalOption(response.payload);
      return myResponse;
    }));
  }

  /**
   * @description Chiamata al BE per ottenere tutte i tipi di intervento, le caratterizzazioni e qualificazioni
   * che definisco il tab "descrizione" di scheda tecnica sulla base di un dato tipo di procedimento
   * @param tipoProcedimento 
   */
  public findTipiEQualificazioni(tipoProcedimento: number): Observable<BaseResponse<TipiEQualificazioni[]>>
  {
    let params = { params: new HttpParams().set("tipoProcedimento", tipoProcedimento.toString()) };
    return this.httpClient.get<BaseResponse<TipiEQualificazioni[]>>(this.TIPI_QUALIFICAZIONI, params);
  }

  /**
   * @description Richiama il metodo findTipiEQualificazioni per poi mappare il payload di ritorno
   * come un array di ConfigOption usati per buildare la pagina descrizione
   * @param tipoProcedimento 
   */
  public findTipiEQualificazioniHasOption(tipoProcedimento: number): Observable<BaseResponse<ConfigOption[]>>
  {
    return this.findTipiEQualificazioni(tipoProcedimento).pipe(
      map(response => 
      {
        let myResponse: BaseResponse<ConfigOption[]> = copyOf(response, ["payload"]);
        if (response.payload)
          myResponse.payload = fromTeQtoConfigOption(response.payload);
        return myResponse;
      })
    );
  }

  /**
   * @description Richiama il metodo findTipiEQualificazioni per poi mappare il payload di ritorno
   * come un array di HierarchicalOption usati per buildare la pagina descrizione
   * @param tipoProcedimento 
   */
  public findTipiEQualificazioniHasOptionHierarchical(tipoProcedimento: number): Observable<BaseResponse<HierarchicalOption[]>>
  {
    return this.findTipiEQualificazioni(tipoProcedimento).pipe(
      map(response => 
      {
        let myResponse: BaseResponse<HierarchicalOption[]> = copyOf(response, ["payload"]);
        if (response.payload)
          myResponse.payload = fromTeQToHierarchicalOption(response.payload);
        return myResponse;
      })
    );
  }

  /**
   * @description effettua il retrieval dei tipi di contenuto (tipi di allegati) previsti per il tipo procedimento in questione
   * nella scheda Allegati (Documentazione amministrativa D , Documentazione Amministrativa E, Documentazione Tecnica)
   * @param tipoProcedimento 
   */
  public getTipiContenuto(tipoProcedimento: number): Observable<TipoContenuto[]>{
    let urlGet=`${this.baseUrl}/dominio/tipi_contenuto.pjson`
    let params = { params: new HttpParams().set("tipoProcedimento", tipoProcedimento.toString()) };
    return this.httpClient
      .get<BaseResponse<TipoContenuto[]>>(urlGet,params)
      .pipe(
        map((response: BaseResponse<TipoContenuto[]>) => {
          return response.payload;
        })
      );
  }

  /**
   * 
   * @param all true anche per i non attivi
   * @returns 
   */
  getTipiProcedimento(all?:boolean): Observable<SelectOption[]> {
    let params ={ params: new HttpParams()};
    if(all){
      params.params=
        params.params.set("ancheInattivi", "true"); 
    }
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_procedimento.pjson`,params)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        }));
  }

  getEnteDelegato(): Observable<SelectOption[]> {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/enti_riceventi.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  /**
   * 
   * @returns anche quelli storici
   */
  getAllEnteDelegato(): Observable<SelectOption[]> {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/enti_riceventi_all.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getTipiDocumentoIdentita(): Observable<SelectOption[]> {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_documento_identita.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getTipiRuoloDitta(): Observable<SelectOption[]> {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_ruolo_ditta.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getTitolaritaInQualitaDi(altroTit:boolean): Observable<SelectOption[]> {
    let altro=altroTit?'_altro_titolare':'';
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/titolarita_in_qualita_di${altro}.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getBpParchi(codIstat:string): Observable<SelectOption[]> {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/bp_parchi_e_riserve.pjson`,{params: httpParams})
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getUcpPaesaggi(codIstat:string): Observable<SelectOption[]> {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/ucp_paesaggi.pjson`,{params: httpParams})
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }
  getBpImmobili(codIstat:string): Observable<SelectOption[]> {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/bp_immobili.pjson`,{params: httpParams})
      .pipe(
        map((response: BaseResponse<SelectOption[]>) => {
          return response.payload;
        })
      );
  }

  getDominio(dominioType: string): Observable<SelectOption[]> {
    const httpParams = new HttpParams().set('dominioType', dominioType);
    switch (dominioType) {
      //qui man mano che accendo il BE aggiungo il case...
      case 'typeProcedimento':
        return this.getTipiProcedimento();
      case 'enteDelegato':
        return this.getEnteDelegato();
      case 'tipoDocumento':
          return this.getTipiDocumentoIdentita();  
      case 'ruoloDitta':
          return this.getTipiRuoloDitta();  
      case 'titolaritaInQualitaDi':
          return this.getTitolaritaInQualitaDi(false);  
      case 'titolaritaInQualitaDiAltroTit':
          return this.getTitolaritaInQualitaDi(true);  
      default:
        break;
    }
    return this.httpClient
      .get<SelectOption[]>('http://localhost:8080/dominio', {
        params: httpParams
      })
      .pipe(
        /** TODO: Add some base response or something like that */
        map((response: SelectOption[]) => response)
      );
  }

  getAllDominio() {
    return this.httpClient
      .get<{ [key: string]: SelectOption[] }>('http://localhost:8080/dominio')
      .pipe(
        /** TODO: Add some base response or something like that */
        map((response: { [key: string]: SelectOption[] }) => response)
      );
  }

  /**ESRI */
  /**
   * rimozione esri edit layer delle geometrie con oid(s) in input
   */
  public deleteOid(oid:any):Promise<number>{
    var headers = { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })} ;  
    return this.httpClient
        .post(CONST.EDIT_LAYER+'/applyEdits',"f=json&deletes="+oid,headers)
        .toPromise()
        .then(response => {
          return (<BaseResponse<number>>response).payload;
        });
  }

/**
   * @description Chiamata al BE per ottenere le sezioni catastali che hanno una label associata es A662  B  => Carbonara
   * value=A662
   * linked=B
   * description=Carbonara
   */
 public getSezioniCatastali(): Observable<SelectOption[]>
 {
   return this.httpClient.get<BaseResponse<SelectOption[]>>(this.SEZIONI_CATASTALI)
   .pipe(
    map((response: BaseResponse<SelectOption[]>) => {
      return response.payload;
    })
  );
 }

 /**
  * 
  * @param codicePratica 
  * @returns 
  */
 public getDisclaimerPratica(codicePratica:string): Observable<SelectOption[]> {
  let parameters: HttpParams = new HttpParams();
  parameters = parameters.append("codicePratica", codicePratica);
  return this.httpClient
    .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/disclaimer_pratica.pjson`,{params:parameters})
    .pipe(
      map((response: BaseResponse<SelectOption[]>) => {
        return response.payload;
      })
    );
}

/**
 * ruoli pratica : PRoponente DElegato
 * @returns 
 */
getRuoloPratica(): Observable<SelectOption[]> {
  return this.httpClient
    .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/ruolo_pratica`)
    .pipe(
      map((response: BaseResponse<SelectOption[]>) => {
        return response.payload;
      })
    );
}

public recuperaImpresa(idPratica:string, id:string, codiceFiscale:string): Observable<BaseResponse<InfoAzienda>> {
  let httpParams:HttpParams = new HttpParams().set('codiceFiscale', codiceFiscale).set('id', id);
  return this.httpClient.get<BaseResponse<InfoAzienda>>(this.URL_RECUPERA_IMPRESA + idPratica, {params: httpParams});
}

public recuperaEnte(idPratica:string, id:string, codiceFiscale:string): Observable<BaseResponse<InfoEnte>> {
  let httpParams:HttpParams = new HttpParams().set('codiceFiscale', codiceFiscale).set('id', id);
  return this.httpClient.get<BaseResponse<InfoEnte>>(this.URL_RECUPERA_ENTE + idPratica, {params: httpParams});
}

getTipiAzienda(): Observable<BaseResponse<SelectOption[]>> {
  return this.httpClient.get<BaseResponse<SelectOption[]>>(this.URL_TIPO_AZIENDA);
}

  /**
   * configurazione sportello
   * @param id id pratica
   * @returns 
   */
  public getSportelloConfig(id:string): Observable<SportelloConfigBean>{
    let urlGet=`${this.baseUrl}/dominio/sportello_config`
    let params = { params: new HttpParams().set("id", id) };
    return this.httpClient
      .get<BaseResponse<SportelloConfigBean>>(urlGet,params)
      .pipe(
        map((response: BaseResponse<SportelloConfigBean>) => {
          return response.payload;
        })
      );
  }

  public autocompleteIpaEnte(query: string): Observable<BaseResponse<Array<IpaEnte>>>
  {
    let params: HttpParams = new HttpParams().set('param', query);
    return this.httpClient.get<BaseResponse<Array<IpaEnte>>>(`${this.baseUrl}/dominio/autocompleteEnte`, {params});
  }

  /**
  * @param id ente delegato
  * @returns 
  */
  public getIndirizziMailDefault(idEnte: string): Observable<BaseResponse<DestinatarioComunicazioneDTO[]>>{
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("id", idEnte);
    return this.httpClient.get<BaseResponse<DestinatarioComunicazioneDTO[]>>(this.GET_INDIRIZZI_MAIL_DEFAULT, {params:parameters});
  }
  
  /**
  * @param id ente delegato
  * @returns 
  */
   public getTipoEnteOrg(idEnte: string): Observable<BaseResponse<String>>{
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("id", idEnte);
    return this.httpClient.get<BaseResponse<String>>(this.GET_TIPO_ENTE, {params:parameters});
  }

}
