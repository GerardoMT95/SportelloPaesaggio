import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { statoLegendConfig } from 'src/app/shared/util/UtilProcessingStatus';
import { Counters, GroupRole } from './../../../../shared/models/models';

@Component({
  selector: 'app-legenda',
  templateUrl: './legenda.component.html',
  styleUrls: ['./legenda.component.scss']
})
export class LegendaComponent implements OnInit
{
  @Input("counters") data: Counters;
  legend = statoLegendConfig;
  havePermission: boolean;

  constructor(private userSvc: UserService) { }

  ngOnInit() { this.havePermission = this.isAdminUser(); }

  private isAdminUser(): boolean
  {
    let havePermission = false;
    const group = this.userSvc.actualGroup;
    const role = this.userSvc.role;
    if(group && role)
      havePermission = [GroupRole.A,GroupRole.D].includes(role);
    return havePermission;
  }

  get countersArray(): {label: string, value: number, class: string, orderIndex: number}[] 
  {
    let array: { label: string, value: number, class: string, orderIndex: number }[] = [];
    Object.keys(this.data).forEach(key =>
    { 
      let newObj = { value: this.data[key], ...this.translateKey(key) };
      array.push(newObj);
    });
    return array.sort((a,b) => a.orderIndex - b.orderIndex);
  }

  private translateKey(key: string): { label: string, class: string, orderIndex: number}
  {
    let obj: any = {};
    switch (key)
    {
      case "n_non_assegnata":
        obj = { label: "FASCIOLO_FIELDS.DA_ASSEGNARE", class: "square-daAssegnare", orderIndex: 1 };
        break;
      case "n_in_attesa_di_protocollazione":
        obj = { label: "FASCIOLO_FIELDS.IN_PROTOCOLLAZIONE", class: "square-protocollazione", orderIndex: 2 };
        break;
      case "n_in_preistruttoria":
        obj = { label: "FASCIOLO_FIELDS.IN_PRE-INVESTIGATION", class: "square-preistruttoria", orderIndex: 3 };
        break;
      case "n_in_lavorazione":
        obj = { label: "FASCIOLO_FIELDS.UNDER_PROCESING", class: "square-inLavorazione", orderIndex: 4 };
        break;
      case "n_in_trasmissione":
        obj = { label: "FASCIOLO_FIELDS.TO_BE_TRANSMITTED", class: "square-daTrasmettere", orderIndex: 5 };
        break;
      case "n_transmitted":
        obj = { label: "FASCIOLO_FIELDS.TRANSMITTED", class: "square-trasmessa", orderIndex: 6 };
        break;
      case "n_sospesa":
        obj = { label: "FASCIOLO_FIELDS.SOSPESA", class: "square-sospesa", orderIndex: 7 };
        break;
      case "n_archiviata":
        obj = { label: "FASCIOLO_FIELDS.ARCHIVIATA", class: "square-archiviata", orderIndex: 8 };
        break;
    }
    return obj;
  }

}
