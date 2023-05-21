import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { toHttpParams } from 'src/app/core/functions/generic.utils';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { CONST } from 'src/app/shared/constants';
import { Allegato } from 'src/app/shared/models/models';
import { CorrispondenzaSearch } from '../../funzionalita-amministratore-applicazione/models/admin-functions.models';
import { RespDettaglio } from '../model/corrispondenza.models';
import { AllegatoCorrispondenza, DettaglioCorrispondenzaDTO } from './../model/corrispondenza.models';
import { TipologicaDTO } from './../model/tipologica';

@Injectable({
  providedIn: 'root'
})
export class GestioneIstanzaComunicazioniService {

  constructor(private httpClient: HttpClient) { }

  public sendComunicazione(idPratica:string,corrispondenzaDTO: DettaglioCorrispondenzaDTO,withProto:boolean): Observable<BaseResponse<boolean>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/send";
    let body = new HttpParams();
    body = body.set('idCorrispondenza', corrispondenzaDTO.corrispondenza.id+"");
    body = body.set('idPratica', idPratica);
    body = body.set('withProto', withProto+'');
    return this.httpClient.post<BaseResponse<boolean>>(url, body);
  }

  public retrieveComunicazioni(corrSearch:CorrispondenzaSearch): Observable<BaseResponse<RespDettaglio>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/comunicazioni/search`;
    //let parameters: HttpParams = new HttpParams();
    //parameters = parameters.append("idFascicolo", idFascicolo.toString());
    return this.httpClient.get<BaseResponse<RespDettaglio>>(url, { params: toHttpParams(corrSearch) });
  }

  public creaBozza(idFascicolo: string): Observable<BaseResponse<DettaglioCorrispondenzaDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/create";
    let body = new HttpParams();
    body = body.set('idPratica', idFascicolo);
    return this.httpClient.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url,body);
  }

  public salvaBozza(idPratica:string,corrispondenzaDTO: DettaglioCorrispondenzaDTO): Observable<BaseResponse<DettaglioCorrispondenzaDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/update/"+idPratica;
    return this.httpClient.put<BaseResponse<DettaglioCorrispondenzaDTO>>(url, corrispondenzaDTO);
  }

  public retrieveComunicazione(idComunicazione: number, idFascicolo?: string): Observable<BaseResponse<DettaglioCorrispondenzaDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/comunicazioni/find`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idPratica", idFascicolo);
    parameters = parameters.append("idComunicazione", idComunicazione.toString());
    return this.httpClient.get<BaseResponse<DettaglioCorrispondenzaDTO>>(url, {params: parameters});
  }

  /*
  public uploadFile(container: any, idFascicolo: string, codiceFascicolo: string): Observable<BaseResponse<DettaglioCorrispondenzaDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/upload";
    let body = {
      container: container,
      idFascicolo: idFascicolo,
      codiceFascicolo: codiceFascicolo
    }
    return this.httpClient.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url, body);
  }*/

  public upload(file: File, idPratica: string, idCorrispondenza: string): Observable<BaseResponse<Allegato>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/uploadAndGetDetail";
    let formData = new FormData();
    formData.append('file', file);
    let params = {params: new HttpParams().set('idPratica', idPratica).set('idCorrispondenza', idCorrispondenza.toString())}
    return this.httpClient.post<BaseResponse<Allegato>>(url, formData, params);
  }
  
  /**
   * viene effettuato un check sulla competenza della pratica... 
   * @param idAllegato 
   * @param idPratica 
   */
  public downloadAllegato(idAllegato: string,idPratica:string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/download";
    let params=new HttpParams().set('idAllegato', idAllegato);
    params=params.set('idPratica',idPratica);
    return this.httpClient.get(url, { observe: 'response', responseType: 'blob', params: params });
  }

  public anteprimaBozza(idPratica:string,corrispondenzaDTO: DettaglioCorrispondenzaDTO): Observable<HttpResponse<Blob>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/anteprima/";
    let params=new HttpParams().set('id', corrispondenzaDTO.corrispondenza.id+'');
    params=params.set('idPratica',idPratica);
    return this.httpClient.get(url, { observe: 'response', responseType: 'blob', params: params });
  }

  /**
   * viene effettuato un check sulla competenza della pratica... 
   * @param idAllegato 
   * @param idPratica 
   */
  public downloadRicevuta(idCms: string,idPratica:string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/download_ricevuta";
    let params=new HttpParams().set('idCms', idCms);
    params=params.set('idPratica',idPratica);
    return this.httpClient.get(url, { observe: 'response', responseType: 'blob', params: params });
  }

  public eliminaBozza(idComunicazione: number, idFascicolo: string): Observable<BaseResponse<DettaglioCorrispondenzaDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/comunicazioni/delete`;
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idPratica", idFascicolo);
    parameters = parameters.append("idCorrispondenza", idComunicazione.toString());
    return this.httpClient.delete<BaseResponse<DettaglioCorrispondenzaDTO>>(url, {params: parameters});
  }

  public deleteFile(idPratica: string, idAllegato: string): Observable<BaseResponse<AllegatoCorrispondenza>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/remove";
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idAllegato", idAllegato);
    parameters = parameters.append("idPratica", idPratica);
    return this.httpClient.delete<BaseResponse<AllegatoCorrispondenza>>(url, {params: parameters});
  }

  public ricerca(container: any): Observable<BaseResponse<DettaglioCorrispondenzaDTO[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/comunicazioni/ricerca";
    return this.httpClient.post<BaseResponse<DettaglioCorrispondenzaDTO[]>>(url, container);
  }

}
