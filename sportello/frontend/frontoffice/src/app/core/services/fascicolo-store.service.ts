import { Fascicolo, AttivitaDaEspletareEnum } from 'src/app/shared/models/models';
import { FascicoloService } from 'src/app/shared/services/http-fascicolo.service';
import { FascicoloState } from '../models/fascicolo-store';
import { HttpCasellaDiControlloService } from 'src/app/shared/services/http-casella-di-controllo';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { HttpPptrService } from 'src/app/shared/services/http-pptr.service';
import { Injectable } from '@angular/core';
import { LocalStorageService } from './local-storage.service';
import { Store } from '../models/store';

@Injectable({
  providedIn: 'root'
})
export class FascicoloStore extends Store<FascicoloState> {
  constructor(
    private httpPptrService: HttpPptrService,
    private fascicoloService: FascicoloService,
    private httpDominio: HttpDominioService,
    private httpCasellaDiControllo: HttpCasellaDiControlloService,
    private localStorageService: LocalStorageService
  ) {
    super(new FascicoloState());
    this.initState();
  }

  //public $aggiornaFascicolo=new Subject();

  editFascicolo(fascicolo: Fascicolo): void {
    this.setState({
      ...this.state,
      fascicoli: this.state.fascicoli.map(f => {
        if (f.codicePraticaAppptr === fascicolo.codicePraticaAppptr) {
          return { ...fascicolo };
        }
        return f;
      })
    });
    this.localStorageService.setStateStorage(this.state);
  }

  /*addFascicolo(fascicolo: Fascicolo): void {
    this.setState({
      ...this.state,
      fascicoli: [...this.state.fascicoli, fascicolo]
    });

    this.localStorageService.setStateStorage(this.state);
  }*/

  getFascicolo(codiceFascicolo: string) {
    this.setState({
      ...this.state,
      fascicolo: this.state.fascicoli.find(fascicolo => fascicolo.codicePraticaAppptr === codiceFascicolo)
    });
  }

  /*removeFascicolo(codiceFascicolo: string) {
    this.setState({
      ...this.state,
      fascicoli: this.state.fascicoli.filter(fascicolo => fascicolo.codicePraticaAppptr !== codiceFascicolo),
      filtered: this.state.fascicoli.filter(fascicolo => fascicolo.codicePraticaAppptr !== codiceFascicolo)
    });
    this.localStorageService.setStateStorage(this.state);
  }*/

  /*getFascicoli(searchQuery?: Fascicolo) {
    let fascicoli = this.state.fascicoli;

    if (searchQuery) {
      fascicoli = this.state.fascicoli.filter(item => {
        const val = [];
        Object.keys(searchQuery).forEach(key => {
          val.push(item[key] === searchQuery[key]);
        });
        return !val.includes(false);
      });
    }

    this.setState({
      ...this.state,
      filtered: fascicoli
    });
  }*/

  async initState() {
    //refresho sempre lo state....
    const state = this.localStorageService.getStateStorage();    
    if(state){
      this.setState(state);
    }else{
      let meta=await this.httpDominio.getAllDominio().toPromise();
      //let tipiProcedimento=await this.httpDominio.getTipiProcedimento().toPromise();
        //let enteDelegato=await this.httpDominio.getEnteDelegato().toPromise();
        let tipiDocumentoIdentita=await this.httpDominio.getTipiDocumentoIdentita().toPromise();
        let tipoRuoloDitta=await this.httpDominio.getTipiRuoloDitta().toPromise();
        let casella=await this.httpCasellaDiControllo.getAllCasella().toPromise();
        let pptr=await this.httpPptrService.getAllPptr().toPromise();
      /*let obj=await forkJoin({
        meta:this.httpDominio.getAllDominio(),
        tipiProcedimento:this.httpDominio.getTipiProcedimento(),
        enteDelegato:this.httpDominio.getEnteDelegato(),
        tipiDocumentoIdentita:this.httpDominio.getTipiDocumentoIdentita(),
        tipoRuoloDitta:this.httpDominio.getTipiRuoloDitta(),
        casella:this.httpCasellaDiControllo.getAllCasella(),
        pptr:this.httpPptrService.getAllPptr()
      }).toPromise();*/
      let metaMerged=meta;
          //metaMerged['typeProcedimento']=tipiProcedimento;
          //metaMerged['enteDelegato']=enteDelegato;
          metaMerged['tipoDocumento']=tipiDocumentoIdentita;
          metaMerged['ruoloDitta']=tipoRuoloDitta;
          this.setState({
            ...this.state,
            meta:metaMerged,
            casella:casella,
            pptr:pptr
          });
      this.localStorageService.setStateStorage(this.state)
    }
    /*state
      ? this.setState(state)
      :  
      forkJoin({
        meta:this.httpDominio.getAllDominio(),
        tipiProcedimento:this.httpDominio.getTipiProcedimento(),
        enteDelegato:this.httpDominio.getEnteDelegato(),
        tipiDocumentoIdentita:this.httpDominio.getTipiDocumentoIdentita(),
        tipoRuoloDitta:this.httpDominio.getTipiRuoloDitta(),
        casella:this.httpCasellaDiControllo.getAllCasella(),
        pptr:this.httpPptrService.getAllPptr()
      }
          
        ).subscribe((obj) => {
          let metaMerged=obj.meta;
          metaMerged['typeProcedimento']=obj.tipiProcedimento;
          metaMerged['enteDelegato']=obj.enteDelegato;
          metaMerged['tipoDocumento']=obj.tipiDocumentoIdentita;
          metaMerged['ruoloDitta']=obj.tipoRuoloDitta;
          this.setState({
            ...this.state,
            meta:metaMerged,
            casella:obj.casella,
            pptr:obj.pptr
          });
          this.localStorageService.setStateStorage(this.state);
        });*/
  }

  

  setBreadcrumbs(breadcrumbs: any): void {
    this.setState({
      ...this.state,
      breadcrumbs: [ ...breadcrumbs ]
    });

    this.localStorageService.setStateStorage(this.state);
  }

  isDisabled() {
    return this.state.fascicolo.attivitaDaEspletare === AttivitaDaEspletareEnum.IN_PREISTRUTTORIA;
  }
}
