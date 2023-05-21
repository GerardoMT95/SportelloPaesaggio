import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { DialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { HttpDominioService } from '../../services/http-dominio.service';
import { dittaCf } from '../../validators/customValidator';
import { IpaEnte } from './../../models/models';

@Component({
  selector: 'app-ente-ditta-dati',
  templateUrl: './ente-ditta-dati.component.html',
  styleUrls: ['./ente-ditta-dati.component.css']
})
export class EnteDittaDatiComponent implements OnInit,OnChanges {

  @Input() nomeEnte: string;
  @Input() formGroupSet: FormGroup;
  //nomi dei formControl all'interno del formgroup
  @Input() nelCaso: string;
  @Input() inQualitaDi: string;
  @Input() descAltroDitta: string;
  @Input() societa: string;
  @Input() partitaIva: string;
  @Input() societaCodiceFiscale: string;
  @Input() validation: boolean;

  @Input() codiceFascicolo: string;
  @Input() cIpa: string;
  @Input() cUO: string = "cuo";
  @Input() tipoAzienda: string;
  @Input() tipoAziendaOptions: SelectOption[];
  public tipoAziendaPrivato :boolean = false;
  public tipoAziendaPublic :boolean = false;
  public tipi_ditta$: Observable<any[]>;

  public entiList: Array<IpaEnte> = [];

  @Input() disable: boolean = false; //eventuale disabilitazione dall'esterno
  constructor(
    private httpService: HttpDominioService,
    private loading: LoadingService,
    private dialog: DialogService
  ) { }

  ngOnInit() {
    this.tipi_ditta$ = this.httpService.getDominio('ruoloDitta');
    this.activeExtraField();
    this.activeAltroDitta(true);
    this.changeTipoAzienda(null);
    this.formGroupSet.addControl('societa_hidden', new FormControl({value: { nome: this.nomeEnte },disabled:this.disable}));
  }

  ngOnChanges(changes: SimpleChanges): void 
  {
    if (changes.validation)
      this.activeExtraField();
    if (changes.nomeEnte && this.formGroupSet && this.formGroupSet.controls.societa_hidden)
      this.formGroupSet.controls.societa_hidden.setValue({ nome: this.nomeEnte });
    console.log("disabled {} changes{} form{}",this.disable,changes,this.formGroupSet)
  }

  public activeExtraField(): void {
    if (!this.disable) {
      if (this.formGroupSet.get(this.nelCaso).value) {
        this.formGroupSet.get(this.inQualitaDi).enable();
        this.formGroupSet.get(this.inQualitaDi).setValidators([Validators.required]);
        this.formGroupSet.get(this.inQualitaDi).updateValueAndValidity();
        if(this.formGroupSet.controls.societa_hidden)
          this.formGroupSet.controls.societa_hidden.enable();
        //this.formGroupSet.get(this.societa).disable();
        // this.formGroupSet.get(this.societa).setValidators([Validators.required]);
        this.formGroupSet.get(this.societa).updateValueAndValidity();
        this.formGroupSet.controls.societaCodiceFiscale.disable();
        this.formGroupSet.get(this.societaCodiceFiscale).setValidators([Validators.required,dittaCf()]);
        this.formGroupSet.get(this.societaCodiceFiscale).updateValueAndValidity();
        this.formGroupSet.get(this.partitaIva).disable();
        this.formGroupSet.get(this.partitaIva).clearValidators();
        this.formGroupSet.get(this.partitaIva).updateValueAndValidity();
        this.formGroupSet.get(this.tipoAzienda).enable();
        this.formGroupSet.get(this.cIpa).disable();
        this.formGroupSet.get(this.cIpa).clearValidators();
        this.formGroupSet.get(this.cIpa).updateValueAndValidity();
        this.formGroupSet.get(this.cUO).disable();
      } else {
        this.formGroupSet.get(this.inQualitaDi).disable();
        this.formGroupSet.get(this.inQualitaDi).setValue('');
        this.formGroupSet.get(this.inQualitaDi).clearValidators()
        this.formGroupSet.get(this.inQualitaDi).updateValueAndValidity();
        if(this.formGroupSet.controls.societa_hidden)
          this.formGroupSet.controls.societa_hidden.disable();
        this.formGroupSet.get(this.societa).disable();
        this.formGroupSet.get(this.societa).setValue('');
        this.formGroupSet.get(this.societa).clearValidators()
        this.formGroupSet.get(this.societa).updateValueAndValidity();
        this.formGroupSet.get(this.societaCodiceFiscale).disable();
        this.formGroupSet.get(this.societaCodiceFiscale).setValue('');
        this.formGroupSet.get(this.societaCodiceFiscale).clearValidators()
        this.formGroupSet.get(this.societaCodiceFiscale).updateValueAndValidity();
        this.formGroupSet.get(this.partitaIva).disable();
        this.formGroupSet.get(this.partitaIva).setValue('');
        this.formGroupSet.get(this.partitaIva).clearValidators()
        this.formGroupSet.get(this.partitaIva).updateValueAndValidity();
        this.formGroupSet.get(this.tipoAzienda).disable();
        this.formGroupSet.get(this.tipoAzienda).setValue('');
        this.formGroupSet.get(this.cIpa).setValue('');
        this.formGroupSet.get(this.cIpa).disable();
        this.formGroupSet.get(this.cIpa).clearValidators();
        this.formGroupSet.get(this.cIpa).updateValueAndValidity();
        this.formGroupSet.get(this.cUO).disable();
        this.activeAltroDitta();
      }
    }
  }

  activeAltroDitta(firstTime?:boolean): void {
    if (!this.disable) {
      if(!firstTime){
        this.formGroupSet.get(this.descAltroDitta).setValue('');
        this.formGroupSet.updateValueAndValidity();
      }
      if (this.formGroupSet.get(this.inQualitaDi).value == 3) {
        this.formGroupSet.get(this.descAltroDitta).enable();
        this.formGroupSet.get(this.descAltroDitta).setValidators([Validators.required]);
        this.formGroupSet.get(this.descAltroDitta).updateValueAndValidity();
      }
      else {
        this.formGroupSet.get(this.descAltroDitta).disable();
        this.formGroupSet.get(this.descAltroDitta).clearValidators()
        this.formGroupSet.get(this.descAltroDitta).updateValueAndValidity();
      }
    }
  }

  public changeTipoAzienda(event:any): void {
    let idTipoAzienda : string = this.formGroupSet.get(this.tipoAzienda).value;
    if(idTipoAzienda){
      for(let tipoAziendaOption of this.tipoAziendaOptions){
        if(tipoAziendaOption.privato && tipoAziendaOption.value == idTipoAzienda){
          this.tipoAziendaPrivato = true;
          this.tipoAziendaPublic = false;
          this.formGroupSet.controls.societaCodiceFiscale.enable();
          this.formGroupSet.controls.societa.disable();
          return ;
        }
        if(!tipoAziendaOption.privato && tipoAziendaOption.value == idTipoAzienda){
          this.tipoAziendaPrivato = false;
          this.tipoAziendaPublic = true;
          this.formGroupSet.controls.societaCodiceFiscale.disable();
          this.formGroupSet.controls.societa.enable();
          return ;
        }
      }
    }
    this.tipoAziendaPrivato = false;
    this.tipoAziendaPublic = false;
  }

  public pulisci()
  {
    this.formGroupSet.controls.societaCodiceFiscale.setValue('');
    this.formGroupSet.controls.societa.setValue('');
    if(this.formGroupSet.controls.societa_hidden){
      this.formGroupSet.controls.societa_hidden.setValue('');
    }
    
    this.formGroupSet.controls.partitaIva.setValue('');
    this.formGroupSet.controls.codiceIpa.setValue('');
    this.formGroupSet.controls.cUo.setValue('');
  }

  public pulisciPrivato()
  {
    let v = this.formGroupSet.controls.societaCodiceFiscale.value;
    if(v != null && v.trim() == '')
      this.pulisci();
  }

  public calcolaAzienda():void{
    let codiceFiscale : string = this.formGroupSet.get(this.societaCodiceFiscale).value;
    if(codiceFiscale && codiceFiscale.length){
      this.loading.emitLoading(true);
      this.httpService.recuperaImpresa(this.codiceFascicolo, this.formGroupSet.get('id').value, codiceFiscale)
      .subscribe(response =>{
        if(CONST.OK === response.codiceEsito){
          if(response.payload){
            this.formGroupSet.get(this.partitaIva).setValue(response.payload.partitaIva);
            this.formGroupSet.get(this.societa).setValue(response.payload.ragioneSociale);
          }else{
            this.dialog.showDialog(true
                                  ,'REQUESTER_TAB.AZIENDA_NOT_FOUND'
                                  ,'generic.warning'
                                  ,DialogButtons.CHIUDI
                                  ,(buttonID: string): void => {}
                                  ,DialogType.WARNING
                                  ,null
                                  );
          }
        }
        this.loading.emitLoading(false);
      })
    }
  }
  public calcolaEnte():void{
    let codiceFiscale : string = this.formGroupSet.get(this.societaCodiceFiscale).value;
    if(codiceFiscale && codiceFiscale.length){
      this.loading.emitLoading(true);
      this.httpService.recuperaEnte(this.codiceFascicolo, this.formGroupSet.get('id').value, codiceFiscale)
      .subscribe(response =>{
        if(CONST.OK === response.codiceEsito){
          if(response.payload){
            this.formGroupSet.get(this.cIpa).setValue(response.payload.codiceIpa);
            this.formGroupSet.get(this.societa).setValue(response.payload.nome);
          }else{
            this.dialog.showDialog(true
                                  ,'REQUESTER_TAB.ENTE_NOT_FOUND'
                                  ,'generic.warning'
                                  ,DialogButtons.CHIUDI
                                  ,(buttonID: string): void => {}
                                  ,DialogType.WARNING
                                  ,null
                                  );
          }
        }
        this.loading.emitLoading(false);
      })
    }
  }

  public searchEnte(arg: any): void
  {
    this.loading.emitLoading(true);
    this.httpService.autocompleteIpaEnte(arg.query).subscribe(response =>
    {
      this.entiList = [];
      if(response.codiceEsito == CONST.OK)
        this.entiList = response.payload;
      this.loading.emitLoading(false);
    });
  }

  public selezionaEnte(arg: IpaEnte): void
  {
    this.formGroupSet.controls.societaCodiceFiscale.setValue(arg.codiceFiscale);
    this.formGroupSet.controls.codiceIpa.setValue(arg.codiceIpa);
    this.formGroupSet.controls.cUo.setValue(arg.codiceUo);
    this.formGroupSet.controls.societa.setValue(arg.nome);
  }

  public getEnteLabelPubblico(): string
  {
    let valore: any = this.formGroupSet.get(this.societa).value;
    if (valore)
      return valore.nome;
    return "";
  }
}