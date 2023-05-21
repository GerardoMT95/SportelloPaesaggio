import { HttpResponse } from '@angular/common/http';
import { SelectItem } from 'primeng/primeng';
import { CONST } from 'src/app/shared/constants';
import { BaseResponse } from 'src/app/shared/components/model/base-response';
import { Observable } from 'rxjs';
import { ReportSearch, ReportList, ReportSelectItem, ReportInsert, ReportSelectItemEstensione } from './../models/report.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaginatedList } from 'src/app/core/models/basic.models';

@Injectable({
  providedIn: 'root'
})
export class ReportServiceService {

  private baseUrl: string = CONST.WEB_RESOURCE_BASE_URL + "/report";
  private SEARCH_REPORT: string = this.baseUrl + "/search-report";
  private INSERT_REPORT: string = this.baseUrl + "/insert";
  private SELECT_TIPOLOGIE_PROCEDIMENTI: string = this.baseUrl + "/tipologie-procedimenti";
  private SELECT_TIPOLOGIE: string = this.baseUrl + "/select-tipologie";
  private DOWNLOAD_REPORT: string = this.baseUrl + "/download-report";



  constructor(private http: HttpClient) {
  }

  public search(bean:ReportSearch): Observable<BaseResponse<PaginatedList<ReportList>>> {
    return this.http.post<BaseResponse<PaginatedList<ReportList>>>(`${this.SEARCH_REPORT}`, bean);
  }

  public addReport(bean:ReportInsert): Observable<BaseResponse<boolean>> {
    return this.http.post<BaseResponse<boolean>>(`${this.INSERT_REPORT}`, bean);
  }
  
  public listReport(): Observable<BaseResponse<ReportSelectItemEstensione[]>> {
    return this.http.get<BaseResponse<ReportSelectItemEstensione[]>>(`${this.SELECT_TIPOLOGIE}`);
  }
  
  public listProcedimenti(): Observable<BaseResponse<ReportSelectItem[]>> {
    return this.http.get<BaseResponse<ReportSelectItem[]>>(`${this.SELECT_TIPOLOGIE_PROCEDIMENTI}`);
  }
  
  public downloadReport(idDownload: string): Observable<HttpResponse<Blob>> {
    return this.http.get(`${this.DOWNLOAD_REPORT}/${idDownload}`,{observe: "response",responseType: "blob"});
  }
  
  // public reportElenco(): Observable<BaseResponse<string>> {
  //   return this.http.get<BaseResponse<string>>(`${this.REPORT_ELENCO_URL}`);
  // }

}
