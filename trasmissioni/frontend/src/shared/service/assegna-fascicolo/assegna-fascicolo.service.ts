import { CONST } from './../../constants';
import { TabelleAssegnamentoFascicoli, AssegnamentoFascicolo, StoricoAssegnamento } from './../../models/assegna-fascicolo-models';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PaginatedBaseResponse } from './../../../app/components/model/paginated-base-response';
import { BaseResponse } from './../../../app/components/model/base-response';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AssegnaFascicoloService {

  constructor(private http: HttpClient) { }

  /**
   * metodo che permette di popolare le tabelle di riepilogo relative ai fascicoli da assegnare e già assegnati
   * @param giaAssegnato true -> la chiamata popola la tabella dei fascicoli già assegnati, false -> la chiamata popola la tabella dei fascicoli da assegnare
   * @param codice se presente vuol dire che è stato fatta una ricerca per codice fascicolo
   * @param page
   * @param limit
   */
  public tabellaAssegnamentoFascicoliSearch(giaAssegnato: boolean, codice: string, page: number, limit: number): Observable<BaseResponse<PaginatedBaseResponse<TabelleAssegnamentoFascicoli>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/assegnamentoFascicoliSearch";
    let params: HttpParams = new HttpParams();
    params = params.append("giaAssegnato", giaAssegnato ? "true" : "false");
    params = params.append("page", page.toString());
    params = params.append("limit", limit.toString());
    if (codice) {
      params = params.append("codice", codice);
    }
    return this.http.get<BaseResponse<PaginatedBaseResponse<TabelleAssegnamentoFascicoli>>>(url, { params: params });
  }

  /**
   * metodo che permette di assegnare un fascicolo ad un funzionario
   * @param assegnamentoFascicolo fascicolo che deve essere assegnato
   */
  public assegnaFascicolo(assegnamentoFascicolo: AssegnamentoFascicolo): Observable<BaseResponse<AssegnamentoFascicolo>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/assegnaFascicolo";
    return this.http.post<BaseResponse<AssegnamentoFascicolo>>(url, assegnamentoFascicolo);
  }

  /**
   * metodo che permette di riassegnare un fascicolo ad un funzionario
   * @param assegnamentoFascicolo fascicolo che deve essere riassegnato
   */
  public riassegnaFascicolo(assegnamentoFascicolo: AssegnamentoFascicolo): Observable<BaseResponse<AssegnamentoFascicolo>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/riassegnaFascicolo";
    return this.http.post<BaseResponse<AssegnamentoFascicolo>>(url, assegnamentoFascicolo);
  }

  /**
   * metodo che permette il retrieve dello storico delle assegnazioni di un dato fascicolo
   * @param idFascicolo id del fascicolo di cui si vuole conoscere lo storico
   */
  public getStoricoAssegnamento(idFascicolo: number): Observable<BaseResponse<StoricoAssegnamento[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/getStoricoAssegnamento";
    let params: HttpParams = new HttpParams();
    params = params.append("idFascicolo", idFascicolo.toString());
    return this.http.get<BaseResponse<StoricoAssegnamento[]>>(url, { params: params });
  }

  /**
   * metodo che permette di disassegnare un fascicolo lasciandolo libero
   * @param assegnamentoFascicolo fascicolo che deve essere disassegnato
   */
  public disassegnaFascicolo(assegnamentoFascicolo: AssegnamentoFascicolo): Observable<BaseResponse<AssegnamentoFascicolo>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/disassegnaFascicolo";
    return this.http.post<BaseResponse<AssegnamentoFascicolo>>(url, assegnamentoFascicolo);
  }

  /**
   * metodo che permette l'autocomplete del codice nella ricerca
   * @param codice codice da autocompletare
   */
  public autocompleteCodice(codice: string, giaAssegnato: boolean): Observable<BaseResponse<string[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/assegnazione/autocompleteCodice";
    let params: HttpParams = new HttpParams();
    params = params.append("codice", codice);
    params = params.append("giaAssegnato", giaAssegnato ? "true" : "false");
    return this.http.get<BaseResponse<string[]>>(url, { params: params });
  }

}
