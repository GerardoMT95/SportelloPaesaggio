import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { combineLatest, from, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnnotazioniInterneDetailDTO, AnnotazioniInterneDTO, DestinatarioComunicazioneDTO, RichiestePostTrasmissioneDetailDTO, RichiestePostTrasmissioneDTO } from 'src/app/components/model/entity/corrispondenza.models';
import { environment } from 'src/environments/environment';
import { SelectOption } from 'src/shared/components/select-field/select-field.component';
import { PlainTypeStringId } from 'src/shared/models/plain-type-string-id.model';
import { StatoFascicolo } from 'src/shared/models/registration-status';
import { isNullOrUndefined } from 'util';
import { Correspondence } from '../components/model/correspondence.model';
import { DettaglioCom } from '../components/model/dettaglioComunicazione';
import { FascicoloDTO, FascicoloPublicDto } from '../components/model/entity/fascicolo.models';
import { VPaesaggioProvvedimentiDTO } from '../components/model/entity/import.models';
import { InformazioniDTO } from '../components/model/entity/info.models';
import { InterventoTabDTO } from '../components/model/entity/intervento.models';
import { LocalizzazioneTabDTO } from '../components/model/entity/localizzazione.models';
import { TipoProcedimentoDTO } from '../components/model/entity/tipoProcedimento.models';
import { DettaglioFascicolo, Fascicolo, Intervento, Localizzazione, Provvedimento, Richiedente, UlterioriInformazioni, LocalizzazioneIntervento, ValidationBeanDto } from '../components/model/fascicolo.models';
import { SchemaRicerca, UtenteAttributeDTO } from '../components/model/model';
import { PlainTypeNumericId } from '../components/model/plain-type-numeric-id.model';
import { DestinatariComunicazione } from '../components/model/recipient.model';
import { ValidationMessage } from '../components/validation-modal/validation-modal.component';
//import { ConfigData } from '../features/admin-utils/admin-module/components/admin-configurazioni/admin-configurazioni.component';
import { CONST } from './../../shared/constants';
import { BaseResponse } from './../components/model/base-response';
import { AllegatoCustomDTO, AllegatoUlterioreDocumentazione, UlterioreDocumentazione } from './../components/model/entity/allegato.models';
import { CorrispondenzaDTO, DettaglioCorrispondenzaDTO } from './../components/model/entity/corrispondenza.models';
import { AcceleratoriDTO, FascicoloSearch } from './../components/model/entity/fascicolo.models';
import { LineaTemporaleDTO } from './../components/model/entity/informazioni.models';
import { TipologicaDTO } from './../components/model/entity/localizzazione.models';
import { PaginatedBaseResponse } from './../components/model/paginated-base-response';
import { AnnotazioniInterneSearch, CorrispondenzaSearch, RichiestePostTrasmissioneSearch, UlterioreDocumentazioneSearch, VPaesaggioProvvedimentiSearch } from './../components/model/search/search.models';
import { UserService } from './user.service';



@Injectable({
  providedIn: 'root'
})
export class AutorizzazioniPaesaggisticheService
{
  
  private baseUrl: string = environment.baseUrl;
  private cache = new Map<string, any>();
  //qui avviso dell'eventuale cambio di tipoProcedimento su form in modo da gestire la dinamicità
  //dei restanti componenti
  private lastTipoProcedimento: string = "";
  private tipoProcedimento$ = new Subject<string>();
  private activeIndex$ = new Subject<number>();
  public salvaFascicolo$ = new Subject<boolean>();//per avviare il salvataggio del fascicolo in caso di cambiamento di un campo sensibile


  constructor(private http: HttpClient,
              private userService: UserService,
              private translate: TranslateService)
  {
  }

  //segnale di cambio pannello utilizzato dalla validation modal
  public nextActiveIndex(indexPannello: number)
  {
    this.activeIndex$.next(indexPannello);
  }
  public getActiveIndex$()
  {
    return this.activeIndex$.asObservable();
  }

  //cambio tipoProcedimento con conseguenti azioni sui form e validatori
  public nextTipoProcedimento(valore: string)
  {
    this.lastTipoProcedimento = valore;
    this.tipoProcedimento$.next(valore);
  }

  public getTipoProcedimento$(): Observable<string>
  {
    return this.tipoProcedimento$.asObservable();
  }

  public getLastTipoProcedimento(): string
  {
    return this.lastTipoProcedimento;
  }

  // AUTOCOMPLETE PER LA PARTE DI RICERCA
  public getResultsRicercaCodiceFascicolo(query: string): Observable<BaseResponse<string[]>>
  {
    let params = { params: new HttpParams().set("codice", query) };

    return this.http.get<BaseResponse<any>>(CONST.WEB_RESOURCE_BASE_URL + '/public/fascicolo/autocompleteCodice', params);
  }

  dataCodIstat = "A669";
  /**
   * mock
   * @param id 
   */
  getCodIstat(id: string): Promise<any>
  {
    return new Promise((resolve, reject) =>
    {
      resolve(this.dataCodIstat);
    });
    /*return this.http.get(CONST.WEB_RESOURCE_BASE_URL + "public/ente/getCodIstat.pjson?id="+id)
    .toPromise().then(data => data);*/
  }

  getFascicoliBE(page: number, limit: number): Observable<BaseResponse<PaginatedBaseResponse<DettaglioFascicolo>>>
  {
    return this.http.get<BaseResponse<PaginatedBaseResponse<DettaglioFascicolo>>>(`${this.baseUrl}/fascicolo/all?page=${page}&limit=${limit}`);
  }

  getFascicoli(paging: any, sortingField?: string): Promise<Fascicolo[]>
  {
    return new Promise((resolve, reject) =>
    {
      let ret: Fascicolo[] = new Array();
      if (localStorage.length > 0)
      {
        for (let index = 0; index < localStorage.length; index++)
        {
          let key = localStorage.key(index);
          if (key.startsWith('AP'))
          {
            let dettFasc = <DettaglioFascicolo>JSON.parse(localStorage.getItem(key));
            //console.log('cache val',this.cacheVal('public/tipi-intervento'))          
            let fascicolo: Fascicolo = {
              id: dettFasc.id,
              codice: dettFasc.codice,
              descrizione: dettFasc.oggettoIntervento,
              codeStatoFascicolo: dettFasc.codeStatoFascicolo,
              statoRegistrazione: dettFasc.codeStatoFascicolo,//e' funzione di codeStatoFascicolo e esitoVerifica
              esitoVerifica: dettFasc.esitoVerifica || '',
              tipologiaIntervento: '',
              comune: dettFasc.ufficioComune,
              /*dettFasc.ufficioComune?
                CONST.getLabelFromValue(CONST.UfficiCompetenza,dettFasc.ufficioComune,'name','id'):
                ''*/
              responsabileProcedimento:
                dettFasc.provvedimento.responsabileProcedimento ?
                  dettFasc.provvedimento.responsabileProcedimento : '',
              numeroProvvedimento: dettFasc.provvedimento.numeroProvvedimento,
              dataProvvedimento: <string>dettFasc.provvedimento.dataProvvedimento,
              richiedente:
                dettFasc.richiedente ?
                  (dettFasc.richiedente.nome || '') + ' ' + (dettFasc.richiedente.cognome || '') :
                  '',
              esitoProvvedimento: dettFasc.provvedimento.idEsitoProvvedimento,//CONST.getLabelFromValue(CONST.EsitiProvvedimento,dettFasc.provvedimento.idEsitoProvvedimento,'name','id'),
              urlProvvedimentoFinale: dettFasc.urlProvvedimentoFinale,
            };
            this.getTipiIntervento().subscribe(res =>
            {
              //console.log('res:',res);
              //console.log('dettFasc.intervento.caratterizzazione:',dettFasc.intervento.idTipoIntervento);
              fascicolo.tipologiaIntervento = CONST.getLabelFromValue(res, dettFasc.intervento.idTipoIntervento)
            });
            this.getEsitiProvvedimento().subscribe(res =>
            {
              fascicolo.esitoProvvedimento =
                CONST.getLabelFromValue(res, dettFasc.provvedimento.idEsitoProvvedimento)
            });
            ret.push(fascicolo);
          }
        }
      }
      resolve(ret);
    });
  }


  testStatus: PlainTypeStringId[] = [
    { id: StatoFascicolo.WORKING, nome: "13" },
    { id: StatoFascicolo.TRANSMITTED, nome: "5" },
    { id: StatoFascicolo.CANCELLED, nome: "0" },
    { id: StatoFascicolo.SELECTED, nome: "0" },
    { id: StatoFascicolo.ON_MODIFY, nome: "0" },
    { id: StatoFascicolo.FINISHED, nome: "0" },
  ];
  /**
   * mocked
   */
  getContatoriFascicolo(): Observable<PlainTypeStringId[]>
  {
    return from([this.testStatus]);
    /*return new Promise((resolve, reject) => {
      resolve({ trasmesso: 10, inLavorazione: 23 });
    });*/
    /*return this.http.get(CONST.WEB_RESOURCE_BASE_URL + "fascicolo/riepiologoOperatore.pjson")
    .toPromise().then(data => {
      let list = (<any>data).payload;
      return list;
    });*/
  }

  getContatoriFascicoloPublic(): Observable<PlainTypeStringId[]>
  {
    return from([this.testStatus.filter(f => f.id === StatoFascicolo.TRANSMITTED || f.id === StatoFascicolo.FINISHED)]);
  }

  /**
     * mock
     */

  /*getUfficiCompetenza(): Promise<UfficioCompetenza[]> {
    return new Promise((resolve, reject) => {
      resolve(CONST.UfficiCompetenza);
    });
  }*/


  /*getTipiProcedimento(): Promise<Idname[]> {
    return new Promise((resolve, reject) => {
      resolve(CONST.TipiProcedimento);
    });
  }*/

  formatDate(date)
  {
    if (date === null || date === "" || date === undefined)
    {
      return "";
    } else
    {
      var d = new Date(date),
        month = "" + (d.getMonth() + 1),
        day = "" + d.getDate(),
        year = d.getFullYear();

      if (month.length < 2) month = "0" + month;
      if (day.length < 2) day = "0" + day;
      return [year, month, day].join("-");
    }
  }


  /**
   * mocked
   * @param formRawValue 
   */
  addFascicolo(formRawValue: any): Promise<any>
  {
    //genero un codice 
    let fascicolo = new DettaglioFascicolo();
    fascicolo.dataCreazione = new Date();
    let id: string = Math.ceil((Math.random() * 100000)) + '';
    let suffix = fascicolo.dataCreazione.toISOString().substring(5, 7) + '-' +
      fascicolo.dataCreazione.toISOString().substring(0, 4);
    fascicolo.dataCreazione = this.formatDate(fascicolo.dataCreazione);
    let codice = 'AP' + id + '-' + suffix;
    fascicolo.codice = codice;
    fascicolo.id = id;
    fascicolo.codeStatoFascicolo = 'WORKING';
    fascicolo.localizzazione = new Localizzazione();
    fascicolo.richiedente = new Richiedente();
    fascicolo.intervento = new Intervento();
    fascicolo.provvedimento = new Provvedimento();
    fascicolo.provvedimento.responsabileProcedimento = 'Mario Rossi';
    fascicolo.userNameCreazione = this.userService.getUser() ? this.userService.getUser().username : 'usernameMancante';
    //console.log('formRaw:{}',formRawValue);
    Object.keys(formRawValue).forEach(element =>
    {
      if (formRawValue[element])
      {
        fascicolo[element] = formRawValue[element];
      }
    });
    localStorage.setItem(codice, JSON.stringify(fascicolo));
    return new Promise((resolve, reject) =>
    {
      resolve({
        code: "OK",
        payload:
        {
          codice: codice,
          formRawValue: fascicolo
        }
      });
    });
  }


  addFascicoloToBE(formRawValue: any): Promise<any>
  {
    //genero un codice 
    let fascicolo = new DettaglioFascicolo();
    fascicolo.userNameCreazione = this.userService.getUser().username;
    //console.log('formRaw:{}',formRawValue);
    Object.keys(formRawValue).forEach(element =>
    {
      if (formRawValue[element])
      {
        fascicolo[element] = formRawValue[element];
      }
    });
    //chiamo il servizio su /fascicolo/
    //console.log(fascicolo, "NEWPLAN SERVICE");
    return this.http
      .post<BaseResponse<number>>(`${this.baseUrl}/fascicolo`, fascicolo)
      .pipe(
        map((response: BaseResponse<number>) =>
        {
          //console.log(response.payload, "RETURNED FASCICOLO ID");
          return response//.payload;
        })
      ).toPromise();
  }

  public creaFascicolo(formRawValue: any): Observable<BaseResponse<FascicoloDTO>>
  {
    let dto = this.transformFormRawValue(formRawValue);
    let url = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/crea";
    console.log(dto);
    return this.http.post<BaseResponse<FascicoloDTO>>(url, dto);
  }

  /**
   * mock
   * @param formRawValue 
   */
  updateFascicolo(formRawValue: any): Promise<any>
  {
    formRawValue.localizzazione.particelle.forEach(particella =>
    {
      if (particella.ente != null)
      {
        particella.ente = particella.ente.label;
      }
    });
    if (!(formRawValue.localizzazione.bpImmobiliAreeInteresse instanceof Array))
    {
      formRawValue.localizzazione.bpImmobiliAreeInteresse = [];
    }
    if (!(formRawValue.localizzazione.bpPaesaggioRurale instanceof Array))
    {
      formRawValue.localizzazione.bpPaesaggioRurale = [];
    }
    if (!(formRawValue.localizzazione.bpParchiRiserve instanceof Array))
    {
      formRawValue.localizzazione.bpParchiRiserve = [];
    }
    formRawValue.localizzazione.bpImmobiliAreeInteresse.forEach((area, index) =>
    {
      formRawValue.localizzazione.bpImmobiliAreeInteresse[index] = area.codice;
    });
    formRawValue.localizzazione.bpPaesaggioRurale.forEach((area, index) =>
    {
      formRawValue.localizzazione.bpPaesaggioRurale[index] = area.codice;
    });
    formRawValue.localizzazione.bpParchiRiserve.forEach((area, index) =>
    {
      formRawValue.localizzazione.bpParchiRiserve[index] = area.codice;
    });
    return this.http.put<BaseResponse<DettaglioFascicolo>>(`${this.baseUrl}/fascicolo`, formRawValue)
      .toPromise()
      .then(response =>
      {
        return response;
      })
      .catch(error =>
      {
        return console.log(error);
      });

    /* return new Promise((resolve, reject) => {
      let objInStorage = JSON.parse(localStorage.getItem(formRawValue.codice))
      let objMerged = { ...objInStorage, ...formRawValue };
      Object.keys(objMerged).forEach(el => {
        if (objMerged[el] instanceof Date) {
          objMerged[el] = this.formatDate(objMerged[el]);
        };
      });
      if (objMerged.localizzazione &&
        objMerged.localizzazione.particelle &&
        objMerged.localizzazione.particelle.length > 0) {
        objMerged.localizzazione.particelle.forEach((el, index) => {
          if (!el.id) {
            el.id = objMerged.id + '-' + index;
          }
        });
      }
      localStorage.setItem(formRawValue.codice, JSON.stringify(objMerged));
      resolve({
        code: "OK",
        payload:
        {
          codice: "AP75035-9-2018",
          formRawValue: objMerged
        }
      });
    }); */
  }

  public salvaFascicolo(entity: InformazioniDTO): Observable<BaseResponse<InformazioniDTO>> 
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/salva";
    return this.http.put<BaseResponse<InformazioniDTO>>(url, entity);
  }

  public salvaLocalizzazioneFascicolo(entity: Localizzazione,idFascicolo:string): Observable<BaseResponse<Localizzazione>> 
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/salvaLocalizzazione/"+idFascicolo;
    return this.http.put<BaseResponse<Localizzazione>>(url, entity);
  }

  public saveAllegatoProvvedimento(idFascicolo: number, provvedimentoFinale?: File, parereMibac?: File): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/provvedimento/carica";
    let formData: FormData = new FormData;
    if (provvedimentoFinale)
      formData.append("provvedimentoFinale", provvedimentoFinale);
    if (parereMibac)
      formData.append("parereMibac", parereMibac);
    return this.http.post<BaseResponse<any>>(url, formData, { params: new HttpParams().set("idFascicolo", idFascicolo.toString()) });
  }

  /**
     * mock
     * @param formRawValue 
     */
  updateVerificaFascicolo(codice: string, formRawValue: any): Promise<any>
  {
    return new Promise((resolve, reject) =>
    {
      let objInStorage = JSON.parse(localStorage.getItem(codice));
      let objMerged = { ...objInStorage, ...formRawValue };
      Object.keys(objMerged).forEach(el =>
      {
        if (objMerged[el] instanceof Date)
        {
          objMerged[el] = this.formatDate(objMerged[el]);
        };
      });
      localStorage.setItem(codice, JSON.stringify(objMerged));
      resolve({
        code: "OK",
        payload:
        {
          codice: "AP75035-9-2018",
          formRawValue: objMerged
        }
      });
    });
  }


  /**
   * mock
   * @param formRawValue 
   */
  trasmettiVerificaFascicolo(codice: string, formRawValue: any): Promise<any>
  {
    return new Promise((resolve, reject) =>
    {
      let objInStorage = JSON.parse(localStorage.getItem(codice));
      let objMerged = { ...objInStorage, ...formRawValue };
      Object.keys(objMerged).forEach(el =>
      {
        if (objMerged[el] instanceof Date)
        {
          objMerged[el] = this.formatDate(objMerged[el]);
        };
      });
      objMerged['codeStatoFascicolo'] = "FINISHED";
      localStorage.setItem(codice, JSON.stringify(objMerged));
      resolve({
        code: "OK",
        payload:
        {
          codice: "AP75035-9-2018",
          formRawValue: objMerged
        }
      });
    });
  }



  deleteFascicolo(id: string): Promise<any>
  {
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.append("id", id);
    return this.http.delete<BaseResponse<any>>(`${this.baseUrl}/fascicolo/cancella`, {params: httpParams})
      .toPromise()
      .then(response =>
      {
        return response;
      })
      .catch(error =>
      {
        return console.log(error);
      });

    /* return new Promise((resolve, reject) => {
      localStorage.removeItem(codice);
      //console.log('removed '+codice);
      resolve({
        code: "OK",
        payload:
        {
        }
      });
    }); */
  }

  /**in caso di errori, verra' restituito un array di ValidationMessage (sezione/messaggi)
   * mock
   * @param formRawValue 
   */
  valida(codiceFascicolo: string): Promise<ValidationMessage[]>
  {
    return new Promise((resolve, reject) =>
    {
      resolve([]
        /*
        esitoCfRichiedente:boolean 
        validazione[key][i] errori di validazione raggruppati per sezione
         */


      );
    });
  }

  /*dettaglioFascicolo={
    codice: "AP75035-9-2018",
    id:'483574857489545',
    descrizione: 'descrizione del fascicolo',
    idTipologiaIntervento: 1,
    tipologiaIntervento: "",
    codiceInternoEnte: "CCINT-ENTE",
    dataProtocolloInternoEnte: '2018-09-09',
    isInSanatoria: true,
    note: "note dell'intervento",
    numeroInternoEnte: "N-INT-ENTE",
    oggettoIntervento: "Oggetto dell'intervento ",
    protocolloInternoEnte: "PROT-888-INT-ENTE",
    tipoProcedimento: 3,
    ufficioComune: 2,
    responsabileProcedimento:'',
    numeroProvvedimento: '',
    dataProvvedimento: '2019-06-10',
    richiedente: 'Mario Rossi',
    codiceComune: '23',
    comune: 'Adelfia',
    dataCreazione: '2019-02-01',
    codeStatoFascicolo: 'WORKING',
    nomeStatoFascicolo: 'inLavorazione'
    /*esitoProvvedimento: string;
    idEsitoProvvedimento: number;
    urlProvvedimentoFinale?: string;
    idStatoFascicolo?: number;
    
    localizzazione?: Localizzazione;
  };*/
  /**
   * mock
   * @param id  del fascicolo
   */
  getDettaglioFascicolo(id: string): Promise<BaseResponse<DettaglioFascicolo>>
  {
    let fascicolo: DettaglioFascicolo;
    return this.http.get<BaseResponse<DettaglioFascicolo>>(`${this.baseUrl}/fascicolo/${id}`)
      .toPromise().then(
        response =>
        {
          return response;
        });

    /* return new Promise((resolve, reject) => {
      let fascicolo: DettaglioFascicolo; //= JSON.parse(localStorage.getItem(codice));
      //console.log('getted:{}',fascicolo);
      //console.log('typeof dataNascita:{}',typeof fascicolo.richiedente.dataNascita);
      resolve({
        codiceEsito: "OK",
        payload: fascicolo,
        descrizioneEsito: "",
        numeroOggettiRestituiti: 0,
        numeroTotaleOggetti: 0    
      });
    }); */
  }

  allegatiShape = 
  [
    {
      nomeFile: 'Localizzazione.zip',
      dataArrivo: '2019-10-03',
      descrizione: 'Area intervento shape file UMT 33',
    }
  ];
  /**
   * mock
   * @param id del fascicolo
   */
  getAllegatiShape(id: string): Promise<any>
  {
    return new Promise((resolve, reject) =>
    {
      resolve({
        code: "OK",
        payload: this.allegatiShape
      });
    });
  }

  //gli attachment li inserisco in un oggetto con chiave ATTACH#codice-fascicolo#sezione#idAttach
  prelevaAttachment(codice: string, sezione: string)
  {
    let ret: PlainTypeNumericId[] = new Array();
    if (localStorage.length > 0) 
    {
      for (let index = 0; index < localStorage.length; index++) 
      {
        let key = localStorage.key(index);
        if (key.startsWith('ATTACH#' + codice + '#' + sezione)) 
        {
          let allObj = JSON.parse(localStorage.getItem(key));
          let descrizione:any;
          if(Array.isArray(allObj.tipoAllegato)){
            let tipiAllegato=allObj.tipoAllegato;
            descrizione=[];
            tipiAllegato.forEach(tipo=>{
              let desTipo=this.translate.instant("tipo_allegato"+(CONST.isPutt()?'_putt':'')+"."+tipo.toUpperCase());
              descrizione.push(desTipo);
            });
          }else{
            descrizione=this.translate.instant("tipo_allegato"+(CONST.isPutt()?'_putt':'')+"."+allObj.tipoAllegato.toUpperCase());
          }
          ret.push(<PlainTypeNumericId>{
            id: allObj.id,
            descrizione: descrizione,
            data: allObj.data,
            mandatario: allObj.mandatario,
            nome: allObj.nome,
            type:  allObj.tipoAllegato,
            checksum: allObj.checksum
          });
        }
      }
    }
    return ret;
  }

  /**
   * 
   * [{"id":7,"nome":"primengTable.pdf","descrizione":"Attestazione relativa all'applicabilità delle condizioni di esclusione del Piano dalle procedure di VAS","data":"2019-11-12T23:00:00.000+0000"},{"id":8,"nome":"primengTable - Copia.pdf","descrizione":"Elaborati del piano urbanistico comunale","data":"2019-11-13T15:51:11.148+0000"},{"id":9,"nome":"primengTable-1.pdf","descrizione":"Copia dell'atto amministrativo di formalizzazione della proposta di piano urbanistico comunale","data":"2019-11-13T15:51:23.681+0000"}]}
   * @param pianoId se vuoto restituisce la lista degli allegati previsti, 
   * es.:
   * [{"id":null,"nome":"Attestazione relativa all'applicabilità delle condizioni di esclusione del Piano dalle procedure di VAS","descrizione":"mandatario","data":null},{"id":null,"nome":"Elaborati del piano urbanistico comunale","descrizione":"mandatario","data":null},{"id":null,"nome":"Copia dell'atto amministrativo di formalizzazione della proposta di piano urbanistico comunale","descrizione":"mandatario","data":null},{"id":null,"nome":"Contributi, pareri e osservazioni già espressi dai soggetti competenti in maniera ambientale e dagli enti territoriali interessati","descrizione":"opzionale","data":null},{"id":null,"nome":"Area intervento SHAPE FILE - UTM33","descrizione":"opzionale","data":null}]
   * se pieno restituisce gli allegati uploadati del fascicolo
   * @param sezione provvedimento oppure allegati
   */
  getAttachments(codice: string, sezione: string, tipoProcedimento: string): Observable<PlainTypeNumericId[]>
  {
    let ret: Observable<PlainTypeNumericId[]>;
    if (sezione == 'provvedimento')
    {
      if (codice)
      {
        ret = new Observable((observer) =>
        {
          observer.next(this.prelevaAttachment(codice, sezione));
          observer.complete()
        });
      }
      else
      {
        ret = new Observable((observer) =>
        {
          observer.next(CONST.AllegatiProvvedimento(tipoProcedimento))
          observer.complete()
        });
      }
    } else if (sezione == 'allegati')
    {
      if (codice)
      {
        ret = new Observable((observer) =>
        {
          observer.next(this.prelevaAttachment(codice, sezione));
          observer.complete();
        });
      } 
      else
      {
        ret = new Observable((observer) =>
        {
          observer.next(CONST.AllegatiAllegati(tipoProcedimento))
          observer.complete();
        });
      }
    }
    else if(sezione=="responsabile"){
      if (codice)
      {
        ret = new Observable((observer) =>
        {
          observer.next(this.prelevaAttachment(codice, sezione));
          observer.complete();
        });
      } 
      else
      {
        ret = new Observable((observer) =>
        {
          observer.next(CONST.tipiAllegatiResponsabile)
          observer.complete();
        });
      }
    }
    return ret;
  }

  checkAttachmentsFormValidity(id: string, sezione: string, tipoProcedimento: string)
  {
    return combineLatest(this.getAttachments('', sezione, tipoProcedimento),
      this.getAttachments(id, sezione, tipoProcedimento)).pipe(
        map(
          ([allAttachments, planAttachments]) =>
          {
            console.log('ALLATT', allAttachments)
            console.log('PLANATTACH', planAttachments)
            const mandatoryAttachments = this.getMandatoryAttachments(allAttachments);
            const formAttachments = this.getFormAttachments(planAttachments);
            //siccome un attachment puo' avere piu' tipi.... 
            console.log('MANDATORY', mandatoryAttachments)
            console.log('FORM ATTACH', formAttachments)
            return mandatoryAttachments.filter(item => !formAttachments.map(m => m.toLowerCase()).includes(item.toLowerCase()));
          }
        )
      );
  }

  getMandatoryAttachments(attachments: any[]):string[]
  {
    return attachments.filter(item => item.descrizione === 'mandatario').map(item => item.type);
  }

  getFormAttachments(attachments: any[]):string[]
  {
    let ret=[];
    attachments.forEach(el=>{
      if(Array.isArray(el.type)){
        el.type.forEach(subEl=>ret.push(subEl));
      }else{
        ret.push(el.type);
      }
    });
    return ret;    
  }

  /*getDestinatari(): Promise<DestinatariComunicazione[]>
  {
    return new Promise((resolve, reject) =>
    {
      resolve(CONST.DestinatariComunicaz);
    });
  }*/

  trasmetti(dettaglioFascicolo: DettaglioFascicolo, destinatari: DestinatariComunicazione[])
  {
    let objInStorage = JSON.parse(localStorage.getItem(dettaglioFascicolo.codice));
    objInStorage.codeStatoFascicolo = StatoFascicolo.TRANSMITTED;
    objInStorage.dataTrasmissione = this.formatDate(new Date());
    localStorage.setItem(dettaglioFascicolo.codice, JSON.stringify(objInStorage));
    return new Promise((resolve, reject) =>
    {
      resolve('OK');
    });
  }

  getCorrespondence(codice: string): Observable<Correspondence[]>
  {
    return new Observable((observer) =>
    {
      observer.next()
      observer.complete();
    });
  }


  isCachable(path: string)
  {
    return path.startsWith('public');
  }

  cacheVal(path: string): any
  {
    //console.log(path+" this.cache.get:{}",this.cache.get(path))
    return this.cache.get(path) || false;
  }

  setCacheVal(path: string, obj: any)
  {
    this.cache.set(path, obj);
  }

  getSetValori(path: string): Observable<SelectItem[]>
  {
    if (this.isCachable(path) && this.cacheVal(path))
    {
      return new Observable((observer) =>
      {
        observer.next(this.cacheVal(path))
        observer.complete();
      });
    }
    let ret = this.http.get<BaseResponse<PlainTypeStringId[]>>(`${this.baseUrl}/${path}`).pipe(map(
      (response: BaseResponse<PlainTypeStringId[]>) =>
      {
        const ret: SelectItem[] = [];
        response.payload.forEach(element =>
        {
          ret.push({ label: element.nome, value: element.id });
        });
        this.setCacheVal(path, ret);
        return ret;
      })
    )
    return ret;
  }


  /**
   * mocked
   * retrieve dei dati di configurazione dell'applicativo (solo admin)
   */
  /* getConfigData(): Promise<ConfigData>
  {
    return new Promise((resolve, reject) =>
    {
      let ret: ConfigData;
      ret = JSON.parse(localStorage.getItem('CONFIG_DATA_AUTPAE'));
      resolve(ret);
    });
  } */

  private toObservable(data: any): Observable<any>
  {
    return new Observable((observer) =>
    {
      observer.next(data);
      observer.complete();
    });
  }


  /**
   * mocked
   * salvataggio dei dati di configurazione.
   * @param ConfigData 
   */
  /* saveConfigData(data: ConfigData): Promise<any>
  {
    return new Promise((resolve, reject) =>
    {
      localStorage.setItem("CONFIG_DATA_AUTPAE", JSON.stringify(data));
      resolve({ code: "OK", payload: {} });
    });
  } */

  getTipiIntervento(tipoProcedimento?: String): Observable<SelectItem[]>
  {
    if(CONST.isPutt()){
      return this.toObservable(CONST.TipoInterventoPutt);
    }else if(CONST.isPareri()){
      return this.toObservable(CONST.TipoInterventoPareri);
    }
    else{
     return this.toObservable(CONST.TipoIntervento.filter(
      el =>
      {
        switch (tipoProcedimento)
        {
          case 'AUT_PAES_SEMPL_DPR_139_2010':
            return !(['RISTR_URBANISTICA'].indexOf(el.value) >= 0);
          case 'ACCERT_COMPAT_PAES_DLGS_42_2004':
            return !(['RISTR_URBANISTICA', 'NUOVA_COSTRUZIONE'].indexOf(el.value) >= 0);
          default:
            return true;
        }
      }
    ));
  }
    //return this.getSetValori('public/tipi-intervento');
  }
  getDurataIntervento(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.DurataIntervento);
    //return this.getSetValori('public/durata-intervento');
  }
  getCaratterizzazioneIntervento(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.CaratterizzazioniIntervento);
    //return this.getSetValori('public/caratterizzazione-intervento');
  }
  getCompatDPR139_2010(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.CompatibilitaDPR139_2010);
    //return this.getSetValori('public/compat_dpr_31_2017-intervento');
  }

  getCompatDPR31_2017(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.CompatibilitaDPR31_2017);
    //return this.getSetValori('public/compat_dpr_31_2017-intervento');
  }
  getCompatDPR31_2017BIS(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.CompatibilitaDPR31_2017BIS);
  }
  getIstanzaRinnovo(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.IstanzaRinnovo);
  }

  getQualificazioneInterventoDPR31_2017(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.MotiviQualificazioneDPR31_2017);
    //return this.getSetValori('public/qualificazione-intervento');
  }

  getQualificazioneInterventoDPCM_2005(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.MotiviQualificazioneDPCM_2005);
  }

  getQualificazioneInterventoDPR139_2010(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.MotiviQualificazioneDPR139_2010);
    //return this.getSetValori('public/qualificazione-intervento');
  }

  getQualificazioneInterventoDLGS42_2004(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.MotiviQualificazioneDLGS42_2004);
    //return this.getSetValori('public/qualificazione-intervento');
  }

  /**
   * Metodo che ritorna la lista di tipi procedimento attualmente attivi per l'applicativo corrente
   * @returns 
   */
  getTipiProcedimento(): Observable<SelectItem[]>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/tipo-procedimento/getByApp"
    let httpParams: HttpParams = new HttpParams().set('application', environment.project);
    let params = { params: httpParams };
    const ret: SelectItem[] = [];
    return this.http.get<BaseResponse<TipoProcedimentoDTO[]>>(url, params).pipe(map(
      (response: BaseResponse<TipoProcedimentoDTO[]>) =>
      {
        const ret: SelectItem[] = [];
        response.payload.forEach(element =>
        {
          ret.push({ label: element.descrizione, value: element.codice });
        });
        //this.setCacheVal(path, ret);
        console.log(JSON.stringify(ret));
        return ret;
      })
    )
    //return this.toObservable(CONST.TipiProcedimento.filter(el=>el.project==environment.project));
    //return this.getSetValori('public/tipi-procedimento');
  }

  getAllTipiProcedimento(): Observable<SelectItem[]>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/getAllTipoProcedimentoByApp"
    let httpParams: HttpParams = new HttpParams().set('application', environment.project);
    let params = { params: httpParams };
    const ret: SelectItem[] = [];
    return this.http.get<BaseResponse<TipoProcedimentoDTO[]>>(url, params).pipe(map(
      (response: BaseResponse<TipoProcedimentoDTO[]>) =>
      {
        const ret: SelectItem[] = [];
        response.payload.forEach(element =>
        {
          ret.push({ label: element.descrizione, value: element.codice });
        });
        //this.setCacheVal(path, ret);
        console.log(JSON.stringify(ret));
        return ret;
      })
    )
    //return this.toObservable(CONST.TipiProcedimento.filter(el=>el.project==environment.project));
    //return this.getSetValori('public/tipi-procedimento');
  }

  /**
   * Metodo che ritorna la lista di tipi procedimento associati al fascicolo il cui id è ricevuto come parametro
   * La lista contiene tutti i tipi procedimento che erano attivi alla creazione del fascicolo, e che magari allo stato attuale
   * non sono più attivi ma devono comunque essere utilizzabili per questo fascicolo
   * vengono filtrati sulla base della dataCreazione, quelli che rano scaduti ( e non selezionabili) lo rimandono anche in editing
   * @param idFascicolo
   * @returns 
   */
  getTipiProcedimentoByFascicolo(idFascicolo:number,dataCreazione:Date): Observable<SelectItem[]>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/tipo-procedimento/getByFascicolo"
    let httpParams: HttpParams = new HttpParams().set('idFascicolo', idFascicolo.toString());
    let params = { params: httpParams };
    const ret: SelectItem[] = [];
    return this.http.get<BaseResponse<TipoProcedimentoDTO[]>>(url, params).pipe(map(
      (response: BaseResponse<TipoProcedimentoDTO[]>) =>
      {
        const ret: SelectItem[] = [];
        response.payload.forEach(element =>
        {
          ret.push({ label: element.descrizione, value: element.codice });
          /*if(element.fineValidita>dataCreazione && 
            element.inizioValidita<dataCreazione){
          }*/
        });
        //this.setCacheVal(path, ret);
        console.log(JSON.stringify(ret));
        return ret;
      })
    )
  }

  getEsitiProvvedimento(isPublic?: boolean): Observable<SelectItem[]>
  {
    let source = CONST.EsitoAutorizzazionePublic;
    if(!isPublic)
      source = CONST.EsitoAutorizzazione
    return this.toObservable(source);
  }

  getEsitiVerifica(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.EsitiVerifica);
  }

  getStatiFascicolo(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.StatoFascicoloStatusAttribute.map(
      el =>
      {
        return { label: el.translated, value: el.enumVal + '' };
      }
    ));
    //return this.getSetValori('public/stati-fascicolo');
  }
  getRegistrationStatus(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.RegistrationStatusAttribute2.map(
      el =>
      {
        return { label: el.translated, value: el.enumVal + '' };
      }
    ));
    //return this.getSetValori('public/stati-fascicolo');
  }
  getTipiRuoloInDitta(): Observable<SelectItem[]>
  {
    return this.toObservable(CONST.DittaQualitaDi);
    //return this.getSetValori('public/ditta-qualita-di');
  }

  public getIntervento(tipoProcedimento: string, idFascicolo: string): Observable<BaseResponse<InterventoTabDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/intervento/get"
    let httpParams: HttpParams = new 
    HttpParams()
    .set('codiceTipoProcedimento', tipoProcedimento.toString())
    .set('idFascicolo', idFascicolo);
    let params = { params: httpParams };
    return this.http.get<BaseResponse<InterventoTabDTO>>(url, params);
  }

  public transformFormRawValue(formRawValue: any): FascicoloDTO
  {
    let dto = new FascicoloDTO();
    dto.usernameUtenteCreazione = this.userService.getUser().username;
    Object.keys(formRawValue).forEach(field =>
    {
      if (formRawValue[field])
      {
        dto[field] = formRawValue[field];
      }
    });
    return dto;
  }

  public findInformazioniGetAll(idFascicolo: string): Observable<BaseResponse<InformazioniDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/datiAll";
    let params = { params: new HttpParams().set('idFascicolo', idFascicolo) };
    return this.http.get<BaseResponse<InformazioniDTO>>(url, params);
  }

  public saveProvvedimento(idFascicolo: string, tipoAllegato: string, file: FormData): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/provvedimento/carica";
    let params: HttpParams = new HttpParams().set('idFascicolo', idFascicolo)
                                             .set("tipoAllegato", tipoAllegato);
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, file, { params: params });
  }

  public saveAllegati(idFascicolo: string, tipoAllegato: string, file: FormData): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/carica";
    let params: HttpParams = new HttpParams().set('idFascicolo', idFascicolo)
                                             .set("tipoAllegato", tipoAllegato);
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, file, { params: params });
  }

  public saveAllegatiMultitipo(fileAndTipi: FormData): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/caricaMultitipo";
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, fileAndTipi);
  }

  public getTipologieAllegati(codiceTipoProcedimento: string): Observable<BaseResponse<AllegatoCustomDTO[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/get";
    let params = {params: new HttpParams().set("codiceTipoProcedimento", codiceTipoProcedimento)};
    return this.http.get<BaseResponse<AllegatoCustomDTO[]>>(url, params);
  }

  public getDropdownsParchi(province?: string[], comuni?: string[]): Observable<BaseResponse<LocalizzazioneTabDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/localizzazione/liste";
    let httpParams: HttpParams = new HttpParams();

    if(province)
      province.forEach(p => { httpParams = httpParams.append("province", p); });
    if (comuni)
      comuni.forEach(c => { httpParams = httpParams.append("comuni", c); });
    
    return this.http.get<BaseResponse<LocalizzazioneTabDTO>>(url, {params: httpParams});
  }

  public getUlterioriInformazioniLocalizzazione(codCat?: string[]): Observable<UlterioriInformazioni>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/localizzazione/liste";
    return this.http.post<BaseResponse<UlterioriInformazioni>>(url, codCat)
    .pipe(map(response=>{return response.payload;}));
  }

  public saveGeoAllegato(idFascicolo: string, file: File): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/geoutil/shape2geoJSON";
    const formData: FormData = new FormData();
    formData.append("file", file);
    return this.http.post<BaseResponse<any>>(url, formData, {params: new HttpParams().set("idFascicolo", idFascicolo)});
  }

  public eliminaAllegato(id: string,idFascicolo:string): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/elimina";
    let params: HttpParams = new HttpParams().set("idAllegato", id).set("idFascicolo",idFascicolo);
    return this.http.delete<BaseResponse<any>>(url, {params: params});
  }

  public eliminaAllegatoCorrispondenza(id: string,idFascicolo:string,idCorrispondenza:string): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/eliminaAllegato";
    let params: HttpParams = new HttpParams().set("idAllegato", id).set("idFascicolo",idFascicolo).set("idCorrispondenza",idCorrispondenza);
    return this.http.delete<BaseResponse<any>>(url, {params: params});
  }

  public eliminaAllegati(ids: string[],idFascicolo:string): Observable<BaseResponse<any>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/eliminaSelezionati";
    let params: HttpParams = new HttpParams();
    ids.forEach(id =>
    {
      params = params.append("listaAllegati", id);
    });
    params = params.set("idFascicolo",idFascicolo);
    return this.http.delete<BaseResponse<any>>(url, {params: params});
  }

  public validaBE(idFascicolo: string): Observable<BaseResponse<ValidationBeanDto>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/valida";
    /* let params = { params: new HttpParams().set("idFascicolo", idFascicolo) }; */
    //return this.http.post<BaseResponse<ValidationBeanDto>>(url, InformazioniDTO);
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo);
    return this.http.post<BaseResponse<ValidationBeanDto>>(url, null,{ params: httpParams });
  }

  public postValidazione(idFascicolo: string): Observable<BaseResponse<DestinatarioComunicazioneDTO[]>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/getDestinatariTrasmissione";
    let params = { params: new HttpParams().set("idFascicolo", idFascicolo) };
    return this.http.get<BaseResponse<DestinatarioComunicazioneDTO[]>>(url, params);
  }

  public trasmessiFascicolo(idFascicolo: string, destinatari: DestinatarioComunicazioneDTO[]): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/trasmetti";
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo);
    return this.http.post<BaseResponse<boolean>>(url, destinatari, { params: httpParams });
  }

  /**
   * metodo sicuro
   * @param idFascicolo 
   * @param id 
   * @returns 
   */
  public downloadAllegatoFascicolo(idFascicolo:string,id: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/download";
    let httpParams: HttpParams =new HttpParams().set('idAllegato', id);
    httpParams=httpParams.append('idFascicolo',idFascicolo+'');
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  

  public downloadRicevutaTrasmissione(id: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/download";
    let httpParams: HttpParams = new HttpParams().set("tipoAllegato", "RICEVUTA_TRASMISSIONE");
    httpParams = httpParams.append('idFascicolo', id);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  /*
  public downloadAllegato3(idAllegato: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/download";
    let httpParams: HttpParams = new HttpParams().set("idAllegato", idAllegato);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams});
  }
  */

  /*public downloadAllegato4(idCorrispondenza: string, idRicevuta: string, tipo: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/download";
    let httpParams: HttpParams = new HttpParams().set("idCorrispondenza", idCorrispondenza);
    httpParams = httpParams.append('idRicevuta', idRicevuta);
    httpParams = httpParams.append('tipoAllegato', tipo);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }*/

  public downloadRicevutaEml(idFascicolo:string,idRicevuta: string, tipo: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/downloadRicevutaMail";
    let httpParams: HttpParams = new HttpParams().set("idRicevuta", idRicevuta);
    httpParams = httpParams.append('tipoAllegato', tipo);
    httpParams = httpParams.append('idFascicolo', idFascicolo);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  public downloadAnteprima(idFascicolo: string, destinatari?: DestinatarioComunicazioneDTO[]): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/allegati/download_AnteprimaTrasmissione";
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo);
    return this.http.post(url, destinatari ? destinatari : null, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  public searchFascicoli(filters: FascicoloSearch): Observable<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/search";
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>>(url, {params: httpParams});
  }

  public searchPublicFascicoli(filters: FascicoloSearch): Observable<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/search";
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<FascicoloDTO>>>(url, { params: httpParams });
  }

  /**
   * effettua retrieve sulla vista congiunta tra autpae e presentazione_istanza
   */
  public searchPublicAllFascicoli(filters: FascicoloSearch): Observable<BaseResponse<PaginatedBaseResponse<FascicoloPublicDto>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/searchAll";
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<FascicoloPublicDto>>>(url, { params: httpParams });
  }

  public getForAccelerators(isPublic?: boolean, filters?: FascicoloSearch): Observable<BaseResponse<AcceleratoriDTO>>
  {
    let prefix: string = isPublic === true ? "/public" : "";
    let url: string = CONST.WEB_RESOURCE_BASE_URL + prefix + "/fascicolo/getForAccelerators";
    let httpParams = new HttpParams();
    if(filters)
    {
      Object.keys(filters).forEach(key =>
      {
        if (filters[key])
          httpParams = httpParams.append(key, filters[key]);
      });
    }
    return this.http.get<BaseResponse<AcceleratoriDTO>>(url, {params: httpParams});
  }

  public postUlterioreDocumentazione(doc: AllegatoUlterioreDocumentazione): Observable<BaseResponse<AllegatoUlterioreDocumentazione>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/ulterioreDoc/caricaAllegato";
    let formData: FormData = new FormData();
    formData.append("file", doc.allegato);
    doc.allegato = null;
    let json = JSON.stringify(doc);
    formData.append("data", new Blob([json], { type: 'application/json' }));
    return this.http.post<BaseResponse<AllegatoUlterioreDocumentazione>>(url, formData);
  }

  public postUlterioreDocumentazioneMulti(doc: UlterioreDocumentazione, files: File[]): Observable<BaseResponse<AllegatoUlterioreDocumentazione>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/ulterioreDoc/caricaAllegatoMulti";
    let formData: FormData = new FormData();
    for(let f of files)
      formData.append("file", f);
    let json = JSON.stringify(doc);
    formData.append("data", new Blob([json], { type: 'application/json' }));
    return this.http.post<BaseResponse<AllegatoUlterioreDocumentazione>>(url, formData);
  }

  public ricercaUlterioreDocumentazione(filter: UlterioreDocumentazioneSearch): Observable<BaseResponse<PaginatedBaseResponse<AllegatoUlterioreDocumentazione>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/ulterioreDoc/search";
    let httpParams: HttpParams = new HttpParams();
    if (filter.idFascicolo)
      httpParams = httpParams.append("idFascicolo", filter.idFascicolo.toString());
    if (filter.titolo)
      httpParams = httpParams.append("titolo", filter.titolo.toString());
    if (filter.descrizione)
      httpParams = httpParams.append("descrizione", filter.descrizione.toString());
    if (filter.inseritoDa)
      httpParams = httpParams.append("inseritoDa", filter.inseritoDa.toString());
    if (filter.visibileA)
    {
      filter.visibileA.forEach(mail =>
      {
        httpParams = httpParams.append("visibileA", mail);
      });
    }
    if (filter.destinatario)
      httpParams = httpParams.append("destinatario", filter.destinatario);
    if(filter.dataCondivisioneDa)
      httpParams = httpParams.append("dataCondivisioneDa", filter.dataCondivisioneDa.toDateString())
    if (filter.dataCondivisioneA)
      httpParams = httpParams.append("dataCondivisioneA", filter.dataCondivisioneA.toDateString())
    if (filter.colonna)
      httpParams = httpParams.append("colonna", filter.colonna);
    if (filter.direzione)
      httpParams = httpParams.append("direzione", filter.direzione);
    if(filter.limit)
      httpParams = httpParams.append("limit", filter.limit.toString());
    if(filter.page)
      httpParams = httpParams.append("page", filter.page.toString());

    return this.http.get<BaseResponse<PaginatedBaseResponse<AllegatoUlterioreDocumentazione>>>(url, {params: httpParams});
  }

  public exportFascicoli(filters: FascicoloSearch, exportType: "PDF"|"CSV"): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/exportFascicoli" + exportType;
    let httpParams: HttpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (!isNullOrUndefined(filters[key]))
        httpParams = httpParams.append(key, filters[key]);
    });
    filters.limit = null;
    filters.page = null;

    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  public getTemplateCorrispondenza(idFascicolo:number): Observable<BaseResponse<CorrispondenzaDTO>>{
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/getTemplate";
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.set("idFascicolo",idFascicolo+"");
    console.log("Params -> ", httpParams);
    return this.http.get<BaseResponse<CorrispondenzaDTO>>(url, {params: httpParams});
  }


  //metodi corrispondenza/comunicazione
  public inviaNuovaComunicazione(comunicazione: CorrispondenzaDTO, files: File[],idFascicolo:number): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/invia/"+idFascicolo;
    let formData: FormData = new FormData();
    let json = JSON.stringify(comunicazione);
    formData.append("comunicazione", new Blob([json], { type: 'application/json' }));
    if (files)
    {
      files.forEach(file =>
      {
        formData.append("allegati", file);
      });
    }
    return this.http.post<BaseResponse<DettaglioCorrispondenzaDTO>>(url, formData);
  }

  public searchCorrispondenza(filter: CorrispondenzaSearch): Observable<BaseResponse<PaginatedBaseResponse<DettaglioCorrispondenzaDTO>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/search";
    let httpParams: HttpParams = new HttpParams();
    Object.keys(filter).forEach(key =>
    {
      if (!isNullOrUndefined(filter[key]) && key.indexOf("dataInvio") === -1)
        httpParams = httpParams.append(key, filter[key]);
    });
    if(filter.dataInvioDa)
      httpParams = httpParams.append("dataInvioDa", filter["dataInvioDa"].toDateString());
    if (filter.dataInvioA)
      httpParams = httpParams.append("dataInvioA", filter["dataInvioA"].toDateString());
    return this.http.get<BaseResponse<PaginatedBaseResponse<DettaglioCorrispondenzaDTO>>>(url, { params: httpParams });
  }

  public deleteBozzaComunicazioni(id: number,idFascicolo:number): Observable<BaseResponse<boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/cancellaBozza";
    let httpParams: HttpParams = new HttpParams().set("idCorrispondenza", id.toString());
    httpParams = httpParams.append("idFascicolo", idFascicolo.toString());
    return this.http.delete<BaseResponse<boolean>>(url, {params: httpParams});
  }
  public caricaAllegatoComunicazione(file: File, idFascicolo: number, idCorrispondenza: number): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/caricaAllegato";
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo.toString())
                                                 .set("idCorrispondenza", idCorrispondenza.toString());
    let formData: FormData = new FormData();
    formData.append("file", file);
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, formData, { params: httpParams } );
  }
  public dettaglioComunicazione(idCorrispondenza: number): Observable<BaseResponse<DettaglioCom>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/corrispondenza/dettaglio";
    let httpParams = new HttpParams().set("idCorrispondenza", idCorrispondenza.toString());
    return this.http.get<BaseResponse<DettaglioCom>>(url, {params: httpParams});
  }


  public lineaTemporale(idFascicolo: string): Observable<BaseResponse<LineaTemporaleDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/azioni/lineaTemporale";
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo);
    return this.http.get<BaseResponse<LineaTemporaleDTO>>(url, {params: httpParams});
  }

  public particelleComuniForGruppo(): Observable<BaseResponse<TipologicaDTO[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/localizzazione/particelleComuniForGruppo";
    return this.http.get<BaseResponse<TipologicaDTO[]>>(url);
  }

  public findSchemaForSearch(): Observable<BaseResponse<SchemaRicerca>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/schemaRicerca";
    return this.http.get<BaseResponse<SchemaRicerca>>(url);
  }

  /**
   * chiamata al BE dopo aver premuto il tasto "prosegui" del tab Esito quando la pratica è in stato SELEZIONATA 
   */
  public prosegui(info: InformazioniDTO, provvedimentoFinaleEsitoVerifica: File): Observable<BaseResponse<any[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/esito/prosegui";
    let formData: FormData = new FormData();
    formData.append("file", provvedimentoFinaleEsitoVerifica);
    formData.append("body", new Blob([JSON.stringify(info)], {type: 'application/json'}));
    return this.http.post<BaseResponse<any[]>>(url, formData);
  }

  /**
   * chiamata al BE per il ripristino della lettera provvedimento finale
   */
  public ripristina(idFascicolo: string): Observable<BaseResponse<number>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/esito/resetLettera";
    let params: HttpParams = new HttpParams();
    params = params.append("idFascicolo", idFascicolo);
    return this.http.get<BaseResponse<number>>(url, {params: params});
  }

  /**
   * chiamata al BE per il caricamento della lettera provvedimento finale 
   */
  public caricaLettera(idFascicolo: string, lettera: File): Observable<BaseResponse<number>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/esito/caricaLettera";
    let params: HttpParams = new HttpParams();
    params = params.append("idFascicolo", idFascicolo);
    let formData: FormData = new FormData();
    formData.append("file", lettera);
    /* formData.append("idFascicolo", idFascicolo); */
    return this.http.post<BaseResponse<number>>(url, formData, {params: params});
  }

  /**
   * chiamata al BE per invio comunicazione e termine della verifica
   */
  public inviaEterminaVerifica(idFascicolo: string): Observable<BaseResponse<boolean>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/esito/invia";
    let params: HttpParams = new HttpParams();
    params = params.append("idFascicolo", idFascicolo);
    return this.http.get<BaseResponse<boolean>>(url, {params: params});
  }

  /**
   * chiamata al BE per il retrieve dei dati delle due tabelle del tab esito
   */
  public getDati(idFascicolo: string): Observable<BaseResponse<any[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/esito/dati";
    let params: HttpParams = new HttpParams();
    params = params.append("idFascicolo", idFascicolo);
    return this.http.get<BaseResponse<any[]>>(url, {params: params});
  }

  /* chiamata per download provvedimento nella sezione pubblica */
  public downloadProvvedimento(idFascicolo: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/downloadProvvedimento";
    let httpParams: HttpParams = new HttpParams().set("idFascicolo", idFascicolo);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams});
  }

  public downloadProvvedimentoByCodice(codicePratica: string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/public/fascicolo/downloadProvvedimentoByCodice";
    let httpParams: HttpParams = new HttpParams().set("codice", codicePratica);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams});
  }

  public downloadProvvedimentoByCodiceFromIstr(codicePratica: string): Observable<HttpResponse<Blob>>
  {
    let url: string = environment.basepath_istruttoria_be + "/public/fascicolo/downloadProvvedimentoByCodice";
    let httpParams: HttpParams = new HttpParams().set("codice", codicePratica);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams});
  }

  public getGruppiOrganizzazioni(): Observable<BaseResponse<PlainTypeStringId[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/gruppiOrganizzazioni";
    return this.http.get<BaseResponse<PlainTypeStringId[]>>(url);
  }

  public getTestoPrivacy(): Observable<BaseResponse<string>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/testo-privacy.pjson";
    return this.http.get<BaseResponse<string>>(url);
  }

  /**
   * rimozione esri edit layer delle geometrie con oid(s) in input
   */
  public deleteOid(oid:any):Promise<number>{
    var headers = { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })} ;  
    return this.http
        .post(CONST.EDIT_LAYER+'/applyEdits',"f=json&deletes="+oid,headers)
        .toPromise()
        .then(response => {
          return (<BaseResponse<number>>response).payload;
        });
  }

  /**
   * metodo che permette l'autocomplete del rup 
   * @param rup rup da autocompletare
   */
  public autocompleteRup(rup: string): Observable<BaseResponse<string[]>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/autocompleteRup";
    let params: HttpParams = new HttpParams();
    params = params.append("rup", rup);
    return this.http.get<BaseResponse<string[]>>(url, {params: params});
  }

  /**
   * Meotodo unico per il download dei risultati di ricerca in are privata
   * @param filters 
   * @param type 
   */
  public exportPrivatePdfCsv(filters: FascicoloSearch, type: "PDF" | "CSV"): Observable<HttpResponse<Blob>>
  {
    switch(type)
    {
      case "PDF":
        return this.exportPdf(filters);
      case "CSV":
        return this.exportCsv(filters);
    }
  }

  /**
   * Metodo per scaricare i risultati di ricerca come file csv
   * @param filters 
   */
  public exportCsv(filters: FascicoloSearch): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/exportFascicoliCSV";
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  /**
   * Metodo per scaricare i risultati di ricerca come file pdf
   * @param filters 
   */
  public exportPdf(filters: FascicoloSearch): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/fascicolo/exportFascicoliPDF";
    let httpParams = new HttpParams();
    Object.keys(filters).forEach(key =>
    {
      if (filters[key])
        httpParams = httpParams.append(key, filters[key]);
    });
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  /**
   * 
   * @returns 
   */
  public sezioniCatastali(): Observable<SelectOption[]> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/localizzazione/sezioniCatastali";
    return this.http.get<BaseResponse<SelectOption[]>>(url).pipe(
      map((response: BaseResponse<SelectOption[]>) => {
        return response.payload;
      })
    );
  }

  public getLastRichiestaAbilitazioneData(): Observable<BaseResponse<UtenteAttributeDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/abilitazioni/get-last-richiesta-data.pjson";
    return this.http.get<BaseResponse<UtenteAttributeDTO>>(url);
  }

  /*----- RICHIESTE POST TRASMISSIONE ----- */

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public getRichiestePostTrasmissione(searchRichiesta: RichiestePostTrasmissioneSearch): Observable<BaseResponse<PaginatedBaseResponse<RichiestePostTrasmissioneDTO>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/search";

    let httpParams = new HttpParams();

    Object.keys(searchRichiesta).forEach(key =>
      {
        if (searchRichiesta[key])
          httpParams = httpParams.append(key, searchRichiesta[key]);
      });
      
    return this.http.get<BaseResponse<PaginatedBaseResponse<RichiestePostTrasmissioneDTO>>>(url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public createRichiestePostTrasmissione(idFascicolo: string, tipoRichiesta: string): Observable<BaseResponse<RichiestePostTrasmissioneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/crea";

    let httpParams = new HttpParams()
            .append("idFascicolo", idFascicolo)
            .append("tipo", tipoRichiesta);

    return this.http.post<BaseResponse<RichiestePostTrasmissioneDTO>>
              (url, null, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public updateRichiestePostTrasmissione(richiesta: RichiestePostTrasmissioneDTO): Observable<BaseResponse<RichiestePostTrasmissioneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/modifica";
 
    return this.http.put<BaseResponse<RichiestePostTrasmissioneDTO>>( url, richiesta);
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public deleteRichiestePostTrasmissione(idFascicolo: string, idRichiesta: string): Observable<Boolean> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/elimina";

    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idRichiesta", idRichiesta);

    return this.http.delete<Boolean>(url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public dettaglioRichiestePostTrasmissione(idFascicolo: string, idRichiesta: string): Observable<BaseResponse<RichiestePostTrasmissioneDetailDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/dettaglio";
    
    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idRichiesta", idRichiesta);

    return this.http.get<BaseResponse<RichiestePostTrasmissioneDetailDTO>>( url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public trasmettiRichiestePostTrasmissione(idFascicolo: string, idRichiesta: string): Observable<BaseResponse<RichiestePostTrasmissioneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/trasmetti";
    
    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idRichiestaPostTrasmissione", idRichiesta);

    return this.http.post<BaseResponse<RichiestePostTrasmissioneDTO>>( url, null, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public caricaAllegatoRicheistaPostTramissione(file: File, idFascicolo: number, idRichiesta: number): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/caricaAllegato";
    let httpParams: HttpParams = new HttpParams().append("idFascicolo", idFascicolo.toString())
                                                 .append("idRichiesta", idRichiesta.toString());
    let formData: FormData = new FormData();
    formData.append("file", file);
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, formData, { params: httpParams } );
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public eliminaAllegatoRicheistaPostTramissione(idAllegato: number, idFascicolo:number, idRichiesta: number): Observable<BaseResponse<Boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/richiestePostTrasmissione/eliminaAllegato";
    let httpParams: HttpParams = new HttpParams().append("idAllegato", idAllegato.toString())
                                                 .append("idFascicolo", idFascicolo.toString())
                                                 .append("idRichiesta", idRichiesta.toString());
    return this.http.delete<BaseResponse<Boolean>>(url, {params: httpParams});
  }

  /*----- ANNOTAZIONI INTERNE -----*/

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
    public isPresentAnnotazione(idFascicolo: string): Observable<BaseResponse<AnnotazioniInterneDetailDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/isPresent";

    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
      
    return this.http.get<BaseResponse<AnnotazioniInterneDetailDTO>>(url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public getAnnotazioniInterne(searchAnnotazione: AnnotazioniInterneSearch): Observable<BaseResponse<PaginatedBaseResponse<AnnotazioniInterneDTO>>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/search";

    let httpParams = new HttpParams();

    Object.keys(searchAnnotazione).forEach(key =>
      {
        if (searchAnnotazione[key])
          httpParams = httpParams.append(key, searchAnnotazione[key]);
      });
      
    return this.http.get<BaseResponse<PaginatedBaseResponse<AnnotazioniInterneDTO>>>(url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public createAnnotazioniInterne(idFascicolo: string): Observable<BaseResponse<AnnotazioniInterneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/crea";
    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);

    return this.http.post<BaseResponse<AnnotazioniInterneDTO>>(url, null, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public updateAnnotazioneInterna(annotazione: AnnotazioniInterneDTO): Observable<BaseResponse<AnnotazioniInterneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/modifica";
 
    return this.http.put<BaseResponse<AnnotazioniInterneDTO>>( url, annotazione);
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public deleteAnnotazioniInterne(idFascicolo: string, idAnnotazioneInterna: string): Observable<Boolean> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/elimina";

    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idAnnotazioneInterna", idAnnotazioneInterna);

    return this.http.delete<Boolean>(url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public dettaglioAnnotazioniInterne(idFascicolo: string, idAnnotazioneInterna: string): Observable<BaseResponse<AnnotazioniInterneDetailDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/dettaglio";
    
    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idAnnotazioneInterna", idAnnotazioneInterna);

    return this.http.get<BaseResponse<AnnotazioniInterneDetailDTO>>( url, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public trasmettiAnnotazioniInterne(idFascicolo: string, idAnnotazioneInterna: string): Observable<BaseResponse<AnnotazioniInterneDTO>> {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/trasmetti";
    
    let httpParams = new HttpParams();

    httpParams = httpParams.append("idFascicolo", idFascicolo);
    httpParams = httpParams.append("idAnnotazioneInterna", idAnnotazioneInterna);

    return this.http.post<BaseResponse<AnnotazioniInterneDTO>>( url, null, { params: httpParams });
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public caricaAllegatoAnnotazioniInterne(file: File, idFascicolo: number, idAnnotazioneInterna: number): Observable<BaseResponse<AllegatoCustomDTO>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/caricaAllegato";
    let httpParams: HttpParams = new HttpParams().append("idFascicolo", idFascicolo.toString())
                                                 .append("idAnnotazioneInterna", idAnnotazioneInterna.toString());
    let formData: FormData = new FormData();
    formData.append("file", file);
    return this.http.post<BaseResponse<AllegatoCustomDTO>>(url, formData, { params: httpParams } );
  }

  /**
   * @author Marta Zecchillo, Raffaele Del Basso
   * @returns 
   */
  public eliminaAllegatoAnnotazioniInterne(idAllegato: string, idFascicolo:string, idAnnotazioneInterna: string): Observable<BaseResponse<Boolean>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/annotazioniInterne/eliminaAllegato";
    let httpParams: HttpParams = new HttpParams().append("idAllegato", idAllegato)
                                                 .append("idFascicolo", idFascicolo)
                                                 .append("idAnnotazioneInterna", idAnnotazioneInterna);
    return this.http.delete<BaseResponse<Boolean>>(url, {params: httpParams});
  }


  /**
   * 
   * @param filter 
   * @returns elenco dei documenti importabili
   */
  public getDocumentiImportabili(filter: VPaesaggioProvvedimentiSearch): Observable<BaseResponse<PaginatedBaseResponse<VPaesaggioProvvedimentiDTO>>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/sportello/provvedimentiUtilizzabili";
    let httpParams: HttpParams = new HttpParams();
    Object.keys(filter).forEach(key =>
    {
      if ((filter[key])){
        httpParams = httpParams.append(key, filter[key]);
      }
    });
    return this.http.get<BaseResponse<PaginatedBaseResponse<VPaesaggioProvvedimentiDTO>>>(url, { params: httpParams });
  }


  /**
   * effettua import dei dati nella bozza all'idFascicolo
   * dopo aver effettuato l'operazione, va riletto il fascicolo
   * @param idFascicolo 
   * @param codiceTrasmissione 
   * @param idProvvedimento 
   * @returns eventuale messaggio di warning su alcune informazioni non importabili
   */
  public importaDaSportelloAmbiente(idFascicolo:string,codiceTrasmissione:string,idProvvedimento:string): Observable<BaseResponse<string>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/sportello/importa";
    let httpParams: HttpParams = new HttpParams().append("idFascicolo", idFascicolo)
                                                 .append("codiceTrasmissione", codiceTrasmissione)
                                                 .append("idProvvedimento", idProvvedimento);
    return this.http.post<BaseResponse<string>>(url, null, { params: httpParams } );
  }

  /**
   * download del provvedimento potenzialmente importabile dallo sportello
   * @param idFascicolo 
   * @param codiceTrasmissione 
   * @param idProvvedimento 
   * @returns 
   */
  public downloadDocumentoSportello(idFascicolo:string,codiceTrasmissione:string,idProvvedimento:string): Observable<HttpResponse<Blob>>
  {
    let url: string = CONST.WEB_RESOURCE_BASE_URL + "/sportello/provvedimentiUtilizzabili/download";
    let httpParams: HttpParams = new HttpParams().append("idFascicolo", idFascicolo)
                                                 .append("codiceTrasmissione", codiceTrasmissione)
                                                 .append("idProvvedimento", idProvvedimento);
    return this.http.get(url, { observe: 'response', responseType: 'blob', params: httpParams });
  }

}

