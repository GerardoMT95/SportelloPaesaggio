import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/primeng';
import { combineLatest, Observable } from 'rxjs';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { UserService } from 'src/app/shared/services/user.service';
import { OperationEventType } from '../../models/seduta.models';
import { BaseResponse, PaginatedList } from './../../../../core/models/basic.models';
import { IButton } from './../../../../core/models/dialog.model';
import { TableConfig } from './../../../../core/models/header.model';
import { CustomDialogService } from './../../../../core/services/dialog.service';
import { DialogButtons, DialogType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from './../../../../shared/constants';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { SearchFields } from './../../../../shared/models/form-search.configurations.models';
import { Fascicolo, FileCommissioneLocale, SedutaDiCommissione } from './../../../../shared/models/models';
import { PraticaSearch, SedutaCommissioneLocaleSearch } from './../../../../shared/models/search.models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { SedutaCommissioneService } from './../../../../shared/services/seduta-commissione/seduta-commissione.service';
import { SeduteDiCommissioneConfig } from './../../configuration/seduteDICommissione.config';

@Component({
  selector: 'app-sedute-di-commissione-page',
  templateUrl: './sedute-di-commissione-page.component.html',
  styleUrls: ['./sedute-di-commissione-page.component.scss']
})
export class SeduteDiCommissionePageComponent implements OnInit
{
  private _configuration = SeduteDiCommissioneConfig.searchConfiguration;
  private _sedute: PaginatedList<SedutaDiCommissione>;
  private filters: SedutaCommissioneLocaleSearch = new SedutaCommissioneLocaleSearch();

  public seduta: SedutaDiCommissione;
  //gestione modal inserimento/modifica seduta
  public display: boolean = false;
  public fascicoliList: SelectItem[];
  //gestione modal dettaglio fascicoli collegati
  public displayFascicoliDetails: boolean = false;
  public fascicoliDetails: Fascicolo[] = [];
  public nomefile: string;
  //gestione dialog dettaglio allegati seduta
  public displayAllegati: boolean = false;
  //gestione modal allega documenti
  public displayAllegaDocumenti: boolean = false;

  constructor(private service: SedutaCommissioneService,
              private user: UserService,
              protected loading: LoadingService,
              private dialog: CustomDialogService) { }

  ngOnInit()
  {
    this.resetFilters();
    this.loading.emitLoading(true);
    this.service.callSearchSedute(this.filters).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this._sedute = response.payload;
      this.loading.emitLoading(false);
    });
  }

  public search(event?: any): void
  {
    this.loading.emitLoading(true);
    this.setFilters(event);
    this.service.callSearchSedute(this.filters).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
        this._sedute = response.payload;
      this.loading.emitLoading(false);
    });
  }

  public eventResolver(event: OperationEventType): void
  {
    switch(event.operation)
    {
      case "REVOCATION":
        //elimina una seduta di commissione
        this.deleteSedutaDiCommissione(event.selected);
        break;
      case "ATTACH":
        //allega verbali o schede tecniche ad una seduta di commissione
        this.openAllegaDocumenti(event.selected);
        break;
      case "MODIFY":
        //Modifica i dati per una seduta di commissione
        this.editSession(event.selected);
        break;
      case "VIEW_TO_EXAMINED":
        //Visualizza fascicoli da esaminare ina determinata seduta
        this.showFascicoliDetails(event.selected, false);
        break;
      case "VIEW_EXAMINED":
        //Visualizza fascicoli esaminati ina determinata seduta
        this.showFascicoliDetails(event.selected, true);
        break;
      case "VIEW":
        //visualizza dettaglio seduta di commissione
        this.showDialogAllegati(event.selected);
        break;
    }
  }

  public resetFilters(): void
  {
    this.filters.sortBy = "nome_seduta";
    this.filters.sortType = "ASC";
    this.filters.limit = 5;
    this.filters.page = 1;
    this.filters.codicePratica = null;
    this.filters.dataSedutaDa = null;
    this.filters.dataSedutaA = null;
  }

  private setFilters(event: any): void
  {
    if(event)
      Object.keys(event).forEach(key => this.filters[key] = event[key]);
  }

  public editSession(seduta?: SedutaDiCommissione): void
  {
    if (seduta)
    {
      let callFindService$ = this.service.callFindSeduta(seduta.id);
      let callPraticheSed$ = this.service.callSearchPraticheEsaminabili(seduta.id);
      this.loading.emitLoading(true);
      combineLatest([callFindService$, callPraticheSed$]).subscribe(([respSed, respPrt]) =>
      {
        if (respSed.codiceEsito === CONST.OK && respPrt.codiceEsito === CONST.OK)
        {
          this.fascicoliList = respPrt.payload.map(m => { return { label: m.codicePraticaAppptr, value: m.id } });
          this.seduta = respSed.payload;
          this.display = true;
        }
        this.loading.emitLoading(false);
      });
    }
    else
    {
       //TODO get dei fascicoli
      this.loading.emitLoading(true);
       this.service.callSearchPraticheEsaminabili().subscribe(response =>
      {
        if(response.codiceEsito === CONST.OK)
        {
          this.fascicoliList = response.payload.map(m => { return { label: m.codicePraticaAppptr, value: m.id } });
          this.display = true;
        }
        this.loading.emitLoading(false);
      });
    }
  }

  public salvaSeduta(seduta: SedutaDiCommissione): void
  {
    this.loading.emitLoading(true);
    let message: string;
    let obs: Observable<BaseResponse<SedutaDiCommissione>>;
    if (seduta.id) 
    {
      obs = this.service.callUpdateSeduta(seduta);
      message = "Seduta di commissione: " + seduta.nomeSeduta + " aggiornata con successo";
    }
    else 
    {
      obs = this.service.callInsertSeduta(seduta);
      message = "Seduta di commissione " + seduta.nomeSeduta + " creata con successo";
    }
    obs.subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.loading.emitLoading(false);
        this._sedute = null;
        this.dialog.showDialog(true, message, "Successo", DialogButtons.CHIUDI, () =>
        {
          this.chiudiSedutaEdit();
          this.search();
        }, DialogType.SUCCESS);
      }
      else
        this.loading.emitLoading(false);
    });
  }

  public chiudiSedutaEdit(): void
  {
    this.seduta = null;
    this.display = false;
  }

  private showFascicoliDetails(seduta: SedutaDiCommissione, esaminati: boolean): void
  {
    this.loading.emitLoading(true);
    this.seduta = seduta;
    this.service.callSearchPratiche(seduta.id, esaminati).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.fascicoliDetails = response.payload;
        this.displayFascicoliDetails = true;
      }
      this.loading.emitLoading(false);
    });
  }

  public showFascicolidetailsAllegato(allegato: FileCommissioneLocale): void
  {
    this.nomefile = allegato.nome;
    this.fascicoliDetails = [...this.seduta.praticheDetails.filter(m => allegato.praticheAssociate.includes(m.id))];
    this.displayFascicoliDetails = true;
  }

  public chiudiDetailsFascicoli(): void
  {
    this.nomefile = null;
    this.displayFascicoliDetails = false;
    this.fascicoliDetails = [];
  }

  private deleteSedutaDiCommissione(seduta: SedutaDiCommissione): void
  {
    let title: string = "Attenzione";
    let message: string = "Sei sicuro di voler revocare la seduta di commissione " + seduta.nomeSeduta + "? L'operazione sarà irreversibile";
    let buttons: IButton[] = DialogButtons.CONFERMA_CHIUDI;
    this.dialog.showDialog(true, message, title, buttons, (buttonId: number) =>
    {
      if(buttonId === 1)
      {
        this.loading.emitLoading(true);
        this.service.callDeleteSeduta(seduta.id).subscribe(response =>
        {
          let dialogType: DialogType;
          buttons = DialogButtons.CHIUDI;
          if(response.codiceEsito === CONST.OK)
          {
            title = "Operazione riuscita";
            message = "Seduta revocata con successo";
            dialogType = DialogType.SUCCESS;
            this.loading.emitLoading(false);
            this.search();
          }
          else
          {
            title = "Operazione fallita";
            message = "Errore durante la revoca della seduta di commissione " + seduta.nomeSeduta;
            dialogType = DialogType.ERROR;
          }
          this.dialog.showDialog(true, message, title, buttons, null, dialogType);
        });
      }
    }, DialogType.WARNING);
  }

  private openAllegaDocumenti(seduta: SedutaDiCommissione): void
  {
    this.doCallFindService(seduta.id, (response: SedutaDiCommissione) => 
    {
      this.seduta = response;
      this.displayAllegaDocumenti = true;
    });
  }

  public chiudiAllegaDocumenti(): void
  {
    this.displayAllegaDocumenti = false;
    this.seduta = null;
  }

  public callComplete(): void
  {
    this.dialog.showDialog(true, "COMMISSIONE_LOCALE.COMPLETE", "Attenzione", DialogButtons.CONFERMA_CHIUDI, () => {this.completeSession()}, DialogType.WARNING);
  }

  public completeSession(): void
  {
    this.loading.emitLoading(true);
    this.service.callConcludiSeduta(this.seduta.id).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        let indexSeduta = this.sedute.map(m => m.id).indexOf(this.seduta.id);
        this.displayAllegaDocumenti = false;
        this.seduta = null;
        this.sedute[indexSeduta].stato = "CONCLUSA";
      }
      this.loading.emitLoading(false);
    });
  }

  private doCallFindService(id: number, onSuccess: (response: SedutaDiCommissione) => void): void
  {
    this.loading.emitLoading(true);
    this.service.callFindSeduta(id).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK) 
        onSuccess(response.payload);
      this.loading.emitLoading(false);
    });
  }

  public attachDocument(event: any): void
  {
    this.loading.emitLoading(true);
    this.service.callUploadAllegatoSeduta(event.file, event.metadata.idSeduta, 
                                          event.metadata.type, event.metadata.fascicoli).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        let indexSeduta = this.sedute.map(m => m.id).indexOf(this.seduta.id);
        let pratiche = response.payload.praticheAssociate;
        if(indexSeduta != -1 && this.seduta.allegati)
        {
          pratiche.forEach(p =>
          {
            if(!this.seduta.allegati.some(a => a.praticheAssociate.some(f => f === p)))
              this.sedute[indexSeduta].nFascicoliEsaminati++;
          });
        }
        else if (indexSeduta != -1)
          this.sedute[indexSeduta].nFascicoliEsaminati+=pratiche.length;
          
        this.seduta.allegati ? this.seduta.allegati.push(response.payload) :
                               this.seduta.allegati = [response.payload]; 
      }
      this.loading.emitLoading(false);
    });
  }

  public deleteFile(event: FileCommissioneLocale): void
  {
    let title: string = "Attenzone";
    let message: string = "Sei sicuro di voler eliminare il documento \"" + event.nome + "\"? L'operazione sarà irreversibile";
    let buttons: IButton[] = DialogButtons.CONFERMA_CHIUDI;
    this.dialog.showDialog(true, message, title, buttons, (buttonId: number) =>
    {
      let _this = this;
      if(buttonId === 1)
      {
        _this.loading.emitLoading(true);
        _this.service.callRemoveAllegatoSeduta(event.id, event.idSeduta).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
          {
            let pratiche: Array<string> = event.praticheAssociate;
            let index = _this.seduta.allegati.map(m => m.id).indexOf(event.id);
            if(index != -1)
              _this.seduta.allegati.splice(index, 1);
            let indexSeduta = _this.sedute.map(m => m.id).indexOf(_this.seduta.id);
            if (indexSeduta != -1 && pratiche && pratiche.length > 0)
            {
              pratiche.forEach(p =>
              {
                if (!_this.seduta.allegati.some(a => a.praticheAssociate.some(f => f === p)))
                  _this.sedute[indexSeduta].nFascicoliEsaminati--;
              });
            }
          }
          _this.loading.emitLoading(false);
        });
      }
    }, DialogType.WARNING);
  }

  public downloadFile(event: FileCommissioneLocale): void
  {
    this.loading.emitLoading(true);
    this.service.callDownloadAllegatoSeduta(event.id, event.idSeduta).subscribe(response =>
    {
      if(response.status === 200)
        downloadFile(response.body, event.nome);
      this.loading.emitLoading(false);
    });
  }

  public showDialogAllegati(seduta: SedutaDiCommissione): void
  {
    this.doCallFindService(seduta.id, (response: SedutaDiCommissione) =>
    {
      this.seduta = response;
      this.displayAllegati = true;
    });
  }

  public chiudiDisplayAllegati(): void
  {
    this.seduta = null;
    this.displayAllegati = false;
  }

  get configuration(): SearchFields[] { return this._configuration; }
  get isRup(): boolean { return this.user.isRup ? this.user.isRup : false; }
  get sedute(): Array<SedutaDiCommissione> { return this._sedute ? this._sedute.list : []; }
  get totalRecords(): number { return this._sedute ? this._sedute.count : 0; }
  get types(): DocumentType[] { return SeduteDiCommissioneConfig.documentoClTypes; }
  get headers(): TableConfig[] { return SeduteDiCommissioneConfig.fileTableHeaders; }
  get files(): FileCommissioneLocale[] { return this.seduta ? this.seduta.allegati.map(m => { return { ...m, type: m.tipoAllegato, fascicoliCount: m.praticheAssociate ? m.praticheAssociate.length : 0}}) : []; }

  private get filtriFascicoli(): PraticaSearch 
  {
    let filtri = new PraticaSearch();
    filtri.page = 0;
    filtri.limit = 0;
    filtri.editable = true;
    filtri.escludiAccertamento = true;
    return filtri;
  }
}
