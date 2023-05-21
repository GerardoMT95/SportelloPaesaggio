import { GestioneDestinatariTrasmissioneComponent } from './components/gestione-destinatari-trasmissione/gestione-destinatari-trasmissione.component';
import { CensimentoOrganizzazioneComponent } from './components/censimento-organizzazione/censimento-organizzazione.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { IndirizziEntiComponent } from "src/app/components/funzoni amministratore/rubrica/indirizzi-enti/indirizzi-enti.component";
import { TemplateComunicazioniComponent } from './../../../components/funzoni amministratore/templating comunicazioni/template-comunicazioni/template-comunicazioni.component';
import { AdminConfigurazioniComponent, GestionePECComponent } from "./components";
import { AdminAttivaModificaComponent } from './components/admin-attiva-modifica/admin-attiva-modifica.component';
import { AdminFascicoloComponent } from "./components/admin-fascicolo/admin-fascicolo.component";
import { AdminShellComponent } from "./components/admin-shell/admin-shell.component";
import { ModificaTemplateDocumentazioniComponent } from "src/app/components/modifica-template-documentazioni/modifica-template-documentazioni.component";
import { ConfigurazioneProtocolloComponent } from "./components/configurazione-protocollo/configurazione-protocollo.component";
import { ConfigurazioneTipiProcedimento } from './components/configurazione-tipi-procedimento/configurazione-tipi-procedimento.component';
import { ConfigurazioneDiogeneComponent } from './components/configurazione-diogene/configurazione-diogene.component';
import { ConfigurazioneSportelloAmbienteComponent } from './components/configurazione-sportello-ambiente/configurazione-sportello-ambiente.component';


const routes: Routes = [
  {path:'', component:AdminShellComponent, data: {breadcrumb: null}, children:[
  {path:'', redirectTo:'info', pathMatch:'full'},
  {path:'configura', component:AdminConfigurazioniComponent,data: {breadcrumb: "Attivazioni e configurazioni"}},
  {path:'annulla-fascicolo', component:AdminFascicoloComponent, data: {breadcrumb: "Annullamento istanza trasmessa"}},
  {path:'attiva-modifica', component:AdminAttivaModificaComponent, data: {breadcrumb: "Attivazione modifica istanza trasmessa"}},
  {path:'templates', component:TemplateComunicazioniComponent, data: {breadcrumb: "Modifica template comunicazioni"}}, /* AdminTemplatesComponent */
  {path:'rubrica', component:IndirizziEntiComponent, data: {breadcrumb: "Modifica indirizzi degli enti"}},
  {path:'template-documentazioni' , component:ModificaTemplateDocumentazioniComponent, data: {breadcrumb: "Modifica template documentazioni"}},
  {path:'configura-protocollo', component:ConfigurazioneProtocolloComponent, data: {breadcrumb: "Configurazione protocollo"}},
  {path:'validita-tipi-procedimento', component:ConfigurazioneTipiProcedimento, data: {breadcrumb: "Modifica validità tipi procedimento"}},
  {path:'gestione-PEC', component: GestionePECComponent, data: {breadcrumb: 'Configurazione PEC'}},
  {path:'gestione-organizzazioni', component: CensimentoOrganizzazioneComponent, data: {breadcrumb: 'Gestione organizzazioni'}},
  {path:'gestione-email-organizzazioni/:type', component: GestioneDestinatariTrasmissioneComponent, data: {breadcrumb: 'Gestione email organizzazioni'}},
  {path:'diogene-config', component:ConfigurazioneDiogeneComponent, data: {breadcrumb: "Configurazione account Diogene"}},
  {path:'integrazione-sportello-config', component:ConfigurazioneSportelloAmbienteComponent, data: {breadcrumb: "Configurazione integrazioni sportello ambiente"}},
  
  
 ]},
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
