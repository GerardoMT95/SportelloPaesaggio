import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/primeng';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { LoadingService } from 'src/app/services/loading.service';
import { ShowAlertService } from 'src/app/services/show-alert.service';
import { CONST } from 'src/shared/constants';
import { checkFileSizeTypeFn, downloadFile } from '../functions/genericFunctions';
import { AnnotazioniInterneDTO } from '../model/entity/corrispondenza.models';
import { DettaglioFascicolo } from '../model/fascicolo.models';

/**
 * component solo mock per illustrare quanto andremo a fare per le annotazioni interne dell'ente
 */


 export enum EsitoControllo { 
  // {label:"Non controllato",value:"NON_CONTROLLATO"},
  // {label:"Positivo",value:"POSITIVO"},
  // {label:"Negativo",value:"NEGATIVO"},
  // {label:"Non completato",value:"NON_COMPLETATO"},
}

@Component({
  selector: 'app-annotazioni-interne',
  templateUrl: './annotazioni-interne.component.html',
  styleUrls: ['./annotazioni-interne.component.scss']
})
export class AnnotazioniInterneComponent implements OnInit {

  @Input() idFascicolo: number;

  public alertData =
    {
      display: false,
      title: "",
      content: "",
      typ: "",
      extraData: null,
      isConfirm: false,
    };
  public display: boolean = false;
  public detail: boolean = false;
  public newButton: boolean = false;
  public inviato: boolean = false;
  public fileArray: any[] = [];
  public disable=false;
  const=CONST;
  public selectEsito:SelectItem[]=[
    {label:"Non controllato",value:"NON_CONTROLLATO"},
    {label:"Positivo",value:"POSITIVO"},
    {label:"Negativo",value:"NEGATIVO"},
    {label:"Non completato",value:"NON_COMPLETATO"},
  ];
  //public selectedEsito: {label: string, value:string};
  public selectedEsito: SelectItem;

  public itemsTotal: number = 0; //totale record ricerca
  public comForm: FormGroup;

  public annotazione = new AnnotazioniInterneDTO();
  constructor(private formBuilder: FormBuilder,
              private autpaeSvc: AutorizzazioniPaesaggisticheService,
              private showAlertDialog: ShowAlertService,
              private translateService: TranslateService) { }

  ngOnInit() {
    this.caricamentoDati();
    this.buildForm();
  }

  public buildForm(): void {
    this.comForm = this.formBuilder.group({
      esitoControllo: new FormControl(null),
      note: new FormControl([]),
    });
  }

  private caricamentoDati() {
    this.display = true;
    this.autpaeSvc.isPresentAnnotazione(String(this.idFascicolo))
      .subscribe(result => { 
          this.annotazione = result.payload.annotazioneBase;
          if(this.annotazione !== null){

            this.detail = true;
            this.newButton = false;
  
            this.comForm.patchValue({
              'note': this.annotazione.note
            })
            
            this.esitoControllo(this.annotazione.esitoControllo);

            if (result.payload.allegati !== null) {
              this.fileArray = result.payload.allegati;
            } else {
              this.fileArray = [];
            }
          }
          else{
            this.detail = false;
            this.newButton = true;
          }
      });
  }

  nuovaAnnotazione() {
    //crea nuova annotazione
    this.autpaeSvc.createAnnotazioniInterne(String(this.idFascicolo))
      .subscribe(result => {
        if (result.codiceEsito === CONST.OK) {
          this.annotazione = result.payload;
          console.log(this.annotazione);
          this.detail = true;
          this.newButton = false;
        }
        else{
          this.alertData = {
            display: true,
            title: "Errore!",
            content: "Errore nella creazione dell'annotazione.",
            typ: "danger",
            extraData: "",
            isConfirm: false,
          };
        }
      });
  }

  callbackAlert(event: any) {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso) {
      this.alertData.display = false;
    } else if (event.isConfirm) {
      if (event.extraData && event.extraData.operazione) {
        switch (event.extraData.operazione) {
          case 'validato':
            //this.doValidate(event.extraData.codice)
            break;
            case "deleteAllegato":
              const id = this.alertData.extraData.id;
              const index = this.alertData.extraData.index;
              this.autpaeSvc
                .eliminaAllegatoAnnotazioniInterne(
                  id,
                  this.annotazione.idFascicolo,
                  this.annotazione.id
                )
                .subscribe((result) => {
                  this.alertData.display = true;
                  this.alertData.title = "Esito Operazione";
  
                  if (result) {
                    //Esito Operazione
                    this.alertData.content =
                      "Cancellazione avvenuta con successo";
                    this.alertData.typ = "success";
                    this.eliminaElemento(null, this.fileArray, index);
                  } else {
                    this.alertData.content =
                      "Errore nella cancellazione dell'allegato!";
                    this.alertData.typ = "danger";
                  }
                  this.alertData.extraData = null;
                  this.alertData.isConfirm = false;
                });
              break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }


  checksumFile(file: any) {
    if (file && file.checksum) {
      return file.checksum;
    } else {
      return "";
    }
  }

  descrizioneFile(file: any) {
    if (file && file.descrizione) {
      return file.descrizione;
    } else {
      return "Ulteriore documentazione";
    }
  }

  public cancellaAllegato(index: number): void {
    if (this.fileArray[index].id)
    {
      this.alertData =
      {
        display: true,
        title: "Attenzione",
        content: "La cancellazione di questo allegato sarà irreversibile, proseguo?",
        typ: "warning",
        extraData: { operazione: "deleteAllegato", id: this.fileArray[index].id, index: index },
        isConfirm: true,
      };
    }
    // else
    // {
    //   if (this.comForm.controls.allegati)
    //   {
    //     let fileIndex = this.comForm.controls.allegati.value.indexOf(this.fileArray[index].file)
    //     if (fileIndex >= 0)
    //       this.comForm.controls.allegati.value.splice(fileIndex);
    //     this.fileArray.splice(index);
    //     if (!this.comForm.controls.allegati.value)
    //       this.comForm.controls.allegati.setValue([]);
    //   }
    // }
  }

  private upload(file: File, idFascicolo: number, idAnnotazione: number): void {
    this.autpaeSvc
      .caricaAllegatoAnnotazioniInterne(file, idFascicolo, idAnnotazione)
      .subscribe((result) => {
        if (result.codiceEsito === CONST.OK) {
          this.fileArray.push(result.payload);
        }
      });
  }

  public allegaFile(event){
    if (event.files) {
      let file = event.files[0];
      let fileCheck = checkFileSizeTypeFn(null, CONST.MAX_5MB);
      let errorValidation = fileCheck(file);
      if (errorValidation) {
        this.showAlertDialog.showAlertDialog(
          true,
          null,
          this.translateService.instant(errorValidation)
        );
        return;
      }
      this.upload(file, this.idFascicolo, Number(this.annotazione.id));
    }
  }

  public download(id: number, filename: string): void {
    this.autpaeSvc.downloadAllegatoFascicolo(this.idFascicolo+'',id.toString()).subscribe((result) => {
      if (result.ok) downloadFile(result.body, filename);
    });
  }

  public sendData(form: NgForm){
    let value = form.value;
    //Setto l'oggetto annotazione di tipo AnnotazioniInterneDTO
    this.annotazione.idFascicolo = String(this.idFascicolo);
    this.annotazione.esitoControllo = this.selectedEsito.value;
    this.annotazione.note = value.note;
    console.log(this.annotazione);

    this.autpaeSvc.updateAnnotazioneInterna(this.annotazione)
      .subscribe(result =>{
        //Dobbiamo recuperare i dati di ritorno??
        if (result.codiceEsito === CONST.OK){
          this.alertData = {
            display: true,
            title: "Operazione completata",
            content: "Annotazione trasmessa con successo.",
            typ: "success",
            extraData: "",
            isConfirm: false,
          };
        }else{
          this.alertData = {
            display: true,
            title: "Errore!",
            content: "Errore nell'aggiunta dell'annotazione.",
            typ: "danger",
            extraData: "",
            isConfirm: false,
          };
        }
      });

  }

  public cancel(){    
    this.comForm.reset();
    this.comForm.setErrors({valid: false});
    this.fileArray = [];
    this.annotazione = new AnnotazioniInterneDTO;
  }

  public checkValidityForm(campo: string){
    return this.comForm.get(campo).touched&&this.comForm.get(campo).invalid?
             true:false;
  }

  public checkTextArea(campo: string){   
    if(this.comForm.get(campo).value==null){
      return true;
    }
    return this.comForm.get(campo).value!=null
            && this.comForm.get(campo).value.length<500? true: false;
  }

  public checkToSave(){
    return this.checkTextArea('note')&&this.comForm.valid? false:true;   
  }

  public checkAllegati(){
    return this.fileArray.length!=0?true:false;
  }

  public esitoControllo(label: string){
    this.selectEsito.forEach(e =>{
        if(e.label === label){
          this.selectedEsito = {label: e.label, value: e.value};
        }
    });
  }

  /*---- Elimina un elemento dell'array ----*/
  eliminaElemento(item: any, array: any[], index: number) {
    if (item != null) {
      array.forEach((e) => {
        if (e.id == item.id) item = e;
      });
      index = array.indexOf(item, 0);
    }

    array.splice(index, 1);
    array.slice();
  }
}
