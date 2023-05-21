import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CONST } from 'src/app/shared/constants';
import { BaseResponse } from './../../components/model/base-response';
import { SchedaTecnica } from './../../models/models';

@Injectable({
  providedIn: 'root'
})
export class SchedaTecnicaService 
{
  private BASE_URL: string = CONST.WEB_RESOURCE_BASE_URL + "/schedaTecnica";
  private SAVE: string = this.BASE_URL + "/save";
  private FIND: string = this.BASE_URL + "/find";
  private VALIDA: string = this.BASE_URL + "/valida";

  constructor(private http: HttpClient) { }

  /**
   * @description Chiamata al servizio per salva tutte le informazioni sulla scheda tecnica
   * @param scheda 
   */
  public saveSchedaTecnica(scheda: SchedaTecnica): Observable<BaseResponse<SchedaTecnica>>
  {
    return this.http.post<BaseResponse<SchedaTecnica>>(this.SAVE, scheda);
  }

  /**
   * @description Chiamata al servizio per ottenere la scheda tecnica di un determinato fascicolo
   * @param idPratica 
   */
  public findSchedaTecnica(idPratica: string): Observable<BaseResponse<SchedaTecnica>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) }
    return this.http.get<BaseResponse<SchedaTecnica>>(this.FIND, params);
  }

  /**
   * @description Chiamata al servizio per la validazione della scheda tecnica
   * @param idPratica 
   */
  public valida(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let params = { params: new HttpParams().set("idPratica", idPratica) }
    return this.http.get<BaseResponse<boolean>>(this.VALIDA, params);
  }



}
