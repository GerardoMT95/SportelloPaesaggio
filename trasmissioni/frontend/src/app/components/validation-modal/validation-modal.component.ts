import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { PromptDialog } from '../model/prompt-dialog.model';
import { DettaglioFascicolo } from '../model/fascicolo.models';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { AllegatiService } from 'src/app/services/allegati.service';
import { isNumber } from '@ng-bootstrap/ng-bootstrap/util/util';

export interface ValidationMessage{
  indexPannello:number;
  sezione:string;
  messaggi:string[];
}

@Component({
  selector: 'app-validation-modal',
  templateUrl: './validation-modal.component.html',
  styleUrls: ['./validation-modal.component.scss']
})

export class ValidationModalComponent implements OnInit, OnDestroy {
  displayPrompt: boolean;
  @Input() invalidFields: ValidationMessage[] = [];
  //@Input() dettaglioFascicolo:DettaglioFascicolo;

  private unsubscribe$ = new Subject<void>();

  constructor(private promptDialogService: ShowPromptDialogService,
    private autPaesSvc:AutorizzazioniPaesaggisticheService,
    private allegatiService:AllegatiService) { }

  ngOnInit() {
    this.promptDialogService.promptValidation
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(
      (response: PromptDialog) => {
        this.displayPrompt = response.displayPrompt;
      }
    );
  }

  close(){
    this.displayPrompt = false;
  }

  closeAndUpdatePanel(indexPannello:number){
    if(Number.isInteger(indexPannello) ){
      this.autPaesSvc.nextActiveIndex(indexPannello);
    }
    this.displayPrompt = false;
  }

  downloadAnteprima(){
    /*this.allegatiService.downloadAnteprima(this.dettaglioFascicolo.codice).subscribe(
      res=>alert('todo download anteprima ' + this.dettaglioFascicolo.codice)
    );*/
    

  }

  ngOnDestroy() {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

}
