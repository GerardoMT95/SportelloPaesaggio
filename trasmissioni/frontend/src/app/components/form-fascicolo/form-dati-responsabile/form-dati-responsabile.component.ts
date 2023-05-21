import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { AbstractControl, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { Subject } from 'rxjs';
import { CONST } from 'src/shared/constants';
import { InformazioniDTO } from '../../model/entity/info.models';
import { RichiedenteDTO } from '../../model/entity/richiedente.models';
import { validDate } from '../../validators/customValidator';

@Component({
  selector: 'app-form-dati-responsabile',
  templateUrl: './form-dati-responsabile.component.html',
  styleUrls: ['./form-dati-responsabile.component.css']
})
export class FormDatiResponsabileComponent implements OnInit {
  @Input() disableAndNotEditable:boolean = false;
  @Input("formRichiedente") form: FormGroup;
  @Input("praticaId") praticaId: string;
  @Input("disable") disable: string;
  @Input() dettaglioFascicolo: InformazioniDTO;//id fascicolo
  @Input() submitted: boolean;

  // private form: FormGroup;
  public tipoAutoComplete: SelectItem[] = CONST.tipoDocumento;
  public it = CONST.IT;
  public NOW_YEAR: any = CONST.NOW_YEAR;
  public tableData: any[];
  public informazioni: RichiedenteDTO;
  public referenteId: number;
  public maxLengthInput: number = CONST.MAX_LENGTH_INPUT;
  public maxDataRilascio: any = CONST.NOW_DATE;
  public minDataScadenza: any = this.getDataScademza();
  public const=CONST;

  private unsubscribe$: Subject<void> = new Subject();

  constructor() {}

  ngOnInit() {
    this.informazioni = this.form.getRawValue();
  } 

  ngOnChanges(changes: SimpleChanges): void 
  {
    if(this.formResponsabile)
    {
      this.dataRilascio.setValidators([Validators.required, validDate(null, new Date())]);
      this.dataScadenza.setValidators([Validators.required, validDate(new Date())]);
    }
  }

  ngOnDestroy(): void 
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();  
  }

  onSubmit(){
    console.log("Click bottone sfoglia");
  }

  validazioneTabella($event:any){}
  
  private getDataScademza(): Date
  {
    let datetime = new Date().getTime();
    let date = new Date(datetime + 86400000);
    return date;
  }

  get formResponsabile(): FormGroup { return this.form ? this.form.controls.responsabile as FormGroup : null; }
  get dataRilascio(): AbstractControl { return this.formResponsabile ? this.formResponsabile.controls.riconoscimentoDataRilascio : null; }
  get dataScadenza(): AbstractControl { return this.formResponsabile ? this.formResponsabile.controls.riconoscimentoDataScadenza : null; }
}
