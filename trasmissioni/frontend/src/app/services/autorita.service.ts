import { LocalSessionServiceService } from './local-session-service.service';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SelectItem } from 'primeng/components/common/selectitem';
import { map } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { PlainTypeStringId } from 'src/shared/models/plain-type-string-id.model';
import { BaseResponse } from '../components/model/base-response';
import { PevRole } from '../components/model/model';
import { CONST } from 'src/shared/constants';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AutoritaService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient, 
    private lss: LocalSessionServiceService) { }

 
  getProceduralAuthoritySelectItem(): Observable<SelectItem[]> {
    return this.http.get<BaseResponse<PlainTypeStringId[]>>(`${this.baseUrl}/autorita-procedurale`).pipe(map(
      (response: BaseResponse<PlainTypeStringId[]>)=>{
        const autoritaProcedurale: SelectItem[]=[];
        response.payload.forEach(element=>{
          autoritaProcedurale.push({label: element.nome, value: element.id});
        });
        let gruppoScelto: any = this.lss.getValue(LocalSessionServiceService.WORKING_GROUP_KEY);
        let item: SelectItem = autoritaProcedurale.find(elem => elem.value === gruppoScelto);
        let items: SelectItem[] = [];
        items.push(item);
        return items;
      })
    )
  }

  /*listCurrentUserRoles(): Observable<PevRole> {
    return this.http.get<BaseResponse<PevRole>>(`${this.baseUrl}/ruoli`)
    .pipe(map(
      (response: BaseResponse<PevRole>) => {
        return response.payload;
      }
    ))
  }*/
}
