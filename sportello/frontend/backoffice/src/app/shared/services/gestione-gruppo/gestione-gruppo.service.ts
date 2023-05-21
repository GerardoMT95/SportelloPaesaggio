
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseResponse } from 'src/app/core/models/basic.models';
import { GruppiRuoliDTO, PlainTypeStringId, UtenteAttributeBean } from '../../components/model/logged-user';
import { CONST } from '../../constants';
import { UtenteAttributeDTO } from '../../models/models';

@Injectable({
  providedIn: 'root'
})
export class GestioneGruppoService
{
  constructor(private http: HttpClient) { }

  public readGruppi(): Observable<BaseResponse<GruppiRuoliDTO[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/autorita-procedurale-nuovo-istruttoria";
    return this.http.get<BaseResponse<GruppiRuoliDTO[]>>(url);
  }

  public controllaGruppo(gruppoScelto: string): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/checkGruppo";
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.append('codiceGruppo', gruppoScelto);
    return this.http.get<BaseResponse<any>>(url, { params: httpParams });
  }

  public getGruppiOrganizzazioni(): Observable<BaseResponse<PlainTypeStringId[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/gruppiOrganizzazioni";
    return this.http.get<BaseResponse<PlainTypeStringId[]>>(url);
  }

  public getGruppiOrganizzazioniConCL(): Observable<BaseResponse<PlainTypeStringId[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/gruppiOrganizzazioniConCL";
    return this.http.get<BaseResponse<PlainTypeStringId[]>>(url);
  }

  public getTestoPrivacy(): Observable<BaseResponse<string>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/testo-privacy.pjson";
    return this.http.get<BaseResponse<string>>(url);
  }

  public getLastRichiestaAbilitazioneData(): Observable<BaseResponse<UtenteAttributeDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/get-last-richiesta-data.pjson";
    return this.http.get<BaseResponse<UtenteAttributeDTO>>(url);
  }

  public updateUtenteAttribute(bean: UtenteAttributeBean): Observable<BaseResponse<boolean>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/updateUtenteAttribute";
    return this.http.post<BaseResponse<boolean>>(url, bean);
  }

}
