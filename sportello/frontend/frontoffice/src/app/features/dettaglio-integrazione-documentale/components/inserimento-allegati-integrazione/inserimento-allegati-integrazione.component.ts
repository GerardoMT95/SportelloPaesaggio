import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { Fascicolo } from 'src/app/shared/models/models';
import { DialogService } from './../../../../core/services/dialog.service';
import { FascicoloStore } from './../../../../core/services/fascicolo-store.service';
import { Allegati, TipoContenuto, Allegato } from './../../../../shared/models/models';
import { HttpDominioService } from './../../../../shared/services/http-dominio.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { switchMapTo } from 'rxjs/operators';

@Component({
  selector: 'app-inserimento-allegati-integrazione',
  templateUrl: './inserimento-allegati-integrazione.component.html',
  styleUrls: ['./inserimento-allegati-integrazione.component.scss']
})
export class InserimentoAllegatiIntegrazioneComponent extends AbstractInputPage implements OnInit 
{
  @Input("fascicolo") fascicolo: Fascicolo;
  @Input("idIntegrazione") idIntegrazione: number;
  @Input("allegati") allegati: Allegati;

  @Output("action") actionEmitter: EventEmitter<"INDIETRO"|"PROSEGUI"> = new EventEmitter();

  public activeIndex: number;
  public tipiContenuto: TipoContenuto[] = null;

  constructor(public fb: FormBuilder,
              public router: Router,
              private dominio: HttpDominioService,
              private loading: LoadingService,
              private dialog: DialogService,
              public route: ActivatedRoute,
              private fascicoloStore: FascicoloStore) 
  { 
    super(dialog, fb, router,route);
  }

  ngOnInit()
  {
    this.fascicoloStore.setBreadcrumbs([{ label: 'Integrazione' }, { label: 'Allegati' }]);
    this.mainForm = this.fb.group({});
    this.caricamentoTipi(this.fascicolo.tipoProcedimento);
  }

  public actionResolver(actionContainer: any): void
  {
    let allegati: Allegato[] = this.findList(actionContainer.type);
    switch(actionContainer.action)
    {
      case "UPLOAD":
        allegati.push(actionContainer.file);
        break;
      case "DELETE":
        let index = allegati.map(m => m.id).indexOf(actionContainer.allegatoId);
        if(index != -1)
          allegati.splice(index, 1);
    }
  }
  private findList(type: string): Allegato[]
  {
    let allegati: Allegato[];
    switch (type)
    {
      case "DOC_AMMINISTRATIVA_E":
        allegati = this.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati;
        break;
      case "DOC_AMMINISTRATIVA_D":
        allegati = this.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati;
        break;
      case "DOC_TECNICA":
        allegati = this.allegati.documentazioneTecnica.grigliaAllegatiCaricati;
        break;
    }
    return allegati;
  }

  public caricamentoTipi(tp: string): void
  {
    this.loading.emitLoading(true);
    this.dominio.getTipiContenuto(parseInt(tp))
                .toPromise()
                .then(ret => this.tipiContenuto = ret)
                .finally(() => this.loading.emitLoading(false));
  }

  public emitAction(event: "PROSEGUI"|"INDIETRO"): void { this.actionEmitter.emit(event); }
  public salva() { throw new Error("Method not implemented."); }
  public saveOperation() { throw new Error("Method not implemented."); }

  public get allegatiVuoti(): boolean
  {
    return !this.allegati || 
           (this.allegati.documentazioneAmministrativa.grigliaPagamentoAllegati.length === 0 &&  
           this.allegati.documentazioneAmministrativa.grigliaAllegatiCaricati.length === 0 &&
           this.allegati.documentazioneTecnica.grigliaAllegatiCaricati.length === 0);

  }


}
