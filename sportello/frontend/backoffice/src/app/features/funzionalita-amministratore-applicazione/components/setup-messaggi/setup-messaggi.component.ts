import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from 'src/app/shared/constants';
import { WebContentDTO } from 'src/app/shared/models/models';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
  selector: 'app-setup-messaggi',
  templateUrl: './setup-messaggi.component.html',
  styleUrls: ['./setup-messaggi.component.scss']
})
export class SetupMessaggiComponent implements OnInit {

  public formMessaggi:FormArray;
  public submitted: boolean=false;
  public loadedFormData = new EventEmitter<boolean>();
  constructor(
    private fb:FormBuilder,
    private adminSvc:AdminFunctionsService,
    private router: Router,
    private translateService: TranslateService,
    private loadingService:LoadingService
  ) { }

  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  private message=
  `
  <table>
    <tbody><tr>
<td colspan="2">				
Attraverso la presente procedura telematica possono essere <b>presentate esclusivamente le istanze di competenza dei seguenti enti:</b>
<ul>
{{entiAttivi}}
</ul>
Con riferimento alla Regione Puglia si specifica che quelle di competenza regionale sono quelle:
<br>
<br>
&gt; afferenti interventi ricadenti nei territori comunali per i quali non è stato delegato l’esercizio delle funzioni paesaggistiche 
<a href="http://www.sit.puglia.it/auth/portal/portale_autorizzazione_paesaggistica/Deleghe/Enti Delegati e Provvedimenti - Ricerca per Comune">link</a>
<br>
&gt; in capo alla Regione poiché, ai sensi del c. 6 art. 146 D.Lgs. 42/2004 e delle N.T.A. del PPTR ed indipendentemente dalla localizzazione, ricadono nelle seguenti casistiche
<br>
<div class="pl-2"> 
a) le infrastrutture stradali, ferroviarie, portuali, aeroportuali e idrauliche e di telecomunicazioni di interesse regionale o sovra regionale;
</div>
<div class="pl-2"> 
b) nuovi insediamenti produttivi, direzionali, commerciali o nuovi parchi tematici che richiedano per la loro realizzazione una superficie territoriale superiore a 40 mila metri quadrati;
</div>
<div class="pl-2"> 
c) impianti di produzione di energia con potenza nominale superiore a 10 Megawatt. 
</div>
<br>
<br>
<br>
<br>    <b>Sei sicuro di voler procedere?</b>
<br> 	
</td>    				    
    </tr>
</tbody></table>
  `;

  ngOnInit() {
    this.loadingService.emitLoading(true);
    this.adminSvc.selectWebContentConfigurabili().subscribe(
        response=>{
          this.buildForm(response.payload);
          this.loadedFormData.next(true);
          this.loadingService.emitLoading(false);
        }
    );
  }

  private buildForm(items:WebContentDTO[]){
    this.formMessaggi = this.fb.array([]);
    items.forEach(item=>{
      let fcontrol={
        codiceContenuto:[item.codiceContenuto,[Validators.required]],
        contenuto:[item.contenuto,[Validators.required]],
        tooltip:[item.tooltip,[]],
      };
      let control=this.fb.group(fcontrol);
      this.formMessaggi.push(control);
    });
    
  }

  public openSalva() {
    this.submitted=true;
    if (this.formMessaggi.valid) {
      this.alertData =
      {
        title: this.translateService.instant('templateDocumentazioni.dialog.salva.title'),
        content: this.translateService.instant('templateDocumentazioni.dialog.salva.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "save" },
        display: true
      };
    }else{
      this.alertData =
    {
      title: this.translateService.instant('generic.warning'),
      content:this.translateService.instant('generic.allRequired'),
      typ: "warning",
      isConfirm: false,
      extraData: {},
      display: true
    };
    }
  }


  public back(): void {
    if(this.formMessaggi.touched){
      this.alertData =
      {
        title: this.translateService.instant('templateComunicazioni.dialog.back.title'),
        content: this.translateService.instant('templateComunicazioni.dialog.back.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "goBack" },
        display: true
      };
    }else{
      this.doGoBack();
    }
  }

  public openInfo(testo:string): void
  {
    this.alertData =
    {
      title: this.translateService.instant('templateComunicazioni.dialog.info.title'),
      content:testo, //this.translateService.instant('templateComunicazioni.dialog.info.content'),
      typ: "info",
      isConfirm: false,
      extraData: {},
      display: true
    };
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    if (event.isChiuso) {
      this.alertData.display = false;
    }
    else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'save':
            this.doSaveData();
            break;
          case 'goBack':
            this.doGoBack()
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

  doGoBack(){
    this.router.navigate(['gestione-istanze']);
  }

  doSaveData(){
    let formArrData=this.formMessaggi.getRawValue();
    this.adminSvc.updateWebContentConfigurabili(formArrData).subscribe(response=>{
      if(response.codiceEsito==CONST.OK && response.payload>=1){
        this.alertData =
          {
            title: "generic.info",
            content: "generic.operazione_ok",
            typ: "success",
            display: true,
            extraData: null,
            isConfirm: false
          };
          this.formMessaggi.markAsUntouched();
      }
    });
  }
  
}
