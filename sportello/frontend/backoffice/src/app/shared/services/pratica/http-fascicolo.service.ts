import { HttpClient, HttpParams } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseResponse, PaginatedList } from 'src/app/core/models/basic.models';
import { populateHttpParams } from 'src/app/features/gestione-istanza-comunicazioni/generic-function/generic-function';
import { CONST } from 'src/app/shared/constants';
import { BFile } from 'src/app/shared/models/allegati.model';
import { Assegnamento, Fascicolo, PagamentiMypayDTO, PagamentiMypaySearch, StoricoASR, StoricoAssegnazioni } from '../../models/models';
import { Protocollo } from '../../../features/gestione-istanza-protocollazione/models/protocollazione-models';
import { PlainFunzRup } from '../../components/model/logged-user';
import { Assegnazione, ConfigurazioneAssegnamento, Counters, DocumentoAllegato, LineaTemporaleDTO, TabellaAssegnamentoFascicolo } from '../../models/models';
import { AssegnazioniSearch, PraticaSearch } from '../../models/search.models';
import { PaginatedBaseResponse } from '../../components/model/paginated-base-response';
import { toHttpParams } from 'src/app/core/functions/generic.utils';
import { DettaglioCorrispondenzaDTO } from 'src/app/features/gestione-istanza-comunicazioni/model/corrispondenza.models';

@Injectable({
  providedIn: 'root'
})
export class FascicoloService
{

  public reloadFascicoloEmitter: EventEmitter<any> = new EventEmitter<any>();

  private URL_BASE = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria";
  //CHIAMATE PRATICA
  private URL_BASE_PRATICA = this.URL_BASE         + "/pratica";
  private URL_SEARCH       = this.URL_BASE_PRATICA + "/search";
  private URL_FIND         = this.URL_BASE_PRATICA + "/find";
  private URL_SWITCH_WORK  = this.URL_BASE_PRATICA + "/switchToInWorking";
  private URL_SWITCH_TRASM = this.URL_BASE_PRATICA + "/switchToTransmission";
  private URL_PROTOCOLLA   = this.URL_BASE_PRATICA + "/protocolla";
  private URL_DOMANDA      = this.URL_BASE_PRATICA + "/getDichiarazioni";
  private URL_REQ_VCL      = this.URL_BASE_PRATICA + "/verbaleCLRequired";

  //CHIAMATE ASR
  private URL_BASE_ASR     = this.URL_BASE     + "/richiestaASR";
  private URL_GET_INSERT   = this.URL_BASE_ASR + "/findDraftOrCreate";
  private URL_UPDATE_ASR   = this.URL_BASE_ASR + "/update";
  private URL_UPLOAD_ASR   = this.URL_BASE_ASR + "/upload";
  private URL_REMOVE_ASR   = this.URL_BASE_ASR + "/remove";
  private URL_TEMPLATE_ASR = this.URL_BASE_ASR + "/request";

  //CHIAMATE ASSEGNAZIONE PRATICA
  private URL_BASE_AP       = this.URL_BASE    + "/assegnazione";
  private URL_FUNZ_ORG      = this.URL_BASE_AP + "/getUtentiForOrganizzazione";
  private URL_AUTO_COD      = this.URL_BASE_AP + "/autocompleteCodice";
  //Configurazone assegnamento automatico
  private URL_GET_CONF      = this.URL_BASE_AP + "/getConfigurazioneForOrganizzazione";
  private URL_SEARCH_CONF   = this.URL_BASE_AP + "/configurazioneAutomaticaSearch";
  private URL_SAVE_CONF     = this.URL_BASE_AP + "/saveConfigurazioneForOrganizzazione";
  //Assegnamento effettivo
  private URL_SEARCH_AP     = this.URL_BASE_AP + "/assegnamentoFascicoliSearch";
  private URL_STORICO_AP    = this.URL_BASE_AP + "/getStoricoAssegnamento";
  private URL_ASSEGNA_PR    = this.URL_BASE_AP + "/assegnaFascicolo";
  private URL_RIASEGNA_PR   = this.URL_BASE_AP + "/riassegnaFascicolo";
  private URL_DISASSOCIA_PR = this.URL_BASE_AP + "/disassegnaFascicolo";

  constructor(private http: HttpClient) { }

  getAllFiascicolo(searchObject?: Fascicolo): Observable<Fascicolo[]> 
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo"
    const httpParams = new HttpParams().set('searchQuery', JSON.stringify(searchObject));
    return this.http.get<Fascicolo[]>(url, { params: httpParams }).pipe(
      map(
        (response: Fascicolo[]) =>
        {
          return response;
        })
    );
  }

  getFascicoloById(id: string): Observable<Fascicolo> 
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo"
    const httpParams = new HttpParams().set('id', id);
    return this.http.get<Fascicolo>(url, { params: httpParams }).pipe(
      map(
        (response: Fascicolo) =>
        {
          return response;
        }
      )
    );
  }

  getFascicolo(fascicolo: Fascicolo): Observable<BaseResponse<Fascicolo>> 
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo"
    const httpParams = new HttpParams().set('id', fascicolo.id.toString());
    return this.http.get<BaseResponse<Fascicolo>>(url, { params: httpParams });
  }

  //realizzo i service reali
  /**
   * @description realizzo la chiamata al BE per ottenere i fascicoli
   * @param filters @class FascicoloSearch
   * @returns @class Observale contenente la risposta alla chiamaata, impacchettata in 
   *          una @class BaseResponse ed in una una @class PaginatedList di @class Fascicolo
   */
  public callSearchFascicoli(filters: PraticaSearch): Observable<BaseResponse<PaginatedList<Fascicolo>>>
  {
    //updated
    let url: string = this.URL_SEARCH;
    let params: HttpParams = populateHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedList<Fascicolo>>>(url, { params: params });
  }
  /**
   * @description realizzo la chiamata al BE per ottenere un oggetto con cui popolare la linea temporale
   * @param codiceFascicolo 
   */
  public callTimeline(codiceFascicolo: string): Observable<BaseResponse<LineaTemporaleDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/timeline";
    let params = {params: new HttpParams().set('codiceFascicolo', codiceFascicolo)};
    return this.http.get<BaseResponse<LineaTemporaleDTO>>(url, params);
  }
  /**
   * @description realizzo la chiamata al BE per ottenere un oggetto @class counters per popolare la legenda 
   * @param filters @class FascicoloSearch
   * @returns @class Observale contenente la risposta alla chiamaata, impacchettata in 
   *          una @class BaseResponse ed in una una @class PaginatedList di @class Fascicolo
   */
  public callCountersFascicoli(filters: PraticaSearch): Observable<BaseResponse<Counters>>
  {
    //updated
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/pratica/getCounters";
    let params: HttpParams = new HttpParams();
    return this.http.get<BaseResponse<Counters>>(url, { params: params });
  }
/**
 * @description realizzo la chiamata al BE per ottenere informazioni sui fascicoli da assegnare e assegnati
 * @param filters @class AssegnazioniSearch
 * @returns @class Observale contenente la risposta alla chiamaata, impacchettata in
 *          una @class BaseResponse ed in una una @class PaginatedList di @class Fascicolo
 */
  public callSearchAssigned(filters: AssegnazioniSearch): Observable<BaseResponse<PaginatedList<TabellaAssegnamentoFascicolo>>>
  {
    //updated
    let params: HttpParams = populateHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedList<TabellaAssegnamentoFascicolo>>>(this.URL_SEARCH_AP, { params: params });
  }
  /**
   * @description Metodo che realizza la chiamata la BE per ottenere una lista di funzionari/rup
   */
  public callGetFunzionario(): Observable<BaseResponse<PlainFunzRup[]>>
  {
    let url: string = this.URL_FUNZ_ORG;
    return this.http.get<BaseResponse<PlainFunzRup[]>>(url);
  }
  /**
   * @description Metodo per richiamare il servizio dell'autocomplete del codice pratica
   * @param query 
   * @param giaAssegnato 
   */
  public callAutocompleteCodice(query: string, giaAssegnato: boolean): Observable<BaseResponse<string[]>>
  {
    let url: string = this.URL_AUTO_COD;
    let params = {params: new HttpParams().set("codice", query).set("giaAssegnato", giaAssegnato.toString())}
    return this.http.get<BaseResponse<string[]>>(url, params);
  }
  /**
   * @description Metodo per ottenere la configurazione di assegnamento automatico attualmente salvata
   */
  public callGetConfigurazioneAssegnamento(): Observable<BaseResponse<ConfigurazioneAssegnamento>>
  {
    let url: string = this.URL_GET_CONF;
    return this.http.get<BaseResponse<ConfigurazioneAssegnamento>>(url);
  }
  /**
   * @description Metodo che richiama il servizio per il salvataggio delle configurazioni di assegnamento automatico
   * @param entity 
   */
  public callSaveConfigurazioneAssegnamento(entity: ConfigurazioneAssegnamento): Observable<BaseResponse<boolean>>
  {
    let url: string = this.URL_SAVE_CONF;
    return this.http.post<BaseResponse<boolean>>(url, entity);
  }
  /**
   * @description Realizzo la chiamata per ottenere lo storico delle assegnazioni per una pratica
   * @param idPratica 
   */
  public callGetStoricoAssegnamento(idPratica: string): Observable<BaseResponse<StoricoAssegnazioni[]>>
  {
    let url = this.URL_STORICO_AP;
    let params = {params: new HttpParams().set("idFascicolo", idPratica)};
    return this.http.get<BaseResponse<StoricoAssegnazioni[]>>(url, params);
  }
  /**
   * @description Realizzo la chiamata per cercare tramite il codice uno specifico fascicolo
   * @param codice codice del fascicolo da ricercare
   */
  public callFindByCode(codice: string): Observable<BaseResponse<Fascicolo>>
  {
    //updated?
    let url: string = this.URL_FIND;
    let params = { params: new HttpParams().set("codiceFascicolo", codice) }
    return this.http.get<BaseResponse<Fascicolo>>(url, params);
  }
  /**
   * @description Realizzo la chiamata per cercare tramite id uno specifico fascicolo
   * @param id id del fascicolo da ricercare
   */
  public callFindById(id: string): Observable<BaseResponse<Fascicolo>>
  {
    //updated?
    let url: string = this.URL_FIND;
    let params = { params: new HttpParams().set("idPratica", id) }
    return this.http.get<BaseResponse<Fascicolo>>(url, params);
  }
  /**
   * @description realizzo la chiamata al BE per salvare una nuova assegnazione
   * @param assegnazione @class Assegnamento
   */
  public callSaveAssignemet(assegnazione: Assegnamento): Observable<BaseResponse<Assegnazione>>
  {
    //updated
    let url: string = this.URL_ASSEGNA_PR;
    return this.http.post<BaseResponse<Assegnazione>>(url, assegnazione);
  }
  callNewSaveAssignement(assegnazione: Assegnamento): Observable<BaseResponse<Assegnazione>>
  {
    //updated
    let url: string = this.URL_RIASEGNA_PR;
    return this.http.post<BaseResponse<Assegnazione>>(url, assegnazione);
  }
  /**
   * @description Realizzo la chiamata al BE per revocare l'associazione per l'attuale funzionario e rup
   * @param entity 
   */
  public callDisassegnaFascicolo(entity: Assegnamento): Observable<BaseResponse<Assegnazione>>
  {
    //updated
    let url: string = this.URL_DISASSOCIA_PR;
    return this.http.post<BaseResponse<Assegnazione>>(url, entity);
  }

  /**
   * @deprecated 
   * @description realizzo la chiamata al BE per salvare una nuova assegnazione per funzionario soprintendenza
   * @param assegnazione @class Assegnazione
  */
  public callSaveAssignemetSop(assegnazione: Assegnazione): Observable<BaseResponse<Assegnazione>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/saveAssignmentSop";
    return this.http.post<BaseResponse<Assegnazione>>(url, assegnazione);
  }
  /**
   * @description realizzo la chiamata al BE per salvare il protocollo
   * @param protocollo @class Protocollo
   */
  public callProtocollaFascicolo(protocollo: Protocollo): Observable<BaseResponse<Fascicolo>>
  {
    //updated
    let url = this.URL_PROTOCOLLA;
    return this.http.put<BaseResponse<Fascicolo>>(url, protocollo);
  }
  /**
   * @description Realizzo la chiamata per ottenere le dichiarazioni caricate dal richiedente
   * @param id 
   */
  public callGetDichiarazioni(idPratica: string): Observable<BaseResponse<Array<BFile>>>
  {
    let url = this.URL_DOMANDA;
    let params = { params: new HttpParams().set('idPratica', idPratica) };
    return this.http.get<BaseResponse<Array<BFile>>>(url, params);
  }
  /**
   * @description realizzo la chiamata per sapere se verbale commissione locale è required
   * @param idPratica 
   */
  public callReqVCL(idPratica: string): Observable<BaseResponse<boolean>>
  {
    let url = this.URL_REQ_VCL;
    let params = { params: new HttpParams().set('idPratica', idPratica) };
    return this.http.get<BaseResponse<boolean>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per far passare un fascicolo allo stato "in lavorazione"
   * @param idFascicolo del fascicolo che passa allo stato "in lavorazione"
   */
  public callFascicoloInLavorazione(idFascicolo: string): Observable<BaseResponse<boolean>>
  {
    //updated
    let url: string = this.URL_SWITCH_WORK;
    let params = { params: new HttpParams().set("idPratica", idFascicolo) }
    return this.http.put<BaseResponse<boolean>>(url, null, params);
  }
/**
 * @description Realizzo la chiamata al BE per far passare un fascicolo allo stato "da trasmettere"
 * @param idFascicolo del fascicolo che passa allo stato "da trasmettere"
 */
  public callFascicoloDaTrasmettere(idFascicolo: string): Observable<BaseResponse<boolean>>
  {
    let url: string = this.URL_SWITCH_TRASM;
    let params = { params: new HttpParams().set("idPratica", idFascicolo) };
    return this.http.put<BaseResponse<boolean>>(url, null, params);
  }
  /**
   * @description Realizzo la chiamata al BE per ottenere tutte le operazioni per attivare/sospendere/archiviare un fascicolo
   * @param richiesta
   */
  public callHandleStateGetHistoric(idPratica: string): Observable<BaseResponse<StoricoASR>>
  {
    //updated
    let url: string = this.URL_SEARCH_AP;//CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/transmit";
    let params = { params: new HttpParams().set("idPratica", idPratica).set("draft", "false") }
    return this.http.get<BaseResponse<StoricoASR>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per ottenere richiesta in bozza per attivare/sospendere/archiviare un fascicolo.
   * Se tale richiesta non è presente allora ne crea una e la fa tornare
   * @param idPratica
   * @param tipoRichiesta
   */
  public callGetOrCreateRequest(idPratica: string): Observable<BaseResponse<StoricoASR>>
  {
    //updated
    let url: string = this.URL_GET_INSERT;
    let params = { params: new HttpParams().set("idPratica", idPratica) };
    return this.http.get<BaseResponse<StoricoASR>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per aggiornare richiesta per attivare/sospendere/archiviare un fascicolo
   * @param richiesta
   */
  public callUpdateASR(richiesta: StoricoASR): Observable<BaseResponse<StoricoASR>>
  {
    //updated
    let url: string = this.URL_UPDATE_ASR;
    return this.http.put<BaseResponse<StoricoASR>>(url, richiesta);
  }
  /**
   * @description Realizzo la chiamata al BE per eliminare richiesta per attivare/sospendere/archiviare un fascicolo
   * @param idRichiesta
   * @param codiceFascicolo
   */
  public callDeleteASR(idRichiesta: number): Observable<BaseResponse<number>>
  {
    //updated
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/gestioneStato";
    let params = { params: new HttpParams().set("idRichiesta", idRichiesta.toString()) };
    return this.http.delete<BaseResponse<number>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per uploadare un documento associandolo ad una 
   * richiesta di attivare/sospendere/archiviare
   * @param file
   * @param idRichiesta
   * @param codiceFascicolo
   */
  public callUploadFileASR(file: File, idRichiesta: number, idPratica: string): Observable<BaseResponse<DocumentoAllegato>>
  {
    //updated
    let url: string = this.URL_UPLOAD_ASR;
    let formData: FormData = new FormData();
    formData.append("file", file);
    let params = { params: new HttpParams().set("idPratica", idPratica).set("idRichiesta", idRichiesta.toString()) };
    return this.http.post<BaseResponse<DocumentoAllegato>>(url, formData, params);
  }
  /**
   * @description Realizzo la chiamata al BE per eliminare un documento associandolo ad una richiesta di attivare/sospendere/archiviare
   * @param idAllegato
   * @param idRichiesta
   * @param codiceFascicolo
   */
  public callDeleteFileASR(idAllegato: string, idRichiesta: number, idPratica: string): Observable<BaseResponse<boolean>>
  {
    //updated
    let url: string = this.URL_REMOVE_ASR;
    let params = { params: new HttpParams().set("idPratica", idPratica)
                                           .set("idRichiesta", idRichiesta.toString())
                                           .set("idAllegato", idAllegato) };
    return this.http.delete<BaseResponse<boolean>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per creare la bozza di template di richiesta archiviazione/riattivazione
   * @param idPratica
   * @param isArchiviazione
   */
  public callCreateRequest(idPratica: string, isArchiviazione: boolean): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let url = this.URL_TEMPLATE_ASR;
    let params = { params: new HttpParams().set("idPratica"             , idPratica)
                                           .set("richiestaArchiviazione", isArchiviazione ? 'true' : 'false')};
    return this.http.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url, null, params);
  }
  /**
   * @description Realizzo la chiamata al BE per ottenere una lista di fascicoli associabili ad una seduta di commmissione
   * @param idSeduta 
   */
  public callRetriveForSession(idSeduta?: number): Observable<BaseResponse<string[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/seduta";
    let httpParams: HttpParams = new HttpParams();
    if(idSeduta)
      httpParams = httpParams.append("idSeduta", idSeduta.toString());
    let params = { params: httpParams };
    return this.http.delete<BaseResponse<string[]>>(url, params);
  }
  /**
   * @description Realizzo la chiamata al BE per ottenere la lista del dettaglio dei fascicoli associati ad una seduta
   * @param idSeduta 
   * @param examined
   */
  public callRetriveDetail(idSeduta: number, examined: boolean): Observable<BaseResponse<Fascicolo[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/sedutaDetails";
    let httpParams: HttpParams = new HttpParams().set("idSeduta", idSeduta.toString()).set("examined", examined.toString());
    let params = { params: httpParams };
    return this.http.delete<BaseResponse<Fascicolo[]>>(url, params);
  }
/**
 * @description Realizzo la chiamata al BE per ottenere la lista del dettaglio dei fascicoli 
 * associati ad una seduta e ad uno specifico allegato
 * @param idSeduta
 * @param idAllegato
 */
  public callRetriveDetailAttachment(idSeduta: number, idAllegato: number): Observable<BaseResponse<Fascicolo[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/detailsAttachment";
    let httpParams: HttpParams = new HttpParams().set("idSeduta", idSeduta.toString()).set("idAllegato", idAllegato.toString());
    let params = { params: httpParams };
    return this.http.delete<BaseResponse<Fascicolo[]>>(url, params);
  }

  public effettuaPagamento(idPratica: string,causale:string,importo:number,retUrl:string): Observable<BaseResponse<PagamentiMypayDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/Fascicolo/effettuaPagamento";
    let params = { params: new HttpParams()
      .set("idPratica", idPratica)
      .set("importo",importo+'')
      .set("causale", causale)
      .set("retUrl", retUrl) };
    return this.http.get<BaseResponse<PagamentiMypayDTO>>(url, params);
  }
 
  public searchPagamenti(pagSearch:PagamentiMypaySearch): Observable<BaseResponse<PaginatedBaseResponse<PagamentiMypayDTO>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/Fascicolo/searchPagamenti`;
    return this.http.get<BaseResponse<PaginatedBaseResponse<PagamentiMypayDTO>>>(url, { params: toHttpParams(pagSearch) });
  }

  /**
   * almeno uno delle due chiavi deve essere avvalorata
   * @param praticaId 
   * @param codicePratica 
   * restituisce una mappa stato,importo  es.:
   	 {
      "KO": 2.09,
      "INCORSO":0.01,
		  "OK": 2
	    }
  */
  public totPagamentiPerStato(praticaId?:string,codicePratica?:string):Observable<BaseResponse<{'OK'?:number,'KO'?:number,'INCORSO'?:number}>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + `/Fascicolo/totPagamentiPerStato`;
    let parms=null;
    if(praticaId){
      parms=new HttpParams().set('praticaId',praticaId);
    }else if(codicePratica){
      parms=new HttpParams().set('codicePratica',codicePratica);
    }
    return this.http.get<BaseResponse<{'OK'?:number,'KO'?:number,'INCORSO'?:number}>>(url, { params: parms });
  }

  // true se ha una configurazione automatica per il servizio di pagamenti 
  public hasPagamentoIntegrato(idOrganizzazione:number): Observable<BaseResponse<boolean>> {
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idOrganizzazione", idOrganizzazione+"");
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL+'/configurazioniEnte/hasPagamenti',{ params: parameters });
  }
}
