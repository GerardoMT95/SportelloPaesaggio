import { Fascicolo } from 'src/app/shared/models/models';
import { Component, OnInit, ViewChild } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { DialogService } from 'primeng/primeng'
import { copyOf, downloadFile } from 'src/app/core/functions/generic.utils'
import { TableConfig } from 'src/app/core/models/header.model'
import { CustomDialogService } from 'src/app/core/services/dialog.service'
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models'
import { DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component'
import { LoggedUser } from 'src/app/shared/components/model/logged-user'
import { fromAllegatoToBasicFile } from 'src/app/shared/functions/ObjectUtils'
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service'
import { LoadingService } from 'src/app/shared/services/loading.service'
import { HttpRelazioneEnteService } from 'src/app/shared/services/relazioneEnte/http-relazione-ente.service'
import { UserService } from 'src/app/shared/services/user.service'
import { NuovaComunicazioneComponent } from '../../../../shared/components/nuova-comunicazione/nuova-comunicazione.component'
import { comunicationsTableHeaders } from '../../gestione-istanza-relazione-ente-config'
import { IButton } from './../../../../core/models/dialog.model'
import { CONST } from './../../../../shared/constants'
import { BasicFile, DocumentType } from './../../../../shared/models/allegati.model'
import { Allegato, DocumentoRelazione, GroupType, RelazioneEnte, StatoEnum, StatoRelazione, TemplateDestinatarioDTO, TemplateEmailDestinatariDto } from './../../../../shared/models/models'
import { HttpAllegatoService } from './../../../../shared/services/http-allegato.service'
import { CorrispondenzaDTO, DettaglioCorrispondenzaDTO, Template, TemplateComunicazione } from './../../../gestione-istanza-comunicazioni/model/corrispondenza.models'
import { DataService } from './../../../gestione-istanza/services/data-service/data.service'
import { RelazioneEnteConst } from './../../configurations/relazione.const'
import { AllegatoService } from 'src/app/shared/services/allegato.service';



@Component({
  selector: 'app-gestione-istanza-relazione-ente-page',
  templateUrl: './gestione-istanza-relazione-ente-page.component.html',
  styleUrls: ['./gestione-istanza-relazione-ente-page.component.scss'],
  providers: [DialogService]
})
export class GestioneIstanzaRelazioneEntePageComponent implements OnInit
{
  @ViewChild("nuovaCom", {static: false}) nuovaCom: NuovaComunicazioneComponent

  public comunicationsTableHeaders: TableConfig[]=[]
  public documenTypeOptions: DocumentType[]=[]
  public allegati: Allegato[] = []
  public realzione: RelazioneEnte
  public form: FormGroup
  public trasmetti: boolean = false
  public statoEnum = StatoEnum
  public headers: TableConfig[]=[] 
  //sezione per la gestione della comunicazione
  public display: boolean = false
  public idComunicazione: number = null
  public templates: TemplateComunicazione[] = []
  public oLabel: string = "label"
  public toEdit: DettaglioCorrispondenzaDTO
  public files: BasicFile[]=[]
  public hasProtocollazione: boolean;
  public nonTrasmessa: boolean = false;


  constructor(private data: DataService,
              private formBuilder: FormBuilder,
              private adminService: AdminFunctionsService,
              private service: HttpRelazioneEnteService,
              private allegatiService: AllegatoService,
              private dialog: CustomDialogService,
              private userService: UserService,
              private sharedData: DataService,
              private loading: LoadingService) {
                this.loading.emitLoading(true)
              }

  ngOnInit()
  {
    this.hasProtocollazione=this.data.hasProtocollazione;
    this.oLabel="label"
    if (!this.readOnly || (this.sharedData.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_CON_AVVIO" ||
        this.sharedData.fascicolo.statoRelazioneEnte === "RELAZIONE_TRASMESSA_SENZA_AVVIO"))
    {
      this.service.callSearchRelazioneEnte(this.sharedData.fascicolo.id).toPromise().then(result =>
      {
        if (result.codiceEsito === CONST.OK && result.payload)
        {
          this.realzione = result.payload
          this.allegati = this.realzione.grigliaAllegati
          //gli allegati vengono trasformati in elementi di tipo file da passare al componente app-allegati-form
          if (!this.allegati)
          {
            this.files = []
            this.allegati = []
          } else
          {
            this.allegati.forEach(element =>
            {
              this.files.push(fromAllegatoToBasicFile(element))
            })
          }
          this.comunicationsTableHeaders = comunicationsTableHeaders
          this.documenTypeOptions = RelazioneEnteConst.documentTypes
          this.headers = RelazioneEnteConst.relazione_table
          this.buildForm()
          //Popolamento del'oggetto che contiene l'elenco dei templateEmail e templateDestinatari 
          if (!this.readOnly)
          {
            this.adminService.
              getTemplatDestEmailList("TRASM_REL_ENTE", this.sharedData.fascicolo.id).
              subscribe(_result =>
              {
                if (_result.codiceEsito === CONST.OK && _result.payload)
                {
                  _result.payload.map((m: TemplateEmailDestinatariDto) =>
                  {
                    let templateTemp: TemplateComunicazione = new TemplateComunicazione()
                    templateTemp.descrizione = m.template.descrizione
                    templateTemp.label = m.template.descrizione
                    templateTemp.value = m.template.codice
                    templateTemp.template = new Template()
                    templateTemp.template.oggetto = m.template.oggetto
                    templateTemp.template.testo = m.template.testo
                    templateTemp.template.riservata = m.template.riservata;
                    templateTemp.template.protocollazione = m.template.protocollazione;
                    templateTemp.template.destinatari = []
                    m.destinatari.map((d: TemplateDestinatarioDTO) =>
                    {
                      let destinatario: DestinatarioComunicazioneDTO = new DestinatarioComunicazioneDTO()
                      destinatario.email = d.email
                      destinatario.id = d.id
                      destinatario.nome = d.denominazione
                      destinatario.pec = d.pec == "pec" ? true : false
                      destinatario.tipo = d.tipo
                      templateTemp.template.destinatari.push(destinatario)
                    })
                    this.templates.push(templateTemp);
                  });
                  this.loading.emitLoading(false);
                } else
                {
                  this.loading.emitLoading(false);
                }
              });
          }
          else
          {
            this.loading.emitLoading(false);
          }
        } else
        {
          this.loading.emitLoading(false)
        }
      });
    }
    else
    {
      this.loading.emitLoading(false);
      this.nonTrasmessa = true;
    }
  }

  buildForm()
  {
    this.form = this.formBuilder.group
    ({
      idRelazioneEnte: [this.realzione.idRelazioneEnte],
      idPratica: [this.realzione.idPratica],
      numeroProtocolloEnte: [this.realzione.numeroProtocolloEnte, [Validators.required]],
      nominativoIstruttore: [this.realzione.nominativoIstruttore, [Validators.required]],
      dettaglioRelazione: [this.realzione.dettaglioRelazione],
      notaInterna: [this.realzione.notaInterna],
    })

    if(this.disabled) 
      this.form.disable()
  }

  get currentUser(): LoggedUser { return this.userService.getUser() }
  get comunicazione(): DettaglioCorrispondenzaDTO { return this.realzione.dettaglioCorrispondenza }

  public save(): void
  {
    this.loading.emitLoading(true)
    this.service.callUpdateRelazioneEnte(this.form.getRawValue()).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK)
      {
        this.loading.emitLoading(false)
        let message: string = "Operazione eseguita con successo"
        let title: string = "Successo"
        let button: IButton[] = [{id: 0, label: "generic.chiudi"}]
        this.dialog.showDialog(true, message, title, button, null, DialogType.SUCCESS);

      }else{
        this.loading.emitLoading(false)
      }
    })
  }

  public upload(event: any): void
  {
    this.loading.emitLoading(true)
    this.service.callUploadFile(this.realzione.idRelazioneEnte, this.realzione.idPratica,
                                event.type, event.file).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK && result.payload)
      {
        this.files.push(fromAllegatoToBasicFile(result.payload))
        this.loading.emitLoading(false)
        let title: string = "Successo"
        let message: string = "Documento caricato con successo"
        let button: IButton[] = [{id: 0, label: "generic.chiudi"}]
        this.dialog.showDialog(true, message, title, button, null, DialogType.SUCCESS)
      }
    })
  }

  public download(event: DocumentoRelazione): void
  {
    this.loading.emitLoading(true)
    this.allegatiService.downloadAllegatoFascicolo(event.id,this.data.idPratica, '/istruttoria/allegati/download.pjson')
    .subscribe(result =>
    //this.allegatiService.callDownloadAllegato(event.id).subscribe(result =>
    {
      if(result.status == 200)
      {
        downloadFile(result.body, event.nome)
        this.loading.emitLoading(false)
      }
      else
      {
        let title: string = "Errore"
        let message: string = "Errore durante il download dell'allegato"
        let button: IButton[] = [{ id: 0, label: "generic.chiudi" }]
        this.dialog.showDialog(true, message, title, button, null, DialogType.ERROR)
        this.loading.emitLoading(false)
      }
    })
  }

  public delete(event: DocumentoRelazione): void
  {
    this.loading.emitLoading(true)
    this.service.callDeleteFile(this.realzione.idRelazioneEnte, event.id,this.data.idPratica).subscribe(result =>
    {
      if (result.codiceEsito === CONST.OK && result.payload === true)
      {
        let i = this.files.map(m => m.id).indexOf(event.id)
        this.loading.emitLoading(false)
        this.files.splice(i, 1)
        let title: string = "Successo"
        let message: string = "Documento eliminato con successo"
        let button: IButton[] = [{ id: 0, label: "generic.chiudi" }]
        this.dialog.showDialog(true, message, title, button, null, DialogType.SUCCESS)
      }
      else
      {
        this.loading.emitLoading(false)
        let title: string = "Errore"
        let message: string = "Errore durante la cancellazione dell'allegato"
        let button: IButton[] = [{ id: 0, label: "generic.chiudi" }]
        this.dialog.showDialog(true, message, title, button, null, DialogType.ERROR)
      }
    })
  }

  public sendCom(draft: boolean, protocollato?: boolean)
  {
    this.nuovaCom.inviato = true
    if(draft || (this.nuovaCom.comForm.valid && this.isValid() && this.nuovaCom.destinatari.length>0))
    {
      let data: CorrispondenzaDTO = this.nuovaCom.comForm.getRawValue()
      let destinatari = this.nuovaCom.destinatari
      let dataSend:DettaglioCorrispondenzaDTO = new DettaglioCorrispondenzaDTO()
      dataSend.corrispondenza = data
      dataSend.corrispondenza.id = this.idComunicazione
      dataSend.corrispondenza.bozza = draft
      dataSend.destinatari = destinatari

      let protStr: "WITH_PROT"|"WITHOUT_PROT" = null
      if(protocollato!=null)
        protStr = protocollato ? "WITH_PROT" : "WITHOUT_PROT"
      this.sendMail(dataSend, protStr)
    }
  }
  public modificaBozza(event: any): void
  {
    console.log("entrato in modificaBozza")
    
    this.toEdit = this.comunicazione
    this.idComunicazione = this.toEdit.corrispondenza.id
    this.display = true
  }
 

  public chiudi(): void
  {
    this.idComunicazione = null
    this.toEdit = null
    this.display = false
  }

  public openComunicationsModal(): void
  {
    this.trasmetti = true
    if(this.isValid())
    {
    this.loading.emitLoading(true);
    this.service.callUpdateRelazioneEnte(this.form.getRawValue()).subscribe(result_update =>
      {
        if(result_update.codiceEsito === CONST.OK){
            let data: CorrispondenzaDTO = new CorrispondenzaDTO()
            data.bozza = true
            this.loading.emitLoading(false);
            if (!this.realzione.dettaglioCorrispondenza )
            {
              this.loading.emitLoading(true);
              this.service.callSaveComunicazione(this.realzione.idRelazioneEnte, data,this.data.idPratica).subscribe(result =>
              {
                this.loading.emitLoading(false);
                if (result.codiceEsito === CONST.OK && result.payload)
                {
                  if (!this.realzione.dettaglioCorrispondenza)
                    this.realzione.dettaglioCorrispondenza = null
                  //this.realzione.comunicazioni.push(result.payload);
                  this.idComunicazione = result.payload.corrispondenza.id
                  this.display = true
                }
              })
            }else
            {
              this.modificaBozza({ id: this.comunicazione.corrispondenza.id, elimina: false })
            }
          }
      });
    } else
    {
      let title: string = "Impossibile proseguire"
      let message: string = "Impossibile proseguire con la trasmissione della relazione: form incompleto o relazione tecnica illustrativa mancante"
      let btn: IButton[] = [{id: 0, label: "generic.chiudi"}]
      this.dialog.showDialog(true, message, title, btn, null, DialogType.ERROR)
    }
  }

  public annulla(): void
  {
    this.form.reset()
    this.form.patchValue
    ({
      //id: [this.realzione.idRelazioneEnte],
      //codiceFascicolo: [this.sharedData.codicePratica]
      idRelazioneEnte: this.realzione.idRelazioneEnte,
      idPratica:this.realzione.idPratica
    })
  }

  private sendMail(data: DettaglioCorrispondenzaDTO, protStr: "WITH_PROT" | "WITHOUT_PROT"): void
  {
    this.loading.emitLoading(true);
    this.service.callUpdateComunicazione(this.realzione.idRelazioneEnte, data,this.data.idPratica, protStr).subscribe(result =>
    {
      let title: string = "Operazione completata"
      let message: string = ""
      let button: IButton[] = [{id: 0, label: "generic.chiudi"}]
      if (result.codiceEsito === CONST.OK && result.payload){
        if (data.corrispondenza.bozza){
          message = "Comunicazione salvata in bozza"
        }else{
          message = "Relazione inviata con successo"
          this.realzione.sent = true
          this.form.disable();
          this.updateLocalData();
        }
        this.realzione.dettaglioCorrispondenza=result.payload
        this.chiudi()
        this.dialog.showDialog(true, message, title, button, null, DialogType.SUCCESS)
      }
      this.loading.emitLoading(false);
    });
    
  }

  private isValid(): boolean
  {
    let valid: boolean = this.form.valid && 
                         //this.realzione.grigliaAllegati &&  //non viene sincronizzata...
                         this.files.some(p => p.type == "REL_TEC_ILL")
    return valid
  }

  get disabled(): boolean 
  { 
    let statoRelazione:StatoRelazione= this.sharedData.fascicolo.statoRelazioneEnte
    let stato: StatoEnum = this.sharedData.fascicolo.attivitaDaEspletare
    let disableStatus: StatoEnum[] = [StatoEnum.InTrasmissione, StatoEnum.Trasmessa]
    let groupType: GroupType = this.userService.groupType
    let disabledGroupTypes: GroupType[] = [GroupType.Soprintendenza, GroupType.EnteTerritoriale, GroupType.CommissioneLocale]
    return (this.realzione && this.realzione.sent) ||
           disableStatus.includes(stato) ||
           disabledGroupTypes.includes(groupType) || statoRelazione!="RELAZIONE_NON_TRASMESSA" 
  }

  private updateLocalData(): void
  {
    let fascicolo: Fascicolo = copyOf(this.sharedData.fascicolo);
    fascicolo.dataTrasmissioneRelazione = new Date();
    this.sharedData.fascicolo = fascicolo;
  }

  //get readOnly(): boolean { return this.userService.groupType != GroupType.EnteDelegato; }
  get readOnly(): boolean { return [GroupType.EnteDelegato, GroupType.Regione].indexOf(this.userService.groupType) < 0; }

  getCodiceFascicolo(){
    return this.sharedData.fascicolo.codicePraticaAppptr;
  }

}
