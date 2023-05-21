import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable, Subject } from 'rxjs';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { GlobalService } from 'src/app/services/global.service';
import { LoadingService } from 'src/app/services/loading.service';
import { CONST } from 'src/shared/constants';
import { TableHeader } from 'src/shared/models/table-header';
import { checkFileSizeTypeFn } from '../../functions/genericFunctions';
import { Allegato } from '../../model/model';
import { PlainTypeNumericId } from '../../model/plain-type-numeric-id.model';
import { FascicoloFormConfig } from '../form-plan-config';
import { InformazioniDTO } from './../../model/entity/info.models';

@Component({
  selector: 'app-form-provvedimento',
  templateUrl: './form-provvedimento.component.html',
  styleUrls: ['./form-provvedimento.component.css']
})


export class FormProvvedimentoComponent implements OnInit
{
  @Input() disableAndNotEditable:boolean = false;
  @Input() fascicoloForm: FormGroup;
  @Input() submitted: boolean;
  @Input() disable: boolean; //disabilita tutti i control
  @Input() codiceFascicolo: string;
  @Input() id: string;//id fascicolo
  @Input() dettaglioFascicolo: InformazioniDTO;//id fascicolo
  public const= CONST;
  //esempio get da pev
  attachmentsPerFileType: PlainTypeNumericId[];
  tableHeadersAttachment: TableHeader[];
  private unsubscribe$ = new Subject<void>();

  protected checkFileType=checkFileSizeTypeFn(null,CONST.MAX_50MB,[".pdf",".p7m"]);
  //oggetto utilizzato per l'alert
  public alertData = 
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };
  esitiProvvedimento$: Observable<SelectItem[]>;
  allegatiProvvedimento: Allegato[];
  listaRupAutocomplete: string[] = [];

  constructor(private loadingService: LoadingService,
    private allegatiService: AllegatiService,
    private autPaesService: AutorizzazioniPaesaggisticheService,
    private globalService: GlobalService,
  ) { }

  ngOnInit()
  {
    this.esitiProvvedimento$ = this.autPaesService.getEsitiProvvedimento();
    this.tableHeadersAttachment = FascicoloFormConfig.allegatiUploadTableHeaders();
  }

  get form() 
  {
     return this.fascicoloForm.controls; 
  }

  myAlertError(msg?: string): void
  {
    this.alertData.display = true;
    this.alertData.isConfirm = false;
    this.alertData.typ = "danger";
    this.alertData.content = msg;
    this.loadingService.emitLoading(false);
  }

  ngOnDestroy() 
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  callbackAlert($event){}

  public autocompleteRup(event: any): void {
    this.autPaesService.autocompleteRup(event.query).subscribe(
      response => {
        if (response.codiceEsito === "OK") {
          this.listaRupAutocomplete = response.payload;
        }
      },
      error => {
        this.alertData = {
          display: true,
          title: "Errore",
          content: error.message,
          typ: "danger",
          extraData: null,
          isConfirm: false,
        };
      }
    );
  }

  public avviso(event: any): void {
    this.alertData = {
      display: true,
      title: "Attenzione",
      content: event,
      typ: "info",
      extraData: null,
      isConfirm: false,
    };
  }
	
}
