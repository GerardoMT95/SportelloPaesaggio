import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/primeng';
import { DialogButtons, ButtonType, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { CONST } from 'src/app/shared/constants';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { ReportSelectItem, ReportSearch, ReportList, ReportInsert, ReportSelectItemEstensione } from './../../models/report.model';
import { ReportServiceService } from './../../services/report-service.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { combineLatest } from 'rxjs';
import { CustomDialogService } from 'src/app/core/services/dialog.service';


@Component({
  selector: 'app-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: ['./report-page.component.css']
})
export class ReportPageComponent implements OnInit, OnDestroy {


  public reports: ReportSelectItemEstensione[] = [];
  public procedimenti: ReportSelectItem[] = [];
  public formati: ReportSelectItem[];
  public loaded: boolean = false;
  public searchBean:ReportSearch;
  public list:ReportList[] = [];
  public count:number;
  public showNew:boolean=false;

  private interval : any;
  constructor(private reportService:ReportServiceService
             ,private breadcrumbService: BreadcrumbService
             ,private loading: LoadingService
             ,private dialog:CustomDialogService
             ,private translate:TranslateService
             ,private router:Router
             ,private allegatoService:AllegatoService
  ) { }

  ngOnInit() {
    this.loading.emitLoading(true);
    this.sendBreadcrumb();
    this.interval =  setInterval(()=>{this.loadList()}, 60000);
    this.loaded = true;
    this.loading.emitLoading(false);
  }

  ngOnDestroy(): void{
    if(this.interval)
      clearInterval(this.interval);
  }

  public search(event:ReportSearch):void{
    console.log(event);
    this.searchBean = event;
    this.searchBean.page = 1;
    if(!this.searchBean.limit){
      this.searchBean.limit = CONST.DEFAULT_PAGE_NUMBER;  
      this.searchBean.sortBy = "dataCreazione";  
      this.searchBean.sortType = "desc";  
    }
    this.loadList();
  }

  public paging(pagintionInfo: any): void {
    this.searchBean.page = pagintionInfo.page ? pagintionInfo.page : 1;
    this.searchBean.limit = pagintionInfo.limit ? pagintionInfo.limit : CONST.DEFAULT_PAGE_NUMBER;
    this.searchBean.sortBy = pagintionInfo.field ? pagintionInfo.field : "dataCreazione";
    this.searchBean.sortType = pagintionInfo.sort === 1 ? "asc" : "desc";
    this.loadList();
  }


  private loadList():void{
    this.loading.emitLoading(true);
    if(this.searchBean.dataTo != null){
      this.searchBean.dataTo.setHours(23, 59, 59, 999);
    }
    this.reportService.search(this.searchBean)
    .subscribe(response =>{
      if(response.codiceEsito == CONST.OK){
        this.list = response.payload.list;
        this.count = response.payload.count;
      }
      this.loading.emitLoading(false);
    });
  }

  public newReport(event: any):void{
    this.showNew = true;
  }
  public closeReport(event: any):void{
    this.showNew = false;
  }
  public addReport(event: ReportInsert):void{
    this.dialog.showDialog(true
                          ,this.translate.instant("report.dialog.confirm.content")
                          ,this.translate.instant("report.dialog.confirm.title"  )
                          ,DialogButtons.OK_CANCEL
                          ,(button) =>{if (button == ButtonType.OK_BUTTON){this.confirmAdd(event);}}
                          ,DialogType.INFORMATION
                          );
  }

  private confirmAdd(event: ReportInsert):void{
    this.loading.emitLoading(true);
    if(event.dataTo != null){
      event.dataTo.setHours(23, 59, 59, 999);
    }
    this.reportService.addReport(event)
    .subscribe(response =>{
      if(response && response.codiceEsito == CONST.OK){
        this.dialog.showDialog(true
                              ,this.translate.instant("report.dialog.success.content")
                              ,this.translate.instant("report.dialog.success.title"  )
                              ,DialogButtons.CHIUDI
                              ,(button) =>{if (button == ButtonType.CLOSE_BUTTON){this.showNew = false;}}
                              ,DialogType.SUCCESS
                              );
        this.loadList();                              
      }else{
        this.loading.emitLoading(false);
      }
    })
  }
    private sendBreadcrumb():void{
      let menus :MenuItem[] = [];
      this.translate.get("MENU_ITEMS.REPORTS")
        .subscribe(content=>{
          menus.push({label:content, routerLink: this.router.url})
            this.breadcrumbService.emitter.emit(menus);
        });
    }

    public download(event:ReportList):void{
      this.loading.emitLoading(true);
      this.reportService.downloadReport(event.id)
      .subscribe(response =>{
        var blob = new Blob([response.body], { type: response.body.type });
        this.allegatoService.downloadElemento(blob,event.fileName);
        this.loading.emitLoading(false);
      })
    }

}
