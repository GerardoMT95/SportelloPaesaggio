import { Injectable } from '@angular/core';
import { combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ente, Fascicolo, StatoEnum } from 'src/app/shared/models/models';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';
import { FascicoloService } from 'src/app/shared/services/pratica/http-fascicolo.service';
import { FascicoloState } from '../models/fascicolo-store';
import { Store } from '../models/store';
import { CONST } from './../../shared/constants';
import { RupGruppoMocked, SedutaDiCommissione } from './../../shared/models/models';
import { FascicoloSearch } from './../../shared/models/search.models';
import { AdminFunctionsService } from './../../shared/services/admin/admin-functions.service';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FascicoloStore extends Store<FascicoloState>
{
  [x: string]: any;
  statoEnum = StatoEnum;
  /* esitoEnum = EsitoDelParere; */
  constructor(private fascicoloService: FascicoloService,
              private httpDominio: HttpDominioService,
              private httpAdminFunctions: AdminFunctionsService,
              private localStorageService: LocalStorageService)
  {
    super(new FascicoloState());
    this.initState();
  }

  addRubricaEnte(ente: Ente): void
  {
    this.setState({
      ...this.state,
      rubricaEnte: [...this.state.rubricaEnte, ente]
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  editRubricaEnte(ente: Ente): void
  {
    this.setState({
      ...this.state,
      rubricaEnte: this.state.rubricaEnte.map((elem) =>
      {
        if (elem.id === ente.id)
        {
          return { ...ente };
        }
        return elem;
      })
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  removeRubricaEnte(id: number)
  {
    this.setState({
      ...this.state,
      rubricaEnte: this.state.rubricaEnte.filter(
        (elem) => elem.id !== id
      )
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  addRubricaIstituzionale(ente: Ente): void
  {
    this.setState({
      ...this.state,
      rubricaIstituzionale: [...this.state.rubricaIstituzionale, ente]
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  editRubricaIstituzionale(ente: Ente): void
  {
    this.setState({
      ...this.state,
      rubricaIstituzionale: this.state.rubricaIstituzionale.map((elem) =>
      {
        if (elem.id === ente.id)
        {
          return { ...ente };
        }
        return elem;
      })
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  removeRubricaIstituzionale(id: number)
  {
    this.setState({
      ...this.state,
      rubricaIstituzionale: this.state.rubricaIstituzionale.filter(
        (elem) => elem.id !== id
      )
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  /*editRup(element: RupGruppoMocked): void
  {
    this.setState({
      ...this.state,
      rup: this.state.rup.map((f) =>
      {
        if (f.codiceGruppo === element.codiceGruppo)
        {
          return { ...element };
        }
        return f;
      })
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  addRup(rup: RupGruppoMocked): void
  {
    this.setState({
      ...this.state,
      rup: [...this.state.rup, rup]
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }*/

  addFile(path: string, file: File): void
  {
    let _files = this.state.files;
    if (!_files)
      _files = {};
    _files[path] = file;
    this.setState({
      ...this.state,
      files: _files
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  removeFile(path: string): void
  {
    let _files = {};
    Object.keys(this.state.files).forEach(key =>
    {
      if (key != path)
      {
        _files[key] = this.state.files[key];
      }
    });
    this.setState({
      ...this.state,
      files: _files
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }


  getFile(path: string): File
  {
    return this.state.files && this.state.files[path] ? this.state.files[path] : null;
  }

  nextid(key: string): number
  {
    let index: number;
    let serial = this.state.serial;
    if (serial[key])
    {
      index = serial[key];
      serial[key] = serial[key] + 1;
    }
    else
    {
      index = 1;
      serial[key] = index + 1;
    }
    this.setState({ ...this.state, serial: serial });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
    return index;
  }

  addSedutaCommissione(seduta: SedutaDiCommissione): void
  {
    this.setState({
      ...this.state,
      seduteDiCommissione: [... this.state.seduteDiCommissione, seduta]
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  editSedutaCommissione(seduta: SedutaDiCommissione): void
  {
    this.setState({
      ...this.state,
      seduteDiCommissione: this.state.seduteDiCommissione.map(m =>
      {
        let obj: SedutaDiCommissione = m;
        if(m.id === seduta.id)
          obj = { ...seduta };
        return obj;
      })
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');

  }

  removeSedutaDICommissione(id: number): void
  {
    this.setState({
      ...this.state,
      seduteDiCommissione: this.state.seduteDiCommissione.filter(p => p.id !== id)
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  addFascicolo(fascicolo: Fascicolo): void
  {
    this.setState({
      ...this.state,
      fascicoli: [...this.state.fascicoli, fascicolo]
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  editFascicolo(fascicolo: Fascicolo): void
  {
    this.setState({
      ...this.state,
      fascicoli: this.state.fascicoli.map((f) =>
      {
        if (f.codicePraticaAppptr === fascicolo.codicePraticaAppptr)
        {
          return { ...fascicolo };
        }
        return f;
      })
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  getFascicolo(codiceFascicolo: string)
  {
    this.setState({
      ...this.state,
      fascicolo: this.state.fascicoli.find(
        (fascicolo) => fascicolo.codicePraticaAppptr === codiceFascicolo
      )
    });
  }

  removeFascicolo(codiceFascicolo: string)
  {
    this.setState({
      ...this.state,
      fascicoli: this.state.fascicoli.filter(
        (fascicolo) => fascicolo.codicePraticaAppptr !== codiceFascicolo
      ),
      filtered: this.state.fascicoli.filter(
        (fascicolo) => fascicolo.codicePraticaAppptr !== codiceFascicolo
      )
    });
    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  getFascicoli(searchQuery?: FascicoloSearch)
  {
    let fascicoli = this.state.fascicoli;

    if (searchQuery)
    {
      fascicoli = this.state.fascicoli.filter((item) =>
      {
        const val = [];
        Object.keys(searchQuery).forEach((key) =>
        {
          if (searchQuery[key])
            val.push(item[key] == searchQuery[key]);
        });
        return !val.includes(false);
      });
    }

    this.setState({
      ...this.state,
      filtered: fascicoli
    });
  }

  getFascicoliPubblica(searchQuery?: Fascicolo)
  {
    let fascicoli = this.state.fascicoli;

    if (searchQuery)
    {
      fascicoli = this.state.fascicoli.filter((item) =>
      {
        const val = [];
        if (
          item.attivitaDaEspletare === this.StatoFascicolo.Trasmessa /*&&
           (item.trasmissioneProvvedimentoFinale.esito === this.esitoEnum.Autorizzato ||
            item.trasmissioneProvvedimentoFinale.esito === this.esitoEnum.AutorizzatoConPrecrizioni) */
        )
        {
          Object.keys(searchQuery).forEach((key) =>
          {
            if (key === 'bpParchiEReserve')
            {
              //val.push(item.istanza.localizzazione.ulterioriInformazioni.bpParchiEReserve === searchQuery[key]);
            } else if (key === 'esitoProvvedimento')
            {
              //val.push(item.trasmissioneProvvedimentoFinale.esito === searchQuery[key]);
            } else if (key === 'numeroProvvedimento')
            {
              val.push(
                //item.trasmissioneProvvedimentoFinale.numeroProvvedimento === searchQuery[key]
              );
            } else if (key === 'dataProvvedimento')
            {
              val.push(
                //item.trasmissioneProvvedimentoFinale.dataRilascioAutorizzazione === searchQuery[key]
              );
            } else
            {
              val.push(item[key] === searchQuery[key]);
            }
          });
        }
        console.log('fascioli', fascicoli);

        return !val.includes(false);
      });
    }

    this.setState({
      ...this.state,
      filtered: fascicoli
    });
  }

  initState()
  {
    const state = this.localStorageService.getStateStorage('fascicoloState');
    if (!state)
    {
      combineLatest([
        //this.fascicoloService.getAllFiascicolo(),
        this.httpDominio.getAllDominio(),
        //this.httpAdminFunctions.getAllRup(),
      ]).pipe(map(
        ([//fascicoli,
                               //meta,rup]: 
                    meta]: 
                   //[Fascicolo[], any, RupGruppoMocked[]]) =>
                   [
                     //Fascicolo[], 
                     any]) =>
      {
        return [//fascicoli,
           meta];
      })).subscribe(([//fascicoli,
         meta]) =>
      {
        let files = {};
        let serial = {};
        //serial[CONST.SERIAL_KEYS.FASCICOLI] = fascicoli.length;
        serial[CONST.SERIAL_KEYS.COMUNICAZIONI] = 25;
        serial[CONST.SERIAL_KEYS.ALLEGATI] = 1;
        serial[CONST.SERIAL_KEYS.RELAZIONI] = 1;
        serial[CONST.SERIAL_KEYS.ULTERIORE_DOCUMENTAZIONE] = 34;
        serial[CONST.SERIAL_KEYS.VERBALI] = 1;
        serial[CONST.SERIAL_KEYS.PARERI] = 1;
        serial[CONST.SERIAL_KEYS.DESTINATARIO] = 7;
        serial[CONST.SERIAL_KEYS.SEDUTE_CL] = 3;
        this.setState({
          ...this.state,
          meta,
          /* casella, */
          /* pptr, */
          //fascicoli,
          files,
          serial,
          /*rup,*/
          /* rubricaEnte, */
          /* seduteDiCommissione, */
          /* rubricaIstituzionale */
        });
        this.localStorageService.setStateStorage(this.state, 'fascicoloState');
      });
    }
    else
      this.setState(state)
  }

  setBreadcrumbs(breadcrumbs: any): void
  {
    this.setState({
      ...this.state,
      breadcrumbs: [...breadcrumbs]
    });

    this.localStorageService.setStateStorage(this.state, 'fascicoloState');
  }

  isDisabled()
  {
    return false;//this.state.fascicolo && (this.state.fascicolo.attivitaDaEspletare === StatoEnum.IstanzaPresentata) && (this.state.fascicolo.attivitaDaEspletare !== null);
  }
}
