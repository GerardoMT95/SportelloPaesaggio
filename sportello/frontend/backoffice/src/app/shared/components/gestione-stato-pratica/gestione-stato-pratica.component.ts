import { UserService } from './../../services/user.service';
import { SelectItem } from 'primeng/primeng';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TipoRichiesta, DocumentoAllegato, StoricoASR, Allegato } from './../../models/models';
import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-gestione-stato-pratica',
  templateUrl: './gestione-stato-pratica.component.html',
  styleUrls: ['./gestione-stato-pratica.component.scss']
})
export class GestioneStatoPraticaComponent implements OnInit, OnChanges
{
  @Input("disabled")           disabled: boolean = false;
  @Input("disabledFileUpload") disableFile: boolean = false;
  @Input("disabledButtons")    disableButtons: boolean = false;
  @Input("data")               data?: StoricoASR;
  @Input("validation")         validation: boolean = false;
  @Input("options")            options: SelectItem[] = [];

  @Output("uploadDocument") uploadFile: EventEmitter<any> = new EventEmitter<any>();
  @Output("deleteDocument") deleteFile: EventEmitter<{ id: number, file: DocumentoAllegato }> = new EventEmitter<{ id: number, file: DocumentoAllegato }>();
  @Output("download") download: EventEmitter<DocumentoAllegato> = new EventEmitter<DocumentoAllegato>();

  @Output("submitForm") submitForm: EventEmitter<StoricoASR> = new EventEmitter<StoricoASR>();
  @Output("close") chiudi: EventEmitter<void> = new EventEmitter<void>();

  private _form: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private user: UserService) { }

  ngOnInit()
  {
    this.buildForm();
    if(this.disabled)
      this.form.disable();
  }

  ngOnChanges(changes: SimpleChanges): void
  {
    if(changes.data.previousValue !== changes.data.currentValue)
    {
      if(this.form)
      {
        this._form.patchValue
        ({
          id: this.data.id,
          idPratica: this.data.idPratica,
          codicePratica: this.data.codicePratica,
          richiesta: this.data.type,
          draft: this.data.draft,
          data: this.data.data,
          utenteCreazione: this.data.usernameUtenteCreazione,
          note: this.data.note,
          allegati: [this.data.allegati]
        });
      }
      
    }
  }

  get tipoRichiesta(): TipoRichiesta { return this.form ? this.form.get("type").value : null; }

  get form(): FormGroup { return this._form; }
  get allegati(): Array<Allegato> { return this.data.allegati }
  get buttonLabel(): string { return this.tipoRichiesta === "ARCHIVIAZIONE" ? "STATE.ARCHIVIA_BTN" :
                                     (this.tipoRichiesta === "ATTIVAZIONE" ? "STATE.ATTIVA_BTN" : 
                                      "STATE.SOSPENDI_BTN"); }

  private buildForm(): void
  {
    let trustedData: StoricoASR = this.data ? this.data : new StoricoASR();
    this._form = this.formBuilder.group
    ({
      id: [trustedData.id],
      idPratica: [trustedData.idPratica],
      codicePratica: [trustedData.codicePratica],
      type: [trustedData.type ? trustedData.type : this.tipoRichiesta, [Validators.required]],
      draft: [trustedData.draft],
      data: [trustedData.data],
      usernameUtenteCreazione: [trustedData.usernameUtenteCreazione ? trustedData.usernameUtenteCreazione : this.user.getUser().username],
      note: [trustedData.note, [Validators.required]],
      allegati: [trustedData.allegati]
    });
  }

  public uploadAttachment(event: any): void { this.uploadFile.emit({id: this.data ? this.data.id : null, file: event.files[0]}); }
  public deleteAttachment(event: any): void { this.deleteFile.emit({ id: this.data ? this.data.id : null, file: event}); }
  public sending(draft: boolean): void 
  { 
    this.validation = true;
    if (this.form.valid)
    {
      this.form.patchValue({ draft: draft });
      this.submitForm.emit(this.form.getRawValue());
    }
     
  }
  public downloadAttachment(event: any): void { this.download.emit(event); }
  public close(): void { this.chiudi.emit(); }
}
