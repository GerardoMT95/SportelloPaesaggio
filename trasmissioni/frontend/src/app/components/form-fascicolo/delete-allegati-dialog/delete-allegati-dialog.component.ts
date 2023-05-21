import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PromptDialog } from '../../model/prompt-dialog.model';
import { ShowPromptDialogService } from 'src/app/services/show-prompt-dialog.service';

@Component({
  selector: 'app-delete-allegati-dialog',
  templateUrl: './delete-allegati-dialog.component.html',
  styleUrls: ['./delete-allegati-dialog.component.scss']
})

export class DeleteAllegatiDialogComponent implements OnInit {

  @Input() sezione:string;
  displayPrompt: boolean;
  private unsubscribe$ = new Subject<void>();
  @Output() emitPlanAllegatiDeletion: EventEmitter<boolean> = new EventEmitter<boolean>();
  constructor(private promptDialogService: ShowPromptDialogService) { }

  ngOnInit() {
    this.promptDialogService.promptDeleteAllegatiObject[this.sezione]
		.pipe(takeUntil(this.unsubscribe$))
		.subscribe((response: PromptDialog) => {
			this.displayPrompt = response.displayPrompt;
		});
  }

  confirm(){
    this.emitPlanAllegatiDeletion.emit(true);
  }

  close(){
    this.displayPrompt = false;
  }

  ngOnDestroy() {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

}
