import { CONST } from './../../../../shared/constants';
import { copyOf } from 'src/app/core/functions/generic.utils';
import { PlainTypeStringId } from './../../../../shared/components/model/logged-user';
import { CommissioneLocaleOrganizzazione, EntiCommissioni } from './../../models/admin-functions.models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { CommissioneLocaleConst } from '../../configurations/commissione-locale-cong';
import { validDate } from 'src/app/shared/validators/customValidator';

@Component({
  selector: 'app-dettaglio-commissione',
  templateUrl: './dettaglio-commissione.component.html',
  styleUrls: ['./dettaglio-commissione.component.scss']
})
export class DettaglioCommissioneComponent implements OnInit
{
  @Input("commissione") commissione: CommissioneLocaleOrganizzazione;
  @Input("entiOptions") entiOptions: Array<PlainTypeStringId> = [];
  @Input("userOptions") userOptions: Array<PlainTypeStringId> = [];

  @Output("salva")  onSave  : EventEmitter<CommissioneLocaleOrganizzazione> = new EventEmitter();
  @Output("chiudi") onClose : EventEmitter<void> = new EventEmitter();
  @Output("addUtente") onAddUtente: EventEmitter<PlainTypeStringId> = new EventEmitter();
  @Output("delUtente") onDelUtente: EventEmitter<string> = new EventEmitter();

  public form: FormGroup;
  public validation: boolean = false;

  public dateLimit = { minStart: null, maxStart: new Date(), minEnd: null, maxEnd: null };
  public yearRangeStart = "1900:" +  new Date().getFullYear();
  public yearRangeEnd   = "1900:" + (new Date().getFullYear() + 50);
  public IT = CONST.IT;

  public userTableHeader: TableConfig[] = CommissioneLocaleConst.headerUsersTable;
  public userDuplicate: boolean = false;
  public customErrorMessages: any = CommissioneLocaleConst.customError;

  public utenteSelezionato: PlainTypeStringId = null;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void
  {
    let c = this.commissione ? this.commissione : new CommissioneLocaleOrganizzazione();
    this.form = this.fb.group({
      id: [c.id],
      denominazione: [c.denominazione, Validators.required],
      dataCreazione: [c.dataCreazione ? new Date(c.dataCreazione) : null],
      dataTermine: [c.dataTermine ? new Date(c.dataTermine) : null],
      organizzazioniAssociate: [c.entiLabelValue, Validators.required]
    });
    let dataCreazione = this.form.controls.dataCreazione;
    let dataTermine = this.form.controls.dataTermine;
    dataTermine.setValidators([Validators.required, validDate(dataCreazione)]);
    this.evaluateLimitDate();
  }

  ngOnChanges(changes: SimpleChanges): void 
  {
    if (this.form && changes.commissione && changes.commissione.currentValue != changes.commissione.previousValue)
    {
      this.form.patchValue({ ...this.commissione });
    }
  }

  public chiudiCom(): void { this.onClose.emit(); }
  public annullaCom(): void { this.form.reset(); }
  public submitCom(): void 
  {
    this.validation = true; 
    if(this.form.valid)
    {
      let details: CommissioneLocaleOrganizzazione = this.form.getRawValue();
      details.organizzazioniAssociate = (details.organizzazioniAssociate as any[]).map(m => m.value);
      this.onSave.emit(details); 
    }
  }

  public removeUser(event: PlainTypeStringId): void {this.onDelUtente.emit(event.value); }
  public aggiungiUtente(): void
  {
    if(!this.userDuplicate)
    {
      this.onAddUtente.emit(this.utenteSelezionato);
      this.utenteSelezionato = null;
    }
  }

  public valutaUtente(event: PlainTypeStringId): void
  {
    if (!(event as any).currentTarget)
    {
      this.utenteSelezionato = event;
      if (event != null && this.utenteSelezionato)
        this.userDuplicate = this.commissione.users.some(u => u.value == event.value);
      else
        this.userDuplicate = false;  
    }
  }

  public evaluateLimitDate(): void
  {
    let today = new Date();
    let dataCreazione = this.form.controls.dataCreazione;
    let dataTermine = this.form.controls.dataTermine;
    if (dataTermine.value < today)
    {
      this.dateLimit.maxStart = dataTermine.value;
      dataCreazione.setValidators([Validators.required, validDate(null, dataTermine)]);
    }
    else
    {
      this.dateLimit.maxStart = today;
      dataCreazione.setValidators([Validators.required, validDate(null, dataTermine)]);
    }
    this.dateLimit.minEnd = dataCreazione.value;
  }

  get usersContainer(): any[] { return this.commissione ? this.commissione.users : []; }

  get invalidUser(): boolean
  {
    if (this.utenteSelezionato && this.commissione.users)
      return this.commissione.users.some(u => u.value == this.utenteSelezionato.value)
    else
      return false;
  }
}
