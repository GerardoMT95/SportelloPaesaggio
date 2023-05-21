import { HttpClient } from '@angular/common/http';
import { CONST } from './../../constants';
import { ConfigurazioneAssegnamento } from './../../models/assegna-fascicolo-models';
import { okresponse } from 'src/shared/functions/mockedResponse';
import { SelectItem } from 'primeng/components/common/selectitem';
import { BaseResponse } from 'src/app/components/model/base-response';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AssegnaFascicoloConfigService {

  constructor(private http: HttpClient) { }

  /**
   * metodo che permette di retrivare tutte le info per la configurazione dell'assegnazione dei fascicoli,
   * comprese alcune dropdown e le tabelle
   */
  public getConfigurazioneForOrganizzazione(): Observable<BaseResponse<ConfigurazioneAssegnamento>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/getConfigurazioneForOrganizzazione";
    return this.http.get<BaseResponse<ConfigurazioneAssegnamento>>(url);
  }

  /**
   * metodo che permette il retrieve delle info necessarie a popolare la sola dropdown dei funzionari
   */
  public getUtentiForOrganizzazione(): Observable<BaseResponse<any[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/getUtentiForOrganizzazione";
    return this.http.get<BaseResponse<any[]>>(url);
  }

  /**
   * metodo che permette il salvataggio delle configurazioni per l'assegnazione dei fascicoli
   * @param configurazioneAssegnamento oggetto con dentro tutte le configurazioni
   */
  public saveConfigurazioneForOrganizzazione(configurazioneAssegnamento: ConfigurazioneAssegnamento): Observable<BaseResponse<boolean>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/saveConfigurazioneForOrganizzazione";
    return this.http.post<BaseResponse<boolean>>(url, configurazioneAssegnamento);
  }

}
