import { environment } from 'src/environments/environment';
import { Subject } from 'rxjs';
import { CdsSettings, CdsSelectItem, Cds, CdsDocument, CdsListItem } from './../models/cds.model';
import { BaseResponse } from './../../../shared/components/model/base-response';
import { Observable } from 'rxjs';
import { CONST } from './../../../shared/constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CdsService {

  private baseUrl: string = CONST.WEB_RESOURCE_BASE_URL + "/cds";
  
  private GET_URL: string = this.baseUrl + "/get";
  private GET_COMITATO_URL: string = this.baseUrl + "/get-comitato";
  private SAVE_URL: string = this.baseUrl + "/save";
  private HAS_CDS_URL: string = this.baseUrl + "/has-cds";
  private GET_CDS_URL: string = this.baseUrl + "/get-cds";
  private AVVIA_URL: string = this.baseUrl + "/avvia";
  private PROVINCIA_URL: string = this.baseUrl + "/provincia";
  private COMUNE_URL: string = this.baseUrl + "/comune";
  private TIPO_URL: string = this.baseUrl + "/tipo";
  private AZIONE_URL: string = this.baseUrl + "/azione";
  private ATTIVITA_URL: string = this.baseUrl + "/attivita";
  private MODALITA_URL: string = this.baseUrl + "/modalita";
  private GET_CDS_DOCUMENT_URL: string = this.baseUrl + "/documentazione-list";
  private DOWNLOAD_CDS_DOCUMENT_URL: string = this.baseUrl + "/download";
  private SAVE_CDS_DOCUMENT_URL: string = this.baseUrl + "/save-documentazione-list";
  private LIST_CDS_URL: string = this.baseUrl + "/list";
  private NEW_CDS_URL: string = this.baseUrl + "/new";
  private DELETE_URL: string = this.baseUrl + "/delete";
  private GET_UTENTI_COMITATO_URL: string = this.baseUrl + "/get-utenti-comitato";
  private GET_UTENTI_RESPONSABILI_URL: string = this.baseUrl + "/lista-responsabili";
  private GET_UTENTI_FUNZIONARI_URL: string = this.baseUrl + "/lista-funzionari";
  private CAN_CREATE_CONFERENZA_URL: string = this.baseUrl + "/can-create-conferenza";

  public action: Subject<string> = new Subject<string>();

  constructor(private http: HttpClient) {
  }

  public get(idPratica:string, id:string): Observable<BaseResponse<CdsSettings>> {
    return this.http.get<BaseResponse<CdsSettings>>(`${this.GET_URL}/${idPratica}/${id}`);
  }
  
  public getComitato(idPratica:string): Observable<BaseResponse<CdsSettings>> {
    return this.http.get<BaseResponse<CdsSettings>>(`${this.GET_COMITATO_URL}/${idPratica}`);
  }
  
  public delete(idPratica:string, id:string): Observable<BaseResponse<boolean>> {
    return this.http.delete<BaseResponse<boolean>>(`${this.DELETE_URL}/${idPratica}/${id}`);
  }
  
  public save(id:string, dto:CdsSettings): Observable<BaseResponse<CdsSettings>> {
    return this.http.post<BaseResponse<CdsSettings>>(`${this.SAVE_URL}/${id}`, dto);
  }
  
  public hasCds(id:string): Observable<BaseResponse<boolean>> {
    return this.http.get<BaseResponse<boolean>>(`${this.HAS_CDS_URL}/${id}`);
  }

  public getCds(id:string): Observable<BaseResponse<Cds[]>> {
    return this.http.get<BaseResponse<Cds[]>>(`${this.GET_CDS_URL}/${id}`);
  }

  public getCdsDetail(idPratica:string, id:string): Observable<BaseResponse<Cds>> {
    return this.http.get<BaseResponse<Cds>>(`${this.GET_CDS_URL}/${idPratica}/${id}`);
  }
  
  public avvia(idPratica:string, id:string): Observable<BaseResponse<boolean>> {
    return this.http.get<BaseResponse<boolean>>(`${this.AVVIA_URL}/${idPratica}/${id}`);
  }
  
  public provincia(query:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.PROVINCIA_URL}`, {params:{"query": query}});
  }
  
  public comune(provincia:string, query:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.COMUNE_URL}/${provincia}`, {params:{"query": query}});
  }

  public tipo(id:String): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.TIPO_URL}/${id}`);
  }
  
  public attivita(id:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.ATTIVITA_URL}/${id}`);
  }
  
  public azione(attivita:string, id:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.AZIONE_URL}/${id}/${attivita}`);
  }
  
  public modalita(): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.MODALITA_URL}`);
  }

  public listCds(id:string): Observable<BaseResponse<CdsListItem[]>> {
    return this.http.get<BaseResponse<CdsListItem[]>>(`${this.LIST_CDS_URL}/${id}`);
  }

  public newCds(id:string): Observable<BaseResponse<CdsSettings>> {
    return this.http.get<BaseResponse<CdsSettings>>(`${this.NEW_CDS_URL}/${id}`);
  }

  public listDocumentiCds(id:string): Observable<BaseResponse<CdsDocument[]>> {
    return this.http.get<BaseResponse<CdsDocument[]>>(`${this.GET_CDS_DOCUMENT_URL}/${id}`);
  }
  public saveDocumentiCds(id:string, list:CdsDocument[]): Observable<BaseResponse<boolean>> {
    return this.http.post<BaseResponse<boolean>>(`${this.SAVE_CDS_DOCUMENT_URL}/${id}`, list);
  }
  
  public downloadDocumentCds(id:string, idCds:number):Observable<HttpResponse<Blob>> {
    return this.http.get(`${this.DOWNLOAD_CDS_DOCUMENT_URL}/${id}/${idCds}`,{
      observe: 'response',
      responseType: 'blob',
    })
  }

  public utentiComitato(idPratica:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.GET_UTENTI_COMITATO_URL}/${idPratica}`);
  }

  public utentiResponsabili(idPratica:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.GET_UTENTI_RESPONSABILI_URL}/${idPratica}`);
  }

  public utentiFunzionari(idPratica:string): Observable<BaseResponse<CdsSelectItem[]>> {
    return this.http.get<BaseResponse<CdsSelectItem[]>>(`${this.GET_UTENTI_FUNZIONARI_URL}/${idPratica}`);
  }

  public canCreateConferenza(idPratica:string): Observable<BaseResponse<boolean>> {
    return this.http.get<BaseResponse<boolean>>(`${this.CAN_CREATE_CONFERENZA_URL}/${idPratica}`);
  }

  public goToConference(id:number):void{
    let percentualeWidth  :number = 95;
    let percentualeHeight :number = 95;
    
    let windowWidth  : number = screen.width;//window.screen.availWidth;
    let windowHeight : number = screen.height;//window.screen.availHeight;
    
    let _width  :number = Math.round((windowWidth * percentualeWidth) / 100);
    let _height :number = Math.round((windowHeight * percentualeWidth) / 100);
    let _left   :number = (windowWidth/2)-(_width/2);
    let _top    :number = (windowHeight/2)-(_height/2) - 33;

    let _options  :string = 'status=1,toolbar=no,scrollbars=yes,resizable=yes'
                          + ',top=' + _top
                          + ',left=' + _left
                          + ',width=' + _width
                          + ',height=' + _height
                          ;
    window.open(environment.linkCds.replace("${id}", id.toString())
               ,"CDS-SPORTELLO-UNICO"
               ,_options
               );

  }
}
