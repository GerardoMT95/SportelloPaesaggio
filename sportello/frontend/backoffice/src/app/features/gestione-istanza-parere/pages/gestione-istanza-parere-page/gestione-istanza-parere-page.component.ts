import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService } from 'primeng/primeng';
import { combineLatest, Observable } from 'rxjs';
import { copyOf, downloadFile, updateAllFormValidity } from 'src/app/core/functions/generic.utils';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { canOperate } from 'src/app/features/gestione-istanza/functions/utils';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { BaseResponse } from 'src/app/shared/components/model/base-response';
import { CONST } from 'src/app/shared/constants';
import { toTemplateComunicazione } from 'src/app/shared/functions/ObjectUtils';
import { Fascicolo, ParereSoprintendenza, StatoEnum } from 'src/app/shared/models/models';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { grigliaAllegatiData } from '../../gestione-istanza-parere-config';
import { NuovaComunicazioneComponent } from './../../../../shared/components/nuova-comunicazione/nuova-comunicazione.component';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { Allegato, GroupType, StatoParere } from './../../../../shared/models/models';
import { AdminFunctionsService } from './../../../../shared/services/admin/admin-functions.service';
import { HttpAllegatoService } from './../../../../shared/services/http-allegato.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { ParereSoprintendenzaService } from './../../../../shared/services/parere-soprintendenza/parere-soprintendenza.service';
import { UserService } from './../../../../shared/services/user.service';
import { AllegatoCorrispondenza, DettaglioCorrispondenzaDTO, TemplateComunicazione } from './../../../gestione-istanza-comunicazioni/model/corrispondenza.models';
import { TipologicaDTO } from './../../../gestione-istanza-comunicazioni/model/tipologica';
import { GestioneIstanzaComunicazioniService } from './../../../gestione-istanza-comunicazioni/service/gestione-istanza-comunicazioni.service';
import { IstanzaFascicolo } from './../../../gestione-istanza/configuration/tabs.const';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { documentTableHeadersParere } from './../../gestione-istanza-parere-config';

@Component({
  selector: 'app-gestione-istanza-parere-page',
  templateUrl: './gestione-istanza-parere-page.component.html',
  styleUrls: ['./gestione-istanza-parere-page.component.scss'],
  providers: [DialogService]
})
export class GestioneIstanzaParerePageComponent implements OnInit
{
  @ViewChild("nuovaCom", {static: false}) componentComunicazione: NuovaComunicazioneComponent;
  public form: FormGroup;
  public validationActivate: boolean = false;
  public validationAllegati: boolean = false;
  public displayCom: boolean = false;
  public templates: TemplateComunicazione[];
  public parere: ParereSoprintendenza;
  public canOperate: boolean;
  public disableButton = false;
  public allegati: Allegato[] = [];
  public statoEnum = StatoEnum;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private dialog: CustomDialogService,
              private sharedData: DataService,
              private service: ParereSoprintendenzaService,
              private comunicazioniService: GestioneIstanzaComunicazioniService,
              private allegatiService: AllegatoService,
              private adminService: AdminFunctionsService,
              private loading: LoadingService)
  {
    this.loading.emitLoading(true);
  }

  ngOnInit()
  {
    this.canOperate = canOperate(this.userService.groupType, 
                                  IstanzaFascicolo.tabs.parere, 
                                  this.sharedData.status);
    if (this.evaluate())
      this.getParereAndTemplate();
    else
      this.loading.emitLoading(false);
  }

  private getParereAndTemplate(): void
  {
    let _this = this;
    let findParere$ = _this.service.find(_this.sharedData.idPratica);
    let findTemplates$ = _this.adminService.getTemplatDestEmailList("TRASM_PAR_SOP", this.fascicolo.id);
    _this.loading.emitLoading(true);
    combineLatest([findParere$, findTemplates$]).subscribe(([respParere, respTemplates]) =>
    {
      if (respParere.codiceEsito === CONST.OK && respTemplates.codiceEsito === CONST.OK)
      {
        _this.parere = respParere.payload;
        _this.allegati = _this.parere.allegati ? _this.parere.allegati : [];
        _this.templates = toTemplateComunicazione(respTemplates.payload);
        _this.buildForm();
      }
      _this.loading.emitLoading(false);
    });
  }

  private evaluate(): boolean
  {
    let statoParere = this.sharedData.fascicolo.statoParereSoprintendenza;
    let getData = false;
    switch(statoParere)
    {
      case "PARERE_NON_PREVISTO":
      case "PARERE_NON_ALLEGATO":
        getData = false;
        break;
      case "PARERE_INSERITO_ENTE":
      case "PARERE_INSERITO_SOPRINTENDENZA":
        getData = true;
        break;
      case "PARERE_IN_BOZZA_ENTE":
        //getData = this.userService.groupType === GroupType.EnteDelegato;
        getData = [GroupType.EnteDelegato, GroupType.Regione].indexOf(this.userService.groupType) >= 0;
        break;
      case "PARERE_IN_BOZZA_SOPRINTENDENZA":
        getData = this.userService.groupType === GroupType.Soprintendenza;
        break;
    }
    return getData;
  }
  
  public createAndNavigateToDetails(): void 
  { 
    let parere: ParereSoprintendenza = new ParereSoprintendenza();
    parere.idPratica = this.fascicolo.id;
    this.loading.emitLoading(true);
    this.service.insert(parere).subscribe(result =>
    {
      if (result.codiceEsito == CONST.OK && result.payload)
      {
        this.parere = result.payload;
        this.buildForm();
        let stato: StatoParere = this.isSoprintendenza ? "PARERE_IN_BOZZA_SOPRINTENDENZA" 
                                                       : "PARERE_IN_BOZZA_ENTE";
        this.changeStatoParere(stato);
      }
      this.loading.emitLoading(false);
    });
  }

  private buildForm(): void
  {
    let parere: ParereSoprintendenza = this.parere;
    this.form = this.formBuilder.group
    ({
      id: [parere.id, Validators.required],
      idPratica: [parere.idPratica, Validators.required],
      numeroProtocollo: [parere.numeroProtocollo],
      nominativoIstruttore: [parere.nominativoIstruttore],
      esitoParere: [parere.esitoParere, Validators.required],
      note: [parere.note],
      dettaglioPrescrizione: [parere.dettaglioPrescrizione]
    });
    this.updateDettaglio();
  }

  public updateDettaglio(): void
  {
    let requiredCondition = ["NON_AUTORIZZATO", "AUT_CON_PRESCRIZ"];
    let esito = this.form.get("esitoParere").value;
    if (requiredCondition.includes(esito))
      this.form.get("dettaglioPrescrizione").enable();
    else
      this.form.get("dettaglioPrescrizione").disable();
  }

  public save(): void
  {
    let parere: ParereSoprintendenza = this.form.getRawValue();
    this.validationActivate = true;
    this.validationAllegati = false;
    if (this.form.valid)
    {
      this.loading.emitLoading(true);
      this.service.update(parere).subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
          this.parere = { ...this.parere, ...response.payload };
        this.loading.emitLoading(false);
        this.successSaveMessage();
      });
    }
  }

  public openTransmit(): void
  {
    updateAllFormValidity(this.form);
    this.validationActivate = true;
    this.validationAllegati = true;
    if (this.form.valid && this.allegati.length > 0)
    {
      this.displayCom = true;
      if(!this.comunicazione)
      {
        this.loading.emitLoading(true);
        this.service.creaComunicazione(this.parere.id,this.fascicolo.id).subscribe(response =>
        {
          if(response.codiceEsito === CONST.OK)
            this.parere.comunicazione = response.payload;
          this.loading.emitLoading(false);
        });
      }
    }
  }

  private prepareAllSaveCom(): Observable<BaseResponse<DettaglioCorrispondenzaDTO>>
  {
    let comunicazione: DettaglioCorrispondenzaDTO = new DettaglioCorrispondenzaDTO();
    comunicazione.corrispondenza = this.componentComunicazione.comForm.getRawValue();
    comunicazione.destinatari = this.componentComunicazione.destinatari;
    comunicazione.corrispondenza.bozza = true;
    this.loading.emitLoading(true);
    return this.comunicazioniService.salvaBozza(this.sharedData.idPratica,comunicazione);
  }

  public saveCom(): void
  {
    this.loading.emitLoading(true);
    this.prepareAllSaveCom().subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this.parere.comunicazione = response.payload;
      this.loading.emitLoading(false);
      this.successSaveMessage();
    });
  }

  public allegaSoprintendenza(): void
  {
    this.loading.emitLoading(true);
    this.prepareAllSaveCom().subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.parere.comunicazione = response.payload;
        this.displayCom = false;
        this.allegaParere();
      }
    });
  }

  public uploadFileCom(event: any): void
  {
    this.loading.emitLoading(true);
    this.comunicazioniService.upload(event.file, this.fascicolo.id, event.idComunicazione).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        if(!this.comunicazione.allegatiInfo)
          this.parere.comunicazione.allegatiInfo = [];
        this.parere.comunicazione.allegatiInfo.push(response.payload);
      }
      this.loading.emitLoading(false);
    });
  }

  private getAllegato(resp: TipologicaDTO): Allegato
  {
    let allegato = new AllegatoCorrispondenza();
    allegato.id = resp.codice;
    allegato.nome = resp.label;
    allegato.data = new Date();
    return allegato;
  }

  public deleteFileCom(event: Allegato): void
  {
    let title = "Attenzione";
    let message = "Sicuro di voler eliminare il documento '" + event.nome +"'? L'operazione sarà irreversibile";
    this.dialog.showDialog(true, message, title, DialogButtons.OK_CANCEL, (button_id) =>
    {
      if(button_id === 1)
      {
        this.loading.emitLoading(true);
        this.comunicazioniService.deleteFile(this.fascicolo.id, event.id as string).subscribe(response =>
        {
          if(response.codiceEsito === CONST.OK)
          {
            let index = this.comunicazione.allegatiInfo.map(m => m.id).indexOf(event.id);
            if(index != -1)
              this.comunicazione.allegatiInfo.splice(index, 1);
            this.loading.emitLoading(false);
          }
        });
      }
    }, DialogType.WARNING);
  }

  public download(document: Allegato): void
  {
    this.loading.emitLoading(true),
    //this.allegatiService.callDownloadAllegato(
    this.allegatiService.downloadAllegatoFascicolo(document.id as string,this.fascicolo.id, '/istruttoria/allegati/download.pjson')
    .subscribe(response =>
    {
      if(response.status == 200)
        downloadFile(response.body, document.nome);
      this.loading.emitLoading(false);
    });
  }

  public annulla(): void
  {
    let title   = "Attenzione";
    let message = "Preseguendo verrà eliminata l'intera bozza del parere, proseguire?";
    this.dialog.showDialog(true, message, title, DialogButtons.CONFERMA_CHIUDI, (buttonId) =>
    {
      if(buttonId == 1)
      {
        this.loading.emitLoading(true);
        this.service.delete(this.parere.id,this.fascicolo.id).subscribe(response =>
        {
          if(response.codiceEsito === CONST.OK)
          {
            this.parere = null;
            this.form.reset();
            this.changeStatoParere("PARERE_NON_ALLEGATO");
          }
          this.loading.emitLoading(false);
        });
      }
    }); 
  }

  public delete(document: Allegato): void
  {
    let title = "Attenzione";
    let message = "Sei sicuro di voler eliminare il documento '"+document.nome;+"'?";
    this.dialog.showDialog(true, message, title, DialogButtons.OK_CANCEL, (button_id) =>
    {
      if(button_id === 1)
      {
        this.loading.emitLoading(true);
        this.service.remove(document.id as string, this.parere.id,this.fascicolo.id).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
          {
            let index = this.parere.allegati.map(m => m.id).indexOf(document.id);
            if (index != -1)
            {
              this.parere.allegati.splice(index, 1);
              this.allegati = this.parere.allegati;
            }
          }
          this.loading.emitLoading(false);
        });
      }
    }, DialogType.WARNING);
  }

  public allegaParere(): void
  {
    updateAllFormValidity(this.form);
    this.validationActivate = true;
    this.validationAllegati = true;
    if (this.form.valid && this.allegatiValidi)
    {
      this.validationActivate = false;
      this.validationAllegati = false;
      let title = "Attenzione";
      let message = "Confermi di voler allegare il parere alla pratica '" + this.fascicolo.codicePraticaAppptr + "'?";
      this.dialog.showDialog(true, message, title, DialogButtons.OK_CANCEL, () =>
      {
        this.loading.emitLoading(true);
        let parere = copyOf(this.parere);
        parere = { ...parere, ...this.form.getRawValue() };
        this.service.allega(parere).subscribe(response =>
        {
          this.loading.emitLoading(false);
          if (response.codiceEsito === CONST.OK)
          {
            let fascicolo = this.fascicolo;
            fascicolo.statoParereSoprintendenza = this.isSoprintendenza ? "PARERE_INSERITO_SOPRINTENDENZA"
              : "PARERE_INSERITO_ENTE";
            let statoParere=fascicolo.statoParereSoprintendenza;  
            fascicolo.dataTrasmissioneParere = new Date();
            this.changeStatoParere(statoParere);
            this.successSaveMessage();
          }
          
        });
      }, DialogType.INFORMATION);
    }
  }

  public upload(event: any): void
  {
    let file: File = event.file;
    this.loading.emitLoading(true);
    this.service.upload(file, event.type, this.parere.id,this.fascicolo.id).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        if(!this.parere.allegati)
          this.parere.allegati = [];
        this.parere.allegati.push(response.payload);
        this.allegati = this.parere.allegati;
      }
      this.loading.emitLoading(false);
    });
  }

  /*private updateLocalData(): void
  {
    let fascicolo: Fascicolo = copyOf(this.sharedData.fascicolo);
    fascicolo.dataTrasmissioneParere = new Date();
    this.sharedData.fascicolo = fascicolo;
  }*/

  private changeStatoParere(stato: StatoParere): void
  {
    let fascicolo = this.fascicolo;
    fascicolo.statoParereSoprintendenza = stato;
    this.sharedData.fascicolo = fascicolo;
  }

  /* get allegati(): Allegato[] { return this.parere && this.parere.allegati ? this.parere.allegati : [] } */
  get showParereForm(): boolean 
  { 
    let stato = this.sharedData.fascicolo.statoParereSoprintendenza;
    let show = (stato === "PARERE_IN_BOZZA_ENTE" && [GroupType.EnteDelegato, GroupType.Regione].indexOf(this.userService.groupType) >= 0) ||
           (stato === "PARERE_IN_BOZZA_SOPRINTENDENZA" && this.isSoprintendenza); 
    return show;
    /* return (stato === "PARERE_IN_BOZZA_ENTE" &&  this.userService.groupType === GroupType.EnteDelegato) ||
           (stato === "PARERE_IN_BOZZA_SOPRINTENDENZA" && this.isSoprintendenza);  */
  }
  get fascicolo(): Fascicolo { return this.sharedData.fascicolo; }
  get isSoprintendenza(): boolean { return this.userService.groupType === GroupType.Soprintendenza; }
  get documentTypeData(): DocumentType[] { return grigliaAllegatiData; }
  get tableHeaders(): TableConfig[] { return documentTableHeadersParere; }
  get comunicazione(): DettaglioCorrispondenzaDTO { return this.parere ? this.parere.comunicazione : null; }
  get allegatiValidi(): boolean { return this.allegati && this.allegati.some(s => s.type == '904'); }


  successSaveMessage(){
    this.dialog.showDialog(true, 'generic.operazioneOk', 'generic.successo', DialogButtons.CHIUDI, (buttonId) =>{},DialogType.SUCCESS);
  }
}
