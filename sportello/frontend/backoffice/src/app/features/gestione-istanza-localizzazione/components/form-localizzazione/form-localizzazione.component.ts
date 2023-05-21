import { Particella } from './../../../../core/models/fascicolo.model';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
/* import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { ComuneService } from 'src/app/services/comune.service';
import { GlobalService } from 'src/app/services/global.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { UserService } from 'src/app/services/user.service'; */
/* import { DataService } from './../../../services/data.service'; */
/* import { requiredDependsOn } from '../../validators/customValidator'; */
import { AllegatoCustomDTO } from 'src/app/features/gestione-istanza-documentazione/model/allegato.models';
import { CONST } from 'src/app/shared/constants';
import { CustomDialogService } from 'src/app/core/services/dialog.service';

//non piu' usato...

/*@Component({
  selector: 'app-form-localizzazione',
  templateUrl: './form-localizzazione.component.html',
  styleUrls: ['./form-localizzazione.component.css']
})*/
export class FormLocalizzazioneComponent implements OnInit
{
  @Input() fascicoloFormLocalizzazione: FormGroup;
  @Input() submitted: boolean;
  @Input() cfErrorRichiedente: boolean; //true se cf errato
  @Input() disable: boolean; //disabilita tutti i control
  @Input() codiceFascicolo: string;
  @Input() id: string;//id fascicolo
  @Input() allegatiShape: AllegatoCustomDTO[];

  subject: BehaviorSubject<any> = new BehaviorSubject<any>({comuni: null, province: null});
  cambioParticelle$: Observable<any>;
  public maxLengthInput: number = CONST.MAX_LENGTH_INPUT;
  public comuneEnteAutocomplete: any;

  public provinciaEnteAutocomplete$: Observable<SelectItem[]>;
  public comuneEnteAutocomplete$: Observable<SelectItem[]>;

  public sezione: string = "provvedimento";

  public hasMap: boolean = false;
  public hasEditor: boolean = true;
  public query: string = "COMUNE='A662' AND SEZIONE = 'B' AND FOGLIO= '1' AND NUMERO='25'";
  public user: string;
  public colonneFile: any[] = 
  [
    {header: "fascicolo.tableAllegati.nomeFile"},
    {header: "fascicolo.tableAllegati.dataArrivo"},
    {header: "fascicolo.tableAllegati.descrizione"}
  ];

  public tipiSelezioneLocalizzazione =
  [
    { label: "Intera regione", value: "R" },
    { label: "Province", value: "P" },
    { label: "Comuni", value: "CO" },
    { label: "Particelle/Mappa", value: "PTC" },
  ]

  //oggetto utilizzato per l'alert
  public alertData = 
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private formBuilder: FormBuilder,
              /* private loadingService: LoadingService,
              private globalService: GlobalService,
              private autPaesSvc: AutorizzazioniPaesaggisticheService,
              private comuneService: ComuneService,
              private userService: UserService, */
              private traslateService: TranslateService,
              private customDialog: CustomDialogService
              /* private promptDialogService: ShowPromptDialogService, */
              /* @Inject(DataService) private dataService: DataService */) { }

  ngOnInit()
  {
    /* this.provinciaEnteAutocomplete$ = this.dataService.getProvinceObs();
    this.comuneEnteAutocomplete$ = this.dataService.getComuniObs(); */

    let controlSelezione = this.fascicoloFormLocalizzazione.controls.tipoSelezioneLocalizzazione;
    /* this.fascicoloFormLocalizzazione.controls.localizzazioneProvince.setValidators(requiredDependsOn(controlSelezione, "P"));
    this.fascicoloFormLocalizzazione.controls.localizzazioneComuni.setValidators(requiredDependsOn(controlSelezione, "CO")); */


    if (!this.fascicoloFormLocalizzazione.get("tipoSelezioneLocalizzazione").value)
        this.fascicoloFormLocalizzazione.get("tipoSelezioneLocalizzazione").patchValue("PTC");

    this.fascicoloFormLocalizzazione.valueChanges.subscribe(change =>
    {
      this.getListe();
    });
    
    this.cambioParticelle$ = this.subject.asObservable();
    this.getListe();
  }

  get form() { return this.fascicoloFormLocalizzazione.controls; }
  closeMap() { this.hasMap = false; }

  ricaricaMappa(ricarica: boolean): void
  {
    if (ricarica)
    {
      this.hasMap = !this.hasMap;
      setTimeout(() =>
      {
        this.hasMap = !this.hasMap;
      }, 200);
    }
  }

  clickSelezione(event: any)
  {
    let selezioneCorrente = this.fascicoloFormLocalizzazione.controls['tipoSelezioneLocalizzazione'].value;

    if (event && event.option.value != selezioneCorrente &&
        selezioneCorrente != null && selezioneCorrente == 'PTC')
    {
      //elimino eventuali righe vuote.
      let formParticelle = this.fascicoloFormLocalizzazione.get('locations') as FormArray
      /* FascicoloFormConfig.pulisciParticelleVuote(formParticelle); */
    }
    if (event && 
        event.option.value != selezioneCorrente && 
        selezioneCorrente != null &&
        this.hasSelezione(selezioneCorrente))
    {
      this.alertData.isConfirm = true;
      this.alertData.typ = "warning";
      this.alertData.title = "Azzeramento selezione"
      this.alertData.content = "Attenzione la selezione preesistente in " + this.tipiSelezioneLocalizzazione.find(el => el.value == selezioneCorrente).label + " verrà eliminata, Proseguo?";
      this.alertData.extraData = { operazione: 'confermaCambioSelezione', oldSel: selezioneCorrente, newSel: event.option.value };
      this.alertData.display = true;
    } 
    else if (event && 
             event.option.value != selezioneCorrente &&
             selezioneCorrente != null)
    {
      this.resetDatiEvalidators(selezioneCorrente);
      this.fascicoloFormLocalizzazione.controls['tipoSelezioneLocalizzazione'].setValue(event.option.value);
      this.fascicoloFormLocalizzazione.controls['tipoSelezioneLocalizzazione'].updateValueAndValidity();
      this.fascicoloFormLocalizzazione.controls['localizzazioneProvince'].updateValueAndValidity();
      this.fascicoloFormLocalizzazione.controls['localizzazioneComuni'].updateValueAndValidity();
    }
  }

  hasSelezione(tipoSelezioneLocalizzazione: string)
  {
    switch (tipoSelezioneLocalizzazione)
    {
      case 'P':
        return this.fascicoloFormLocalizzazione.controls['localizzazioneProvince'].value != null &&
          this.fascicoloFormLocalizzazione.controls['localizzazioneProvince'].value.length > 0;
      case 'CO':
        return this.fascicoloFormLocalizzazione.controls['localizzazioneComuni'].value != null &&
          this.fascicoloFormLocalizzazione.controls['localizzazioneComuni'].value.length > 0;
      case 'PTC':
        let formValue = this.fascicoloFormLocalizzazione.value;
        return formValue.particelle.filter(element => Object.keys(element).some(item => element[item])).length > 0;
      default:
        break;
    }
    return false;
  }

  resetDatiEvalidators(oldSel: string)
  {
    if (oldSel == 'PTC')
    {
      this.clearParticelle();
      this.hasMap = false;
    } else if (oldSel == 'P')
    {
      this.fascicoloFormLocalizzazione.controls['localizzazioneProvince'].setValue(null);
    }
    else if (oldSel == 'CO')
    {
      this.fascicoloFormLocalizzazione.controls['localizzazioneComuni'].setValue(null);
    }
  }

  clearParticelle()
  {
    let formArrParticelle = (this.fascicoloFormLocalizzazione.get('particelle') as FormArray);
    while (formArrParticelle.length !== 0)
    {
      formArrParticelle.removeAt(0)
    }
  }

  checkDuplicato(event: any, indice: number): void
  {
    const control = <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle;
    var compare = control.value[indice];
    var found = false;
    for (let s in control.value)
    {
      var compareTwo = control.value[s];
      if (
        parseInt(s) != indice &&
        compareTwo.codiceEnte != null && compare.codiceEnte != null &&
        compare.codiceEnte.toLowerCase().trim() == compareTwo.codiceEnte.toLowerCase().trim() &&
        compare.foglio.toLowerCase().trim() == compareTwo.foglio.toLowerCase().trim() &&
        compare.sezione.toLowerCase().trim() == compareTwo.sezione.toLowerCase().trim() &&
        compare.particella.toLowerCase().trim() == compareTwo.particella.toLowerCase().trim()
      )
      {
        found = true;
      }
    }
    if (found) 
    {
      <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle[indice].controls.particella.setValue('');
      this.alertData.display = true;
      this.alertData.isConfirm = false;
      this.alertData.typ = "info";
      this.alertData.content = "Questa particella risulta già inserita";
    }
  }

  initParticella(val?: Particella): FormGroup 
  {
    let tmp = this.formBuilder.group({ /* ...FascicoloFormConfig.prepareFormBuilder(FascicoloFormConfig.particellaFormField(this.formBuilder), val) */ });
    tmp.controls.ente = this.formBuilder.group({});
    if(tmp.controls.ente.value === {})
      tmp.controls.ente.setValue(null);
    return tmp;
  }



  result(results): void 
  {
    //console.log(results);
    /* this.loadingService.emitLoading(true); */
    var all = true;
    var escluse: string[] = new Array();
    //Object.entries(results).forEach(([key, value]) => {
    for (let i in results)
    {
      var compare = results[i].feature.attributes;
      //console.log(compare);
      const control = <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle;
      //Object.entries(control.value).forEach(([key, value]) => {
      var found = false;
      for (let s in control.value)
      {
        //console.log(control.value[s]);
        var compareTwo = control.value[s];
        //compareTwo.ente.label != null && (compare.nome_comune.toLowerCase() == compareTwo.ente.label.toLowerCase()) &&
        if (
          compareTwo.codiceEnte != null && compare.comune.toLowerCase().trim() == compareTwo.codiceEnte.toLowerCase().trim() &&
          compare.foglio.toLowerCase().trim() == compareTwo.foglio.toLowerCase().trim() &&
          compare.sezione.toLowerCase().trim() == compareTwo.sezione.toLowerCase().trim() &&
          compare.numero.toLowerCase().trim() == compareTwo.particella.toLowerCase().trim()
        )
        {
          found = true;
        }
      }
      if (!found)
      {
        let comune = { 'label': compare.nome_comune, 'value': null, 'codiceEnte': compare.comune };
        let tmpData: any = {};
        tmpData.id = compare.objectid;
        tmpData.ente = comune;
        tmpData.type = "single";
        tmpData.foglio = compare.foglio;
        tmpData.sezione = compare.sezione;
        tmpData.particella = compare.numero;
        tmpData.codiceEnte = compare.comune;
        tmpData.sub = "";
        control.push(this.initParticella(tmpData));
        // CALLBACK MAPPA
      } else
      {
        all = false;
        escluse.push(compare.numero);
      }
    }
    if (all)
    {
      this.alertData.display = true;
      this.alertData.typ = "success";
      this.alertData.isConfirm = false;
      this.alertData.content = "Particelle Aggiunte con successo";
      /* this.loadingService.emitLoading(false); */
    } else
    {
      this.alertData.display = true;
      this.alertData.typ = "info";
      this.alertData.isConfirm = false;
      this.alertData.content = "Tutte le particelle sono state aggiunte tranne: " + escluse + " in quanto già presenti.";
      /* this.loadingService.emitLoading(false); */
    }
  }

  public fls(event: any): boolean
  {
    event.preventDefault();
    return false;
  }

  onAddRow(): void
  {
    const control = <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle;
    control.push(this.initParticella());
  }


  // ALERT GENERICO PER LA GESTIONE DELLA VALIDAZIONE
  myAlertErrorValidazione(msg?: string): void
  {
    this.alertData.display = true;
    this.alertData.isConfirm = false;
    this.alertData.typ = "danger";
    this.alertData.content = msg;
    /* this.loadingService.emitLoading(false); */
  }


  // ALERT GENERICO PER LA GESTIONE DEGLI ERRORI
  myAlertError(msg?: string): void
  {
    /*this.display = true;
    this.isConfirm = false;
    this.typ = "danger";
    if(msg)
      this.content = msg;
    else
      this.content = "Procedura fallita, riprovare per favore";*/
    /* this.loadingService.emitLoading(false); */
  }

  callbackAlert(event)
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
    {
      this.alertData.display = false;
    } else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'rimuoviParticella':
            event.extraData.particelleFormArray.removeAt(event.extraData.i);
            break;
          case 'confermaDeleteAllegato':
            this.deleteGeoAllegati(event.extraData.row);
            break;
          case 'confermaCambioSelezione':
            this.alertData.extraData.confermato = true;
            if (this.alertData.extraData.oldSel)
            {
              this.resetDatiEvalidators(this.alertData.extraData.oldSel);
            }
            this.fascicoloFormLocalizzazione.controls['tipoSelezioneLocalizzazione'].setValue(event.extraData.newSel);
            this.fascicoloFormLocalizzazione.controls['tipoSelezioneLocalizzazione'].updateValueAndValidity();
            this.fascicoloFormLocalizzazione.controls['localizzazioneProvince'].updateValueAndValidity();
            this.fascicoloFormLocalizzazione.controls['localizzazioneComuni'].updateValueAndValidity();
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }


  public onClearEnte(event: string, indice: number): void
  {
    this.fascicoloFormLocalizzazione['controls'].particelle['controls'][indice].controls.ente.setValue('');
  }

  public searchEnte(event: any, idselect: string): void
  {
    /* this.comuneService.getResultsEnteSiti(event.query).then(data =>
    {
      this.comuneEnteAutocomplete = data;
    }); */
  }

  onLocalizza(): void
  {
    this.myAlertErrorValidazione("TODO localizzazione tramite WEBGIS");
  }

  onAddRowFileShape(event: any)
  {
    let file: File = event.files[0];
    /* this.autPaesSvc.saveGeoAllegato(this.id, file).subscribe(result =>
    {
      if(result.codiceEsito == CONST.OK && result.payload)
      {
        let tmp: AllegatoCustomDTO = new AllegatoCustomDTO();
        tmp.id = result.payload.idAllegato;
        tmp.nome = file.name;
        tmp.tipo = "LOCALIZZAZIONE";
        tmp.dataCaricamento = new Date()
        this.allegatiShape.push(tmp);
      }
    }); */
  }

  onDeleteRow(i: number): void
  {
    var arrayComuni = [];
    //console.log("click",i);
    //const control = <FormArray>this.fascicoloForm.controls['items'];
    const control = <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle;
    Object.keys(control.value).forEach(key =>
    {
      if (control.value[key].ente)
      {
        //if(control.value[key].ente.value != null)
        //  arrayComuni.push(control.value[key].ente.value);
        //else if(control.value[key].ente.codiceEnte != null)
        if (control.value[key].codiceEnte != null)
          arrayComuni.push(control.value[key].codiceEnte);
      }
    });
    var counts = {};
    arrayComuni.forEach(function (x)
    {
      counts[x] = (counts[x] || 0) + 1;
    });

    //if(control.controls[i].value.ente!=null && ((control.controls[i].value.ente.value !=null && counts[control.controls[i].value.ente.value]==1)||(control.controls[i].value.ente.codiceEnte !=null && counts[control.controls[i].value.ente.codiceEnte]==1))){
    if (control.controls[i].value.codiceEnte != null &&
        counts[control.controls[i].value.codiceEnte] == 1)
    {
      this.alertData.display = true;
      this.alertData.typ = "info";
      this.alertData.isConfirm = true;
      this.alertData.content = "In questo modo verrà eliminata la selezione relativa al comune in " +
        this.traslateService.instant('fascicolo.localizzazioneSection.altreInfo.titolo');
      +", procedere?";
      this.alertData.extraData = { operazione: 'rimuoviParticella', particelleFormArray: control, i: i };
      //console.log("click if",i);
    } else
    {
      control.removeAt(i);
    }
  }

  /**
     * 
     * event -> {label: "Bari", value: "5620"}
     *
     **/
  addDU(event: any, indice?: number): void
  {
    if (event.value != null)
    {
      //ALG 2018-01-16 => CR collegare siti ad enti
      /* this.loadingService.emitLoading(true);
      this.autPaesSvc.getCodIstat(event.value).then(resp =>
      {
        this.loadingService.emitLoading(false);
        if (resp.code == CONST.OK)
        {
          if (indice != null)
          {
            const control = <FormArray>this.fascicoloFormLocalizzazione['controls'].particelle['controls'];
            control[indice].controls.codiceEnte.setValue(resp.payload);
            this.checkDuplicato(event, indice);
          }
        }
      }).catch(error =>
      {
        this.loadingService.emitLoading(false);
      }) */
    }
  }

  download(row: AllegatoCustomDTO)
  {
    /* this.autPaesSvc.downloadAllegato(row.id.toString()).subscribe(result =>
    {
      if (result.ok)
      {
        downloadFile(result.body, row.nome);
      }
    }); */
  }

  delete(row: AllegatoCustomDTO)
  {
    this.alertData.title = "Attenzione";
    this.alertData.content = "Cancellazione irreversibile, proseguo?";
    this.alertData.typ = "info";
    this.alertData.isConfirm = true;
    this.alertData.extraData =
    {
      operazione: "confermaDeleteAllegato",
      row: row
    }
    this.alertData.display = true;
  }

  deleteGeoAllegati(row: AllegatoCustomDTO): void
  {
    /* this.autPaesSvc.eliminaAllegato(row.id.toString()).subscribe(result =>
    {
      if(result.codiceEsito === CONST.OK)
      {
        let index = this.allegatiShape.map(m => m.id).indexOf(row.id);
        if(index >= 0)
          this.allegatiShape.splice(index, 1);
      }
    }); */
  }

  public getListe()
  {
    let province: string[] = this.fascicoloFormLocalizzazione.get("localizzazioneProvince").value;
    if(province && province.length === 0)
      province = null;
    let comuni: string[] = null;
    if (this.fascicoloFormLocalizzazione.get("tipoSelezioneLocalizzazione").value === "PTC")
    {
      comuni = [];
      let formArrParticelle = (this.fascicoloFormLocalizzazione.get('particelle') as FormArray);
      /* formArrParticelle.controls.forEach(particella =>
      {
        let ente = particella.get("ente").value;
        if(!comuni.includes(ente))
          comuni.push(ente);
      }); */
    }
    else if (this.fascicoloFormLocalizzazione.get("tipoSelezioneLocalizzazione").value === "CO")
      comuni = this.fascicoloFormLocalizzazione.get("localizzazioneComuni").value;
    let data: any = { comuni: null, province: null};
    if (this.fascicoloFormLocalizzazione.get("tipoSelezioneLocalizzazione").value != "R" &&
        (!province && !comuni))
    {
      data = null;
    }
    else
    {
      data = {comuni: comuni, province: province};
    }
    this.subject.next(data);
  }
}
