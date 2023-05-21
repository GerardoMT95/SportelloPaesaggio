import { CONST } from './../constants';
import { PagamentiMypayDTO, PagamentiMypaySearch, PraticaDelegato, ValidInfoDto, WebContentDTO } from './../models/models';
import { Injectable } from '@angular/core';
import { Fascicolo,FascicoloList, FascicoloSearch, AltroTitolare } from 'src/app/shared/models/models';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { SelectOption } from '../components/select-field/select-field.component';
import { BaseResponse } from '../components/model/base-response';
import { BFile } from '../models/allegati.model';
import { toHttpParams } from '../functions/generic.utils';
import { PaginatedBaseResponse } from '../components/model/paginated-base-response';

@Injectable({
  providedIn: 'root'
})
export class FascicoloService {
   
  constructor(private httpClient: HttpClient) { }
  private baseUrl: string = CONST.WEB_RESOURCE_BASE_URL;

  removeFascicolo(fascicolo: Fascicolo): Promise<BaseResponse<number>> {
    return this.httpClient.post<BaseResponse<number>>(`${this.baseUrl}/Fascicolo/delete.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<number>>data));
  }

  updateFascicolo(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {

    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Fascicolo/update.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));

  }
  updateRichiedente(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {

    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Fascicolo/updateRichiedente`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));

  }
  cambioEnte(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {

    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Fascicolo/cambioEnte.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));

  }
  cambioTipoProcedimento(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {

    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Fascicolo/cambioTipoProcedimento.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));

  }

  getAllFiascicolo(searchObject?: FascicoloSearch): Observable<FascicoloList> {

    return this.httpClient.post<BaseResponse<FascicoloList>>(`${this.baseUrl}/Pratica/search.pjson`, searchObject).pipe(
      map((response: BaseResponse<FascicoloList>) => {
        return response.payload;
      })
    );

  }

  getFascicolo(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {

    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Fascicolo/find.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));

  }


  /*addFascicolo(fascicolo: Fascicolo): Promise<BaseResponse<Fascicolo>> {
    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Pratica/insert.pjson` ,fascicolo,{})
    .toPromise().then(data => (<BaseResponse<Fascicolo>>data));
  }*/

  public creaFascicolo(fascicolo: Fascicolo, delegato: PraticaDelegato, delega: File): Promise<BaseResponse<Fascicolo>>
  {
    let formData: FormData = new FormData();
    formData.append('data', new Blob([JSON.stringify(fascicolo)], { type: "application/json" }));
    if(delegato)
    {
      formData.append('delegato', new Blob([JSON.stringify(delegato)], { type: "application/json" }));
      formData.append('delega', delega);
    }
    return this.httpClient.post<BaseResponse<Fascicolo>>(`${this.baseUrl}/Pratica/creaPratica`, formData)
      .toPromise().then(data => (<BaseResponse<Fascicolo>>data));
  }


  creaTitolare(fascicolo: Fascicolo): Promise<BaseResponse<AltroTitolare>> {
    return this.httpClient.post<BaseResponse<AltroTitolare>>(`${this.baseUrl}/Fascicolo/crea_titolare.pjson`, fascicolo,{})
      .toPromise().then(data => (<BaseResponse<AltroTitolare>>data));

  }

  eliminaTitolare(param: SelectOption): Promise<BaseResponse<number>> {
    return this.httpClient.post<BaseResponse<number>>(`${this.baseUrl}/Fascicolo/elimina_titolare.pjson`, param,{})
      .toPromise().then(data => (<BaseResponse<number>>data));

  }

  public validaIstanza(codicePratica: string, idPratica: string): Observable<BaseResponse<boolean>> {
    let url: string = `${this.baseUrl}/Fascicolo/validaIstanza`;
    let params: HttpParams = new HttpParams();
    params = params.append("codicePratica", codicePratica);
    params = params.append("idPratica", idPratica);
    return this.httpClient.get<BaseResponse<boolean>>(url, {params: params});
  }

  public validaRichiedente(codicePratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = `${this.baseUrl}/Fascicolo/validaRichiedente`;
    let params: HttpParams = new HttpParams();
    params = params.append("codicePratica", codicePratica);
    return this.httpClient.get<BaseResponse<boolean>>(url, { params: params });
  }

  public validaAllegati(codicePratica: string, idPratica: string): Observable<BaseResponse<boolean>> {
    let url: string = `${this.baseUrl}/Fascicolo/validaAllegati`;
    let params: HttpParams = new HttpParams();
    params = params.append("codicePratica", codicePratica);
    params = params.append("idPratica", idPratica);
    return this.httpClient.get<BaseResponse<boolean>>(url, {params: params});
  }

  public validaAll(codicePratica: string, idPratica: string): Observable<BaseResponse<ValidInfoDto>> {
    let url: string = `${this.baseUrl}/Fascicolo/valida`;
    let params: HttpParams = new HttpParams();
    params = params.append("codicePratica", codicePratica);
    params = params.append("idPratica", idPratica);
    return this.httpClient.get<BaseResponse<ValidInfoDto>>(url, {params: params});
  }

  /**
   * public downloadFile(idAllegato: string): Observable<HttpResponse<Blob>> {
    let params: HttpParams = new HttpParams();
    params = params.append("idAllegato", idAllegato);
    return this.httpClient.get(this.DOWNLOAD, {
      params: params,
      observe: 'response',
      responseType: 'blob',
    });
  }
   */

  public downloadDomandaIstanza(codicePratica: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/downloadDomandaIstanza";
    return this.httpClient.get(url, {
      params: new HttpParams().set("codiceFascicolo", codicePratica),
      observe: 'response',
      responseType: 'blob'
    });
  }

  public downloadDomandaSchedaTecnica(codicePratica: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/downloadDomandaDichiarazioneTecnica";
    return this.httpClient.get(url, {
      params: new HttpParams().set("codiceFascicolo", codicePratica),
      observe: 'response',
      responseType: 'blob'
    });
  }

  public getMetadatiDichiarazioni(idPratica: string): Observable<BaseResponse<BFile[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/getDichiarazioni";
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.httpClient.get<BaseResponse<BFile[]>>(url, params);
  }

  public passaAGeneraStampaDomanda(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/generaStampaDomanda";
    let params = {params: new HttpParams().set("idPratica", idPratica)};
    return this.httpClient.get<BaseResponse<boolean>>(url, params);
  }

  public passaAllegaDocumentiGenerati(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/allegaDocumentiGenerati";
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.httpClient.get<BaseResponse<boolean>>(url, params);
  }

  public passaAInProtocollazione(idPratica: string, codicePratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/presentaIstanza";
    let params = { params: new HttpParams().set("idPratica", idPratica).set("codicePratica", codicePratica) };
    return this.httpClient.get<BaseResponse<boolean>>(url, params);
  }

  public backToCompilazioneIstanza(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/compilaDomanda";
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.httpClient.put<BaseResponse<boolean>>(url, null, params);
  }

  public presaVisione(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/presaVisione";
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.httpClient.get<BaseResponse<boolean>>(url,  params);
  }
  
  public effettuaPagamento(idPratica: string,causale:string,importo:number,retUrl:string): Observable<BaseResponse<PagamentiMypayDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/effettuaPagamento";
    let params = { params: new HttpParams()
      .set("idPratica", idPratica)
      .set("importo",importo+'')
      .set("causale", causale)
      .set("retUrl", retUrl) };
    return this.httpClient.get<BaseResponse<PagamentiMypayDTO>>(url, params);
  }
 
  public searchPagamenti(pagSearch:PagamentiMypaySearch): Observable<BaseResponse<PaginatedBaseResponse<PagamentiMypayDTO>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/Fascicolo/searchPagamenti`;
    return this.httpClient.get<BaseResponse<PaginatedBaseResponse<PagamentiMypayDTO>>>(url, { params: toHttpParams(pagSearch) });
  }

  /**
   * almeno uno delle due chiavi deve essere avvalorata
   * @param praticaId 
   * @param codicePratica 
   * restituisce una mappa stato,importo  es.:
   	 {
      "KO": 2.09,
      "INCORSO":0.01,
		  "OK": 2
	    }
  */
  public totPagamentiPerStato(praticaId?:string,codicePratica?:string):Observable<BaseResponse<{'OK'?:number,'KO'?:number,'INCORSO'?:number}>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/Fascicolo/totPagamentiPerStato`;
    let parms=null;
    if(praticaId){
      parms=new HttpParams().set('praticaId',praticaId);
    }else if(codicePratica){
      parms=new HttpParams().set('codicePratica',codicePratica);
    }
    return this.httpClient.get<BaseResponse<{'OK'?:number,'KO'?:number,'INCORSO'?:number}>>(url, { params: parms });
  }

  // true se ha una configurazione automatica per il servizio di pagamenti 
  public hasPagamentoIntegrato(idOrganizzazione:number): Observable<BaseResponse<boolean>> {
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idOrganizzazione", idOrganizzazione+"");
    return this.httpClient.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL+'/configurazioniEnte/hasPagamenti',{ params: parameters });
  }

  //messaggio di creazione nuovo fascicolo
  public getMessaggio(messaggioPk:WebContentDTO): Observable<BaseResponse<WebContentDTO>> {
    return this.httpClient.post<BaseResponse<WebContentDTO>>(CONST.WEB_RESOURCE_BASE_URL+'/WebContentConfigurabili/find.pjson',messaggioPk);
  }


  /**
   * Aggiorna i flag di esonero pagamenti e bollo
   */
  public updateEsoneriPagamento(id:string,esoneroBollo:boolean,esoneroOneri:boolean): Promise<BaseResponse<number>> {

    return this.httpClient.put<BaseResponse<number>>(`${this.baseUrl}/Fascicolo/esoneri/`+id, 
    {
      esoneroBollo:esoneroBollo,
      esoneroOneri:esoneroOneri
    },
    {})
      .toPromise().then(data => (<BaseResponse<number>>data));

  }

  public getLastDelegato(): Observable<BaseResponse<PraticaDelegato>>
  {
    return this.httpClient.get<BaseResponse<PraticaDelegato>>(`${this.baseUrl}/Fascicolo/lastDelegato`);
  }

}
