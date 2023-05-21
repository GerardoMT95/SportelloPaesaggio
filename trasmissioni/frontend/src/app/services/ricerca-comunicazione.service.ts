import { CONST } from './../../shared/constants';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseResponse } from '../components/model/base-response';
import { map } from 'rxjs/operators';
import { TipoSelect, TabellaComunicazione, DettaglioComunicazione, RicercaComunicazione, NuovaTabellaComunicazione, AutocompleteRequest } from '../components/model/model';
import { DettaglioCom } from '../components/model/dettaglioComunicazione';

@Injectable({
  providedIn: 'root'
})

export class RicercaComunicazioneService
{

  constructor(private http: HttpClient) { }

  public getMittente(query: string): Promise<BaseResponse<TipoSelect[]>>
  {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/tipoEnteComunicazione/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });

  }

  /**
   * mocked
   */
  public getFromTo(): Promise<BaseResponse<TipoSelect[]>>
  {
    let ret = {
      codiceEsito: "OK", descrizioneEsito: "success", numeroTotaleOggetti: 1, numeroOggettiRestituiti: 1, payload: [
        {
          label: "Regione-ARPA",
          value: {
            from: {
              id: "4d9fb1f8-c710-4ca8-aaa9-20ea617b7b46", denominazione: "Regione",
              descrizione: "Rappresenza un'utenza di tipo Regione", codiceAmbito: "ORP"
            },
            to: { id: "2bc8462e-0eab-412b-b6ac-597ef46646a1", denominazione: "ARPA", descrizione: "Rappresenza un'utenza di tipo ARPA", codiceAmbito: "ORP" }
          }
        },
      ]
    };
    return new Promise((resolve, reject) =>
    {
      resolve(ret)
    });


    /*return this.http.get<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/fromTo.pjson')
      .toPromise().then(response =>
      {
        return response;
      });*/

  }

  public getComune(query: string): Promise<BaseResponse<TipoSelect[]>>
  {
    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/comunepuglia/autocomplete.pjson',
      { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getImpianto(query: string): Promise<BaseResponse<TipoSelect[]>>
  {


    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/impianto/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getProvincia(query: string): Promise<BaseResponse<TipoSelect[]>>
  {

    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/provincia/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getAro(query: string): Promise<BaseResponse<TipoSelect[]>>
  {
    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/aro/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getGestore(query: string): Promise<BaseResponse<TipoSelect[]>>
  {
    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/gestore/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getTipologia(query: string): Promise<BaseResponse<TipoSelect[]>>
  {

    let filtro: AutocompleteRequest = { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE };

    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/tipocomunicazione/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });
  }

  public getNumeroprotocollo(query: string): Promise<BaseResponse<TipoSelect[]>>
  {
    // TODO: Passare AutocompleteRequest chiamata da get a post

    return this.http.post<BaseResponse<TipoSelect[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/nrProtocollo/autocomplete.pjson', { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE })
      .toPromise().then(response =>
      {
        return response;
      });

    /*  let filtro: AutocompleteRequest = { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE  };
     return this.http.get<BaseResponse<TipoSelect[]>>('/mock/numeroprotocollo.json')
       .pipe<BaseResponse<TipoSelect[]>>(
         map(data => {
           let list: TipoSelect[]
           list = data.payload.filter(item => item.label.toLowerCase().includes(filtro.filter.toLowerCase()))
 
           let resp: BaseResponse<TipoSelect[]> =
           {
             code: "200",
             message: "ok",
             partialSize: 10,
             size: list.length,
             payload: list
           }
 
 
           return resp;
         })).toPromise().then(response => {
           return response;
         }); */
  }


  public getListaTabellaComunicazione(query: string): Promise<BaseResponse<TabellaComunicazione[]>>
  {

    let filtro: AutocompleteRequest = { filter: query, limit: CONST.LIMIT_AUTOCOMPLETE };
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<BaseResponse<TabellaComunicazione[]>>('/mock/tabellacomunicazione.json').toPromise().then(response =>
    {
      return response;
    });
  }


  public getDettaglio(id: string, idDestinatario: string): Promise<BaseResponse<DettaglioComunicazione>>
  {
    let param: HttpParams = new HttpParams().set("id", id).set("idDestinatario", idDestinatario);
    // aggingere all chiamata {params:param}
    return this.http.get<BaseResponse<DettaglioComunicazione>>(CONST.WEB_RESOURCE_BASE_URL + 'dettaglio/getDettaglio.pjson', { params: param })
      .toPromise()
      .then(response => { return response });
  }

  public ricerca(entity: RicercaComunicazione): Promise<BaseResponse<TabellaComunicazione[]>>
  {

    // aggingere all chiamata {params:param}
    return this.http.post<BaseResponse<TabellaComunicazione[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/comunicazioni.pjson', entity)
      .toPromise()
      .then(response => { return response });
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////     METODI   NUOVA PAGINA DI RICERCA   /////////////////////////////////////////////////////////////////   
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public nuovaRicerca(entity: RicercaComunicazione): Promise<BaseResponse<any>>
  {

    return new Promise((resolve, reject) =>
    {
      resolve(
        {
          codiceEsito: "OK", descrizioneEsito: "success", numeroTotaleOggetti: 1, numeroOggettiRestituiti: 1,
          payload: {
            list:
              [{
                idComunicazione: "d3f6cba4-179d-423a-ba08-75b2dba6ce10",
                idEmlCmis: "3b6394ee-748e-422a-a18e-10717dd562bb;1.0",
                codiceComunicazione: "COM-TR-P-20/11/2019/15:39:39264",
                messageId: "720319977.3.1574260781323.JavaMail.root@mail",
                data: 1574260779264,
                nrProtocollo: 'A00/09888999/2019',
                oggetto: "Trasmissione autorizzazione paesaggistica XXXXXXXXXX",
                testo: null, annoComunicazione: 2020, esitoComunicazione: null, denominazioneTipo: "PPTR - Trasmissioni",
                mittente: {
                  id: "300f155b-433a-4349-9528-2b611997ab98", loginame: null, mail: "ORP_COM", pec: null, tipoEnte: "Regione",
                  codEnte: "REGIONE", denEnte: "PUGLIA", nominativo: null, statoInvio: 0
                },
                destinatari: [
                  {
                    id: "2c9c8aae-90de-429d-b3fc-982bf0f7aa37",
                    loginame: null,
                    mail: "comunebari@testmailpec.it",
                    pec: "comunebari@testmailpec.it",
                    tipoEnte: "COMUNE",
                    codEnte: "071007",
                    denEnte: "BARI",
                    nominativo: null,
                    statoInvio: 1
                  },
                  {
                    id: "07ef5562-74c7-4691-9490-c4394353ab6d",
                    loginame: null,
                    mail: "test_procedimenti@pec.rupar.puglia.it",
                    pec: "test_procedimenti@pec.rupar.puglia.it",
                    tipoEnte: "REGIONE",
                    codEnte: "REGIONE",
                    denEnte: "PUGLIA",
                    nominativo: null,
                    statoInvio: 2
                  },
                  {
                    id: "07ef5562-74c7-4691-9490-c4394353ab34",
                    loginame: null,
                    mail: "testtest@pec.rupar.puglia.it",
                    pec: "testtest@pec.rupar.puglia.it",
                    tipoEnte: "REGIONE",
                    codEnte: "REGIONE",
                    denEnte: "PUGLIA",
                    nominativo: "nominativo test",
                    statoInvio: 0
                  }
                ]
              }], count: 1
          }
        })
    });
    // aggingere all chiamata {params:param}
    /*return this.http.post<BaseResponse<NuovaTabellaComunicazione[]>>(CONST.WEB_RESOURCE_BASE_URL + 'ricerca/comunicazioniNuovo.pjson', entity)
      .toPromise()
      .then(response => { return response });*/
  }

  /**mocked */
  public getDettaglioNew(id: string): Promise<BaseResponse<DettaglioCom>>
  {
    let detom: DettaglioCom = {
      codiceComunicazione: "COM-TR-P-20/11/2019/15:39:39264",
      numeroProtocollo: 'A00/09888999/2019',
      mittente: "", mailMittente: "user39@pec.rupar.puglia.it",
      oggetto: "Trasmissione autorizzazione paesaggistica 987897897",
      tipologia: "",
      pec: true,
      data: "2019-11-20T14:39:39.264Z",
      testo: "",
      allegati: [
        {
          id: 0,
          nome: "certificazione_2020_protocollato.pdf",
          dataCaricamento: new Date(),
          idCms: "",
          mimeType: "txt",
          dimensione: 200,
          multiplo: false,
          obbligatorio: false,
          tipo: "allegato",
          utenteInserimento: "test"
        }
        /* {
          id: 0,
          nomeOriginale: "certificazione_2020_protocollato.pdf",
          nrProtocollo: "0002437",
          dimensione: 0,
          idCmis: "19776350-9153-4ed1-88f4-76eb5abe6005;1.0"
        } */
      ],
      //idCms: "3b6394ee-748e-422a-a18e-10717dd562bb;1.0",
      ricevutaAccettazione: null,
      destinatari: [
        {
          tipoDestinatario: "Cc", denominazione: "", nominativo: null,
          idDestinatario: "59d54f8a-72d6-43d5-8c8c-8332795318a9",
          indirizzo: "test_procedimenti@pec.rupar.puglia.it",
          ricevute: [{
            data: "2019-11-20T14:39:39.264Z",
            tipoRicevuta: "tiporicevutaval",
            errore: "nessuno",
            descrizioneErrore: null,
            idCmsEml: "19776350-9153-4ed1-88f4-76eb5abe6225;1.0",
            idCmsDatiCert: "19776350-9153-4ed1-88f4-76eb5abe6125;1.0",
            idCmsSmime: "19776350-9153-4ed1-88f4-76eb77be6005;1.0"
          }]
        },
        {
          tipoDestinatario: "To", denominazione: "",
          nominativo: null, idDestinatario: "2c8a1b4d-7a47-4ee4-80d7-f8c2d5be3eee",
          indirizzo: "comunebari@testmailpec.it", ricevute: [
            {
              data: "2019-11-20T14:39:39.264Z",
              tipoRicevuta: "tiporicevutaval",
              errore: "casella errata",
              descrizioneErrore: null,
              idCmsEml: "19776350-9153-4ed1-88f4-76eb5abe6225;1.0",
              idCmsDatiCert: "19776350-9153-4ed1-88f4-76eb5abe6125;1.0",
              idCmsSmime: "19776350-9153-4ed1-88f4-76eb77be6005;1.0"
            }
          ]
        }
      ]
    };
    return new Promise((resolve, reject) =>
    {
      resolve({ codiceEsito: "OK", descrizioneEsito: "success", numeroTotaleOggetti: 1, numeroOggettiRestituiti: 1, payload: detom });
    });


    /*let param: HttpParams = new HttpParams().set("id", id);
    // aggingere all chiamata {params:param}
    return this.http.get<BaseResponse<DettaglioCom>>(CONST.WEB_RESOURCE_BASE_URL + 'dettaglio/getDettaglioNew.pjson', { params: param })
      .toPromise()
      .then(response => { return response });*/
  }

  /*public getDettaglioCom():DettaglioCom{

    const dettaglio : DettaglioCom = {
          codiceComunicazione: "CM247FGBV",
          data: "17/05/2019",
          numeroProtocollo: "521155",
          mittente: "Altro",
          mailMittente: "blabla@gmail.com",
          oggetto: "Lavorazione sitPuglia",
          tipologia: "Solleciti",
          pec: true,
          allegati: [{
              id: "00012",
              dimensione: 150,
              idCmis: "hgmhgfhgfhj",
              nomeOriginale: "Allegato1",
              nrProtocollo: "PRA789",
          }, {
              id: "00013",
              dimensione: 150,
              idCmis: "hgmhgfhgfhj",
              nomeOriginale: "Allegato2",
              nrProtocollo: "PRA781144",
          }],
          idCms: "khgjhgjhgjh",
          ricevutaAccetazione: null,
          destinatari: [{
              tipoDestinatario: "Tipo",
              denominazione: "Denominazione",
              nominativo: "Pippo Baudo",
              idDestinatario: "pippo001",
              indirizzo: "pippobaudo54@gmail.com",
              ricevute: {
                  data: "10/10/18",
                  tipoRicevuta: "Tipo ricevuta",
                  errore: "Errore generico",
                  descrizioneErrore: "Questa descrizione esiste per errore è diverso da nessuno",
                  idCmsEml: "kjgkjg",
                  idCmsDatiCert: "jhgjhgjk",
                  idCmsSmime: "jhghkgjh",
              },
          },
              {tipoDestinatario: "Tipo",
                  denominazione: "Denominazione",
                  nominativo: "",
                  idDestinatario: "pippo001",
                  indirizzo: "pippobaudo54@gmail.com",
                  ricevute: {
                      data: "10/10/18",
                      tipoRicevuta: "Tipo ricevuta",
                      errore: "Non recapitato",
                      descrizioneErrore: "Questa descrizione esiste per errore è diverso da nessuno",
                      idCmsEml: "kjgkjg",
                      idCmsDatiCert: "jhgjhgjk",
                      idCmsSmime: "jhghkgjh",
                  },
              }]
      };
    return dettaglio;
  }*/

}
