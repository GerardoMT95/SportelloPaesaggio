import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { DialogService } from 'primeng/primeng';
import { Observable, of } from 'rxjs';
import { TableConfig } from 'src/app/core/models/header.model';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { DataService } from 'src/app/features/gestione-istanza/services';
import { DialogButtons } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';
import { AllegatoDocumentazione, Fascicolo, GroupType, Role, UlterioreDocumentazione, UlterioreDocumentazioneBean } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { GestioneIstanzaDocumentazioneService } from '../../service/gestione-istanza-documentazione.service';
import { DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';


@Component({
  selector: 'app-gestione-istanza-documentazione-page',
  templateUrl: './gestione-istanza-documentazione-page.component.html',
  styleUrls: ['./gestione-istanza-documentazione-page.component.scss'],
  providers: [DialogService]
})
export class GestioneIstanzaDocumentazionePageComponent implements OnInit
{
  searchObject: any = {};
  documentazioneTableHeaders: TableConfig[] = [];
  currentUser: LoggedUser;
  userRole;
  role = Role;

  public allegaNuovo: boolean = false;
  public abilitaNuovo:boolean=true;
  public documentazioni: UlterioreDocumentazione[] = []
  public doc: UlterioreDocumentazione;
  public entiOptions$: Observable<SelectItem[]>;
  public tipoOrgEnteDelegato: string;
  public indirizziMailDefault: DestinatarioComunicazioneDTO[] = [];
  public visibilitaMailDefault: boolean = false; 

  public tipiOrganizzazioneSelect=[
  {
    value: GroupType.EnteDelegato,
    label: "Ente Delegato",
  },
  {
    value: GroupType.Soprintendenza,
    label: "Soprintendenza"
  },
  {
    value: GroupType.EnteTerritoriale,
    label: "Ente Territoriale"
  },
  {
    value: GroupType.Regione,
    label: "Regione"
  },
  {
    value: GroupType.CommissioneLocale,
    label: "Commissione Locale"
  }];

  constructor(private userService: UserService,
              private gestioneIstanzaDocumentazioneService: GestioneIstanzaDocumentazioneService,
              private customDialogService: CustomDialogService,
              private data: DataService,
              private loading: LoadingService,
              private dominioService: HttpDominioService
  )
  {
    this.currentUser = this.userService.getUser();
  }

  ngOnInit()
  {
    this.getRole();
    this.documentazioneTableHeaders = [
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.FILE',
        field: 'nome'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.TITOLO_DOCUMENTO',
        field: 'titolo'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.DESCRIZIONE_CONTENUTO',
        field: 'descrizione'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.DATA_CONDIVISIONE',
        field: 'data'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.DESTINATARIO_NOTIFICA',
        field: 'notificaA'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.VISIBILE_A',
        field: 'visualizzabiliDa'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.INSERITO_DA',
        field: 'inseritoDa'
      },
      {
        header: 'ULTERIORE_DOCUMENTAZIONE.TABLE.RUOLO',
        field: 'ruolo'
      },
      {
        header: 'TECHNICAL_DOCUMENTATION.HASH',
        field: 'checksum'
      },
      {
        header: '',
        field: 'displayButton'
      }
    ];
    if (this.userRole === this.role.EnteTerritoriale)
    {
      this.documentazioneTableHeaders = this.documentazioneTableHeaders.filter((item) =>
        item.field === 'allegati' ? false : true
      );
    }
    if(this.userService.groupType==GroupType.CommissioneLocale){
      this.abilitaNuovo=false;
    }

    this.retrieveDocumentazioni();
    this.retrieveTipoOrgEnteDelegato(this.fascicolo.enteDelegato);
    //this.entiOptions();
    this.doc= new UlterioreDocumentazione();
    this.doc.idFascicolo=this.fascicolo.id
  }
  
  get fascicolo(): Fascicolo
  {
    return this.data.fascicolo;
  }

  get documentazione()
  {
    if (this.documentazioni.length!=0){
      return this.documentazioni
    }else{
      return [];
    }
    if (this.documentazioni.length!=0)
    {
      const ulterioreDocumentazione = this.documentazioni.filter((item) =>
      {
        const val = Object.keys(this.searchObject).map((key) =>
          item[key].includes(this.searchObject[key])
        );

        const insertedByCurrentUser = this.insertedByCurrentUser(
          item.inseritoDa,
          (this.currentUser.nome + " " + this.currentUser.cognome)
        );

        if (!val.includes(false))
        {
          item.visibileA = insertedByCurrentUser ? 'Inviato' : 'Ricevuto';
        }

        const roles = item.visualizzabiliDa.map((role) => role.value);
        /* if (
          this.sharedWithMe(roles, this.currentUser.role) ||
          insertedByCurrentUser ||
          this.currentUser.role === this.role.GestoreRegione
        ) {
          return !val.includes(false);
        } */

        return false;
      });

      return ulterioreDocumentazione;
    }
    return [];
  }

  insertedByCurrentUser(insertedBy: string, currentUser: string)
  {
    return insertedBy === currentUser;
  }

  /* sharedWithMe(roles: Role[], currentUserRole: Role) {
    if (roles && roles.length) {
      return roles.includes(currentUserRole);
    }

    return true;
  } */

  /* openAttach(elementIndex?: number) {
    const isEdit = elementIndex >= 0;
    const documents = this.fascicolo.ulterioreDocumentazione ? this.fascicolo.ulterioreDocumentazione : [];

    const ref = this.dialogService.open(AggiungiUlterioreDocumentazioneComponent, {
      data: {
        currentUser: this.currentUser,
        documentazioni: documents && isEdit ? documents[elementIndex] : null
      },
      header: 'Allega Nuovo',
      width: '90%',
      height: '90%',
      styleClass: 'h-90'
    });

    ref.onClose.subscribe((response: { data?: any; action: string }) => {
      if (response !== undefined) {
        switch (response.action) {
          case 'ATTACH_DOCUMENT':
            isEdit
              ? (documents[elementIndex] = { ...this.prepareForAttachment(response.data) })
              : documents.push(this.prepareForAttachment(response.data));
            this.fascicolo.ulterioreDocumentazione = documents;
            this.store.editFascicolo(this.fascicolo);
            break;
          case 'SEND_MAIL':
            break;
        }
      }
    });
  } */

  public azioni(container: any): void
  {
    if (container.azione === "modifica")
    {
      this.modificaDocumentazione(this.fascicolo.id, container.idUlterioreDocumentazione);
    }
    else if (container.azione === "download")
    {
      console.log(container);
    }
  }

  private modificaDocumentazione(idFascicolo: string, idDocumentazione: number): void
  {
    this.gestioneIstanzaDocumentazioneService.modificaDocumentazione(idFascicolo, idDocumentazione).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.doc = response.payload;
          this.allegaNuovo = true;
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public allegaNuovoTrue(): void
  {
    this.allegaNuovo = true;
  }

  /* prepareForAttachment(data: UlterioreDocumentazione) {
    const document = data;
    document.inseritoDa = data.inseritoDa
      ? data.inseritoDa
      : (this.userService.getCurrentUser().nome + " " + this.userService.getCurrentUser().cognome);
    document.dataConsivisione = new Date().toLocaleDateString();
    document.ruolo = data.visualizzabiliDa
      .map((item) => item.label)
      .toString()
      .split(',')
      .join(', ');

    return document;
  } */

  updateQuery(searchQuery?: any)
  {
    searchQuery={...searchQuery,idFascicolo: this.fascicolo.id};
    
    this.loading.emitLoading(true)
    this.gestioneIstanzaDocumentazioneService.ricerca(searchQuery).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.documentazioni = response.payload.list;
          this.loading.emitLoading(false)
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.loading.emitLoading(false)
      }
    );
  }

  getRole()
  {
    this.userRole = this.userService.role;
  }

  public deleteDocumetazione(idDocumentazione: number): void
  {
    this.customDialogService.showDialog(
      true,
      "Sei sicuro di voler eliminare la documentazione?",
      "Attenzione",
      DialogButtons.CONFERMA_CHIUDI,
      (idbutton: number) =>
      {
        if (idbutton === 1)
        {
          this.eliminaDocumentazione(this.fascicolo.id, idDocumentazione);
        }
      },
      DialogType.WARNING,
      null,
      null
    );
  }

  private eliminaDocumentazione(idFascicolo: string, idDocumentazione: number): void
  {
    this.gestioneIstanzaDocumentazioneService.eliminaDocumentazione(idFascicolo, idDocumentazione).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.customDialogService.showDialog(
            true,
            response.descrizioneEsito,
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public newAttachment(obj: FormData): void
  {

    this.customDialogService.showDialog(
      true,
      "Il documento sarà allegato e condiviso. Proseguire?",
      "Info",
      DialogButtons.CONFERMA_CHIUDI,
      (idbutton: number) =>
      {
        if (idbutton === 1)
          this.inviaDocumentazione(obj);
      },
      DialogType.INFORMATION,
      null,
      null
    );
    
  }

  public newAttachmentMulti(obj: {doc: UlterioreDocumentazioneBean, files: File[]}): void
  {
    let formData = new FormData();
    for(let file of obj.files)
      formData.append('file', file);
    let json = JSON.stringify(obj.doc);
    formData.append("data", new Blob([json], { type: 'application/json' }));
    this.customDialogService.showDialog(
      true,
      "I documenti saranno allegati e condivisi. Proseguire?",
      "Info",
      DialogButtons.CONFERMA_CHIUDI,
      (idbutton: number) =>
      {
        if (idbutton === 1)
          this.inviaDocumentazioneMulti(formData);
      },
      DialogType.INFORMATION,
      null,
      null
    );
    
  }

  private inviaDocumentazione(obj: FormData): void
  {
    this.loading.emitLoading(true)
    this.gestioneIstanzaDocumentazioneService.inviaDocumentazione(obj).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
            
          );
          this.allegaNuovo = false;
          this.doc= new UlterioreDocumentazione();
          this.doc.idFascicolo=this.fascicolo.id
          this.retrieveDocumentazioni();
          this.loading.emitLoading(false)
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.allegaNuovo = false;
        this.loading.emitLoading(false)
      }
    );
  }

  private inviaDocumentazioneMulti(obj: FormData): void
  {
    this.loading.emitLoading(true)
    this.gestioneIstanzaDocumentazioneService.inviaDocumentazioneMulti(obj).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.customDialogService.showDialog(
            true,
            'generic.operazioneOk',
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
            
          );
          this.allegaNuovo = false;
          this.doc= new UlterioreDocumentazione();
          this.doc.idFascicolo=this.fascicolo.id
          this.retrieveDocumentazioni();
          this.loading.emitLoading(false)
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.allegaNuovo = false;
        this.loading.emitLoading(false)
      }
    );
  }

  private salvaBozza(obj: UlterioreDocumentazione): void
  {
    obj.idFascicolo = this.fascicolo.id;
    this.gestioneIstanzaDocumentazioneService.salvaBozza(obj).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.customDialogService.showDialog(
            true,
            response.descrizioneEsito,
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
        }
        this.ngOnInit();
        this.allegaNuovo = false;
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.allegaNuovo = false;
      }
    );
  }

  public retrieveDocumentazioni(): void
  {
    this.loading.emitLoading(true)
    this.gestioneIstanzaDocumentazioneService.ricerca({'idFascicolo':this.fascicolo.id}).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          this.documentazioni = response.payload.list;
          this.loading.emitLoading(false)
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
        this.loading.emitLoading(false)
      }
    );
  }

  public retrieveTipoOrgEnteDelegato(idEnteDelegato: string): void
   {
    this.loading.emitLoading(true)
    this.dominioService.getTipoOrgEnteDelegato(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          this.tipoOrgEnteDelegato = response.payload;
          if(response.payload == "REG" || response.payload == "ED") 
            this.retrieveDestinatariDefault(idEnteDelegato)
          else 
            this.visibilitaMailDefault = true;
          this.entiOptions();
          this.loading.emitLoading(false);         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loading.emitLoading(false)
       }
     );
  }

  public retrieveDestinatariDefault(idEnteDelegato: string): void
   {
    this.loading.emitLoading(true);
    this.dominioService.getIndirizziMailDefault(idEnteDelegato).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK" )
        {
          this.indirizziMailDefault = response.payload;
          this.indirizziMailDefault.forEach(element => {
            element.tipo = 'TO';
          });
          if(this.indirizziMailDefault.length > 0)
            this.visibilitaMailDefault = true;
          else 
            this.visibilitaMailDefault = false;
          this.loading.emitLoading(false);
         }
       },
       error =>
       {
         this.customDialogService.showDialog(
           true,
           error.message,
           "Attenzione",
           DialogButtons.CHIUDI,
           null,
           DialogType.ERROR,
           null,
           null
         );
         this.loading.emitLoading(false)
       }
     );
  }

  public uploadFile(container: any): void
  {
    let allegato:AllegatoDocumentazione= new AllegatoDocumentazione();
    allegato.formatoFile = container.file.type;
    allegato.nome=container.file.name;
    allegato.lastModified=container.file.lastModifiedDate;
    this.doc.allegati=[];
    this.doc.allegati.push(allegato);

    let body = {
      container: container,
      idFascicolo: this.fascicolo.id,
      codiceFascicolo: this.fascicolo.codicePraticaAppptr
    };
  }

  public uploadFileMulti(container: any): void
  {
    let allegati: Array<AllegatoDocumentazione> = [];
    for (let file of container.files)
    {
      let allegato = new AllegatoDocumentazione();
      allegato.formatoFile = file.type;
      allegato.nome = file.name;
      allegato.lastModified = file.lastModifiedDate;
      allegati.push(allegato);
    }
    this.doc.allegati = allegati;
  }

  public deleteFile(container: any): void
  {
    this.doc.allegati=[];
  }

  public eliminaFile(container: any): void
  {
    let body = {
      container: container,
      idFascicolo: this.fascicolo.id,
      codiceFascicolo: this.fascicolo.codicePraticaAppptr
    };
    this.gestioneIstanzaDocumentazioneService.deleteFile(body).subscribe(
      response =>
      {
        if (response.codiceEsito === "OK")
        {
          this.customDialogService.showDialog(
            true,
            response.descrizioneEsito,
            "Successo",
            DialogButtons.CHIUDI,
            null,
            DialogType.SUCCESS,
            null,
            null
          );
          this.doc = response.payload;
        }
      },
      error =>
      {
        this.customDialogService.showDialog(
          true,
          error.message,
          "Attenzione",
          DialogButtons.CHIUDI,
          null,
          DialogType.ERROR,
          null,
          null
        );
      }
    );
  }

  public entiOptions(): void
  {
    let entiOptions: SelectItem[] = [];
    /*let richiedente: SelectItem = {
      label: "Richiedente",
      value: GroupType.Richiedente,
      disabled: false
    };*/
    let enteDelegato: SelectItem = {
      label: "Ente Delegato",
      value: GroupType.EnteDelegato,
      disabled: true
    };
    let soprintendenza: SelectItem = {
      label: "Soprintendenza",
      value: GroupType.Soprintendenza,
      disabled: false
    };
    let enteTerritoriale: SelectItem = {
      label: "Ente Territoriale",
      value: GroupType.EnteTerritoriale,
      disabled: false
    };
    let regione: SelectItem = {
      label: "Regione",
      value: GroupType.Regione,
      disabled: false
    };
    let commissioneLocale: SelectItem = {
      label: "Commissione Locale",
      value: GroupType.CommissioneLocale,
      disabled: false
    };

    //entiOptions.push(richiedente);
    entiOptions.push(enteDelegato);
    entiOptions.push(soprintendenza);
    entiOptions.push(enteTerritoriale);
    entiOptions.push(regione);
    entiOptions.push(commissioneLocale);
    entiOptions.forEach(
      gt=>{
        if(gt.value==this.userService.groupType || gt.value == this.tipoOrgEnteDelegato)
            gt.disabled=true;
          });
    this.entiOptions$ = of(entiOptions);
  }
  public canceled($event){
    this.allegaNuovo=false;
    this.doc= new UlterioreDocumentazione(); 
    this.doc.idFascicolo=this.fascicolo.id
  }

  get readOnly(): boolean { return this.userService.groupType === GroupType.Amministratore; }
  get isRegione(): boolean { return this.userService.groupType === GroupType.Regione; }
  get isED(): boolean { return this.userService.groupType === GroupType.EnteDelegato; }
}
