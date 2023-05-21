import { OnInit, Component, Input, OnDestroy} from '@angular/core';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';
import { Router } from '@angular/router';
import { PromptDialog } from 'src/app/components/model/prompt-dialog.model';
import { ShowAlertService } from 'src/app/services/show-alert.service';
//import { PlanService } from '../../services/plan.service';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil, timestamp } from 'rxjs/operators';
import { AllegatiService } from 'src/app/services/allegati.service';
import { AutorizzazioniPaesaggisticheService } from 'src/app/services/autorizzazioni-paesaggistiche.service';
import { downloadFile } from '../functions/genericFunctions';
//import { Plan } from 'src/app/shared/models/plan.model';

@Component({
	selector: 'app-prompt-modal',
	templateUrl: './prompt-modal.component.html',
	styleUrls: ['./prompt-modal.component.scss']
})

/**
 * modale utilizzata al termine della trasmissione 
 */
export class PromptModalComponent implements OnInit, OnDestroy {
	displayAlert: boolean;
	displayPrompt: boolean;
	@Input() codiceFascicolo: string;
	@Input() idFascicolo: string;
	
	private unsubscribe$ = new Subject<void>();

	constructor(
		private promptDialogService: ShowPromptDialogService,
		private autPaesSvc:AutorizzazioniPaesaggisticheService,
		private router: Router,
	) {}

	ngOnInit() {
		this.promptDialogService.promptObject
		.pipe(takeUntil(this.unsubscribe$))
		.subscribe((response: PromptDialog) => {
			this.displayPrompt = response.displayPrompt;
		});
	}
	
	close() {
		this.displayPrompt = false;
		this.router.navigate(['private/fascicolo']);
	}

	ngOnDestroy() {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}
	
	downloadAttestatoRicezione(){
  		this.autPaesSvc.downloadRicevutaTrasmissione(this.idFascicolo.toString()).subscribe(
			  response => {
				if (response.ok) {
				  downloadFile(response.body, "ricevuta di trasmissione.pdf");
				}
			  }
			);
		
		
			/* this.autPaesSvc.downloadAnteprima(this.dettaglio.id.toString()).subscribe(result => {
			  if (result.ok) {
				downloadFile(result.body, "anteprima.pdf");
			  }
			}); */
		/*this.allegatiService.downloadAttestatoRicezione(this.codiceFascicolo).
		subscribe(res=>alert('todo download attestato ricezione'));*/
	}
}
