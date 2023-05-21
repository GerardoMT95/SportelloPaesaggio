import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable, of } from 'rxjs';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { CONST } from 'src/app/shared/constants';
import { GroupType, UlterioreDocumentazione } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { UserService } from 'src/app/shared/services/user.service';
import { customEmailValidator } from 'src/app/shared/validators/customValidator';
import { AllegatoDocumentazione, TemplateDestinatarioDTO } from './../../../../shared/models/models';

@Component({
  selector: 'app-aggiungi-ulteriore-documentazione',
  templateUrl: './aggiungi-ulteriore-documentazione.component.html',
  styleUrls: ['./aggiungi-ulteriore-documentazione.component.css']
})
export class AggiungiUlterioreDocumentazioneComponent implements OnInit {
  @Input("doc") ulterioreDocumentazione: UlterioreDocumentazione;
  @Input("entiOptions") entiOptions$: Observable<SelectItem[]>;
  @Output("onComplete") complete: EventEmitter<any> = new EventEmitter<any>();
  @Output("onCanceled") canceled: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output("uploadFile") uploadFile: EventEmitter<any> = new EventEmitter<any>();
  @Output("deleteFile") deleteFile: EventEmitter<any> = new EventEmitter<any>();

  public form: FormGroup;
  public inviato: boolean = false;
  public selectedDefault: Array<any> = [];
  public allegati: File[]=[];
  public destinatari : DestinatarioComunicazioneDTO[]=[] ;

  constructor(private formBuilder: FormBuilder,
    private adminService: AdminFunctionsService,
    private loading: LoadingService,
    private userService: UserService) { }

  ngOnInit() {
    this.allegati=[]
    this.inviato = false;
    this.buildForm();
    this.resetVisualizzabiliDaDefault()
    this.getDestinatariDefault()
    //this.selectedDefaultItem();
  }

  get formNotifica(): FormArray { return <FormArray>this.form.controls.notificaA; }
  get fileArray(): AllegatoDocumentazione[] { return this.ulterioreDocumentazione && this.ulterioreDocumentazione.allegati ? this.ulterioreDocumentazione.allegati : []; }

  private getDestinatariDefault(){
    this.loading.emitLoading(true)
    let gt=this.userService.groupType;
    let template=""
    if(gt==GroupType.EnteDelegato){
      template="ULTER_DOC_ED";
    }else if(gt==GroupType.CommissioneLocale){ 
      template="ULTER_DOC_CL";
    }
    else if(gt==GroupType.EnteTerritoriale){
      template="ULTER_DOC_ET";
    }else if(gt==GroupType.Soprintendenza){
      template="ULTER_DOC_SOP";
    }
    else if (gt == GroupType.Regione)
    {
      template = "ULTER_DOC_REG";
    }

    this.adminService.
          infoTemplateEmail(template).
              subscribe(_result =>
                {
                  if(_result.codiceEsito === CONST.OK && _result.payload){
                    _result.payload.destinatari.map((d:TemplateDestinatarioDTO)=>{
                        let destinatario:DestinatarioComunicazioneDTO =new DestinatarioComunicazioneDTO()
                            destinatario.email=d.email
                            destinatario.id=d.id
                            destinatario.nome=d.denominazione
                            destinatario.pec = d.pec=="pec" ? true:false
                            destinatario.tipo=d.tipo
                            this.destinatari.push(destinatario)
                      })
                    }
                    this.loading.emitLoading(false)
                  },
                  error =>
                  {
                    this.loading.emitLoading(false);
                  }
                  )
  }
  private buildForm(): void {
    this.form = this.formBuilder.group
      ({
        titoloDocumento: new FormControl(this.ulterioreDocumentazione && this.ulterioreDocumentazione.titoloDocumento ? this.ulterioreDocumentazione.titoloDocumento : null, Validators.required),
        descrizioneContenuto: new FormControl(this.ulterioreDocumentazione && this.ulterioreDocumentazione.descrizioneContenuto ? this.ulterioreDocumentazione.descrizioneContenuto : null, Validators.required),
        allegati: new FormControl([]/* , [Validators.required] */),
        visibileA: new FormControl([]),
      });
  }

  private buildNotificaA(): FormGroup[] {
    let array: FormGroup[] = [];
    if (this.ulterioreDocumentazione && this.ulterioreDocumentazione.notificaA) {
      this.ulterioreDocumentazione.notificaA.forEach(destinatario => {
        //array.push(this.buildSingleEmail(destinatario));
      });
    }
    else {
      array.push(this.buildSingleEmail());
    }

    return array;
  }

  private buildSingleEmail(initilize?: string): FormGroup {
    return this.formBuilder.group({ email: new FormControl(initilize ? initilize : null, [Validators.required, customEmailValidator]) });
  }

  public addRow(init?: string): void {
    this.formNotifica.controls.push(this.buildSingleEmail(init));
  }

  public removeRow(index: number): void {
    this.formNotifica.controls.splice(index, 1);
    if (!this.formNotifica.controls || this.formNotifica.controls.length === 0)
      this.formNotifica.controls = [this.buildSingleEmail()];
  }

  public allegaFile(event: any): void {
    this.allegati.push(event.files[0]);
    if (event.files && event.files.length > 0) {
      

      let container = {
        //idDocumentazione: this.ulterioreDocumentazione.id,
        file: event.files[0]
      };
      this.uploadFile.emit(container);
    }
  }

  public svuotaAllegato(id: number): void {
    let container = {
      idDocumentazione: this.ulterioreDocumentazione.id,
      idAllegato: id
    };
    this.deleteFile.emit(container);
    this.allegati=[];
  }

  
  public onSubmit(bozza: boolean): void {
    let formData: FormData = new FormData();
    this.inviato = true;
    if (bozza || (this.form.valid && this.destinatari.length>0 && this.fileArray.length > 0)) {
      let obj: UlterioreDocumentazione = new UlterioreDocumentazione();
      Object.keys(this.form.controls).forEach(key => {
        if (key != "notificaA")
          obj[key] = this.form.controls[key].value;
      });
      obj.notificaA = this.destinatari;
      obj.idFascicolo=this.ulterioreDocumentazione.idFascicolo;
      //this.formNotifica.controls.forEach((emailForm: FormGroup) => obj.notificaA.push(this.destinatari));
      let json = JSON.stringify(obj);
      formData.append("data", new Blob([json], { type: 'application/json' }));
      formData.append('file',this.allegati[0]);
      //formData.append('file',this.allegati[0]);
      this.complete.emit(formData);
    }
  }
/*
  public onSubmit(bozza: boolean): void {
    this.inviato = true;
    this.formNotifica.updateValueAndValidity();
    if (bozza || (this.form.valid && this.formNotifica.length > 0 && this.formNotifica.valid && this.fileArray.length > 0)) {
      let obj: UlterioreDocumentazione = new UlterioreDocumentazione();
      Object.keys(this.form.controls).forEach(key => {
        if (key != "notificaA")
          obj[key] = this.form.controls[key].value;
      });
      obj.notificaA = [];
      this.formNotifica.controls.forEach((emailForm: FormGroup) => obj.notificaA.push(emailForm.controls.email.value));
      obj.bozza = bozza;
      obj.id = this.ulterioreDocumentazione.id;
      obj.files=this.allegati;
      this.complete.emit(obj);
    }
  }*/

  public onCanceled(): void {
    //faccio comparire un modal che darà la conferma alla cancellazione
    this.canceled.emit(true); 
    this.form.reset();
    this.entiOptions();
    this.selectedDefault = [];
    this.resetVisualizzabiliDaDefault();
  }

  public selectedDefaultItem(): void {
    this.entiOptions$.subscribe(
      response => {
        response.forEach(item => {
          if (this.ulterioreDocumentazione.visualizzabiliDa.includes(item.value)) {
            this.selectedDefault.push(item.value);
          }
        });
      }
    );
  }

  private resetVisualizzabiliDaDefault(): void {
    this.entiOptions$.subscribe(
      response => {
        response.forEach(item => {
          if (item.value === GroupType.EnteDelegato || item.disabled ) {
            this.selectedDefault.push(item.value);
          }
        });
      }
    );
  }

  private entiOptions(): void {
    let entiOptions: SelectItem[] = [];
    let richiedente: SelectItem = {
      label: "Richiedente",
      value: GroupType.Richiedente,
      disabled: false
    };
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
    entiOptions.push(richiedente);
    entiOptions.push(enteDelegato);
    entiOptions.push(soprintendenza);
    entiOptions.push(enteTerritoriale);
    this.entiOptions$ = of(entiOptions);
  }


  public addDestinatario(event: DestinatarioComunicazioneDTO): void
  {
    this.destinatari.length === 0 ? event.tipo = "TO" : event.tipo = "TO"; //mettiamo sempre to... 
    if (!this.destinatari.map(m => m.email).includes(event.email))
      this.destinatari = [...this.destinatari, event];
  }

  public removeDestinatario(event: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.destinatari.splice(index, 1);
  }

  public cambiaTipologia(event: DestinatarioComunicazioneDTO): void
  {
    let index = this.destinatari.map(m => m.email).indexOf(event.email);
    if (index != -1)
      this.destinatari[index].tipo = event.tipo;
  }

}
