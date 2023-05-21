import { Fascicolo, TipoAllegatoCL, SimpleFileCommissioneLocale } from './../../models/models';
import { populateHttpParams } from 'src/app/features/gestione-istanza-comunicazioni/generic-function/generic-function';
import { SedutaCommissioneLocaleSearch } from './../../models/search.models';
import { HttpParams, HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CONST } from 'src/app/shared/constants';
import { Injectable } from '@angular/core';
import { BaseResponse, PaginatedList } from 'src/app/core/models/basic.models';
import { FileCommissioneLocale, SedutaDiCommissione } from '../../models/models';

@Injectable({
  providedIn: 'root'
})
export class SedutaCommissioneService
{
  private BASE_URL = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/sedutaCommissione";

  private URL_FIND     = this.BASE_URL + "/find";
  private URL_SEARCH   = this.BASE_URL + "/search";
  private URL_INSERT   = this.BASE_URL + "/insert";
  private URL_UPDATE   = this.BASE_URL + "/update";
  private URL_DELETE   = this.BASE_URL + "/delete";
  private URL_UPLOAD   = this.BASE_URL + "/remove";
  private URL_REMOVE   = this.BASE_URL + "/remove";
  private URL_DOWNLOAD = this.BASE_URL + "/download";
  private URL_PUBLIC   = this.BASE_URL + "/pubblica";
  private URL_PRATICHE = this.BASE_URL + "/searchPraticheAssociate";
  private URL_SEARCH_A = this.BASE_URL + "/searchAttachments";
  private URL_SEARCH_P = this.BASE_URL + "/searchPraticheEsaminabili";

  constructor(private http: HttpClient) { }

  /**
   * @description Realizzo la chiamata al BE per ottenere il dettaglio di un seduta di commissione
   * @param idSeduta 
   */
  public callFindSeduta(idSeduta: number): Observable<BaseResponse<SedutaDiCommissione>>
  {
    let params = {params: new HttpParams().set('idSeduta', idSeduta.toString())};
    return this.http.get<BaseResponse<SedutaDiCommissione>>(this.URL_FIND, params);
  }
  /**
   * @description Realizzo la chiamata al BE per fare una ricerca sulle sedute
   * @param filters 
   */
  public callSearchSedute(filters: SedutaCommissioneLocaleSearch): Observable<BaseResponse<PaginatedList<SedutaDiCommissione>>>
  {
    let params = {params: populateHttpParams(filters)};
    return this.http.get<BaseResponse<PaginatedList<SedutaDiCommissione>>>(this.URL_SEARCH, params);
  }
  /**
   * @description Realizzo la chiamata al BE inserire una nuova seduta di commissione
   * @param body
   */
  public callInsertSeduta(body: SedutaDiCommissione): Observable<BaseResponse<SedutaDiCommissione>>
  {
    return this.http.post<BaseResponse<SedutaDiCommissione>>(this.URL_INSERT, body);
  }
  /**
   * @description Realizzo la chiamata al BE per aggiornare una seduta di commissione esistente
   * @param body
   */
  public callUpdateSeduta(body: SedutaDiCommissione): Observable<BaseResponse<SedutaDiCommissione>>
  {
    return this.http.put<BaseResponse<SedutaDiCommissione>>(this.URL_UPDATE, body);
  }
  /**
   * @description Realizzo la chiamata al BE revocare una seduta di commissione
   * @param idSeduta 
   */
  public callDeleteSeduta(idSeduta: number): Observable<BaseResponse<number>>
  {
    let params = {params: new HttpParams().set('idSeduta', idSeduta.toString())};
    return this.http.delete<BaseResponse<number>>(this.URL_DELETE, params);
  }
  /**
   * @description Realizzo la chiamata al BE per effettuare l'upload di un allegato con i suoi metadati
   * @param file 
   * @param idSeduta 
   * @param tipoAllegato 
   * @param praticheAssociate 
   */
  public callUploadAllegatoSeduta(file: File, idSeduta: number, tipoAllegato: TipoAllegatoCL, praticheAssociate: Array<string>): Observable<BaseResponse<FileCommissioneLocale>>
  {
    let body = {idSeduta, tipoAllegato, praticheAssociate};
    let formData = new FormData();
    formData.append("file", file);
    formData.append("body", new Blob([JSON.stringify(body)], {type: 'application/json'}));
    return this.http.post<BaseResponse<FileCommissioneLocale>>(this.URL_UPLOAD, formData);
  }
  /**
   * @description Realizzo la chiamata al BE per eliminare un documento associato ad una seduta di commissione
   * @param idAllegato 
   * @param idSeduta
   */
  public callRemoveAllegatoSeduta(idAllegato: string, idSeduta:number): Observable<BaseResponse<number>>
  {
    let params = { params: new HttpParams().set('idAllegato', idAllegato).set('idSeduta', idSeduta.toString())};
    return this.http.delete<BaseResponse<number>>(this.URL_REMOVE, params);
  }
  /**
   * Effettuo il download di un allegato della seduta di commissione
   * @param idAllegato 
   * @param idSeduta 
   */
  public callDownloadAllegatoSeduta(idAllegato: string, idSeduta: number): Observable<HttpResponse<Blob>>
  {
    let httpParams = new HttpParams().set('idAllegato', idAllegato).set('idSeduta', idSeduta.toString());
    return this.http.get(this.URL_DOWNLOAD, 
    {
      params: httpParams,
      observe: 'response',
      responseType: 'blob',
    });
  }
  /**
   * @description Realizzo la chiamata al BE per portare la seduta in stato di 'conclusa'
   * @param idSeduta 
   */
  public callConcludiSeduta(idSeduta: number): Observable<BaseResponse<boolean>>
  {
    let params = {params: new HttpParams().set('idSeduta', idSeduta.toString())};
    return this.http.put<BaseResponse<boolean>>(this.URL_PUBLIC, null, params);
  }
  /**
   * @description realizzo la chiamata al BE per ottenere le pratiche da esaminare/esaminate per una determinata seduta
   * @param idSeduta 
   * @param esaminate 
   */
  public callSearchPratiche(idSeduta: number, esaminate: boolean): Observable<BaseResponse<Array<Fascicolo>>>
  {
    let params = { params: new HttpParams().set('idSeduta', idSeduta.toString()).set('esaminate', esaminate.toString()) };
    return this.http.get<BaseResponse<Array<Fascicolo>>>(this.URL_PRATICHE, params);
  }
  /**
   * @description Realizza la chiamata al BE per ottenere tutti gli allegati caricati, durante una o più
   * sedute di commissione, riguardanti la pratica esaminata
   * @param idPratica 
   */
  public callFindAllegati(idPratica: string): Observable<BaseResponse<Array<SimpleFileCommissioneLocale>>>
  {
    let params = {params: new HttpParams().set('idPratica', idPratica)};
    return this.http.get<BaseResponse<Array<SimpleFileCommissioneLocale>>>(this.URL_SEARCH_A, params);
  }

  /**
   * @description Ottengo le pratiche associabili alla commissione corrente
   * @param idSeduta 
   */
  public callSearchPraticheEsaminabili(idSeduta?: number): Observable<BaseResponse<Array<Fascicolo>>>
  {
    let params = new HttpParams();
    if(idSeduta) params = params.append('idSeduta', idSeduta.toString());
    return this.http.get<BaseResponse<Array<Fascicolo>>>(this.URL_SEARCH_P, {params});
  }

}
