import { EmailOrganizzazioneBean, EmailOrganizzazioneSearch, EmailOrganizzazioneBaseBean } from './../../features/admin-utils/admin-module/components/gestione-destinatari-trasmissione/conf/gestione-destinatari-trasmissione-conf';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { toHttpParams } from 'src/app/components/functions/genericFunctions';
import { BaseResponse } from 'src/app/components/model/base-response';
import { DettaglioTemplate, TemplateDocDTO, TipoTemplate } from 'src/app/components/model/entity/admin.models';
import { TipologicaDTO } from 'src/app/components/model/entity/localizzazione.models';
import { TipoProcedimentoDTO } from 'src/app/components/model/entity/tipoProcedimento.models';
import { EnteSummary, OrganizzazioneExtendedBean, TipoOrganizzazione, TipoOrganizzazioneSpecifica } from 'src/app/features/admin-utils/admin-module/components/censimento-organizzazione/conf/conf-organizzazione';
import { CONST } from './../../../shared/constants';
import { DestinatarioIstituzionaleDto, RubricaIstituzionaleSearch } from './../../components/model/entity/corrispondenza.models';
import { FascicoloDTO, FascicoloSearch, TemplateDocSearch } from './../../components/model/entity/fascicolo.models';
import { ConfigurazioneProtocollo, DiogeneApplicationConfigbean, PannelloAmministratore } from './../../components/model/model';
import { PaginatedBaseResponse } from './../../components/model/paginated-base-response';
import { OrganizzaizoneBean, OrganizzaizoneSearchBean, OrganizzazioneAbilitazioneBean } from './../../features/admin-utils/admin-module/components/censimento-organizzazione/conf/conf-organizzazione';
import { ConfigurazionePECBean } from './../../features/admin-utils/admin-module/components/gestione-pec/data-conf';
import { VPaesaggioTipoCaricaDocumentoDTO } from 'src/app/components/model/admin-function/admin-function-model';

@Injectable({
  providedIn: "root"
})
export class AdminService 
{
  // -- Pannello di configurazoe --
  private BASE_ADMIN_URL      = CONST.WEB_RESOURCE_BASE_URL + "/amministratore/";
  private URL_ADMIN_PANEL     = this.BASE_ADMIN_URL + "infoPannello";
  private URL_LISTA_GRP       = this.BASE_ADMIN_URL + "listaGruppi";
  private URL_SAVE            = this.BASE_ADMIN_URL + "savePannello";
  private AUTOCOMPLETE_CODICE = this.BASE_ADMIN_URL + "autocompleteCodice";
  // -- Rubrica --
  private BASE_RUBRICA_URL = CONST.WEB_RESOURCE_BASE_URL + "/rubrica/";
  private URL_RUBRICA_IST = this.BASE_RUBRICA_URL + "istituzionaleAdminSearch";
  private URL_MOD_RUB_IST = this.BASE_RUBRICA_URL + "istituzionaleAdminUpdate";
  // -- Template comunicazione --
  private BASE_TEMPLATE_URL = CONST.WEB_RESOURCE_BASE_URL + "/templateEmail/";
  private FIND_TEMPLATE     = this.BASE_TEMPLATE_URL + "info";
  private GET_ALL           = this.BASE_TEMPLATE_URL + "getAll";
  private SAVE_TEMPLATE     = this.BASE_TEMPLATE_URL + "salva";
  private RESET_EMAIL       = this.BASE_TEMPLATE_URL + "resetEmail";
  private RESET_DEST        = this.BASE_TEMPLATE_URL + "resetDestinatari";
  // -- Template documentazione --
  private BASE_TEMPLATE_DOC_URL = CONST.WEB_RESOURCE_BASE_URL + "/templateDoc/";
  private SAVE_TEMPLATE_DOC = this.BASE_TEMPLATE_DOC_URL + "salva";
  private RESET = this.BASE_TEMPLATE_DOC_URL + "reset";
  private INFO = this.BASE_TEMPLATE_DOC_URL + "info";
  private GET_ALL_DOC           = this.BASE_TEMPLATE_DOC_URL + "getAll";
  private GET_ALL_DOC_PAGINATED = this.BASE_TEMPLATE_DOC_URL + "getAllPaginated";
  private DELETE_IMAGE_TEMPLATE = this.BASE_TEMPLATE_DOC_URL +"eliminaImage";
  private DOWNLOAD_IMAGE_TEMPLATE = this.BASE_TEMPLATE_DOC_URL + "downloadImage";
  private GET_PREVIEW = this.BASE_TEMPLATE_DOC_URL + "download_Anteprima";
  
  // -- Configurazione PEC --
  private SAVE_OR_UPDATE      = this.BASE_ADMIN_URL + "savePecConfiguration";
  private RESET_CONFIGURATION = this.BASE_ADMIN_URL + "resetPecConfiguration";
  private GET_CONFIGURATION   = this.BASE_ADMIN_URL + "getPecConfiguration";

  //Configurazione tipi procedimento
  private BASE_TIPI_PROCEDIMENTO_URL = CONST.WEB_RESOURCE_BASE_URL + "/tipo-procedimento/";
  private GET_ALL_TIPI_PROCEDIMENTO     = this.BASE_TIPI_PROCEDIMENTO_URL + "getAll";
  private UPDATE_TIPO_PROCEDIMENTO     = this.BASE_TIPI_PROCEDIMENTO_URL + "updateValidita";
  private GET_TIPI_PROCEDIMENTO_WITH_ATTRIBUTES= this.BASE_TIPI_PROCEDIMENTO_URL + "getAllByCurrentApp";

  // -- Annullamento fascicolo --
  private ANNULLA_FASCICOLO          = this.BASE_ADMIN_URL + "annullaFascicolo";
  private GET_FASCICOLO_DA_ANNULLARE = this.BASE_ADMIN_URL + "getFascicoloDaAnnullare";
  // -- Attivazione/Disattivazione modifica fascicolo --
  private GET_FASCICOLO_DA_MODIFICARE = this.BASE_ADMIN_URL + "getFascicoloDaModificare";
  private MODIFICA_FASCICOLO          = this.BASE_ADMIN_URL + "modificaFascicolo";
  private GET_FASCICOLI_IN_MODIFICA   = this.BASE_ADMIN_URL + "getFascicoliInModifica";
  private REVOCA_MODIFICA             = this.BASE_ADMIN_URL + "revocaModifica";
  //-- configurazione protocollo --
  private GET_CONF_PROTOCOLLO = this.BASE_ADMIN_URL + "getProtocolloConf";
  private UPDATE_CONF_PROTOCOLLO = this.BASE_ADMIN_URL + "updateProtocolloConf";
  
  // -- Gestione organizzazioni
  private URL_DROPDOWN_ENTI       = this.BASE_ADMIN_URL + "dropdownEntiOrganizzazioneTerritori";  
  private URL_DROPDOWN_ENTI_ASSOC = this.BASE_ADMIN_URL + "dropdownEntiOrganizzazioneAssociazione";
  private URL_SEARCH_ORG          = this.BASE_ADMIN_URL + "searchOrganizzazioni";
  private URL_FIND_DETAILS        = this.BASE_ADMIN_URL + "findOrganizzazione";
  private URL_INSERT_ORG          = this.BASE_ADMIN_URL + "insertOrganizzazione";
  private URL_UPDATE_ORG          = this.BASE_ADMIN_URL + "updateOrganizzazione";
  private URL_ENABLE_ORG          = this.BASE_ADMIN_URL + "attivaOrganizzazione";
  private URL_DISABLE_ORG         = this.BASE_ADMIN_URL + "disattivaOrganizzazione";
  private URL_AGG_EN_ORG          = this.BASE_ADMIN_URL + "modificaTermineAbilitazione";

  //gestione mail organizzazione
  private URL_GET_ORGS       = this.BASE_ADMIN_URL + "searchOrganizzazioniByTipi";
  private URL_ENTI_ORGANIZ   = this.BASE_ADMIN_URL + "getAllEntiByOrganizzazione";
  private URL_MAIL_ENTE_ORG  = this.BASE_ADMIN_URL + "getMailEnteOrganizzazione";
  private URL_INSERT_EMAIL   = this.BASE_ADMIN_URL + "saveEmailOrganizzazione";
	private URL_UPDATE_EMAIL   = this.BASE_ADMIN_URL + "updateEmailOrganizzazione";
	private URL_DELETE_EMAIL   = this.BASE_ADMIN_URL + "deleteEmailOrganizzazione";

  //diogene configuration
  private DIOGENE_CONFIGURATION   = this.BASE_ADMIN_URL + "diogeneConfiguration";

  //download manuale
  private DOWNLOAD_MANUALE_ADMIN = this.BASE_ADMIN_URL +"downloadManuale";

  //sportello configuration
  private TIPI_DOCUMENTO_SPORTELLO   = this.BASE_ADMIN_URL + "tipiDocSportello";
  private OPTIONS_TIPI_DOCUMENTO_SPORTELLO   = this.BASE_ADMIN_URL + "optionsTipiDocSportello";


  constructor(private http: HttpClient) { }

  // -- Pannello di configurazione --
  public getPanelInfo(): Observable<BaseResponse<PannelloAmministratore>> { return this.http.get<BaseResponse<PannelloAmministratore>>(this.URL_ADMIN_PANEL); }
  public listaGruppi(): Observable<BaseResponse<TipologicaDTO[]>> { return this.http.get<BaseResponse<TipologicaDTO[]>>(this.URL_LISTA_GRP); }
  public saveAdminPanel(panel: PannelloAmministratore): Observable<BaseResponse<boolean>> { return this.http.post<BaseResponse<boolean>>(this.URL_SAVE, panel); }

  // -- Template di comunicazione --
  public findTemplates(codice: TipoTemplate): Observable<BaseResponse<DettaglioTemplate>> { return this.http.get<BaseResponse<DettaglioTemplate>>(this.FIND_TEMPLATE, { params: new HttpParams().set("codice", codice.toString()) }); }
  public getAll(): Observable<BaseResponse<DettaglioTemplate[]>> { return this.http.get<BaseResponse<DettaglioTemplate[]>>(this.GET_ALL); }
  public saveTemplate(template: DettaglioTemplate): Observable<BaseResponse<boolean>> { return this.http.post<BaseResponse<boolean>>(this.SAVE_TEMPLATE, template); }
  public resetEmail(codice: TipoTemplate): Observable<BaseResponse<DettaglioTemplate>> { return this.http.post<BaseResponse<DettaglioTemplate>>(this.RESET_EMAIL, null, {params: new HttpParams().set("codice", codice.toString())}); }
  public resetDestinatari(codice: TipoTemplate): Observable<BaseResponse<DettaglioTemplate>> { return this.http.post<BaseResponse<DettaglioTemplate>>(this.RESET_DEST, null, { params: new HttpParams().set("codice", codice.toString()) }); }

  // -- Template di documentazione --
  public getAllDoc(): Observable<BaseResponse<TemplateDocDTO[]>> { return this.http.get<BaseResponse<TemplateDocDTO[]>>(this.GET_ALL_DOC); }
  public getAllDocPaginated(filters: TemplateDocSearch): Observable<BaseResponse<PaginatedBaseResponse<TemplateDocDTO>>>{ let params: HttpParams = toHttpParams(filters); return this.http.get<BaseResponse<PaginatedBaseResponse<TemplateDocDTO>>>(this.GET_ALL_DOC_PAGINATED, {params: params });}
  public saveTemplateDoc(template: TemplateDocDTO):Observable<BaseResponse<boolean>>{return this.http.post<BaseResponse<boolean>>(this.SAVE_TEMPLATE_DOC, template);}
  public resetTemplateDoc(nome: string): Observable<BaseResponse<TemplateDocDTO>>{return this.http.post<BaseResponse<TemplateDocDTO>>(this.RESET, null, {params: new HttpParams().set("nome",nome)}); }
  public getInfoDoc(nomeTemplate:string): Observable<BaseResponse<TemplateDocDTO>>{return this.http.get<BaseResponse<TemplateDocDTO>>(this.INFO,{params: new HttpParams().set("nome",nomeTemplate)})};
  public getPreview(nomeTemplate:string): Observable<HttpResponse<Blob>>{
    let httpParams: HttpParams = new HttpParams().set("nome",nomeTemplate);
    return this.http.post(this.GET_PREVIEW, httpParams ,{observe:'response', responseType: 'blob'});
  }


  //----------------------- Configurazione tipi procedimento --------------------------
  public getAllTipiProcedimento(): Promise<BaseResponse<TipoProcedimentoDTO[]>> { 
    return this.http.get<BaseResponse<TipoProcedimentoDTO[]>>(this.GET_ALL_TIPI_PROCEDIMENTO)
    .toPromise()
    .then(response => { return response });
  }

  /**
   * 
   * @returns Restituisce lista di tutti i tipi procedimento (anche quelli scaduti) con tutte le informazioni
   */
  public getTipiProcedimentoWithAttributes(): Promise<BaseResponse<TipoProcedimentoDTO[]>> { 
    return this.http.get<BaseResponse<TipoProcedimentoDTO[]>>(this.GET_TIPI_PROCEDIMENTO_WITH_ATTRIBUTES)
    .toPromise()
    .then(response => { return response });
  }

  public updateTipoProcedimento(entity:TipoProcedimentoDTO): Promise<BaseResponse<string>> {
    return this.http.post<BaseResponse<string>>(this.UPDATE_TIPO_PROCEDIMENTO, entity)
      .toPromise()
      .then(response => { return response });
  }

  //-----------------------------------------------------------------------------------


  public annullaFascicolo(idFascicolo: number): Observable<BaseResponse<boolean>> {
    let url: string = this.ANNULLA_FASCICOLO;
    let params: HttpParams = new HttpParams();
    params = params.append("id", idFascicolo.toString());
    return this.http.post<BaseResponse<boolean>>(url, null, { params: params });
  }

  public getFascicoloDaAnnullare(codiceFascicolo: string): Observable<BaseResponse<FascicoloDTO>> {
    let url: string = this.GET_FASCICOLO_DA_ANNULLARE;
    let params: HttpParams = new HttpParams();
    params = params.append("codice", codiceFascicolo);
    return this.http.get<BaseResponse<FascicoloDTO>>(url, { params: params });
  }

  public getFascicoloDaModificare(codiceFascicolo: string): Observable<BaseResponse<FascicoloDTO>> {
    let url: string = this.GET_FASCICOLO_DA_MODIFICARE;
    let params: HttpParams = new HttpParams();
    params = params.append("codice", codiceFascicolo);
    return this.http.get<BaseResponse<FascicoloDTO>>(url, { params: params });
  }

  public modificaFascicolo(idFascicolo: string, giorni: number): Observable<BaseResponse<boolean>> {
    let url: string = this.MODIFICA_FASCICOLO;
    let params: HttpParams = new HttpParams();
    params = params.append("id", idFascicolo);
    params = params.append("giorni", giorni.toString());
    return this.http.post<BaseResponse<boolean>>(url, null, { params: params });
  }

  public autocompleteCodice(codiceFascicolo: string,
                            forAnnullamento?:boolean,
                            forModifica?:boolean,
                            inModifica?:boolean): Observable<BaseResponse<string[]>> {
    let url: string = this.AUTOCOMPLETE_CODICE;
    let params: HttpParams = new HttpParams();
    params = params.append("codice", codiceFascicolo);
    if(forAnnullamento){
      params = params.append("isAnnullabile", "true");
    }
    if(forModifica){
      params = params.append("isModificabile", "true");
    }
    if(inModifica){
      params = params.append("isInModifica", "true");
    }
    return this.http.get<BaseResponse<string[]>>(url, { params: params });
  }

  public getFascicoliInModifica(filters: FascicoloSearch): Observable<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>> {
    let url: string = this.GET_FASCICOLI_IN_MODIFICA;
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key => {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>>(url, {params: httpParams});
  }

  public revocaModifica(idFascicolo: string): Observable<BaseResponse<boolean>> {
    let url: string = this.REVOCA_MODIFICA;
    let params: HttpParams = new HttpParams();
    params = params.append("id", idFascicolo);
    return this.http.post<BaseResponse<boolean>>(url, null, { params: params });
  }

  // -- modifica rubrica --
  public ricercaContatti(filters: RubricaIstituzionaleSearch): Observable<BaseResponse<PaginatedBaseResponse<DestinatarioIstituzionaleDto>>>
  {
    let params: HttpParams = toHttpParams(filters);
    return this.http.get<BaseResponse<PaginatedBaseResponse<DestinatarioIstituzionaleDto>>>(this.URL_RUBRICA_IST, {params: params});
  }

  public modificaContatto(contatto: DestinatarioIstituzionaleDto): Observable<BaseResponse<boolean>>
  {
    return this.http.put<BaseResponse<boolean>>(this.URL_MOD_RUB_IST, contatto);
  }

  public eliminaImage(id: string): Observable<BaseResponse<any>>
  {
    let url: string = this.DELETE_IMAGE_TEMPLATE;
    let params: HttpParams = new HttpParams().set("idAllegato", id);
    return this.http.delete<BaseResponse<any>>(url, {params: params});
  }

   public downloadImage(id: string): Observable<HttpResponse<Blob>>
   {
     let url: string = this.DOWNLOAD_IMAGE_TEMPLATE;
     return this.http.get(url, { observe: 'response', responseType: 'blob', params: new HttpParams().set('idAllegato', id) });
   }

  public saveOrUpdateConfigurationPec(bean: ConfigurazionePECBean): Observable<BaseResponse<boolean>>
  {
    return this.http.post<BaseResponse<boolean>>(this.SAVE_OR_UPDATE, bean);
  }

  public resetConfigurationPec(): Observable<BaseResponse<ConfigurazionePECBean>>
  {
    return this.http.post<BaseResponse<ConfigurazionePECBean>>(this.RESET_CONFIGURATION, null);
  }

  public getConfigurationPec(): Observable<BaseResponse<ConfigurazionePECBean>>
  {
    return this.http.get<BaseResponse<ConfigurazionePECBean>>(this.GET_CONFIGURATION);

  }

  /**
   * Get Configurazione Protocollo
   */
  public getCongifurazioneProtocollo(): Promise<BaseResponse<ConfigurazioneProtocollo>> {
    return this.http.get<BaseResponse<ConfigurazioneProtocollo>>(this.GET_CONF_PROTOCOLLO)
      .toPromise()
      .then(response => { return response });
  }
  /**
   * Salva configurazione protocollo
   * @param entity 
   */
  public salvaConfigurazioneProtocollo(entity : ConfigurazioneProtocollo): Promise<BaseResponse<string>> {
    return this.http.post<BaseResponse<string>>(this.UPDATE_CONF_PROTOCOLLO, entity)
      .toPromise()
      .then(response => { return response });
  }

  public dropdownOrganizzazioneEntiTerritori(tipo: TipoOrganizzazione): Observable<BaseResponse<Array<EnteSummary>>>
  {
    let params: HttpParams = new HttpParams().set('tipo', tipo);
    return this.http.get<BaseResponse<Array<EnteSummary>>>(this.URL_DROPDOWN_ENTI, {params});
  }

  public dropdownOrganizzazioneEntiAssociazione(tipo?: TipoOrganizzazione): Observable<BaseResponse<Array<EnteSummary>>>
  {
    let params: HttpParams = new HttpParams();
    if(tipo)
      params = params.append('tipo', tipo);
    return this.http.get<BaseResponse<Array<EnteSummary>>>(this.URL_DROPDOWN_ENTI_ASSOC, {params});
  }

  public searchConfigurazioni(search: OrganizzaizoneSearchBean): Observable<BaseResponse<PaginatedBaseResponse<OrganizzaizoneBean>>>
  {
    let params: HttpParams = new HttpParams();
    Object.keys(search).forEach(key => 
    {
      if(search[key])
      {
        switch(key)
        {
          case 'dataCreazioneDa':
          case 'dataCreazioneA':
          case 'dataTermineDa':
          case 'dataTermineA':
            params = params.append(key, new Date(search[key]).toDateString());
          default:
            params = params.append(key, search[key].toString());
        }
      }
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<OrganizzaizoneBean>>>(this.URL_SEARCH_ORG, {params});
  }

  public findOrganizzazioneDetails(id: number): Observable<BaseResponse<OrganizzazioneExtendedBean>>
  {
    let params: HttpParams = new HttpParams().set('idOrganizzazione', id.toString());
    return this.http.get<BaseResponse<OrganizzazioneExtendedBean>>(this.URL_FIND_DETAILS, {params});
  }

  /* 
    private URL_INSERT_ORG    = this.BASE_ADMIN_URL + "insertOrganizzazione";
    private URL_UPDATE_ORG    = this.BASE_ADMIN_URL + "updateOrganizzazione";
    private URL_ENABLE_ORG    = this.BASE_ADMIN_URL + "attivaOrganizzazione";
    private URL_DISABLE_ORG   = this.BASE_ADMIN_URL + "disattivaOrganizzazione";
  */
  public insertOrganizzazione(bean: OrganizzazioneExtendedBean): Observable<BaseResponse<OrganizzazioneExtendedBean>>
  {
    return this.http.post<BaseResponse<OrganizzazioneExtendedBean>>(this.URL_INSERT_ORG, bean);
  }

  public updateOrganizzazione(bean: OrganizzazioneExtendedBean): Observable<BaseResponse<OrganizzazioneExtendedBean>>
  {
    return this.http.put<BaseResponse<OrganizzazioneExtendedBean>>(this.URL_UPDATE_ORG, bean);
  }

  public enableOrganizzazione(bean: OrganizzazioneAbilitazioneBean): Observable<BaseResponse<boolean>>
  {
    return this.http.post<BaseResponse<boolean>>(this.URL_ENABLE_ORG, bean);
  }

  public disableOrganizzazione(idOrganizzazione: number): Observable<BaseResponse<boolean>>
  {
    let params: HttpParams = new HttpParams().set('idOrganizzazione', idOrganizzazione.toString());
    return this.http.get<BaseResponse<boolean>>(this.URL_DISABLE_ORG, {params});
  }

  public aggEnableOrganizzazione(bean: OrganizzazioneAbilitazioneBean): Observable<BaseResponse<boolean>>
  {
    return this.http.put<BaseResponse<boolean>>(this.URL_AGG_EN_ORG, bean);
  }


  //gestione mail organizzazioni
  public searchOrganizzazioneByTipo(tipi: Array<TipoOrganizzazioneSpecifica>): Observable<BaseResponse<Array<OrganizzaizoneBean>>>
  {
    let params: HttpParams = new HttpParams();
    for(let t of tipi)
      params = params.append('tipi', t);
    return this.http.get<BaseResponse<Array<OrganizzaizoneBean>>>(this.URL_GET_ORGS, {params});
  }

  public listEntiByOrganizzazione(idOrganizzazione: number, isSoprintendenza: boolean): Observable<BaseResponse<Array<EnteSummary>>>
  {
    let params: HttpParams = new HttpParams().set('idOrganizzazione', idOrganizzazione.toString())
                                             .set('isSoprintendenza', isSoprintendenza ? 'true' : 'false');
    return this.http.get<BaseResponse<Array<EnteSummary>>>(this.URL_ENTI_ORGANIZ, {params});
  }

  public listMailOrganizzazione(search: EmailOrganizzazioneSearch): Observable<BaseResponse<PaginatedBaseResponse<EmailOrganizzazioneBean>>>
  {
    return this.http.post<BaseResponse<PaginatedBaseResponse<EmailOrganizzazioneBean>>>(this.URL_MAIL_ENTE_ORG, search);
  }

  public insertPaesagioEmail(bean: EmailOrganizzazioneBaseBean): Observable<BaseResponse<EmailOrganizzazioneBean>>
  {
    return this.http.post<BaseResponse<EmailOrganizzazioneBean>>(this.URL_INSERT_EMAIL, bean);
  }

  public updatePaesagioEmail(bean: EmailOrganizzazioneBaseBean): Observable<BaseResponse<EmailOrganizzazioneBean>>
  {
    return this.http.put<BaseResponse<EmailOrganizzazioneBean>>(this.URL_UPDATE_EMAIL, bean);
  }

  public deletePaesagioEmail(idPaesaggioEmail: number): Observable<BaseResponse<EmailOrganizzazioneBean>>
  {
    let params: HttpParams = new HttpParams().set('idPaesaggioEmail', idPaesaggioEmail.toString());
    return this.http.delete<BaseResponse<EmailOrganizzazioneBean>>(this.URL_DELETE_EMAIL, {params});
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
   * 
   * @returns download del manuale di amministratore
   */
  public downloadManuale():Observable<HttpResponse<Blob>>{
    return this.http.get(this.DOWNLOAD_MANUALE_ADMIN
                        ,{observe     : 'response'
                         ,responseType: 'blob'
                         }
                       );
  }


  /**
   * 
   * @returns  lista di uuid di tipi selezionati
   */
  public getTipiDocSportello(): Observable<BaseResponse<string[]>>
  {
    return this.http.get<BaseResponse<string[]>>(this.TIPI_DOCUMENTO_SPORTELLO);
  }

  /**
   * upsert configurazione
   * @param bean 
   * @returns 1 se aggiornato
   */
  public upsertTipiDocSportello(tipi: string[]): Observable<BaseResponse<number>>
  {
    return this.http.post<BaseResponse<number>>(this.TIPI_DOCUMENTO_SPORTELLO, tipi);
  }

  /**
   * 
   * @returns opzioni per dropdown
   */
  public optionsTipiDocSportello(): Observable<BaseResponse<VPaesaggioTipoCaricaDocumentoDTO[]>>
  {
    return this.http.get<BaseResponse<VPaesaggioTipoCaricaDocumentoDTO[]>>(this.OPTIONS_TIPI_DOCUMENTO_SPORTELLO);
  }

}
