import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AdminEnteSezioniConst, AdminSezioniConst, SezioneType } from '../../../funzionalita-amministratore-applicazione/configurations/AdminSezioniConst';

@Component({
  selector: 'app-configurazione-sezioni',
  templateUrl: './configurazione-sezioni.component.html',
  styleUrls: ['./configurazione-sezioni.component.scss']
})
export class ConfigurazioneSezioniComponent implements OnInit {

  @Output("select") select: EventEmitter<SezioneType> = new EventEmitter();
  public sezioni = null;

  constructor(private router: Router) {
    this.sezioni = this.router.url.includes('admin-app') ? AdminSezioniConst.sezioniConfigurazioneTipologia : AdminEnteSezioniConst.sezioniConfigurazioneTipologia
  }
  
  ngOnInit() {}
  
  public navigate(value: SezioneType): void { this.select.emit(value); }

}
