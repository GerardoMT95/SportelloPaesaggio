import { SearchConfiguration } from 'src/app/shared/components/form-search/configuration/search.configuration';
import { CONST } from 'src/app/shared/constants';
import { ReportSearch } from './../../models/report.model';
import { Component, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormSearchComponent } from 'src/app/shared/components';
import { SearchFields } from 'src/app/shared/models/form-search.configurations.models';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-report-search',
  templateUrl: './report-search.component.html',
  styleUrls: ['./report-search.component.css']
})
export class ReportSearchComponent implements OnInit {

  // @ViewChild("formSearch", {static:false})
  public formSearch:FormGroup;

  @Output("search")
  public searchEmitter:EventEmitter<ReportSearch> = new EventEmitter<ReportSearch>();

  public configuration: SearchFields[];

  public index:number = 0;

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    // this.configuration = SearchConfiguration.getConfigurazioneReport();
    this.formSearch = this.fb.group({
        dataDa:[null, null]
        ,dataA:[null, null]
      });
    this.search();
  }
  
  public search():void{       
    let bean : ReportSearch = {dataFrom: this.formSearch.controls.dataDa.value
                              ,dataTo: this.formSearch.controls.dataA.value
                              ,page:1
                              ,limit:CONST.DEFAULT_PAGE_NUMBER
                              ,sortType:'desc'
                              ,sortBy:'dataCreazione'
                              };
    this.searchEmitter.emit(bean);
  }

  public reset(){
    this.formSearch.reset();
  }
}
