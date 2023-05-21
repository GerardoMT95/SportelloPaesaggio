import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subject } from 'rxjs';
import { AllegatiService } from 'src/app/services/allegati.service';
import { CONST } from 'src/shared/constants';
import { TableHeader } from 'src/shared/models/table-header';
import { checkFileSizeTypeFn } from '../../functions/genericFunctions';
import { FascicoloFormConfig } from '../form-plan-config';
import { InformazioniDTO } from './../../model/entity/info.models';

@Component({
  selector: 'app-form-allegati',
  templateUrl: './form-allegati.component.html',
  styleUrls: ['./form-allegati.component.css']
})
export class FormAllegatiComponent implements OnInit {
  @Input() fascicoloForm: FormGroup;
  @Input() dettaglioFascicolo:InformazioniDTO;
  @Input() submitted: boolean;
  @Input() disable: boolean; //disabilita tutti i control
  @Input() codiceFascicolo: string;
  @Input() id: string;//id fascicolo

  public checkFileTypeSize=checkFileSizeTypeFn(null,CONST.MAX_50MB);  
  
  tableHeadersAttachment:TableHeader[];

  private unsubscribe$ = new Subject<void>();
  constructor(private allegatiService:AllegatiService) { }

  ngOnInit() {
    this.tableHeadersAttachment=FascicoloFormConfig.allegatiUploadTableHeaders();
  }


  ngOnDestroy() 
  {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

}
