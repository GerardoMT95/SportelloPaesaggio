import { UserService } from 'src/app/shared/services/user.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DialogService } from 'primeng/primeng';
import { TableConfig } from 'src/app/core/models/header.model';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { SimpleChanges } from '@angular/core';
import { CorrispondenzaSearch, DettaglioCorrispondenzaDTO } from '../../../models/corrispondenza.model';
import { LoggedUser } from 'src/app/shared/components/model/logged-user';


@Component({
  selector: 'app-comunicazione-table',
  templateUrl: './comunicazione-table.component.html',
  styleUrls: ['./comunicazione-table.component.scss'],
  animations: [
    trigger('rowExpansionTrigger', [
      state(
        'void',
        style({
          transform: 'translateX(-10%)',
          opacity: 0
        })
      ),
      state(
        'active',
        style({
          transform: 'translateX(0)',
          opacity: 1
        })
      ),
      transition('* <=> *', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
    ])
  ]
})
export class ComunicazioneTableComponent implements OnInit {
  //@Input() meta: { [key: string]: SelectOption[] };
  @Input("totalItems") totalObject       : number;
  @Input("numeroOggettiRestituiti") numeroOggettiRestituiti       : number;
  @Input() tableData: DettaglioCorrispondenzaDTO[];
  @Input() tableHeader: TableConfig[];
  @Input() currentUser: LoggedUser;
  @Output() deleteChange: EventEmitter<any> = new EventEmitter<any>();
  @Output() navigateChange: EventEmitter<any> = new EventEmitter<any>();
  @Output("pagingOrOrderBy")  pageChange =new EventEmitter<CorrispondenzaSearch>();
  
  @Input() tableSearch : CorrispondenzaSearch;
  constructor(private dialogService: DialogService,private userService: UserService) {}

  ngOnInit() {
    console.log('tableHeader', this.tableHeader);
     console.log('tableData', this.tableData[0]);
  }
  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    if(changes.tableData)
    {
      console.log("table data: ", this.tableData);
    }
    
  }
  delete(codiceFascicolo: string) {
    this.deleteChange.emit(codiceFascicolo);
  }
  navigate(id: number, azione: string) {
    let container = {
      id: id,
      azione: azione,
    };
    this.navigateChange.emit(container);
  }
  get user(): LoggedUser {
    return this.userService.getUser();
  }

  public pagingChanges(event: any): void 
  {
    if (event.page) this.tableSearch.page = event.page;
    if (event.limit) this.tableSearch.limit = event.limit; 
    this.pageChange.emit(this.tableSearch); 
  }

}
