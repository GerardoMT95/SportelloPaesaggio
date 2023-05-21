import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { LocalizazioneInfo } from '../../models/models';



@Component({
  selector: 'app-tabella-particelle',
  templateUrl: './tabella-particelle.component.html',
  styleUrls: ['./tabella-particelle.component.css']
})
export class TabellaParticelleComponent implements OnInit {
  @Input() formComune:FormGroup;
  @Input() codiceCatastaleComune:string;
  @Input() disable:boolean;
  @Input() tipoSelezioneLocalizzazione:string;
  @Output() deleteParticella=new EventEmitter<{formComune:FormGroup,indexParticella:number}>();
  @Output() deleteAllParticelle=new EventEmitter<FormGroup>();
  @Output() zoomParticella=new EventEmitter<{formComune:FormGroup,indexParticella:number}>();
  constructor() { }

  ngOnInit() {
  }
  emitDelete(i:number){
    this.deleteParticella.emit({formComune:this.formComune,indexParticella:i});
  }
  emitZoomMap(i:number){
    this.zoomParticella.emit({formComune:this.formComune,indexParticella:i});
  }

  emitDeleteAll(){
    this.deleteAllParticelle.emit(this.formComune);
  }

  particelle():LocalizazioneInfo[]{
    return this.formComune.get('particelle').value;
  }

  decodificaSezione(sezione:string){
    let sezioneDecodificata=sezione;
    //traduco in label l'eventuale sezione.
    return sezioneDecodificata;
  }
}
