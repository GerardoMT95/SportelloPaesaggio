import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { toHttpParams } from 'src/app/components/functions/genericFunctions';
import { BaseResponse } from 'src/app/components/model/base-response';
import { DestinatarioComunicazioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { CONST } from 'src/shared/constants';
import { DestinatarioSearch } from './../../../app/components/model/entity/corrispondenza.models';
import { PaginatedBaseResponse } from './../../../app/components/model/paginated-base-response';

@Injectable({
  providedIn: 'root'
})
export class RubricaService 
{
  //rubrica ente
  private BASE_RUB_ENTE_URL: string = CONST.WEB_RESOURCE_BASE_URL + "/rubrica";
  private URL_GET_RUB_ENTE:  string = this.BASE_RUB_ENTE_URL + "/enteSearch";
  private URL_MOD_RUB_ENTE: string = this.BASE_RUB_ENTE_URL + "/enteUpdate";
  private URL_DEL_RUB_ENTE: string = this.BASE_RUB_ENTE_URL + "/enteDelete";
  private URL_ADD_RUB_ENTE: string = this.BASE_RUB_ENTE_URL + "/enteInsert";

  //rubrica istituzionale
  private BASE_RUB_IST_URL: string = CONST.WEB_RESOURCE_BASE_URL + "/rubrica";
  private URL_GET_IST_ENTE: string = this.BASE_RUB_IST_URL + "/istituzionaleSearch";

  public editingSubject: Subject<boolean> = new Subject();

  constructor(private http: HttpClient) { }

  /**
   * @description Richiamo dei servizi per ottenere le info paginate per popolare la rubrica dell'ente
   * @param filters filtri per la ricerca nella rubrica dell'ente
   */
  public getIndirizziRubricaEnte(filters?: DestinatarioSearch): Observable<BaseResponse<PaginatedBaseResponse<DestinatarioComunicazioneDTO>>>
  {
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<DestinatarioComunicazioneDTO>>>(this.URL_GET_RUB_ENTE, {params: params});
  }
  /**
   * @description Richiamo dei servizi per modificare un indirizzo della rubrica dell'ente
   * @param indirizzo elemento da modificare
   */
  public modificaInRubricaEnte(indirizzo: DestinatarioComunicazioneDTO): Observable<BaseResponse<boolean>>
  {
    return this.http.put<BaseResponse<boolean>>(this.URL_MOD_RUB_ENTE, indirizzo);
  }
  /**
   * @description Richiamo dei servizi per eliminare un indirizzo dalla rubrica dell'ente
   * @param indirizzo elemento da eliminare
   */
  public eliminaDaRubricaEnte(indirizzo: DestinatarioComunicazioneDTO): Observable<BaseResponse<boolean>>
  {
    let params: HttpParams = toHttpParams(indirizzo);
    return this.http.delete<BaseResponse<boolean>>(this.URL_DEL_RUB_ENTE, {params: params});
  }
  /**
   * @description Richiamo dei servizi per aggiungere un indirizzo alla rubrica dell'ente
   * @param indirizzo elemento da aggiungere
   */
  public aggiungiInRubricaEnte(indirizzo: DestinatarioComunicazioneDTO): Observable<BaseResponse<DestinatarioComunicazioneDTO>>
  {
    return this.http.post<BaseResponse<DestinatarioComunicazioneDTO>>(this.URL_ADD_RUB_ENTE, indirizzo);
  }
  /**
   * @description Richiamo dei servizi per ottenere le info paginate per popolare la rubrica istituzionale
   * @param filters filtri per la ricerca nella rubrica istituzionale
   */
  public getIndirizziRubricaIstituzionale(filters?: DestinatarioSearch): Observable<BaseResponse<PaginatedBaseResponse<DestinatarioComunicazioneDTO>>>
  {
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<DestinatarioComunicazioneDTO>>>(this.URL_GET_IST_ENTE, {params: params});
  }
}
