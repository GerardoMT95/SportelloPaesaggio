import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { CorrispondenzaDTO, DettaglioCorrispondenzaDTO } from 'src/app/features/gestione-istanza-comunicazioni/model/corrispondenza.models';
import { RelazioneCorrispondenza, RelazioneDettaglioCorrispondenza } from 'src/app/features/gestione-istanza-relazione-ente/model/relazioneEnte.models';
import { CONST } from 'src/app/shared/constants';
import { Allegato, RelazioneEnte } from '../../models/models';
import { ComunicazioniTemplate, DocumentoRelazione } from './../../models/models';

@Injectable({
  providedIn: 'root'
})
export class HttpRelazioneEnteService 
{
  private BASE_URL = CONST.WEB_RESOURCE_BASE_URL + "/relazione";
  constructor(private http: HttpClient) { }

  /**
   * @description realizzo la chiamata al BE per ottenere la relazione ente per un determinato fascicolo
   * @param codiceFascicolo codice del fascicolo per cui si desidera ottenere la relazione ente
   */
  public callSearchRelazioneEnte(codiceFascicolo: string): Observable<BaseResponse<RelazioneEnte>>
  {
    let url: string = this.BASE_URL + "/search.pjson";
    let params = new HttpParams().set('idPratica', codiceFascicolo);
    return this.http.post<BaseResponse<RelazioneEnte>>(url, params);
  } 
  /**
   * @description realizzo la chiamata al BE per il salvataggio di una nuova relazione per un determinato fascicolo
   * @param relazione @class RelazioneEnte da salvare
   */
  public callSaveRelazioneEnte(relazione: RelazioneEnte): Observable<BaseResponse<RelazioneEnte>>
  {
    let url: string = this.BASE_URL + "/save";
    return this.http.post<BaseResponse<RelazioneEnte>>(url, relazione);
  }
  /**
   * @description realizzo la chiamata al BE per l'aggiornamento di una relazione per un determinato fascicolo
   * @param relazione @class RelazioneEnte da salvare
   */
  public callUpdateRelazioneEnte(relazione: RelazioneEnte): Observable<BaseResponse<RelazioneEnte>>
  {
    let url: string = this.BASE_URL + "/update.pjson";
    return this.http.post<BaseResponse<RelazioneEnte>>(url, relazione);
  }
  /**
  * @description realizzo la chiamata al BE per la cancellazione di una relazione per un determinato fascicolo
  * @param id della relazione da eliminare
  */
  public callDeleteRelazioneEnte(id: number): Observable<BaseResponse<RelazioneEnte>>
  {
    let url: string = this.BASE_URL + "/delete";
    let params = { params: new HttpParams().set('id', id.toString()) };
    return this.http.put<BaseResponse<RelazioneEnte>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per il salvtaggio di un file della relazione ente
   * @param idRelazione id della relazione a cui è associato il file
   * @param codiceFascicolo codice fascicolo relazione
   * @param file @class File da uploadare
   */
  public callUploadFile(idRelazione: number, codiceFascicolo: string, tipoAllegato: "REL_TEC_ILL"|"OTHER", file: File): Observable<BaseResponse<Allegato>>
  {
    let url: string = this.BASE_URL + "/upload_allegato.pjson";
    
    let formData: FormData = new FormData();
    
    formData.append("tipoAllegato", tipoAllegato);
    formData.append("idRelazione", idRelazione.toString());
    formData.append("codiceFascicolo", codiceFascicolo);
    formData.append("file", file);
    //const HttpUploadOptions = {
    //  headers: new HttpHeaders({ "Content-Type": "multipart/form-data" })
    //}
    return this.http.post<BaseResponse<Allegato>>(url, formData);
  }
  /**
   * @description realizzo la chiamata al be per eliminare il file ed il record associato ad esso
   * @param idRelazione 
   * @param idFile 
   */
  public callDeleteFile(idRelazione: number, idFile: number,idPratica:string): Observable<BaseResponse<boolean>>
  {
    let url: string = this.BASE_URL + "/deleteAllegato.pjson";
    let params = new HttpParams().set('idFile', idFile.toString()).set('idPratica', idPratica);

    return this.http.post<BaseResponse<boolean>>(url, params);
  }
  /**
   * @description Relizzo la chiamata al BE per il salvataggio di una nuova comunicazione per una relazione
   * @param idRelazione relazione a cui allegare la comunicazione
   * @param comunicazione 
   * @param tipoInvia indica, nel caso la comunicazione fosse da inviare se l'invio deve essere con o senza protocollazione regionale
   */
  public callSaveComunicazione(idRelazione: number, comunicazione: CorrispondenzaDTO,idPratica:string, tipoInvia?: "WITH_PROT" | "WITHOUT_PROT"): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let url: string = this.BASE_URL + "/saveComunicazione.pjson";
    let req:RelazioneCorrispondenza = new RelazioneCorrispondenza();
    let params =new HttpParams().set('idPratica', idPratica);
    req.corrispondenza=comunicazione;
    req.idRelazione=idRelazione;
    req.idPratica=idPratica;
    if(tipoInvia)
      req.tipoInvia=tipoInvia;
    
    return this.http.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url,req,{params:params});
  }
  /**
   * @description Relizzo la chiamata al BE per l'aggiornamento di una comunicazione per una relazione
   * @param idRelazione relazione a cui allegare la comunicazione
   * @param comunicazione
   * @param tipoInvia indica, nel caso la comunicazione fosse da inviare se l'invio deve essere con o senza protocollazione regionale
   */ 
  public callUpdateComunicazione(idRelazione: number, comunicazione: DettaglioCorrispondenzaDTO,idPratica:string, tipoInvia?: "WITH_PROT" | "WITHOUT_PROT"): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let url: string = this.BASE_URL + "/updateComunicazione.pjson";
    let request:RelazioneDettaglioCorrispondenza = new RelazioneDettaglioCorrispondenza();
    request.corrispondenza=comunicazione
    request.idRelazione=idRelazione;
    request.idPratica=idPratica;
    if(tipoInvia)
    request.tipoInvia=tipoInvia
    return this.http.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url, request);

  }
  /**
   * @description Realizzo la chiamata al be per la cancellazione di uno specifico allegato
   * @param idRelazione 
   * @param idComunicazione 
   */
  public callDeleteComunicazione(idRelazione: number, idComunicazione: number): Observable<BaseResponse<boolean>>
  {
    let url: string = this.BASE_URL + "/deleteAllegato.pjson";
    let httpParams: HttpParams = new HttpParams().set("idRelazione", idRelazione.toString())
                                                 .set("idComunicazione", idComunicazione.toString());
    let params = { params: httpParams };
    return this.http.delete<BaseResponse<boolean>>(url, params);
  }
}
