import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseResponse } from '../../components/model/base-response';
import { DettaglioCorrispondenzaDTO } from './../../../features/gestione-istanza-comunicazioni/model/corrispondenza.models';
import { CONST } from './../../constants';
import { Allegato, ParereSoprintendenza } from './../../models/models';

@Injectable({
  providedIn: 'root'
})
export class ParereSoprintendenzaService
{
  private URL_BASE   = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/parere";

  private URL_FIND       = this.URL_BASE + "/find";
  private URL_INSERT     = this.URL_BASE + "/insert";
  private URL_UPDATE     = this.URL_BASE + "/update";
  private URL_DELETE     = this.URL_BASE + "/delete";
  private URL_UPLOAD     = this.URL_BASE + "/upload";
  private URL_REMOVE     = this.URL_BASE + "/remove";
  private URL_INSERT_ALL = this.URL_BASE + "/insertAll";
  private URL_ALLEGA     = this.URL_BASE + "/allega";
  private URL_CREA_COM   = this.URL_BASE + "/createComunication";

  constructor(private http: HttpClient) { }

  /**
   * @description Realizzo la chiamata al BE per ottenere i dati del parere soprintendenza
   * associato ad una pratica
   * @param idPratica 
   */
  public find(idPratica: string): Observable<BaseResponse<ParereSoprintendenza>>
  {
    let params = { params: new HttpParams().set('idPratica', idPratica) };
    return this.http.get<BaseResponse<ParereSoprintendenza>>(this.URL_FIND, params);
  }
  /**
   * @description Realizzo la chiamata al BE per inserire un nuovo parere
   * @param entity 
   */
  public insert(entity: ParereSoprintendenza): Observable<BaseResponse<ParereSoprintendenza>>
  {
    return this.http.post<BaseResponse<ParereSoprintendenza>>(this.URL_INSERT, entity);
  }
  /**
   * @description Realizzo la chiamata al BE per aggiornare un parere
   * @param entity
   */
  public update(entity: ParereSoprintendenza): Observable<BaseResponse<ParereSoprintendenza>>
  {
    return this.http.put<BaseResponse<ParereSoprintendenza>>(this.URL_UPDATE, entity);
  }
  /**
   * @description Realizzo la chiamata al BE per eliminare il parere
   * @param idParere 
   */
  public delete(idParere: number,idPratica:string): Observable<BaseResponse<boolean>>
  {
    let params = { params: new HttpParams().set('idParere', idParere.toString()).set('idPratica',idPratica) };
    return this.http.delete<BaseResponse<boolean>>(this.URL_DELETE, params);
  }
  /**
   * @description Realizzo la chiamata por allegare un parere mibac ad un parere
   * @param file 
   * @param idParere 
   */
  public upload(file: File, type: number, idParere: number,idPratica:string): Observable<BaseResponse<Allegato>>
  {
    let formData = new FormData();
    formData.append('file', file);
    let params = { params: new HttpParams()
      .set('idParere', idParere.toString())
      .set('type', type.toString()) 
      .set('idPratica', idPratica) 
    };
    return this.http.post<BaseResponse<Allegato>>(this.URL_UPLOAD, formData, params);
  }
  /**
   * @description Realizzo la chiamata al BE per rimuove un documento allegato
   * @param idAllegato 
   * @param idParere 
   */
  public remove(idAllegato: string, idParere: number,idPratica:string): Observable<BaseResponse<boolean>>
  {
    let params: HttpParams = new HttpParams();
    params = params.append('idAllegato', idAllegato);
    params = params.append('idParere', idParere.toString());
    params = params.append('idPratica', idPratica);
    return this.http.delete<BaseResponse<boolean>>(this.URL_REMOVE, {params});
  }
  /**
   * @description Realizzo la chiamata al BE per allegare il parere inserito alla pratica definitivamente
   * @param idParere 
   */
  public allega(parere: ParereSoprintendenza): Observable<BaseResponse<ParereSoprintendenza>>
  {
    return this.http.put<BaseResponse<ParereSoprintendenza>>(this.URL_ALLEGA, parere);
  }
  /**
   * @description Realizzo la chiamata al BE per creare una corrispondenza ed associare 
   * al record di parere con id specificato
   * @param idParere 
   */
  public creaComunicazione(idParere: number,idPratica:string): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let params = { params: new HttpParams().set('idParere', idParere.toString()).set('idPratica', idPratica) };
    return this.http.post<BaseResponse<DettaglioCorrispondenzaDTO>>(this.URL_CREA_COM, null, params);
  }

}
