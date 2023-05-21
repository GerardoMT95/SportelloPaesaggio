import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { toHttpParams } from 'src/app/core/functions/generic.utils';
import { BaseResponse, PaginatedList } from 'src/app/core/models/basic.models';
import { DestinatarioIstituzionaleDto, DichiarazioneDTO, DiogeneApplicationConfigbean, DisclaimerDTO, RubricaIstituzionaleSearch, SezioneCatastaleSearch, TariffaDTO, TipoProcedimento } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { RupDTO } from 'src/app/features/funzionalita-amministratore-ente/conf-censimento-rup/model/rup.models';
import { Configurazione } from 'src/app/features/funzionalita-amministratore-ente/gestione-servizi-integrazione/models/ente-admin.models';
import { TemplateDocDTO, TemplateDocSearch } from 'src/app/features/funzionalita-amministratore-ente/modifica-testi-documentazione/models/template-doc-model';
import { AllegatiDTO } from 'src/app/features/gestione-istanza-documentazione/model/allegato.models';
import { PaginatedBaseResponse } from '../../components/model/paginated-base-response';
import { SelectOption } from '../../components/select-field/select-field.component';
import { CdsGetConfigurazione, CdsSaveConfigurazione, TemplateEmailDestinatariDto, TemplateEmailDTO, WebContentDTO } from '../../models/models';
import { CLSearch, CommissioneLocaleOrganizzazione, Container, EntiCommissioni } from './../../../features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { PlainTypeStringId } from './../../components/model/logged-user';
import { CONST } from './../../constants';

@Injectable({
  providedIn: 'root'
})
export class AdminFunctionsService {
  // -- Rubrica --
  private BASE_RUBRICA_URL = CONST.WEB_RESOURCE_BASE_URL + "/rubrica";
  private BASE_SEZ_CATASTALI = CONST.WEB_RESOURCE_BASE_URL + "/sezCatastaliConfig";
  private BASE_WEB_CONTENT_CONFIG = CONST.WEB_RESOURCE_BASE_URL + "/WebContentConfigurabili";
  private URL_RUBRICA_IST = this.BASE_RUBRICA_URL + "/istituzionaleAdminSearch";
  private URL_SEZIONI_CATASTALI = this.BASE_SEZ_CATASTALI + "/sezioniCatastaliSearch";
  private URL_SEZIONI_CATASTALI_SAVE_OR_UPDATE = this.BASE_SEZ_CATASTALI + "/sezioniCatastaliSaveOrUpdate";
  private URL_SEZIONI_CATASTALI_DELETE = this.BASE_SEZ_CATASTALI + "/sezioniCatastaliDelete";
  private URL_MOD_RUB_IST = this.BASE_RUBRICA_URL + "/istituzionaleAdminUpdate";
  // -- Servizi integrazione --
  private BASE_SI_URL = CONST.WEB_RESOURCE_BASE_URL + "/configurazioniEnte";
  private URL_SI_FIND = this.BASE_SI_URL + "/find";
  private URL_SI_SAVE = this.BASE_SI_URL + "/save";
  private URL_HAS_PROTOCOLLO = this.BASE_SI_URL + "/hasProtocollo";
  // -- Gestione commissione locale --
  private URL_BASE_CL    = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/sedutaCommissione"
  private URL_FIND_CL    = this.URL_BASE_CL + "/findCL";
  private URL_SEARCH_CL  = this.URL_BASE_CL + "/searchCL";
  private URL_POPULATE   = this.URL_BASE_CL + "/populate";
  private URL_INSERT_CL  = this.URL_BASE_CL + "/insertCL";
  private URL_UPDATE_CL  = this.URL_BASE_CL + "/updateCL";
  private URL_ADD_USER   = this.URL_BASE_CL + "/addUser"; 
  private URL_DEL_USER   = this.URL_BASE_CL + "/removeUser";
  private URL_FIND_USERS = this.URL_BASE_CL + "/findUsers";
  private URL_CHECK_DATE = this.URL_BASE_CL + "/checkDate";

  // -- admin applicazione
  private BASE_ADMIN_URL = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/admin";
  private DIOGENE_CONFIGURATION = this.BASE_ADMIN_URL + "/diogeneConfiguration";
  private PROCEDIMENTI_URL = this.BASE_ADMIN_URL + "/procedimenti";
  private DICHIARAZIONE_URL = this.BASE_ADMIN_URL + "/procedimenti/{id}/dichiarazione";
  private GET_CONFERENZA_SERVIZI_URL = this.BASE_ADMIN_URL + "/procedimenti/{id}/get-conferenza-servizi";
  private SAVE_CONFERENZA_SERVIZI_URL = this.BASE_ADMIN_URL + "/procedimenti/{id}/save-conferenza-servizi";
  private BASE_ADMIN_DISCLAIMER_URL = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/admin/disclaimer";
  // -- admin disclaimer procedimento
  private DISCLAIMER_URL = this.BASE_ADMIN_DISCLAIMER_URL + "/procedimenti/{id}/disclaimer";
  private TARIFFE_URL = this.PROCEDIMENTI_URL + "/{id}/tariffe";
  private SAVE_TARIFFE_URL = this.PROCEDIMENTI_URL + "/save-tariffe";

  constructor(private http: HttpClient) { }

  // -- Rubrica Istituzionale --
  public ricercaContatti(filters: RubricaIstituzionaleSearch): Observable<BaseResponse<PaginatedBaseResponse<DestinatarioIstituzionaleDto>>> {
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<DestinatarioIstituzionaleDto>>>(this.URL_RUBRICA_IST, { params: params });
  }
  public modificaContatto(contatto: DestinatarioIstituzionaleDto): Observable<BaseResponse<boolean>> {
    return this.http.put<BaseResponse<boolean>>(this.URL_MOD_RUB_IST, contatto);
  }

  // -- Servizi di Integrazione --
  public findServiziIntegrazione(): Observable<BaseResponse<Configurazione>> {
    return this.http.get<BaseResponse<Configurazione>>(this.URL_SI_FIND);
  }
  public saveServiziIntegrazione(conf: Configurazione): Observable<BaseResponse<Configurazione>> {
    return this.http.post<BaseResponse<Configurazione>>(this.URL_SI_SAVE, conf);
  }
  // true se ha una configurazione automatica per il servizio di protocollo della mia organizzazione
  public hasProtocolloAutomatico(idOrganizzazione:number): Observable<BaseResponse<boolean>> {
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("idOrganizzazione", idOrganizzazione+"");
    return this.http.get<BaseResponse<boolean>>(this.URL_HAS_PROTOCOLLO,{ params: parameters });
  }

  //-- gestione censimento rup
  public getRupEnte(search: any): Observable<BaseResponse<PaginatedList<RupDTO>>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/rup/search";
    return this.http.get<BaseResponse<PaginatedList<RupDTO>>>(url, { params: toHttpParams(search) });
  }

  //-- gestione censimento rup

  /*public getAllRup(): Observable<RupGruppoMocked[]> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/admin/rup/getAll";
    return this.http.get<RupGruppoMocked[]>(url);
  }*/

  public getPotenzialiRup(): Observable<BaseResponse<PlainTypeStringId[]>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/rup/potenzialiRup";
    return this.http.get<BaseResponse<PlainTypeStringId[]>>(url);
  }

  public deleteRup(username: string): Observable<BaseResponse<number>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/rup/deleteRup";
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("username", username);
    return this.http.delete<BaseResponse<number>>(url, { params: parameters });
  }

  public addRup(container: RupDTO): Observable<BaseResponse<number>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/rup/addRup";
    return this.http.post<BaseResponse<number>>(url, container);
  }

  //-- template comunicazioni
  public searchTemplateEmail(search: any): Observable<BaseResponse<PaginatedList<TemplateEmailDTO>>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/search";
    return this.http.get<BaseResponse<PaginatedList<TemplateEmailDTO>>>(url, { params: toHttpParams(search) });
  }

  public infoTemplateEmail(codice: string): Observable<BaseResponse<TemplateEmailDestinatariDto>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/info";
    return this.http.get<BaseResponse<TemplateEmailDestinatariDto>>(url, { params: { codice: codice } });
  }

  public deleteTemplateEmail(codice: string): Observable<BaseResponse<number>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/delete";
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("codice", codice);
    return this.http.delete<BaseResponse<number>>(url, { params: parameters });
  }

  public saveTemplateEmail(container: TemplateEmailDestinatariDto): Observable<BaseResponse<TemplateEmailDestinatariDto>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/saveOrInsert";
    return this.http.post<BaseResponse<TemplateEmailDestinatariDto>>(url, container);
  }

  /**
   * restituisce il TemplateEmailDestinatariDto con alcuni dati default già settati
   */
  public getNewTemplateEmail(): Observable<BaseResponse<TemplateEmailDestinatariDto>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/getNew";
    return this.http.get<BaseResponse<TemplateEmailDestinatariDto>>(url);
  }

  
  /**
   * restituisce i TemplateEmailDestinatariDto di tutti i template dell'organizzazione
   */
  public getTemplatDestEmailList(sezione:string,idPratica:string): Observable<BaseResponse<Array<TemplateEmailDestinatariDto>>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateEmail/searchTemplate";
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("sezione", sezione);
    parameters = parameters.append("idPratica", idPratica);
    return this.http.get<BaseResponse<Array<TemplateEmailDestinatariDto>>>(url,{ params: parameters });
  }

  // -- Template di documentazione --
  public getAllDoc(): Observable<BaseResponse<TemplateDocDTO[]>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/getAll";
    return this.http.get<BaseResponse<TemplateDocDTO[]>>(url);
  }
  public getAllDocPaginated(filters: TemplateDocSearch): 
      Observable<BaseResponse<PaginatedBaseResponse<TemplateDocDTO>>> {
        let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/getAllPaginated";
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<TemplateDocDTO>>>(
      url, { params: params });
  }

  public saveTemplateDoc(template: TemplateDocDTO): Observable<BaseResponse<boolean>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/salva";
    return this.http.post<BaseResponse<boolean>>(url, template);
  }
  public resetTemplateDoc(nome: string): Observable<BaseResponse<TemplateDocDTO>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/reset";
    return this.http.post<BaseResponse<TemplateDocDTO>>(
      url, null, { params: new HttpParams().set("nome", nome) });
  }

  public getInfoDoc(nomeTemplate: string): Observable<BaseResponse<TemplateDocDTO>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/info";
    return this.http.get<BaseResponse<TemplateDocDTO>>(
      url, { params: new HttpParams().set("nome", nomeTemplate) })
  };
  public getPreview(nomeTemplate: string): Observable<HttpResponse<Blob>> {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/downloadAnteprima";
    let httpParams: HttpParams = new HttpParams().set("nome", nomeTemplate);
    return this.http.post(url, httpParams, { observe: 'response', responseType: 'blob' });
  }

  public eliminaImage(id: string): Observable<BaseResponse<any>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/eliminaImage";
    let params: HttpParams = new HttpParams().set("idAllegato", id);
    return this.http.delete<BaseResponse<any>>(url, {params: params});
  }

  public uploadLogo(input: FormData): Promise<BaseResponse<AllegatiDTO>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/carica";
    return this.http.post<BaseResponse<AllegatiDTO>>( url, input)
      .toPromise().then(data => data);
  }

  public callDownloadLogo(idAllegato: string): Observable<HttpResponse<Blob>>
  {
    let url = CONST.WEB_RESOURCE_BASE_URL + "/istruttoria/templateDoc/downloadLogo";
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: new HttpParams().set('idAllegato', idAllegato) });
  }

  //---------------fine template di documentazione

  // -- Gestione commissione locale --
  public callFindCL(idCommissione: number): Observable<BaseResponse<CommissioneLocaleOrganizzazione>>
  {
    let params = { params: new HttpParams().set('idCommissione', idCommissione.toString()) };
    return this.http.get<BaseResponse<CommissioneLocaleOrganizzazione>>(this.URL_FIND_CL, params);
  }

  public callPopulate(): Observable<BaseResponse<Container>>
  {
    return this.http.get<BaseResponse<Container>>(this.URL_POPULATE);
  }

  public callSearchCL(search: CLSearch): Observable<BaseResponse<PaginatedList<CommissioneLocaleOrganizzazione>>>
  {
    let params = toHttpParams(search);
    if(search.dataValiditaDa)
      params = params.append('dataValiditaDa', new Date(search.dataValiditaDa).toDateString());
    if (search.dataValiditaA)
      params = params.append('dataValiditaA', new Date(search.dataValiditaA).toDateString());
    return this.http.get<BaseResponse<PaginatedList<CommissioneLocaleOrganizzazione>>>(this.URL_SEARCH_CL, {params});
  }

  public callSaveCL(entity: CommissioneLocaleOrganizzazione): Observable<BaseResponse<CommissioneLocaleOrganizzazione>>
  {
    return this.http.post<BaseResponse<CommissioneLocaleOrganizzazione>>(this.URL_INSERT_CL, entity);
  }

  public callUpdateCL(entity: CommissioneLocaleOrganizzazione): Observable<BaseResponse<CommissioneLocaleOrganizzazione>>
  {
    return this.http.put<BaseResponse<CommissioneLocaleOrganizzazione>>(this.URL_UPDATE_CL, entity);
  }

  public callAddUser(username: string, idCommissione: number): Observable<BaseResponse<boolean>>
  {
    let params = { params: new HttpParams().set("username", username).set("idCommissione", idCommissione.toString()) };
    return this.http.post<BaseResponse<boolean>>(this.URL_ADD_USER, null, params);
  }

  public callRemoveUser(username: string, idCommissione: number): Observable<BaseResponse<boolean>>
  {
    let params = { params: new HttpParams().set("username", username).set("idCommissione", idCommissione.toString()) };
    return this.http.delete<BaseResponse<boolean>>(this.URL_DEL_USER, params);
  }

  public callFindUsersCL(idCommissione: number): Observable<BaseResponse<Array<PlainTypeStringId>>>
  {
    let params = { params: new HttpParams().set('idCommissione', idCommissione.toString()) };
    return this.http.get<BaseResponse<Array<PlainTypeStringId>>>(this.URL_FIND_USERS, params);
  }

  public callCheckDate(idCommissione: number, enti: number[], dataInizioVal: Date, dataTermineVal: Date): Observable<BaseResponse<Array<EntiCommissioni>>>
  {
    let params = new HttpParams();
    enti.forEach(e => params = params.append('enti', e.toString()));
    params = params.append('dataInizioVal', dataInizioVal.toDateString());
    params = params.append('dataFineVal', dataTermineVal.toDateString());
    if(idCommissione)
      params = params.append('idCommissione', idCommissione.toString());
    return this.http.get<BaseResponse<Array<EntiCommissioni>>>(this.URL_CHECK_DATE, {params});
  }

  // -- configurazione sezioni catastali ricerca elementi --
  public ricercaSezioniCatastali(filters: SezioneCatastaleSearch): Observable<BaseResponse<PaginatedBaseResponse<SelectOption>>> {
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<SelectOption>>>(this.URL_SEZIONI_CATASTALI, { params: params });
  }


  // --  configurazione sezioni catastali salva o inserisci item
  public saveOrUpdateSezioneCatastale(item:SelectOption): Observable<BaseResponse<number>>
  {
    return this.http.post<BaseResponse<number>>(this.URL_SEZIONI_CATASTALI_SAVE_OR_UPDATE, item);
  }

  // --  configurazione sezioni catastali elimina item
  public deleteSezioneCatastale(item: SelectOption): Observable<BaseResponse<number>> {
    let parameters: HttpParams = new HttpParams();
    parameters = parameters.append("codCatastale", item.value+'');
    parameters = parameters.append("sezione", item.linked);
    return this.http.delete<BaseResponse<number>>(this.URL_SEZIONI_CATASTALI_DELETE, { params: parameters });
  }

  // web content configurabili 
  public selectWebContentConfigurabili(): Observable<BaseResponse<WebContentDTO[]>>{
    return this.http.get<BaseResponse<WebContentDTO[]>>(this.BASE_WEB_CONTENT_CONFIG+'/select.pjson', {});
  } 

  public updateWebContentConfigurabili(messaggi:WebContentDTO[]): Observable<BaseResponse<number>>{
    return this.http.post<BaseResponse<number>>(this.BASE_WEB_CONTENT_CONFIG+'/updateSelected.pjson', messaggi);
  }

  /**
   * fecth diogene configuration
   * @returns 
   */
 public getDiogeneConfiguration(): Observable<BaseResponse<DiogeneApplicationConfigbean>>
 {
   return this.http.get<BaseResponse<DiogeneApplicationConfigbean>>(this.DIOGENE_CONFIGURATION);
 }

 /**
  * upsert configurazione
  * @param bean 
  * @returns 
  */
 public upsertDiogeneConfiguration(bean: DiogeneApplicationConfigbean): Observable<BaseResponse<DiogeneApplicationConfigbean>>
 {
   return this.http.post<BaseResponse<DiogeneApplicationConfigbean>>(this.DIOGENE_CONFIGURATION, bean);
 }
 
 /**
  * lista dei procedimenti dell'applicativo
  * @returns 
  */
 public listProcedimenti(): Observable<BaseResponse<TipoProcedimento[]>>
 {
   return this.http.get<BaseResponse<TipoProcedimento[]>>(this.PROCEDIMENTI_URL);
 }

 public updateProcedimento(id:string,bean:TipoProcedimento): Observable<BaseResponse<number>>
 {
  return this.http.put<BaseResponse<number>>(this.PROCEDIMENTI_URL+"/"+id, bean);
 }

 /**
  * dettaglio tipo procedimento
  * @param idTipoProcedimento 
  * @returns 
  */
 public getConfProcedimento(idTipoProcedimento: string): Observable<BaseResponse<TipoProcedimento>>
 {
   return this.http.get<BaseResponse<TipoProcedimento>>(this.PROCEDIMENTI_URL+"/"+idTipoProcedimento);
 }

 /**
  * preleva il dettaglio della configurazione corrente
  * @param idTipoProcedimento 
  * @returns 
  */
 public getDichiarazioneCorrente(idTipoProcedimento: string): Observable<BaseResponse<DichiarazioneDTO>>
 {
   return this.http.get<BaseResponse<DichiarazioneDTO>>(this.DICHIARAZIONE_URL.replace("{id}",idTipoProcedimento));
 }


 /**
  * aggiorna la configurazione corrente
  * @param id id procedimento
  * @param bean 
  * @returns 
  */
 public updateDichiarazioneCorrente(id:string,bean:DichiarazioneDTO): Observable<BaseResponse<number>>
 {
  return this.http.put<BaseResponse<number>>(this.DICHIARAZIONE_URL.replace("{id}",id), bean);
 }


 /**
  *  get disclaimer correnti per il tipo procedimento in input
  * @param idTipoProcedimento 
  * @returns 
  */
 public getDisclaimersCorrente(idTipoProcedimento: string): Observable<BaseResponse<DisclaimerDTO[]>>
 {
   return this.http.get<BaseResponse<DisclaimerDTO[]>>(this.DISCLAIMER_URL.replace("{id}",idTipoProcedimento));
 }

 /**
  * effettua update su data_fine  e insert 
  * @param id 
  * @param disclaimers 
  * @returns disclaimers attuali
  */
 public updateDisclaimersCorrente(id:string,disclaimers:DisclaimerDTO[]): Observable<BaseResponse<DisclaimerDTO[]>>
 {
  return this.http.post<BaseResponse<DisclaimerDTO[]>>(this.DISCLAIMER_URL.replace("{id}",id), disclaimers);
 }

 public getTariffeCorrente(idTipoProcedimento: string): Observable<BaseResponse<TariffaDTO[]>>
 {
   return this.http.get<BaseResponse<TariffaDTO[]>>(this.TARIFFE_URL.replace("{id}",idTipoProcedimento));
 }

 public saveTariffe(tariffe: TariffaDTO[]): Observable<BaseResponse<TariffaDTO[]>>
 {
   return this.http.post<BaseResponse<TariffaDTO[]>>(this.SAVE_TARIFFE_URL, tariffe);
 }

 
public getConfigurazioneCds(idTipoProcedimento: string): Observable<BaseResponse<CdsGetConfigurazione>>
{
  return this.http.get<BaseResponse<CdsGetConfigurazione>>(this.GET_CONFERENZA_SERVIZI_URL.replace("{id}",idTipoProcedimento));
}

public saveConfigurazioneCds(id:string,bean:CdsSaveConfigurazione): Observable<BaseResponse<CdsGetConfigurazione>>
{
return this.http.post<BaseResponse<CdsGetConfigurazione>>(this.SAVE_CONFERENZA_SERVIZI_URL.replace("{id}",id), bean);
} 

}
