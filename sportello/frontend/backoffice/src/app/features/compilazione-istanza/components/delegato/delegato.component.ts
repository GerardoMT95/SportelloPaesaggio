import { PraticaDelegato } from './../../../../shared/models/models';
import { AfterContentInit, Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Subject } from 'rxjs';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo } from 'src/app/shared/models/models';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { IstanzaConst } from '../../constants/istanza-const';

@Component({
  selector: 'app-delegato',
  templateUrl: './delegato.component.html',
  styleUrls: ['./delegato.component.css']
})
export class DelegatoComponent implements OnInit, AfterContentInit 
{
  const = CONST;

  @Input("fascicolo") fascicolDett: Fascicolo;
  @Input("soloLettura") soloLettura: boolean=true;
  @Input("delegato") delegatoPratica: PraticaDelegato[];

  @Input() titolaritaInQualitaDiAltroTit: SelectOption[];
  @Input() tipoDocumentoOptions: SelectOption[];
  @Input() validation: boolean = true;
  @Input("disabled") disable = false;
  altroTitolareForm: FormGroup;
  altriTitolariFormArr: FormArray;
  @Input() mainForm: FormGroup;
  private unsubscribe$ = new Subject<void>();
  public id: string;
  delegatoFormArray: FormArray;

  documentTableHeaders = IstanzaConst.documentoReferenteCOnfigTable;
  tableData:any[]=[];
  tableSollevamento:any[]=[];

  constructor(private fb: FormBuilder
             ,private loadingService: LoadingService
             ,private translateService: TranslateService
             ,private allegatiService: AllegatoService
             ,private route: ActivatedRoute)
  {
    this.id = this.route.snapshot.paramMap.get('id');
  }
  
  
  ngOnInit() {
    this.buildForm();
  }

  ngAfterContentInit():void{
  }
  
  ngOnDestroy(): void {
    this.mainForm.removeControl('delegatoPratica');
  }

  buildForm() {
    this.delegatoFormArray = this.fb.array([]);
    this.mainForm.addControl('delegatoPratica', this.delegatoFormArray);
    if (this.delegatoPratica && this.delegatoPratica.length > 0) {
      this.delegatoPratica.forEach(
        delegato => {
          let formDelegato: FormGroup =  this.buildFormItem(delegato);
          this.addDelegaDocumentInfo(delegato);
          this.delegatoFormArray.push(formDelegato);
        }
      );
    }
    if(this.disable)
      this.delegatoFormArray.disable();
  }
  public buildFormItem(delegato: PraticaDelegato):FormGroup {
    let formDelegato = this.fb.group({cognome              : [delegato.cognome, Validators.required]
                                     ,nome                 : [delegato.nome,  Validators.required]
                                     ,codiceFiscale        : [delegato.codiceFiscale,  [Validators.required, Validators.pattern(CONST.regexCodFisc)]]
                                     ,sesso                : [delegato.sesso,  Validators.required ]
                                     ,dataNascita          : [(delegato.dataNascita ? new Date(delegato.dataNascita) : null ),  Validators.required]
                                     ,nazioneNascita       : [{value: delegato.idNazioneNascita, description: delegato.nazioneNascita},  Validators.required]
                                     ,provinciaNascita     : [{value: delegato.idProvinciaNascita, description: delegato.provinciaNascita}]
                                     ,comuneNascita        : [{value: delegato.idComuneNascita, description: delegato.comuneNascita}]
                                     ,comuneNascitaEstero  : [delegato.comuneNascitaEstero]
                                     ,nazioneResidenza     : [{value: delegato.idNazioneResidenza, description: delegato.nazioneResidenza},  Validators.required]
                                     ,provinciaResidenza   : [{value: delegato.idProvinciaResidenza, description: delegato.provinciaResidenza},  Validators.required]
                                     ,comuneResidenza      : [{value: delegato.idComuneResidenza, description: delegato.comuneResidenza}]
                                     ,comuneResidenzaEstero: [delegato.comuneResidenzaEstero]
                                     ,indirizzoResidenza   : [delegato.indirizzoResidenza,  null]
                                     ,civicoResidenza      : [delegato.civicoResidenza]
                                     ,capResidenza         : [delegato.capResidenza]
                                     ,mail                 : [delegato.mail]
                                     ,pec                  : [delegato.pec]
                                   });
    return formDelegato;
  }

  private getFormGroup(idx:number):FormGroup{
    return <FormGroup>this.delegatoFormArray.controls[idx];
  }
  
  public getFormBean():PraticaDelegato[]{
    let itemsN = this.delegatoFormArray.controls.length;
    let result : PraticaDelegato[] = [];
    for(let idx = 0 ; idx < itemsN ; idx++){
      result.push(this.getDto(idx));
    }
    return result;
  }
  private getDto(idx:number):PraticaDelegato{
    let formGroup : FormGroup = this.getFormGroup(idx);
    let result : PraticaDelegato = {nome                   :formGroup.controls.nome                 .value 
                                   ,cognome                :formGroup.controls.cognome              .value 
                                   ,codiceFiscale          :formGroup.controls.codiceFiscale        .value 
                                   ,sesso                  :formGroup.controls.sesso                .value 
                                   ,dataNascita            :formGroup.controls.dataNascita          .value 
                                   ,idNazioneNascita       :formGroup.controls.nazioneNascita       .value ? formGroup.controls.nazioneNascita       .value.value : null
                                   ,nazioneNascita         :formGroup.controls.nazioneNascita       .value ? formGroup.controls.nazioneNascita       .value.label : null  
                                   ,idComuneNascita        :formGroup.controls.comuneNascita        .value ? formGroup.controls.comuneNascita        .value.value : null
                                   ,comuneNascita          :formGroup.controls.comuneNascita        .value ? formGroup.controls.comuneNascita        .value.label : null
                                   ,comuneNascitaEstero    :formGroup.controls.comuneNascitaEstero  .value
                                   ,idNazioneResidenza     :formGroup.controls.nazioneResidenza     .value ? formGroup.controls.nazioneResidenza     .value.value : null
                                   ,nazioneResidenza       :formGroup.controls.nazioneResidenza     .value ? formGroup.controls.nazioneResidenza     .value.label : null
                                   ,idComuneResidenza      :formGroup.controls.comuneResidenza      .value ? formGroup.controls.comuneResidenza      .value.value : null
                                   ,comuneResidenza        :formGroup.controls.comuneResidenza      .value ? formGroup.controls.comuneResidenza      .value.label : null
                                   ,comuneResidenzaEstero  :formGroup.controls.comuneResidenzaEstero.value 
                                   ,indirizzoResidenza     :formGroup.controls.indirizzoResidenza   .value 
                                   ,civicoResidenza        :formGroup.controls.civicoResidenza      .value 
                                   ,capResidenza           :formGroup.controls.capResidenza         .value 
                                   ,mail                   :formGroup.controls.mail                 .value 
                                   ,pec                    :formGroup.controls.pec                  .value 
                                   };
    

    return result;
  }

  private addDelegaDocumentInfo(delegato:PraticaDelegato){
    if(delegato.allegatoDelega){
      this.tableData.push([delegato.allegatoDelega]);
    }else{
      this.tableData.push([{
        id: null,
        nome: null,
        descrizione: '',
        data: null,
       }]);
    }
    if(delegato.allegatoSollevamento){
      this.tableSollevamento.push([delegato.allegatoSollevamento]);
    }else{
      this.tableSollevamento.push([{
        id: null,
        nome: null,
        descrizione: '',
        data: null,
       }]);
    }
  }

  scaricaDocumento(idAndName) {
    this.loadingService.emitLoading(true);
    this.allegatiService.downloadAllegatoFascicolo(idAndName.id,this.fascicolDett.id, '/istruttoria/allegati/download.pjson')
        .toPromise()
        .then(data => {
            this.loadingService.emitLoading(false);
            var blob = new Blob([data.body], { type: data.body.type });
            this.allegatiService.downloadElemento(blob, idAndName.name);
        })
        .catch(error => {
            console.log('download error:', JSON.stringify(error));
            this.loadingService.emitLoading(false);
        });        
    }

}
