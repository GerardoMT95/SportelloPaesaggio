import { PlainTypeStringId } from './../components/model/logged-user';
import { Ente, ContainerInitSearch } from './../models/models';
import { Injectable } from '@angular/core';
import { combineLatest, Observable } from 'rxjs';
import { SelectOption } from '../components/select-field/select-field.component';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { map, filter } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { UlterioriContestiPaesaggistici, ConfigOption, HierarchicalOption, TipiEQualificazioni, TipoContenuto } from '../models/models';
import { isNullOrUndefined } from 'util';
import { copyOf } from 'src/app/core/functions/generic.utils';
import { fromUCPToHierarchicalStruct, fromUCPToHierarchicalOption, fromTeQtoConfigOption, fromTeQToHierarchicalOption } from '../functions/ObjectUtils';
import { CONST } from '../constants';
import { SportelloConfigBean } from '../components/model/model';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';

@Injectable({
  providedIn: 'root'
})
export class HttpDominioService {
  constructor(private httpClient: HttpClient) {}

  private baseUrl: string = CONST.WEB_RESOURCE_BASE_URL;
  private TIPI_QUALIFICAZIONI: string = this.baseUrl + "/dominio/qualificazioni";
  private ULTERIORI_CONTESTI_PAESAGGISTICI: string = this.baseUrl + "/dominio/ulterioriContestiPaesag";
  private URL_COMUNI: string = this.baseUrl + "/dominio/comuniOrganizzazione";
  private URL_ENTI: string = this.baseUrl + "/dominio/entiDelegati";
  private URL_POPULATE: string = this.baseUrl + "/dominio/populate";
  private GET_TIPO_ORG_ENTE_DELEGATO: string = this.baseUrl + "/dominio/tipo_org_ente_delegato";
  private GET_INDIRIZZI_MAIL_DEFAULT= this.baseUrl + "/dominio/indirizziMailDefault";

  public getComuni(): Observable<BaseResponse<PlainTypeStringId[]>>
  {
    return this.httpClient.get<BaseResponse<PlainTypeStringId[]>>(this.URL_COMUNI);
  }

  public getEnti(): Observable<BaseResponse<PlainTypeStringId[]>>
  {
    return this.httpClient.get<BaseResponse<PlainTypeStringId[]>>(this.URL_ENTI);
  }

  public callPopulateSearch(): Observable<BaseResponse<ContainerInitSearch>>
  {
    return this.httpClient.get<BaseResponse<ContainerInitSearch>>(this.URL_POPULATE);
  }

  /**
   * @description Chiamata al BE per ottenere una lista di ulteriori contesti paesaggistici
   * @param date parametro opzionale nel caso in cui si voglia ottenere i dati di un particolare momento storico
   */
  public findUlterioriContestiPaesaggistici(idTipoProcedimento: number, date?: Date, sezione?: string): Observable<BaseResponse<UlterioriContestiPaesaggistici[]>>
  {
    let httpParams: HttpParams = new HttpParams().set("idTipoProcedimento", idTipoProcedimento.toString());
    if (!isNullOrUndefined(date))
      httpParams = httpParams.append("dataPratica", date.toDateString());
    if (!isNullOrUndefined(sezione))
      httpParams = httpParams.append("sezione", sezione);
    let params = { params: httpParams };
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
  public getTipiContenuto(tipoProcedimento: number): Observable<TipoContenuto[]>
  {
    let urlGet = `${this.baseUrl}/dominio/tipi_contenuto.pjson`
    let params = { params: new HttpParams().set("tipoProcedimento", tipoProcedimento.toString()) };
    return this.httpClient
      .get<BaseResponse<TipoContenuto[]>>(urlGet, params)
      .pipe(
        map((response: BaseResponse<TipoContenuto[]>) =>
        {
          return response.payload;
        })
      );
  }

  getTipiProcedimento(): Observable<SelectOption[]>
  {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_procedimento.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getEnteDelegato(): Observable<SelectOption[]>
  {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/enti_riceventi.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getTipiDocumentoIdentita(): Observable<SelectOption[]>
  {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_documento_identita.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getTipiRuoloDitta(): Observable<SelectOption[]>
  {
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/tipi_ruolo_ditta.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getTitolaritaInQualitaDi(altroTit: boolean): Observable<SelectOption[]>
  {
    let altro = altroTit ? '_altro_titolare' : '';
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/titolarita_in_qualita_di${altro}.pjson`)
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getBpParchi(codIstat: string): Observable<SelectOption[]>
  {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/bp_parchi_e_riserve.pjson`, { params: httpParams })
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getUcpPaesaggi(codIstat: string): Observable<SelectOption[]>
  {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/ucp_paesaggi.pjson`, { params: httpParams })
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }
  getBpImmobili(codIstat: string): Observable<SelectOption[]>
  {
    const httpParams = new HttpParams().set('codIstat', codIstat);
    return this.httpClient
      .get<BaseResponse<SelectOption[]>>(`${this.baseUrl}/dominio/bp_immobili.pjson`, { params: httpParams })
      .pipe(
        map((response: BaseResponse<SelectOption[]>) =>
        {
          return response.payload;
        })
      );
  }

  getDominio(dominioType: string): Observable<SelectOption[]>
  {
    const httpParams = new HttpParams().set('dominioType', dominioType);
    switch (dominioType)
    {
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

  getAllDominio()
  {
    return combineLatest([this.getTipiProcedimento(),
      this.getTipiDocumentoIdentita(),
      this.getTitolaritaInQualitaDi(true),
      ])
      .pipe(
        map(([tp,tdocid,titF])=>{ return {response:
          {
          typeProcedimento:tp,
          tipoDocumento:tdocid,
          titolaritaInQualitaDi:titF,
        }}; }));
    /*return this.httpClient
      .get<{ [key: string]: SelectOption[] }>('http://localhost:8080/dominio')
      .pipe(
        map((response: { [key: string]: SelectOption[] }) => response)
      );*/
  }

  /**ESRI */
  /**
   * rimozione esri edit layer delle geometrie con oid(s) in input
   */
  public deleteOid(oid: any): Promise<number>
  {
    var headers = { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }) };
    return this.httpClient
      .post(CONST.EDIT_LAYER + '/applyEdits', "f=json&deletes=" + oid, headers)
      .toPromise()
      .then(response =>
      {
        return (<BaseResponse<number>>response).payload;
      });
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

/**
 * @param id ente delegato
 * @returns 
 */
  public getTipoOrgEnteDelegato(id: string): Observable<BaseResponse<string>>{
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("id", id);
    return this.httpClient.get<BaseResponse<string>>(this.GET_TIPO_ORG_ENTE_DELEGATO, {params:parameters});
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

}


