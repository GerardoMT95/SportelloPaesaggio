import { BaseResponse } from './../models/basic.models';
import { HttpParams } from '@angular/common/http';
export function okResponse<T>(payload: T, descrizioneEsito?: string, numeroTotaleOggetti?: number): BaseResponse<T>
{
    let oggettiRestituiti = payload instanceof Array ? payload.length : (payload ? 1 : 0);
    return new BaseResponse(descrizioneEsito, "OK", numeroTotaleOggetti, oggettiRestituiti, payload);
}
export function koResponse<T>(): BaseResponse<T>
{
    return new BaseResponse(null, "KO", 0, 0, null);
}