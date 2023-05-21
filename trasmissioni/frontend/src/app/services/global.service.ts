import { BaseResponse } from './../components/model/base-response';
import { CONST } from 'src/shared/constants';
import { UserService } from './user.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
//import { TipoIntervento, StatoFascicolo, EsitoProvvedimento, SicZps } from '../components/model/model';
//import { globalResponse } from '../components/model/fascicolo.models';
import { TranslateService } from '@ngx-translate/core';
import { StatoFascicolo, SicZps } from '../components/model/model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
    private translateService: TranslateService
  ) { }


  //Redirect in base al ruolo dell'utente
  public redirect(urlDiPartenza:string=""): void {
    if (this.userService.isLogged()){
      let gruppoUtentePredefinito=null;
      if(urlDiPartenza && urlDiPartenza != ""){
        //se nell'url ho il gruppo con cui proseguire....
        let urlPartenzaObj=new URL(urlDiPartenza,environment.baseUrl);
        if(urlPartenzaObj.searchParams.has(CONST.GRUPPO_UTENTE_HEADER)){
          gruppoUtentePredefinito=urlPartenzaObj.searchParams.get(CONST.GRUPPO_UTENTE_HEADER);  
        }
      }
      if(urlDiPartenza){
        this.router.navigate(['/private/gestione-gruppo',{previousUrl:urlDiPartenza,gruppoUtentePredefinito:gruppoUtentePredefinito}]);
      }else{
        this.router.navigate(['/private/gestione-gruppo']);
      }
    }
    else{
      this.router.navigate(['/error']);
    }
      
  }


  refreshSession(): Promise<BaseResponse<boolean>> {
    return this.http.get<BaseResponse<boolean>>(CONST.WEB_RESOURCE_BASE_URL + "/logged/user/refresh.pjson", CONST.httpOptions)
      .toPromise().then(data => data);
  }


  /*getListInterventi(): Promise<TipoIntervento[]> {
    //return this.http.get<globalResponse<TipoIntervento[]>>(CONST.WEB_RESOURCE_BASE_URL + "public/tipoIntevento/list.pjson",CONST.httpOptions)
    //.toPromise().then(data => data.payload);
    let mapped = CONST.TipiIntervento.map(
      el => { return { id: Number(el.value), name: el.translated } }
    );
    return new Promise((resolve, reject) => {
      resolve(mapped);
    });
  }*/

  /**
   * mock
   */
  getStatoFascicolo(): Promise<StatoFascicolo[]> {
    //return this.http.get<globalResponse<TipoIntervento[]>>(CONST.WEB_RESOURCE_BASE_URL + "public/tipoIntevento/list.pjson",CONST.httpOptions)
    //.toPromise().then(data => data.payload);
    return new Promise((resolve, reject) => {
      let mapped = CONST.StatoFascicoloStatusAttribute.map(
        el => {
          return { name: el.translated, id: el.id };
        }
      );
      resolve(mapped);
    }
    );
  }

  /**
   * mock
   */

  /*getEsitiProvvedimento(): Promise<EsitoProvvedimento[]> {
    //return this.http.get<globalResponse<TipoIntervento[]>>(CONST.WEB_RESOURCE_BASE_URL + "public/tipoIntevento/list.pjson",CONST.httpOptions)
    //.toPromise().then(data => data.payload);
    return new Promise((resolve, reject) => {
      resolve(CONST.EsitiProvvedimento);
    });
  }*/

  /**
   * mock
   */

  getParchiRiserve(idEnti: any, codiciCatatstali: any): Promise<SicZps[]> {
    return new Promise((resolve, reject) => {
      resolve(CONST.ParchiRiserve.filter(el => {
        return idEnti.indexOf(el.ente)>=0;
      }));
    });
  }

  /**
     * mock
     */
  getPaesaggioRurale(idEnti: any, codiciCatatstali: any): Promise<SicZps[]> {
    return new Promise((resolve, reject) => {
      resolve(CONST.PaesaggioRurale.filter(el => {
        return idEnti.indexOf(el.ente)>=0;
      }));
    });
  }

  /**
   * mock
   */
  getImmobiliAreeInteresse(idEnti: any[], codiciCatatstali: any): Promise<SicZps[]> {
    let ret = CONST.ImmobiliAreeInteresse.filter(el => {
      return idEnti.indexOf(el.ente)>=0;
    });
    //console.log('immobili e aree di interesse finltrati per {} = {}', idEnti, ret);
    return new Promise((resolve, reject) => {
      resolve(ret);
    });
  }


}
