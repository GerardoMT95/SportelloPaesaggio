import { UserService } from './../../../../shared/services/user.service';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';
import { GroupType } from './../../../../shared/models/models';
import { CONST } from 'src/app/shared/constants';
import { Fascicolo } from 'src/app/shared/models/models';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';

@Component({
  selector: 'app-fascicolo',
  templateUrl: './fascicolo.component.html',
  styleUrls: ['./fascicolo.component.scss']
})
export class FascicoloComponent implements OnInit
{
  @Input() form: FormGroup;
  @Input() entiDelegati: SelectOption[];

  private l: GroupType

  constructor(private shared: DataService,
              private user  : UserService) { }

  ngOnInit() 
  {
  }

  get tpMapping(): any[] { return CONST.mappingTipiProcedimento; }
  get groupsFakeList(): any[] { return [{ description: this.user.nomeGruppo, value: this.user.actualGroup }]};

}
