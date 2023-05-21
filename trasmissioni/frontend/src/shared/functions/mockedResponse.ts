import { CONST } from 'src/shared/constants';
import { BaseResponse } from './../../app/components/model/base-response';
export function okresponse(payload: any, totalItems?: number): BaseResponse<any>
{
    return {
        payload,
        codiceEsito: CONST.OK,
        descrizioneEsito: "ok",
        numeroOggettiRestituiti: payload instanceof Array ? payload.length : 1,
        numeroTotaleOggetti: totalItems ? totalItems : payload instanceof Array ? payload.length : 1
    };
}