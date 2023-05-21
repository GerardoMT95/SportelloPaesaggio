import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TableConfig } from './../../../../core/models/header.model';
import { GroupType, StoricoAssegnazioni } from './../../../../shared/models/models';
import { UserService } from './../../../../shared/services/user.service';
import { AssegnaFascicoloConfiguration } from './../../configuration/assegnazione-fascicolo.configuration';
@Component({
  selector: 'app-tabella-storico',
  templateUrl: './tabella-storico.component.html',
  styleUrls: ['./tabella-storico.component.scss']
})
export class TabellaStoricoComponent implements OnInit
{
  @Input("idPratica") idPratica: string;
  @Input("codicePratica") codiceFascicolo: string; 
  @Input("data") assegnazioni: StoricoAssegnazioni[];
  @Input("display") display: boolean;
  @Output("onClose") chiudi: EventEmitter<boolean>;

  public tableHeaders: TableConfig[];

  constructor(private userService: UserService)
  {
    this.chiudi = new EventEmitter<boolean>();
    this.tableHeaders = AssegnaFascicoloConfiguration.tableHeadersSA;
  }

  ngOnInit()
  {
    if (!this.isED)
    {
      this.tableHeaders.forEach((item, index, array) =>
      {
        if (item.field === "denominazioneRup")
          array.splice(index, 1);
      });
    }
  }

  close()
  {
    this.display = false;
    this.chiudi.emit(this.display);
  }

  get isED(): boolean { return this.userService.groupType === GroupType.EnteDelegato; }

}
