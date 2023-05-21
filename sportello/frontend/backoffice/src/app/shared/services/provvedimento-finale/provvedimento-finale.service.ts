import { HttpResponse } from '@angular/common/http';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { BaseResponse } from '../../components/model/base-response';
import { ProvvedimentoFinale } from '../../models/models';
import { CONST } from './../../constants';
import { Allegato, ParereSoprintendenza } from './../../models/models';

@Injectable({
  providedIn: 'root'
})
export class ProvvedimentoFinaleService
{
  private BASE_URL   = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/provvedimento";
  private FIND       = this.BASE_URL + "/find";
  private INSERT     = this.BASE_URL + "/insert";
  private UPDATE     = this.BASE_URL + "/update";
  private UPLOAD     = this.BASE_URL + "/upload";
  private REMOVE     = this.BASE_URL + "/remove";
  private SAVE_DEST  = this.BASE_URL + "/saveDestinatari";
  private FIND_DEST  = this.BASE_URL + "/findDestinatariFissi"
  private TRASMETTI  = this.BASE_URL + "/trasmettiProvvedimento";
  private GENERA_PDF = this.BASE_URL + "/generaPdf";
  private GET_RICEVUTA_TRASMISSIONE = this.BASE_URL + "/getRicevutaTrasmissione";

  constructor(private http: HttpClient) { }

  /**
   * @description Realizza la chiamata al BE per ottenere i dati del provvedimento finale
   * @param idPratica 
   */
  public find(idPratica: string): Observable<BaseResponse<ProvvedimentoFinale>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.http.get<BaseResponse<ProvvedimentoFinale>>(this.FIND, params);
  }
  /**
   * @description Realizza la chiamata al BE per inserire un parere di soprintendenza in bozza
   * @param entity 
   */
  public insert(entity: ParereSoprintendenza): Observable<BaseResponse<ProvvedimentoFinale>>
  {
    return this.http.post<BaseResponse<ProvvedimentoFinale>>(this.INSERT, entity);
  }
  /**
   * @description Realizza la chiamata al BE per aggiornare un parere di soprintendenza
   * @param entity 
   */
  public update(entity: ParereSoprintendenza): Observable<BaseResponse<ProvvedimentoFinale>>
  {
    return this.http.put<BaseResponse<ProvvedimentoFinale>>(this.UPDATE, entity);
  }
  /**
   * @description Realizza la chiamata al BE per inserire un allegato ad un parere
   * @param file 
   * @param idPratica 
   */
  public upload(file: File, type: number, idPratica: string): Observable<BaseResponse<Allegato>>
  {
    let formData = new FormData();
    formData.append('file', file);
    let params = { params: new HttpParams().set("idPratica", idPratica).set("type", type.toString()) };
    return this.http.post<BaseResponse<Allegato>>(this.UPLOAD, formData, params);
  }
  /**
   * @description Realizza la chiamata al BE per eliminare un allegato
   * @param idAllegato 
   */
  public remove(idAllegato: string): Observable<BaseResponse<boolean>>
  {
    let params = { params: new HttpParams().set("idAllegato", idAllegato) };
    return this.http.delete<BaseResponse<boolean>>(this.REMOVE, params);
  }
  /**
   * @description Realizza la chiamata al BE per salvare gli ulteriori destinatari
   * @param idPratica 
   */
  public saveDestinatari(destinatari: Array<DestinatarioComunicazioneDTO>, idPratica: string): Observable<BaseResponse<Array<DestinatarioComunicazioneDTO>>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.http.post<BaseResponse<Array<DestinatarioComunicazioneDTO>>>(this.SAVE_DEST, destinatari, params);
  }
  /**
   * @description Realizza la chiamata al BE per ottenere i destinatari fissi
   * @param idPratica 
   */
  public findDestinatariFissi(idPratica: string): Observable<BaseResponse<Array<DestinatarioComunicazioneDTO>>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.http.get<BaseResponse<Array<DestinatarioComunicazioneDTO>>>(this.FIND_DEST, params);
  }
  /**
   * @description Realizza la chiamata al BE per trasmettere il provvedimento
   * @param idPratica 
   */
  public trasmettiProvvedimento(destinatari: Array<DestinatarioComunicazioneDTO>, idPratica: string): Observable<BaseResponse<ProvvedimentoFinale>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.http.put<BaseResponse<ProvvedimentoFinale>>(this.TRASMETTI, destinatari, params);
  }
  /**
   * @description Realizza la chiamata al BE per generare il pdf del documento di trasmissione
   * @param idPratica 
   */
  public generaPDF(idPratica: string): Observable<HttpResponse<Blob>>
  {
    let params =  new HttpParams().set("idPratica", idPratica);
    return this.http.get(this.GENERA_PDF, { observe: 'response', responseType: 'blob', params });  
  }

  /**
     * @description Realizza la chiamata al BE per generare il pdf del documento di trasmissione
     * @param idPratica 
     */
  public getRicevutaTrasmissione(idPratica: string): Observable<HttpResponse<Blob>> {
    let params = new HttpParams().set("idPratica", idPratica);
    return this.http.get(this.GET_RICEVUTA_TRASMISSIONE, { observe: 'response', responseType: 'blob', params });
  }

}
