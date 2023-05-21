import { Component, Input, OnInit } from '@angular/core';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { AttivitaDaEspletareEnum, AttivitaDaEspletareEnumLabels, Fascicolo, TipoContenuto } from 'src/app/shared/models/models';
import { HttpDominioService } from '../../services/http-dominio.service';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-dettaglio-fascicolo',
  templateUrl: './dettaglio-fascicolo.component.html',
  styleUrls: ['./dettaglio-fascicolo.component.scss']
})
export class DettaglioFascicoloComponent implements OnInit
{

  @Input("typeProcedimento") typeProcedimento: SelectOption[]; 
  @Input("fascicolo") fascicolo: Fascicolo;
  loaded:boolean=false;
  public activeIds: string[] = ['dettaglio'];//default aperto
  
  public tipoProcedimento: string;
  public compilaDomandaVal = AttivitaDaEspletareEnum.COMPILA_DOMANDA;
  public attivitaDaEspletareLabel:string = "";

  constructor(private httpDominioService:HttpDominioService) { 
  }

  ngOnInit()
  {
    this.attivitaDaEspletareLabel=AttivitaDaEspletareEnumLabels.get(this.fascicolo.attivitaDaEspletare);
    let idx = -1;
    if (this.typeProcedimento)
      {
        idx = this.typeProcedimento.findIndex(el => el.value == this.fascicolo.tipoProcedimento);
        this.loaded=true;
      }
    if(idx>=0){
      this.tipoProcedimento=this.typeProcedimento[idx].description;
    } else{
      this.httpDominioService.getTipiProcedimento().subscribe(tipi=>{
        let idxNew=tipi.findIndex(el => el.value == this.fascicolo.tipoProcedimento);
        idxNew>=0?this.tipoProcedimento=tipi[idxNew].description:'errore';
        this.loaded=true;
      });
     
    }
    
  }


  getComuniIstanza()
  {
    let ret = '';
    let comuni: string[] = [];
    if (this.fascicolo.istanza && this.fascicolo.istanza.localizzazione && this.fascicolo.istanza.localizzazione.localizzazioneComuni)
      this.fascicolo.istanza.localizzazione.localizzazioneComuni.forEach(localInfo => comuni.push(localInfo.comune));
    return comuni ? comuni.join(' - ') : '';
  }

  getRichiedente(){
    if(this.fascicolo.istanza && this.fascicolo.istanza.richiedente.nome && this.fascicolo.istanza.richiedente.cognome){
        return (this.fascicolo.istanza.richiedente.nome + ' ' + this.fascicolo.istanza.richiedente.cognome);
    }
    return ' ';
  }

}
