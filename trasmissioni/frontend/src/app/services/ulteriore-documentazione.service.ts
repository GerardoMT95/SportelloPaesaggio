import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserService } from './user.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { BaseResponse } from '../components/model/base-response';
import { DestinatarioDTO } from '../components/model/entity/corrispondenza.models';
import { CONST } from 'src/shared/constants';

@Injectable({
  providedIn: 'root'
})
export class UlterioreDocumentazioneService {

  private baseUrl: string = environment.baseUrl;



  constructor(private http: HttpClient,
    private userService: UserService,
    private translate: TranslateService) { }


    public getDestinatariDefault(): Observable<BaseResponse<DestinatarioDTO[]>>{
      let url: string = CONST.WEB_RESOURCE_BASE_URL + "/ulterioreDoc/destinatariDefault";
      // let params = { params: new HttpParams().set("", null) };
      return this.http.get<BaseResponse<DestinatarioDTO[]>>(url);
    }


}
