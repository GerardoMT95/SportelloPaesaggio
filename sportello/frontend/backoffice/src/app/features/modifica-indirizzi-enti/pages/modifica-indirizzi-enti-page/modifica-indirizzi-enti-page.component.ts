import { Component, OnInit } from '@angular/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { IndirizziEnti } from 'src/app/shared/models/models';
import { Router } from '@angular/router';
import { paths } from 'src/app/app-routing.module';

@Component({
  selector: 'app-modifica-indirizzi-enti-page',
  templateUrl: './modifica-indirizzi-enti-page.component.html',
  styleUrls: ['./modifica-indirizzi-enti-page.component.scss']
})
export class ModificaIndirizziEntiPageComponent implements OnInit {

  indirizziEntiTableHeaders: TableConfig[] = [];
  constructor(private router: Router) { }

  ngOnInit() {
     /* this.indirizziEntiStore.setBreadcrumbs([{label: 'Indirizzi Enti'}]); */
     this.loadIndirizziEnti();

     this.indirizziEntiTableHeaders = [
      {
        header: 'INDIRIZZI_ENTI.DENOMINAZIONE',
        field: 'denominazione'
      },
      {
        header: 'INDIRIZZI_ENTI.TIPOLOGIA',
        field: 'tipologia'
      },
      {
        header: 'INDIRIZZI_ENTI.PEC',
        field: 'pec'
      },
      {
        header: 'INDIRIZZI_ENTI.MAIL',
        field: 'mail'
      },
      {
        header: '',
        field: '',
        width: 6
      }
    ];
  }

  loadIndirizziEnti(searchQuery?: IndirizziEnti) {
    const query = searchQuery ? searchQuery : new IndirizziEnti();
    /* this.indirizziEntiStore.getIndirizziEnti(query); */
  }

  get indirizziEntiList() {
    return null//this.indirizziEntiStore.state.filtered;
  }

  navigateToDetails(event: string) {
    this.router.navigate([paths.entityAddressDetails(event)]);
  }

}
