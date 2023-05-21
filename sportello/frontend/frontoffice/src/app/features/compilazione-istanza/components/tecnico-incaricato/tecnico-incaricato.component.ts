import { Component, OnInit, Input, EventEmitter, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Fascicolo } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { IstanzaFormService } from 'src/app/shared/services/istanza-form/istanza-form.service';
import { Subject } from 'rxjs';
import { requiredIfNotEmpty, requiredNotEmpty } from 'src/app/shared/validators/customValidator';

import { NgxCsvParser, NgxCSVParserError } from 'ngx-csv-parser';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { FileUploadComponent } from 'src/app/shared/components';
import { DialogService } from 'src/app/core/services/dialog.service';


@Component({
  selector: 'app-tecnico-incaricato',
  templateUrl: './tecnico-incaricato.component.html',
  styleUrls: ['./tecnico-incaricato.component.scss']
})
export class TecnicoIncaricatoComponent implements OnInit,OnDestroy {

  @Input() tipoDocumentoOptions: SelectOption[];  
  @Input() fascicolo: Fascicolo;   
  @Input() mainForm: FormGroup;
  @Input() validation: boolean;
  private unsubscribe$=new Subject<void>();

  @ViewChild("fileUpload", {static: false}) 
  public fileUpload: FileUploadComponent;

  const=CONST;
  constructor(private fb: FormBuilder,
    private allegatiService: AllegatoService,
    private loadingService: LoadingService,
    private istanzaFormService:IstanzaFormService
    ,private ngxCsvParser: NgxCsvParser
    ,private dialogService:DialogService
    ) {  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
  

  ngOnInit() {
    this.buildForm();
    ViewMapper.mapObjectToForm((this.mainForm.get("tecnicoIncaricato") as FormGroup), this.fascicolo.istanza.tecnicoIncaricato);
    this.istanzaFormService.tecnicoIncaricatoFormComplete$.next(true);
  }

  buildForm() {
    let formTecnico= this.fb.group({
      id: [null],//hidden
      nome: ['', requiredNotEmpty],
      cognome: ['', requiredNotEmpty],
      codiceFiscale: ['', [Validators.required,Validators.pattern(CONST.regexCodFisc)]],
      sesso: ['', Validators.required],
      natoIl: ['', Validators.required],
      natoStato: ['', Validators.required],
      natoCitta: [''],//diventano required a partire dallo stato
      natoComune: [''],
      natoProvincia: [''],
      residenteIn: this.fb.group({
        stato: [''],
        citta: [''],
        comune: [''],
        provincia: [''],
        via: [''],
        n: [''],
        cap: ['']
      }),
      conStudioIn: this.fb.group({
        stato: [''],
        citta: [''],
        comune: [''],
        provincia: [''],
        via: [''],
        n: [''],
        cap: ['']
      }),
      recapitoTelefonico: ['',Validators.pattern(CONST.PHONE_PATTERN)],
      // indirizzoEmail: ['', Validators.required],
      pec: ['', [Validators.required, Validators.email,Validators.pattern(CONST.PATTERN_PEC)]],
      iscritoAllOrdine: [''],  
      di: [''],
      n: [''],
      fax: [''],
      cellulare: [''],
      documento: this.fb.group({
        idTipo: ['', Validators.required],
        numero: ['',requiredNotEmpty],
        dataRilascio: ['',Validators.required],
        enteRilascio: ['',requiredNotEmpty],
        dataScadenza: ['', Validators.required]
      }),
      esisteDocumento: [this.fascicolo.istanza.tecnicoIncaricato.documento && 
        this.fascicolo.istanza.tecnicoIncaricato.documento.docAllegato!=null, Validators.requiredTrue]
    });
    /*
    formTecnico.get('residenteIn').get('cap').setValidators(
      [Validators.pattern('[0-9]{5}'),requiredIfNotEmpty(formTecnico.get('residenteIn').get('comune'))]);
    formTecnico.get('conStudioIn').get('cap').setValidators(
        [Validators.pattern('[0-9]{5}'),requiredIfNotEmpty(formTecnico.get('conStudioIn').get('comune'))]);
        */
    this.mainForm.addControl("tecnicoIncaricato",formTecnico);
  }

  get tecnico() {
    return this.mainForm.get("tecnicoIncaricato") as FormGroup;
  }

  public validazioneTabella(event: Array<any>): void {
    let documento: any = event[0];
    if (documento.id) {
      this.mainForm.get("tecnicoIncaricato").get("esisteDocumento").setValue(true);
    }
    else {
      this.mainForm.get("tecnicoIncaricato").get("esisteDocumento").setValue(false);
    }
  }

  public onUploadIniPec(files: File[]){
    this.loadingService.emitLoading(true);
    if(files.length > 0){
      let fileToUpload = files[0];
      this.ngxCsvParser.parse(files[0], { header: true, delimiter: ';' })
      .pipe().subscribe({
        next: (result: any[]): void => {
          if(result.length > 0){
            let item : any = result[0];
            this.tecnico.controls['nome'            ].setValue(item[1]);
            this.tecnico.controls['cognome'         ].setValue(item[2]);
            this.tecnico.controls['codiceFiscale'   ].setValue(item[5]);
            this.tecnico.controls['pec'             ].setValue(item[0]);
            this.tecnico.controls['iscritoAllOrdine'  ].setValue(item[3]);
            this.tecnico.controls['di'].setValue(item[3]);
            this.tecnico.controls['n' ].setValue(item[4]);
            this.dialogService.showDialog(true,
              'generic.operazioneOk',
               'generic.info',
               DialogButtons.CHIUDI,
               (buttonID: string): void =>{},
                DialogType.SUCCESS,
               null
               );
          }else {
            this.dialogService.showDialog(true,
              'TECHNICIAN_IN_CHARGE.INI-PEC.PARSER-ERROR',
               'generic.warning',
               DialogButtons.CHIUDI,
               (buttonID: string): void =>{},
                DialogType.WARNING,
               null
               );
            }
        },
        error: (error: NgxCSVParserError): void => {
          this.loadingService.emitLoading(false);

          this.dialogService.showDialog(true,
                                 'TECHNICIAN_IN_CHARGE.INI-PEC.PARSER-ERROR',
                                  'generic.warning',
                                  DialogButtons.CHIUDI,
                                  (buttonID: string): void =>{},
                                   DialogType.WARNING,
                                  null
                                  );
        }
      });
    }    
    this.loadingService.emitLoading(false);
    this.fileUpload.clearUpload();
  }

  public goIniPec():void{
    window.open("https://www.inipec.gov.it/ordini-e-collegi/cerca-pec-ordini"
               ,'INIPEC'
               ,'status=1,toolbar=no,scrollbars=yes,resizable=yes' 
               );
  }

}
