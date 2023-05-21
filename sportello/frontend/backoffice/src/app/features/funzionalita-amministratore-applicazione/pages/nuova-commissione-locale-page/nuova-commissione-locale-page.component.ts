import { Component, OnInit } from '@angular/core';
import { combineLatest } from 'rxjs';
import { copyOf } from 'src/app/core/functions/generic.utils';
import { PlainTypeStringId } from 'src/app/shared/components/model/logged-user';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { IButton } from './../../../../core/models/dialog.model';
import { TableConfig } from './../../../../core/models/header.model';
import { CONST } from './../../../../shared/constants';
import { SearchFields } from './../../../../shared/models/form-search.configurations.models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { CommissioneLocaleConst } from './../../configurations/commissione-locale-cong';
import { CLSearch, CommissioneLocaleOrganizzazione, EntiCommissioni } from './../../models/admin-functions.models';

@Component({
  selector: 'app-nuova-commissione-locale-page',
  templateUrl: './nuova-commissione-locale-page.component.html',
  styleUrls: ['./nuova-commissione-locale-page.component.scss']
})
export class NuovaCommissioneLocalePageComponent implements OnInit
{
  public utentiCommissione: Array<PlainTypeStringId> = [];
  public entiCommissione: Array<PlainTypeStringId> = [];
  public commissioneScelta: CommissioneLocaleOrganizzazione = null;
  public showDetails: boolean = false;
  public displayMembers: boolean = false;
  public displayError: boolean = false;
  public usersContainer: any[] = [];

  public commissioni: Array<CommissioneLocaleOrganizzazione> = [];
  public totalRecords: number = 0;
  public filters: CLSearch = new CLSearch();
  public searchConfig: SearchFields[] = copyOf(CommissioneLocaleConst.searchConfiguration);
  public userTableHeader: TableConfig[] = CommissioneLocaleConst.headerUsersTable;
  public comTableHeader: TableConfig[] = CommissioneLocaleConst.tableHeader;
  public membersButton: IButton = CommissioneLocaleConst.customButton;

  public errorsEnti: Array<EntiCommissioni> = [];
  public errorsTableHeader: TableConfig[] = CommissioneLocaleConst.headerErrorsTable;

  constructor(private service: AdminFunctionsService,
            private loading: LoadingService) { }

  ngOnInit()
  {
    this.searchAll();
  }

  private searchAll(): void
  {
    this.loading.emitLoading(true);
    let _this = this;
    let obsPopola$ = _this.service.callPopulate();
    let obsCerca$ = _this.service.callSearchCL(_this.filters);
    combineLatest([obsPopola$, obsCerca$]).subscribe(([respPop, respSearch]) =>
    {
      if (respPop.codiceEsito === CONST.OK && respSearch.codiceEsito === CONST.OK)
      {
        _this.utentiCommissione = respPop.payload.users;
        _this.entiCommissione = respPop.payload.enti;
        _this.commissioni = respSearch.payload.list;
        _this.totalRecords = respSearch.payload.count;
        _this.searchConfig[2].extra.options = _this.utentiCommissione;
      }
      this.loading.emitLoading(false);
    });
  }

  public tableChange(obj: any): void
  {
    if (obj.page) this.filters.pagina = obj.page;
    if (obj.limit) this.filters.limite = obj.limit;
    this.search();
  }

  public search(f?: CLSearch): void
  {
    if (f) this.filters = { ...this.filters, ...f };
    if (this.filters.username && (this.filters.username as any).value)
      this.filters.username = (this.filters.username as any).value;
    this.loading.emitLoading(true);
    this.service.callSearchCL(this.filters).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.commissioni = response.payload.list;
        this.totalRecords = response.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  public resetFilters(): void
  {
    delete this.filters;
    this.filters = new CLSearch();
  }

  public openEdit(event: CommissioneLocaleOrganizzazione): void
  {
    if (this.showMembers)
    {
      if (event)
      {
        this.loading.emitLoading(true);
        this.service.callFindCL(event.id).subscribe(response =>
        {
          if (response.codiceEsito === CONST.OK)
          {
            this.commissioneScelta = response.payload;
            this.showDetails = true;
          }
          this.loading.emitLoading(false);
        });
      }
      else
        this.showDetails = true
    }

  }

  public chiudiDetails(): void
  {
    this.commissioneScelta = null;
    this.showDetails = false;
  }

  public salvaDetails(event: CommissioneLocaleOrganizzazione): void
  {
    this.loading.emitLoading(true);
    let callSaveCommisione$ = !event.id ? this.service.callSaveCL(event) : this.service.callUpdateCL(event);
    callSaveCommisione$.subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK && response.payload)
      {
        this.searchAll();
        this.commissioneScelta = response.payload;
        this.loading.emitLoading(false);
      }
      else if (response.descrizioneEsito === "INVALID_DATE")
      {
        this.checkDate(event);
      }
      else
        this.loading.emitLoading(false);
    });
  }

  private checkDate(cl: CommissioneLocaleOrganizzazione): void
  {
    this.loading.emitLoading(true);
    this.service.callCheckDate(cl.id, cl.organizzazioniAssociate, cl.dataCreazione, cl.dataTermine).subscribe(checkResponse =>
    {
      if (checkResponse.codiceEsito === CONST.OK)
      {
        this.errorsEnti = checkResponse.payload;
        this.displayError = true;
        console.error("Errore: ci sono già delle commissioni associate: ", checkResponse.payload);
        this.loading.emitLoading(false);
      }
      else this.loading.emitLoading(false);
    });
  }

  public showMembers(event: CommissioneLocaleOrganizzazione): void
  {
    this.loading.emitLoading(true);
    this.service.callFindUsersCL(event.id).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        this.commissioneScelta = event;
        this.usersContainer = response.payload;
        this.displayMembers = true;
      }
      this.loading.emitLoading(false);
    });
  }

  public chiudiDialog(): void
  {
    if (this.displayMembers)
    {
      this.commissioneScelta = null;
      this.usersContainer = [];
      this.displayMembers = false;
    }
    else
    {
      this.displayError = false;
      this.errorsEnti = [];
    }
  }

  public aggiungiUtente(user: PlainTypeStringId): void
  {
    this.loading.emitLoading(true);
    let _this = this;
    this.service.callAddUser(user.value, this.commissioneScelta.id).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        if (!_this.commissioneScelta.users)
          _this.commissioneScelta.users = [];
        _this.commissioneScelta.users.push(user);
      }
      _this.loading.emitLoading(false);
    });
  }

  public eliminaUtente(username: string): void
  {
    this.loading.emitLoading(true);
    this.service.callRemoveUser(username, this.commissioneScelta.id).subscribe(response =>
    {
      if (response.codiceEsito === CONST.OK)
      {
        let index = this.commissioneScelta.users.map(m => m.value).indexOf(username);
        if (index != -1)
          this.commissioneScelta.users.splice(index, 1);
      }
      this.loading.emitLoading(false);
    });
  }

  get display(): boolean { return this.displayMembers || this.displayError; }
  set display(d: boolean){ this.displayError = false; this.displayMembers = false; }
}
