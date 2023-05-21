import { toHttpParams } from 'src/app/core/functions/generic.utils';
import { HttpParams } from '@angular/common/http';
import { BaseResponse } from './../../model/base-response';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";

@Injectable()
export class AutocompleteService
{
    constructor(private http: HttpClient){}

    public doCall(endponint: string, params: any): Observable<BaseResponse<any>>
    {
        let _params = { params: toHttpParams(params) };
        return this.http.get<BaseResponse<any>>(endponint, _params);
    }
}