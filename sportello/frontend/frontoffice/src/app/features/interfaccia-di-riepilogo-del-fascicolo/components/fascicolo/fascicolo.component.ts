import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DialogService } from 'src/app/core/services/dialog.service';
import { FascicoloStore } from 'src/app/core/services/fascicolo-store.service';
import { ButtonType, DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { PagamentoDto } from 'src/app/shared/models/models';

@Component({
  selector: 'app-fascicolo',
  templateUrl: './fascicolo.component.html',
  styleUrls: ['./fascicolo.component.scss']
})
export class FascicoloComponent implements OnInit {

  @Input() form: FormGroup;
  @Input() typeProcedimento: SelectOption[];
  @Input() enteDelegato: SelectOption[];
  //@Input() metadata: any;
  //@Input() fascicolo: Fascicolo;
  @Input() disabled:boolean=false;
  //@Input() pagamentiRegistrati:{ OK?: number; KO?: number; INCORSO?: number; }=null; //non piu' usati
  @Input() pagamento:PagamentoDto; //non piu' usati
  @Input() currentUserOwner:boolean=false; //flag per capire se è l'owner della pratica è può quindi editarla
  @Input() showCodiceSegreto: boolean = false;
  isInSanatoriaEnabled:boolean=false;
  @Output() cambioEnte = new EventEmitter<any>();
  @Output() cambioTipoProcedimento = new EventEmitter<any>();
  
  constructor(private serviceDialog: DialogService,
    private fascicoloStore:FascicoloStore) { }

  private lastTipoProcedimento="";
  private lastEnteDelegato="";

  ngOnInit() {
    //console.log(' aaaaa', this.form)
    this.lastTipoProcedimento=this.form.get('tipoProcedimento').value;
    this.lastEnteDelegato=this.form.get('enteDelegato').value;
    /*if(this.pagamentiRegistrati && 
        (this.pagamentiRegistrati.INCORSO || this.pagamentiRegistrati.OK)){
      //se ha effettuato pagamenti non faccio cambiere piu' ente!!!
      this.form.get('enteDelegato').disable();
    }*/
    if(this.pagamento || !this.currentUserOwner){
      //se ha effettuato pagamenti non faccio cambiere piu' ente!!!
      //Aggiunto anche se non è l'owner della pratica (deve quindi vedere solo in lettura)
      this.form.get('enteDelegato').disable();
    }
    this.refreshIsInSanatoriaEnabled();
    this.form.get('tipoProcedimento').valueChanges.subscribe(el=>
      {
        this.refreshIsInSanatoriaEnabled();
        if(el==this.lastTipoProcedimento)return; //ritorno indietro
        this.serviceDialog.showDialog(
          true,
          'DETTAGLIO-FASCICOLO.CAMBIO_TIPO_ALERT',
          'generic.warning',
          DialogButtons.OK_CANCEL,
          (idButton:number)=>{
            if (idButton==ButtonType.CANCEL_BUTTON){
              this.form.get('tipoProcedimento').setValue(this.lastTipoProcedimento);
              //alert('rimesso a '+this.form.get('tipoProcedimento').value);
            }else if (idButton==ButtonType.OK_BUTTON){
              this.lastTipoProcedimento=this.form.get('tipoProcedimento').value;
              this.cambioTipoProcedimento.emit(true);
            }
          },
          DialogType.WARNING
        );
      }
      );
      this.form.get('enteDelegato').valueChanges.subscribe(el=>
        {
          if(el==this.lastEnteDelegato)return; //ritorno indietro
          this.serviceDialog.showDialog(
            true,
            'DETTAGLIO-FASCICOLO.CAMBIO_ENTE_ALERT',
            'ATTENZIONE',
            DialogButtons.OK_CANCEL,
            (idButton:number)=>{
              if (idButton==ButtonType.CANCEL_BUTTON){
                this.form.get('enteDelegato').setValue(this.lastEnteDelegato);
              }else if (idButton==ButtonType.OK_BUTTON){
                this.lastEnteDelegato=this.form.get('enteDelegato').value;
                this.cambioEnte.emit(true);
              }
            },
            DialogType.WARNING
          );
        }
        );
  }

  
  refreshIsInSanatoriaEnabled(){
    let enabled=false;
    let tipoProcedimento=this.form.get('tipoProcedimento').value;
    this.typeProcedimento.forEach(element => {
      if((element.value==tipoProcedimento || 
          element.value==tipoProcedimento.value) && 
          element.linked && element.linked=='isInSanatoria'){
            this.isInSanatoriaEnabled=true;
            enabled=true;
      }
    }); 
    if(!enabled){
      this.isInSanatoriaEnabled=false;
      this.form.get('isInSanatoria').setValue(false);
    }
  }

}
