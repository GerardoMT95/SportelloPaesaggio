import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReportInsert, ReportSelectItem, ReportSelectItemEstensione } from './../../models/report.model';
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ReportServiceService } from '../../services/report-service.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-report-new',
  templateUrl: './report-new.component.html',
  styleUrls: ['./report-new.component.css']
})
export class ReportNewComponent implements OnInit {

  @Input("showNew")
  public showNew:boolean;
  @Input("reports")
  public reports: ReportSelectItemEstensione[];
  @Input("procedimenti")
  public procedimenti: ReportSelectItem[];
  @Input("formati")
  public formati: ReportSelectItem[];
  @Output("addReport")
  public addReportEmitter:EventEmitter<ReportInsert> = new EventEmitter<ReportInsert>();
  @Output("showNewEvent")
  public showNewEmitter:EventEmitter<string> = new EventEmitter<string>();
  @Output("hideNewEvent")
  public hideNewEventEmitter:EventEmitter<string> = new EventEmitter<string>();
  
  public validation:boolean;

  public reportSelect: ReportSelectItem[];
  public form:FormGroup;
  public caricato:boolean = false;

  constructor(private fb:FormBuilder,
              private reportService:ReportServiceService
             ,private loading: LoadingService
             ,private translate:TranslateService
             ,private router:Router
             ,private allegatoService:AllegatoService) { }

  ngOnInit() {
    let obsReport$ = this.reportService.listReport();
    let obsProcedimenti$ = this.reportService.listProcedimenti();

    combineLatest([obsReport$
                  ,obsProcedimenti$
                ])
    .subscribe(([respReport
                ,respProcedimenti
              ]) => {
      if (respReport.codiceEsito === CONST.OK
       && respProcedimenti.codiceEsito === CONST.OK
      ){
        this.procedimenti = respProcedimenti.payload;
        this.reports = respReport.payload;
        this.reportSelect = this.reports.map(x => x.labelValue); 
        console.log(this.reportSelect);
      }
      this.loading.emitLoading(false);
    });
    this.form = this.fb.group({tipo :[null, [Validators.required]]
                              ,formato:[null, [Validators.required]]
                              ,procedimenti:[null, null]
                              ,dataDa:[null, null]
                              ,dataA:[null, null]
                              });
    this.caricato = true;
  }

  public nuovoReport():void{
    this.form.reset();
    this.validation = false;
    this.showNewEmitter.emit("");
  }

  public nuovo():void{
    this.validation = true;
    if(this.form.valid){
      let procedimenti:ReportSelectItem[] = this.form.controls.procedimenti.value;
      let procedimentiValue:string[] = [];
      if(procedimenti){
        procedimentiValue = procedimenti.map(procedimento => procedimento.value);
      }
      let insert : ReportInsert = {dataFrom:this.form.controls.dataDa.value
                                  ,dataTo:this.form.controls.dataA.value
                                  ,formato:this.form.controls.formato.value.value
                                  ,tipo:this.form.controls.tipo.value.value
                                  ,tipoProcedimento:procedimentiValue
                                  };
      this.addReportEmitter.emit(insert);
    }
    
  }
  public chiudi():void{
    this.validation = false;
    this.hideNewEventEmitter.emit("");
  }

  updateFormati(event: any){
    this.formati = [];
    if(event.value != null){
      let report = this.reports.find(x => x.labelValue.value == event.value.value);
      if(report.formati[report.labelValue.value] != null && report.formati[report.labelValue.value].length > 0){
          if(report.formati[report.labelValue.value].find(x => x == "pdf") != null){
            this.formati.push({label:this.translate.instant("report.formato.PDF"), value:"pdf"});
          }
          if(report.formati[report.labelValue.value].find(x => x == "rtf") != null){
            this.formati.push({label:this.translate.instant("report.formato.RTF"), value:"rtf"});
          }
          if(report.formati[report.labelValue.value].find(x => x == "xls") != null){
            this.formati.push({label:this.translate.instant("report.formato.XLS"), value:"xls"});
          }
      }
    }
    
  }
}
