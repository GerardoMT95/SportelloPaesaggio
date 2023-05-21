import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { TableConfig } from 'src/app/core/models/header.model';
import { DestinatarioComunicazioneDTO } from 'src/app/features/interfaccia-di-riepilogo-del-fascicolo/models/corrispondenza.model';
import { CONST } from '../../constants';
import { copyOf } from '../../functions/generic.utils';
import { RubricaService } from '../../services/rubrica/rubrica.service';


@Component({
  selector: 'app-tabella-indirizzi',
  templateUrl: './tabella-indirizzi.component.html',
  styleUrls: ['./tabella-indirizzi.component.css']
})
export class TabellaIndirizziComponent
{
  @Input("header") tableHeader: TableConfig[] =
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
    }
  ];
  @Input("addresses") addresses: DestinatarioComunicazioneDTO[] = [];
  @Input("paginator") paginator: boolean = true;
  @Input("page") page: number = 1;
  @Input("totalItems") totalRecords: number = 0;
  @Input("editable") editable: boolean = true;
  @Input("deletable") deletable: boolean = true;
  @Input("selectable") selectable: boolean = true;
  @Input("selectableType") selectableType: boolean = false;

  @Input("disableEditButton") disableEditButton: boolean = false;
  @Input("disableDeleteButton") disableDeleteButton: boolean = false;
  @Input("disableSelectButton") disableSelectButton: boolean = false;

  @Output("onRemove") onRemove: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("onEdit") onEdit: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("onSelect") onSelect: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("changeType") onChangeType: EventEmitter<DestinatarioComunicazioneDTO> = new EventEmitter<DestinatarioComunicazioneDTO>();
  @Output("onPaging") onPaging: EventEmitter<any> = new EventEmitter<any>();

  public clone: DestinatarioComunicazioneDTO = null;
  public cloneTipo: "TO"|"CC" = null;
  public modifyIndex: number = null;
  public inEditing: boolean = false; 
  public rows: number = 5;

  private REGEX_EMAIL = new RegExp(CONST.PATTERN_MAIL);

  public items: SelectItem[] =
  [
    {
      label: "CC",
      value: "CC"
    },
    {
      label: "TO",
      value: "TO"
    }
  ];
  public pec_mail: SelectItem[] =
  [
    {label: "email", value: false},
    {label: "PEC", value: true}
  ];

  constructor(private rubrica: RubricaService) { }

  public emitRemoveEvent(row: DestinatarioComunicazioneDTO): void { this.onRemove.emit(row); }
  public emitSelectEvent(row: DestinatarioComunicazioneDTO): void { this.onSelect.emit(row); }
  public onRowEditSave(dest: DestinatarioComunicazioneDTO): void 
  { 
    this.onChangeType.emit({...dest, tipo: this.cloneTipo}); 
    this.cloneTipo = null;
    this,this.editingCancelled();
  }
  public emitPagingEvent(event: any): void { this.onPaging.emit(event); }
  public pageChangedAction(page: number): void { this.onPaging.emit({ page }); }
  public emitEditEvent(): void 
  { 
    this.onEdit.emit(this.clone);
    this.clone = null;
    this.editingCancelled();
  }

  public validRow(row: DestinatarioComunicazioneDTO): boolean { return this.testNome(row.nome) && this.testMail(row.email); }
  private testNome(nome: string): boolean { return nome && nome != ""; }
  private testMail(mail: string): boolean { return mail && this.REGEX_EMAIL.test(mail); }

  public validField(row: DestinatarioComunicazioneDTO, field: string): boolean
  {
    if(field === "nome")
      return this.testNome(row.nome);
    else if(field === "email")
      return this.testMail(row.email);
  }

  public startEditingRow(row: DestinatarioComunicazioneDTO): void
  {
    this.clone = copyOf(row);
    let index = this.addresses.map(m => m.id).indexOf(row.id);
    this.editingStarted(index);
  }

  public cancelEditingRow(): void
  {
    this.clone = null;
    this.editingCancelled();
  }

  public startEditingType(row: DestinatarioComunicazioneDTO): void
  {
    this.cloneTipo = row.tipo;
    let index = this.addresses.map(m => m.email).indexOf(row.email);
    this.editingStarted(index);
  }

  private editingCancelled(): void
  {
    this.inEditing = false;
    this.modifyIndex = null;
    this.rubrica.editingSubject.next(false);
  }
  private editingStarted(index: number): void
  {
    this.inEditing = true;
    this.modifyIndex = index;
    this.rubrica.editingSubject.next(true);
  }

  get first(): number { return (this.page - 1) * this.rows; }
  get showEditButtons(): boolean { return this.deletable && !(this.selectable || this.editable)}
  get readonly(): boolean { return !this.editable && !this.deletable && !this.selectable; }

}
