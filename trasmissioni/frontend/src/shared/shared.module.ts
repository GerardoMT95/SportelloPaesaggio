import { AggiungiUlterioreDocumentazioneMultiComponent } from './../app/components/form-fascicolo/form-ulteriore-documentazione/aggiungi-ulteriore-documentazione-multi/aggiungi-ulteriore-documentazione-multi.component';
import { FormUdComponent } from './../app/components/form-fascicolo/form-ulteriore-documentazione/form-ud/form-ud.component';
import { DropdownFieldComponent } from './components/dropdown-field/dropdown-field';
import { CircleComponent } from './components/circle/circle.component';
import { AggiungiUlterioreDocumentazioneComponent } from './../app/components/form-fascicolo/form-ulteriore-documentazione/aggiungi-ulteriore-documentazione/aggiungi-ulteriore-documentazione.component';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from "@ngx-translate/core";
import { FormsModule, FormControlName } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
//PRIMENG
import { SelectButtonModule } from 'primeng/selectbutton';
import { CalendarModule } from 'primeng/calendar';
import { PanelModule, OverlayPanelModule } from 'primeng/primeng';
import { TabViewModule } from 'primeng/tabview';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FileUploadModule } from 'primeng/fileupload';
import { KeyFilterModule } from 'primeng/keyfilter';
import { DropdownModule } from 'primeng/dropdown';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { MultiSelectModule } from 'primeng/multiselect';
import { FieldsetModule } from 'primeng/fieldset';
import { CheckboxModule } from 'primeng/checkbox';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { AccordionModule } from 'primeng/accordion';
import { TableModule } from "primeng/table";
import { TooltipModule } from "primeng/tooltip";
import { DataViewModule } from "primeng/dataview";
import { ToggleButtonModule } from 'primeng/togglebutton';
import { EditorModule } from 'primeng/editor';
import { InputSwitchModule } from 'primeng/inputswitch';
import { SplitButtonModule } from 'primeng/splitbutton';
import {ScrollPanelModule} from 'primeng/scrollpanel';




// BOOTSTRAP FOR ANGULAR
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';
library.add(fas, far);
//interni
import { DataTableComponent } from './components/data-table/data-table.component';
import { NoDataComponent } from './components/no-data/no-data.component';
import { StatusListBarComponent } from './components/status-list-bar/status-list-bar.component';
import { AlertDialogComponent } from '../app/components/alert-dialog/alert-dialog.component';
import { PromptModalComponent } from '../app/components/prompt-modal/prompt-modal.component';
import { TimelineComponent } from '../app/components/timeline/timeline.component';
import { FormRiepilogoComponent } from '../app/components/form-fascicolo/form-riepilogo/form-riepilogo.component';
import { FormRichiedenteComponent } from '../app/components/form-fascicolo/form-richiedente/form-richiedente.component';
import { FormInterventoComponent } from '../app/components/form-fascicolo/form-intervento/form-intervento.component';
import { DisableControlDirective } from '../shared/directive/disable-control.directive';
import { AlertComponent } from '../app/components/alert/alert.component';
import { FormProvvedimentoComponent } from 'src/app/components/form-fascicolo/form-provvedimento/form-provvedimento.component';
import { EsriMapComponent } from '../app/components/esri-map/esri-map.component';
import { TableNoFilterComponent } from 'src/app/components/form-fascicolo/table-no-filter/table-no-filter.component';
import { AllegatiFormComponent } from 'src/app/components/form-fascicolo/allegati-form/allegati-form.component';
import { FormAllegatiComponent } from 'src/app/components/form-fascicolo/form-allegati/form-allegati.component';
import { DeleteAllegatiDialogComponent } from 'src/app/components/form-fascicolo/delete-allegati-dialog/delete-allegati-dialog.component';
import { ValidationModalComponent } from 'src/app/components/validation-modal/validation-modal.component';
import { MailComposeComponent } from 'src/app/components/form-fascicolo/mail-compose/mail-compose.component';
import { FormCorrispondenzaComponent } from 'src/app/components/form-fascicolo/form-corrispondenza/form-corrispondenza.component';
import { DettaglioComComponent } from 'src/app/components/form-fascicolo/form-corrispondenza/dettaglio-com/dettaglio-com.component';
import { ManualeComponent } from 'src/app/components/manuale/manuale.component';
import { CartografiaComponent } from 'src/app/components/cartografia/cartografia.component';
import { FormEsitoComponent } from 'src/app/components/form-fascicolo/form-esito/form-esito.component';
import { CanAccessDirective } from './directive/can-access.directive';
import { SearchCorrispondenzaComponent } from 'src/app/components/form-fascicolo/form-corrispondenza/search-corrispondenza/search-corrispondenza.component';

import { SafeHtmlPipe } from 'src/app/pipe/safeHtml/safe-html.pipe';
import { FormSearchComponent } from 'src/app/components/form-search/form-search.component';
import { ValueOfPipe } from 'src/app/pipe/valueOf/value-of.pipe';
import { NuovaComunicazioneComponent } from 'src/app/components/form-fascicolo/form-corrispondenza/nuovaComunicazione/nuova-comunicazione/nuova-comunicazione.component';
import { DettaglioEnteComponent } from 'src/app/components/funzoni amministratore/rubrica/dettaglio-ente/dettaglio-ente.component';
import { IndirizziEntiComponent } from 'src/app/components/funzoni amministratore/rubrica/indirizzi-enti/indirizzi-enti.component';
import { TemplateComunicazioniComponent } from 'src/app/components/funzoni amministratore/templating comunicazioni/template-comunicazioni/template-comunicazioni.component';
import { DettaglioTemplateComponent } from 'src/app/components/funzoni amministratore/templating comunicazioni/dettaglio-template/dettaglio-template.component';
import { LocalizzazioneComponent } from 'src/app/components/form-fascicolo/localizzazione/localizzazione.component';
import { DatePickerFieldComponent } from './components/date-picker-field/date-picker-field.component';
import { EditorFieldComponent } from './components/editor-field/editor-field.component';
import { MultiselectFieldComponent } from './components/multiselect-field/multiselect-field.component';
import { SelectFieldComponent } from './components/select-field/select-field.component';
import { TooltipComponent } from './components/tooltip/tooltip.component';
import { TextareaFieldComponent } from './components/textarea-field/textarea-field.component';
import { TextFieldComponent } from './components/text-field/text-field.component';
import { InputErrorComponent } from './components/input-error/input-error.component';
import { CheckboxFieldComponent } from './components/checkbox-field/checkbox-field.component';
import { BuildingTableComponent } from './components/building-table/building-table.component';
import { ControlAccessDirective } from './directive/control-access/control-access.directive';
import { AggiuntiDestinatarioComponent } from './components/aggiunti-destinatario/aggiunti-destinatario.component';
import { TabellaIndirizziComponent } from './components/tabella-indirizzi/tabella-indirizzi.component';
import { SectionHeaderComponent } from './components/section-header/section-header.component';
import { RiconoscimentoAllegatoComponent } from 'src/app/components/riconoscimento-allegato/riconoscimento-allegato.component';
import { FormDatiResponsabileComponent } from 'src/app/components/form-fascicolo/form-dati-responsabile/form-dati-responsabile.component';
import { EditNotifyComponent } from './components/edit-notify/edit-notify.component';
import { AssegnaFascicoloConfigComponent } from './components/assegna-fascicolo-config/assegna-fascicolo-config.component';
import { AssegnaFascicoloComponent } from './components/assegna-fascicolo/assegna-fascicolo.component';
import { TabellaParticelleComponent } from './components/tabella-particelle/tabella-particelle.component';
import { ModificaTemplateDocumentazioniComponent } from 'src/app/components/modifica-template-documentazioni/modifica-template-documentazioni.component';
import { NumberFieldComponent } from './components/number-field/number-field.component';
import { RowsNumberHandlerComponent } from './components/rows-number-handler/rows-number-handler.component';
import { FileSizePipe } from 'src/app/pipe/filesize/filesize.pipe';
import { ConfigurazioneProtocolloComponent } from 'src/app/features/admin-utils/admin-module/components/configurazione-protocollo/configurazione-protocollo.component';
import { AccettaPrivacyComponent } from './components/accetta-privacy/accetta-privacy.component';
import { GenericModalFormComponent } from './components/generic-modal-form/generic-modal-form.component';
import { ConfigurazioneTipiProcedimento } from 'src/app/features/admin-utils/admin-module/components/configurazione-tipi-procedimento/configurazione-tipi-procedimento.component';
import { VerificaImprontaHashComponent } from './components/verifica-impronta-hash/verifica-impronta-hash.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { RichiestePostTrasmissioneComponent } from 'src/app/components/richieste-post-trasmissione/richieste-post-trasmissione.component';
import { GenericTableComponent } from 'src/app/components/generic-table/generic-table.component';
import { AnnotazioniInterneComponent } from 'src/app/components/annotazioni-interne/annotazioni-interne.component';
import { ConfigurazioneDiogeneComponent } from 'src/app/features/admin-utils/admin-module/components/configurazione-diogene/configurazione-diogene.component';
import { PasswordFieldComponent } from './components/password-field/password-field.component';
import { ImportProvvedimentoComponent } from './components/import-provvedimento/import-provvedimento.component';

const components = [

  DataTableComponent,
  NoDataComponent,
  StatusListBarComponent,
  AlertDialogComponent,
  PromptModalComponent,
  TimelineComponent,
  FormRiepilogoComponent,
  FormRichiedenteComponent,
  DisableControlDirective,
  CanAccessDirective,
  ControlAccessDirective,
  FormInterventoComponent,
  FormProvvedimentoComponent,
  AllegatiFormComponent,
  TableNoFilterComponent,
  EsriMapComponent,
  FormAllegatiComponent,
  DeleteAllegatiDialogComponent,
  ValidationModalComponent,
  MailComposeComponent,
  PromptModalComponent,
  FormCorrispondenzaComponent,
  DettaglioComComponent,
  AlertComponent,
  ManualeComponent,
  CartografiaComponent,
  FormEsitoComponent,
  SearchCorrispondenzaComponent,
  SafeHtmlPipe,
  FileSizePipe,
  ValueOfPipe,
  FormSearchComponent,
  NuovaComunicazioneComponent,
  IndirizziEntiComponent,
  DettaglioEnteComponent,
  TemplateComunicazioniComponent,
  DettaglioTemplateComponent,
  LocalizzazioneComponent,
  DatePickerFieldComponent,
  EditorFieldComponent,
  MultiselectFieldComponent,
  SelectFieldComponent,
  TooltipComponent,
  TextareaFieldComponent,
  TextFieldComponent,
  InputErrorComponent,
  CheckboxFieldComponent,
  BuildingTableComponent,
  SectionHeaderComponent,
  RiconoscimentoAllegatoComponent,
  FormDatiResponsabileComponent,
  EditNotifyComponent,
  AssegnaFascicoloConfigComponent,
  AssegnaFascicoloComponent,
  AggiungiUlterioreDocumentazioneComponent,
  TabellaParticelleComponent,
  ModificaTemplateDocumentazioniComponent,
  ConfigurazioneProtocolloComponent,
  ConfigurazioneTipiProcedimento,
  NumberFieldComponent,
  AggiuntiDestinatarioComponent,
  TabellaIndirizziComponent,
  AssegnaFascicoloConfigComponent,
  AssegnaFascicoloComponent,
  RowsNumberHandlerComponent,
  AccettaPrivacyComponent,
  ControlAccessDirective,
  GenericModalFormComponent,
  CircleComponent,
  DropdownFieldComponent,
  VerificaImprontaHashComponent,
  FileUploadComponent,
  RichiestePostTrasmissioneComponent,
  GenericTableComponent,
  AnnotazioniInterneComponent,
  ConfigurazioneDiogeneComponent,
  PasswordFieldComponent,
  FormUdComponent,
  AggiungiUlterioreDocumentazioneMultiComponent,
  ImportProvvedimentoComponent,
];

const modules: any[] = [
  CommonModule,
  TranslateModule,
  FormsModule,
  ReactiveFormsModule,
  NgbModule,
  FontAwesomeModule,
  DialogModule,
  DropdownModule,
  SelectButtonModule,
  CalendarModule,
  TabViewModule,
  RadioButtonModule,
  FileUploadModule,
  KeyFilterModule,
  AutoCompleteModule,
  MultiSelectModule,
  DropdownModule,
  DialogModule,
  CheckboxModule,
  FieldsetModule,
  AccordionModule,
  BreadcrumbModule,
  TooltipModule,
  TableModule,
  DataViewModule,
  ToggleButtonModule,
  EditorModule,
  PanelModule,
  OverlayPanelModule,
  InputSwitchModule,
  SplitButtonModule, 
  ScrollPanelModule
];

@NgModule({
  declarations: [
    ...components,
  ],
  imports: [
    ...modules
  ],
  exports: [
    ...modules,
    ...components
  ],
})
export class SharedModule { }