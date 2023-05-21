import { Observable } from 'rxjs';
import { CONST } from 'src/app/shared/constants';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AvviaPagamentoRequestBean, PagamentoDto } from '../models/models';
import { BaseResponse } from '../components/model/base-response';
@Injectable({
    providedIn: 'root'
  })
export class PagoPaService {
  
    constructor( 
      private http:HttpClient,
      private loadingService:LoadingService
    ) { }

    /**
     * Metodo di avvio del pagamento
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
    public avvioPagamento(id:string, request: AvviaPagamentoRequestBean) {
      return this.http.post<BaseResponse<PagamentoDto>>(
        CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/avvia-pagamento/" + id,
        request
      )
    }

    /**
     * Metodo di annullamento del pagamento
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public annullaPagamento(id:string) {
        return this.http.delete<BaseResponse<boolean>>(
          CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/delete-pagamento/" + id
        )
      }

    /**
     * Metodo di recupero del pagamento(se presente)
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public getPagamento(id:string) {
        return this.http.get<BaseResponse<PagamentoDto>>(
          CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/get-pagamento/" + id
        )
    } 

    /**
     * Metodo di recupero del pagamento(se presente)
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public getPagamentoByCodPratica(codicePraticaAppptr:string) {
      return this.http.get<BaseResponse<PagamentoDto>>(
        CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/get-pagamento-by-cod-appptr/" + codicePraticaAppptr
      )
  }
    
    /**
     * Metodo di recupero/genera url mypay
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public getUrlMyPay(id:string) {
        return this.http.get<BaseResponse<string>>(
          CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/get-url-mypay/" + id
        )
    } 
    
    /**
     * Metodo verifica pagamento
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public verificaPagamento(id:string) {
        return this.http.get<BaseResponse<boolean>>(
          CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/verifica-pagamento/" + id
        )
    } 

    /**
     * Metodo di download della ricevuta
     * Alessio Bottalico
     * @date 12/07/2021
     * @param params
     * @param url
     */
     public downloadRicevuta(id:string): Observable<HttpResponse<Blob>> {
        return this.http.get(
          CONST.WEB_RESOURCE_BASE_URL + "/pagamenti/download-ricevuta/" + id, {
              observe: "response",
              responseType: "blob"
          }
        )
    } 

}