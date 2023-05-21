import { customEmailValidator } from 'src/app/shared/validators/customValidator';
import { CustomDialogService } from './../../../core/services/dialog.service';
import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TableConfig } from 'src/app/core/models/header.model';
import { CONST } from '../../constants';
import { LoadingService } from '../../services/loading.service';
import { RubricaService } from '../../services/rubrica/rubrica.service';
import { UserService } from '../../services/user.service';
import { DialogButtons, DialogType } from '../confirmation-dialog/confirmation-dialog.component';
import { GroupType } from '../../models/models';
import { DestinatarioComunicazioneDTO, DestinatarioSearch } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';

@Component({
  selector: 'app-aggiungi-destinatario',
  templateUrl: './aggiungi-destinatario.component.html',
  styleUrls: ['./aggiungi-destinatario.component.css']
})
export class AggiungiDestinatarioComponent implements OnInit
{
  @Input("tableHeader") indirizziAssociatiHeader: TableConfig[] =
  [
    {
      header: "indirizziEnti.label.den",
      field: "nome"
    },
    {
      header: "indirizziEnti.label.pec_mail",
      field: "email"
    },
    {
      header: "indirizziEnti.label.isPec",
      field: "pec"
    },
    {
      header: "indirizziEnti.label.tipo",
      field: "tipo"
    }
  ];
  @Input("addresses") addresses: DestinatarioComunicazioneDTO[] = [];
  @Input("showAddButton") showAddButton: boolean = true;
  @Input("titleAdresses") fieldName: string = "rubrica.destinatari";
  @Input("collapseButton") collapseButton: boolean = true;
  @Input("collapsed") collapsed: boolean = false;

  @Output("onAdd") add: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("onRemove") remove: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("onChangeType") change: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();

  public addressBookTypes: SelectItem[] =
    [
      //{label: "Rubrica ente", value: "ente"},
      { label: "Rubrica istituzionale", value: "ist" },
      { label: "Nuovo destinatario", value: "new" }
    ];

  public pec_mail: SelectItem[] =
    [
      { label: "email", value: false },
      { label: "PEC", value: true }
    ];

  public cacheEnte: DestinatarioSearch;
  public cacheIstituzionale: DestinatarioSearch;

  public rubricaEnte: DestinatarioComunicazioneDTO[] = [];
  public rubricaIstituzionale: DestinatarioComunicazioneDTO[] = [];

  public totalItemsIstituzionale: number = 0;
  public totaleItemsEnte: number = 0;

  public selected: string = "ist";
  public formRicerca: FormGroup;
  public formInserimento: FormGroup;
  public toEdit: DestinatarioComunicazioneDTO = null;
  public validate: boolean = false;

  public inEditing: boolean = false;
  private unsubscribe: Subject<void> = new Subject();

  constructor(private rubrica: RubricaService,
    private loading: LoadingService,
    private user: UserService,
    private dialog: CustomDialogService,
    private fb: FormBuilder) { }

  ngOnInit()
  {
    this.initCache();
    this.buildFormRicerca();
    this.buildFormInserimento();
    this.ricercaRubricaIstituzionale();
    if (!this.collapseButton)
      this.collapsed = false;
    if (this.rubricaEnteEnabled)
      this.addressBookTypes.unshift({ label: "Rubrica ente", value: "ente" });
    this.showAddButton = this.showAddButton && this.rubricaEnteEnabled; //non può comparire il pulsante se non compare la rubrica dell'ente
    this.startObserving();
  }

  ngOnChanges(changes: SimpleChanges): void 
  {
    if (changes.collapsed && this.collapsed && !this.collapseButton)
      this.collapsed = false;
  }

  ngOnDestroy(): void 
  {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  private initCache(): void
  {
    this.cacheEnte = { nome: '', email: '', page: 1, limit: 5 };
    this.cacheIstituzionale = { nome: '', email: '', page: 1, limit: 5 };
  }

  private startObserving(): void
  {
    this.rubrica.editingSubject
      .asObservable()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(edit => this.inEditing = edit);
  }

  private buildFormRicerca(): void
  {
    this.formRicerca = this.fb.group({
      nome: [''],
      email: [''],
    });
  }

  private buildFormInserimento(): void
  {
    this.formInserimento = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, customEmailValidator]],
      tipo: ['CC'],
      pec: [false],
      inRubrica: [false]
    });
  }

  private ricercaRubricaEnte(): void
  {
    let _self = this;
    _self.cacheEnte = { ..._self.cacheEnte, ..._self.formRicerca.getRawValue() };
    _self.loading.emitLoading(true);
    console.log(_self.cacheEnte)
    _self.rubrica.getIndirizziRubricaEnte(_self.cacheEnte).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        _self.rubricaEnte = response.payload.list;
        _self.totaleItemsEnte = response.payload.count;
      }
      _self.loading.emitLoading(false);
    });
  }

  private ricercaRubricaIstituzionale(): void
  {
    let _self = this;
    _self.cacheIstituzionale = { ..._self.cacheIstituzionale, ..._self.formRicerca.getRawValue() };
    _self.loading.emitLoading(true);
    console.log("ist: ", _self.cacheIstituzionale);
    _self.rubrica.getIndirizziRubricaIstituzionale(_self.cacheIstituzionale).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        _self.rubricaIstituzionale = response.payload.list;
        _self.totalItemsIstituzionale = response.payload.count;
      }
      _self.loading.emitLoading(false);
    });
  }

  public ricerca(): void 
  {
    if (this.selected === "ist")
      this.ricercaRubricaIstituzionale();
    else if (this.selected === "ente")
      this.ricercaRubricaEnte();
  }

  public reset(): void 
  {
    if (this.selected === "new")
    {
      this.validate = false;
      this.formInserimento.reset();
      this.formInserimento.patchValue({ "inRubrica": false });
      this.formInserimento.patchValue({ "pec": false });
    }
    else
    {
      let cached = this.selected === "ente" ? this.cacheEnte : this.cacheIstituzionale;
      this.formRicerca.patchValue({ nome: cached.nome, email: cached.email });
      this.ricerca();
    }
  }

  public resetRicerca(): void
  {
    this.formRicerca.reset();
    if (this.selected === "ente")
    {
      this.cacheEnte.page = 1;
      this.ricercaRubricaEnte();
    }
    else if (this.selected === "ist")
    {
      this.cacheIstituzionale.page = 1;
      this.ricercaRubricaIstituzionale();
    }
  }

  public saveAddress(address: DestinatarioComunicazioneDTO): void
  {
    this.loading.emitLoading(true);
    this.rubrica.aggiungiInRubricaEnte(address).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.loading.emitLoading(false);
        this.ricercaRubricaEnte();
      }
      else
        this.loading.emitLoading(false);
    });
  }

  public modAddress(address: DestinatarioComunicazioneDTO): void 
  {
    this.loading.emitLoading(true);
    this.rubrica.modificaInRubricaEnte(address).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        let index = this.rubricaEnte.map(m => m.id).indexOf(address.id);
        this.rubricaEnte[index] = address;
        this.rubricaEnte = [...this.rubricaEnte];
        this.toEdit = null;
        this.validate = false;
      }
      this.loading.emitLoading(false);
    });
  }

  public annullaModifica(): void 
  {
    this.toEdit = null;
    this.validate = false;
  }

  public deleteAddress(address: DestinatarioComunicazioneDTO): void
  {
    let message = "Sei sicuro di voler eliminare l'indirizzo di \"" + address.nome + "\" dalla rubrica dell'ente?";
    this.dialog.showDialog(true, message, "Attenzione", DialogButtons.OK_CANCEL, (button) =>
    {
      if (button == 1)
        this.doDelete(address);
    }, DialogType.WARNING)
  }

  private doDelete(data: DestinatarioComunicazioneDTO): void
  {
    let _self = this;
    _self.loading.emitLoading(true);
    _self.rubrica.eliminaDaRubricaEnte(data).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        _self.loading.emitLoading(false);
        this.cacheEnte.page = 1;
        this.ricercaRubricaEnte()
      }
      else
        _self.loading.emitLoading(false);
    });
  }

  public removeElement(address: DestinatarioComunicazioneDTO): void { this.remove.emit(address); }
  public addElement(address: DestinatarioComunicazioneDTO): void 
  {
    this.add.emit(this.pulisciIndirizzo(address));
  }
  public changeElement(address: DestinatarioComunicazioneDTO): void { this.change.emit(address); }

  public pulisciIndirizzo<T extends DestinatarioComunicazioneDTO>(address: T): DestinatarioComunicazioneDTO
  {
    let value = { nome: address.nome, email: address.email, pec: address.pec, tipo: address.tipo };
    return value;
  }

  public addNewElement(): void
  {
    this.validate = true;
    if (this.formInserimento.valid)
    {
      let m: any = this.formInserimento.getRawValue();
      let tmp: DestinatarioComunicazioneDTO = { nome: m.nome, pec: m.pec, email: m.email, tipo: m.tipo };
      if (this.formInserimento.get("inRubrica").value === true)
      {
        this.loading.emitLoading(true);
        this.rubrica.aggiungiInRubricaEnte(tmp).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
          {
            let message = "Destinatario aggiunto correttamente in rubrica";
            this.dialog.showDialog(true, message, "Successo", DialogButtons.CHIUDI, null, DialogType.SUCCESS);
            this.validate = false;
            this.formInserimento.reset(),
              this.selected = "ente";
            this.loading.emitLoading(false);
            this.ricercaRubricaEnte();
          }
          else
            this.loading.emitLoading(false);
        });
      }
      this.addElement(tmp);
    }
  }

  public resetFormInserimento(): void
  {
    this.validate = false;
    this.formInserimento.reset();
  }

  public paginazioneEvent(event: any): void
  {
    console.log("event tabella indirizzi: ", event)
    if (this.selected === "ente")
    {
      if (event.limit) this.cacheEnte.limit = event.limit;
      if (event.first) this.cacheEnte.page = event.first === 0 ? 1 : ((event.first / this.cacheEnte.limit) + 1);
      else if (event.page) this.cacheEnte.page = event.page;
      this.ricercaRubricaEnte();
    }
    else if (this.selected === "ist")
    {
      if (event.limit) this.cacheIstituzionale.limit = event.limit;
      if (event.first) this.cacheIstituzionale.page = event.first === 0 ? 1 : ((event.first / this.cacheIstituzionale.limit) + 1);
      else if (event.page) this.cacheIstituzionale.page = event.page;
      this.ricercaRubricaIstituzionale();
    }
  }

  get rubricaEnteEnabled(): boolean { return this.user.groupType != GroupType.Amministratore; }
  get isAdmin(): boolean { return this.user.groupType != GroupType.Amministratore; }

}
