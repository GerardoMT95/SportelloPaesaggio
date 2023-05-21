import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable } from 'rxjs';
import { AutoritaService } from 'src/app/services/autorita.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { CONST } from 'src/shared/constants';
import { InformazioniDTO } from '../../model/entity/info.models';

@Component({
  selector: 'app-form-riepilogo',
  templateUrl: './form-riepilogo.component.html',
  styleUrls: ['./form-riepilogo.component.css']
})

export class FormRiepilogoComponent implements OnInit
{

  @Input() disableAndNotEditable:boolean = false;
  @Input() fascicoloForm: FormGroup;
  @Input() dettaglio: InformazioniDTO;
  @Input() submitted: boolean;
  @Input() id?: number;
  @Input("disable") disable: boolean = false;
  @Output() cambioTipoProcedimento = new EventEmitter<String>();
  //vengono utilizzati in caso di form disabilitato 
  @Input() tipoProcedimento: string;
  @Input() enteCompetente: string;
  maxLength: 5000;

  public alertData = 
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  public it: any = CONST.IT;
  MAX_YEAR = CONST.NOW_YEAR;
  public const=CONST;

  public tipiProcedimento$: Observable<SelectItem[]>;
  public maxLengthTextArea: number = CONST.MAX_LENGTH_TEXTAREA;
  entiCompetenza$: Observable<SelectItem[]>;
  constructor(private autPaesSvc: AutorizzazioniPaesaggisticheService,
              private autoritaService: AutoritaService) { }

  ngOnInit()
  {
    //console.log("********* ID:"+this.id);
    if (this.id){ 
      // Se ì presente l'id bisogna prendere i tipi di procedimento salvati e associati a questo fascicolo al momento della creazione dello stesso
      this.tipiProcedimento$ = this.autPaesSvc.getTipiProcedimentoByFascicolo(this.id,this.dettaglio.dataCreazione);
    }else {
      //Altrimenti si caricano i tipi di procedimento attivi in questo momento
      this.tipiProcedimento$ = this.autPaesSvc.getTipiProcedimento();
    }
    
    this.entiCompetenza$ = this.autoritaService.getProceduralAuthoritySelectItem();
  }

  get f() { return this.fascicoloForm.controls; }
  resettaForm(event: any) { this.cambioTipoProcedimento.emit(event.value); }

  enableInSanatoria(tipoProcedimento: string)
  {
    return ['ACCERT_COMPAT_PAES_DPR_139_2010',
    //        'ACCERT_COMPAT_PAES_DLGS_42_2004'].indexOf(tipoProcedimento) >= 0;
    'ACCERT_COMPAT_PAES_DPR_31_2017'].indexOf(tipoProcedimento) >= 0;
  }


  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
    {
      this.alertData.display = false;
      if (event.extraData && event.extraData.operazione === "eliminaAllegatoExtra")
      {
        this.fascicoloForm.get("tipoProcedimento").setValue(this.autPaesSvc.getLastTipoProcedimento());
      }
    } 
    else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'eliminaAllegatoExtra':
            //do something
            this.autPaesSvc.eliminaAllegati(this.alertData.extraData.toDelete,this.id+'').subscribe(result =>
            {
              if(result.codiceEsito === CONST.OK && result.payload)
              {
                this.cambioTipoProcedimento.emit(this.alertData.extraData.codice);
              }
            });
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }




}
