import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MenuItem } from 'primeng/primeng';
import { SelectItem } from 'primeng/primeng';
import { DialogButtons, DialogType } from '../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CONST } from '../../shared/constants';
import { LoadingService } from '../../shared/services/loading.service';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { TipoProcedimento } from '../funzionalita-amministratore-applicazione/models/admin-functions.models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import {  SezioniConf } from '../funzionalita-amministratore-applicazione/configurations/AdminSezioniConst';
import { paths } from 'src/app/app-routing.module';

@Component({
  selector: 'app-gestione-procedimenti-page',
  templateUrl: './gestione-procedimenti-page.component.html',
  styleUrls: ['./gestione-procedimenti-page.component.css']
})
export class GestioneProcedimentiPageComponent implements OnInit {
  public idSelezionato: string = null;
  public amministratoreApp: boolean = false;

  public listProcedimenti: Array<TipoProcedimento> = [];
  public dettaglio: TipoProcedimento = null;
  public form: FormGroup;
  public modifica: boolean = null;

  public suggestions: Array<SelectItem> = [];
  public validation: boolean = false;

  constructor(private loading: LoadingService,
              private service: AdminFunctionsService,
              private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder,
              private dialog: CustomDialogService,
              private breadcrumbService:BreadcrumbService,
              private translateService:TranslateService,
              ) 
  {
    this.amministratoreApp = this.router.url.includes('admin-app');
    console.log(this.amministratoreApp);
    this.route.paramMap.subscribe(p =>
    {
      if (p.get("id") != null && p.get("id").trim() != "")
        this.idSelezionato = p.get("id");
    });
  }

  ngOnInit() {
    this.searchProcedimenti();
  }

  private searchProcedimenti(): void {
    this.loading.emitLoading(true);
    this.service.listProcedimenti().subscribe(response => {
      this.loading.emitLoading(false);
      if (response.codiceEsito === CONST.OK) {
        this.listProcedimenti = response.payload;
        if (this.idSelezionato) {
          let d = this.listProcedimenti.find(p => p.id == this.idSelezionato);
          this.openDettaglio(d);
        }
        else{
          this.loadBreadcrumb(); 
        }
        
      }
    });
  }

  public goToDetail(d: TipoProcedimento): void{
    if(this.amministratoreApp){
      this.router.navigateByUrl(paths.configurazioneProcedimentoDetail(d.id));
    } else {
      this.router.navigateByUrl(paths.configurazioneProcedimentoEnteDetail(d.id));
    }
  }
  
  public back(): void{
    if(this.amministratoreApp){
      this.router.navigateByUrl(paths.configurazioneProcedimenti());
    } else {
      this.router.navigateByUrl(paths.configurazioneProcedimentiEnte());
    }
  }

  
  public openDettaglio(d: TipoProcedimento): void
  {
    this.dettaglio = d;
    this.buildForm();
    this.loadBreadcrumb(); 
  }

  public navigate(codice: SezioniConf): void {
    switch (codice.value) {
      case "DICHIARAZIONE":
        this.goTo(codice.path);
      case "DISCLAIMER":
          this.goTo(codice.path);  
        break;
      case "TARIFFE":
          this.goTo(codice.path);  
        break;
      case "CONFERENZA_SERVIZI":
        this.goTo(codice.path);  
      break;

    }
  }

  public beautify(item: SelectItem): string {
    return item.label + " [" + item.value + "]";
  }

 
  public salva(): void {
    this.validation = true;
    if (this.form.valid) {
      this.validation = false;
      this.loading.emitLoading(true);
      let d = this.form.getRawValue();
      this.service.updateProcedimento(this.dettaglio.id+"",d).subscribe(response => {
        if (response.codiceEsito === CONST.OK) {
          let m = 'generic.operazioneOk';
          let t = 'generic.successo';
          this.dialog.showDialog(true, m, t, DialogButtons.CHIUDI, null, DialogType.SUCCESS);
          this.loadBreadcrumb();
        }
        this.loading.emitLoading(false);
      });
    }
  }
  
  private buildForm(): void {
    this.form = this.fb.group({
      id: [this.dettaglio.id]
      ,descrizione: [this.dettaglio.descrizione]
      ,sanatoria: [this.dettaglio.sanatoria]
      ,abilitatoPresentazione: [this.dettaglio.abilitatoPresentazione]
    });
  }

  private goTo(route: string): void {
    this.router.navigate([this.amministratoreApp ? 'admin-app' : 'admin', route, this.dettaglio.id]);
  }

  private loadBreadcrumb():void{
    let breadcrumbs : MenuItem[] =[];
    this.translateService.get("MENU_ITEMS.configProc").subscribe((content) => {
      breadcrumbs.push({ label: content, routerLink: this.amministratoreApp ? paths.configurazioneProcedimenti() : paths.configurazioneProcedimentiEnte()});
      if(this.idSelezionato && this.dettaglio){
            breadcrumbs.push({ label: this.dettaglio.descrizione, 
              routerLink: this.amministratoreApp ? paths.configurazioneProcedimentoDetail(this.idSelezionato) : paths.configurazioneProcedimentoEnteDetail(this.idSelezionato)});
            this.breadcrumbService.emitter.emit(breadcrumbs)
      }else{
        this.breadcrumbService.emitter.emit(breadcrumbs)
      }
    });


  }

  

}
