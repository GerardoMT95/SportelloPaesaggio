import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, ButtonType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DisclaimerDTO } from '../../models/admin-functions.models';

@Component({
  selector: 'app-disclaimer-detail',
  templateUrl: './disclaimer-detail.component.html',
  styleUrls: ['./disclaimer-detail.component.scss']
})
export class DisclaimerDetailComponent implements OnInit {

  
  @Input("idProcedimento") idProcedimento: string;
  @Input("isAddDichiarazione") isAddDichiarazione: boolean;
  @Input("disclaimer") disclaimer: DisclaimerDTO;
  @Input("arrayDichiarazioni") arrayDichiarazioni: Array<DisclaimerDTO> = new Array<DisclaimerDTO>();

  @Output() indietroChange = new EventEmitter();
  

  form: FormGroup;

  //variabili per la validazione del form
  public validOn: boolean = false;
  //boolean per la validazione del form, se è true il testo è vuoto altrimenti è pieno
  public testoEditor: boolean = true;

  public tipoReferenteOptions:SelectOption[]=[
    {value:"SD",description:"Soggetto Dichiarante"},
    {value:"TR",description:"Tecnico Redattore"},
  ]

  constructor(private service: AdminFunctionsService,
    public dialogService: CustomDialogService,
    private loadingService: LoadingService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.form = this.fb.group({
      testo: [this.disclaimer ? this.disclaimer.testo : null, Validators.required],
      required:[this.disclaimer ? (<DisclaimerDTO>this.disclaimer).required:true],
      tipoReferente:[this.disclaimer ? (<DisclaimerDTO>this.disclaimer).tipoReferente:"SD",Validators.required]
    })
    // mi setto la variabile per capire se il testo è vuono o meno
    if (this.disclaimer)
      this.testoEditor = this.disclaimer.testo == '' ? true : false;
  }

  get testo() {
    return this.form.get("testo").value;
  }
  get required() {
    return this.form.get("required").value;
  }
  get tiporeferente() {
    return this.form.get("tipoReferente").value;
  }

  public closeDettaglio(afterSave?:boolean) {
    if(this.form.touched && !afterSave){
          this.dialogService.showDialog(true
            , "generic.abbandonaModifiche"
            , "generic.info"
            , DialogButtons.CONFERMA_CHIUDI
            , (buttonID: ButtonType): void => {
              if (buttonID == ButtonType.OK_BUTTON)
              this.indietroChange.emit();         
            }
            , DialogType.INFORMATION
            , null
          );
    }else{
      this.indietroChange.emit();
    }
  }

  /**
   * Questo metodo serve per captare le modifiche effettuate nel p-editor. 
   * se il p-editor è vuoto setto la boolean a true, se è piena a false
   * così non ci saranno problemi di validazione del form.
   * @param event 
   */
  public textChange(event) {
    this.testoEditor = event.textValue == '' ? true : false;
  }

  public createDisclaimer(testo:string,tipoReferente:string,required:boolean):DisclaimerDTO{
    return {
      id:null,
      required:required,
      testo:testo,
      tipoProcedimento:Number.parseInt(this.idProcedimento),
      tipoReferente:tipoReferente,
      ordine:this.disclaimer.ordine};
  }
  
  public aggiungiDichiarazione(): void {
    this.validOn = true;
    if (this.form.valid && !this.testoEditor) {
      if (this.isAddDichiarazione)
        this.arrayDichiarazioni.push(this.createDisclaimer(this.testo,this.tiporeferente,this.required));
      else {
        this.disclaimer.testo = this.testo;
        this.disclaimer.required = this.required;
        this.disclaimer.tipoReferente = this.tiporeferente;
      }
      this.closeDettaglio(true);
    }
  }


}
