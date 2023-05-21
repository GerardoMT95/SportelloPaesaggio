import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { TableConfig } from 'src/app/core/models/header.model';
import { DataService } from 'src/app/features/gestione-istanza/services';
import { CONST } from './../../../../shared/constants';
import { DocumentType } from './../../../../shared/models/allegati.model';
import { SimpleFileCommissioneLocale, SimplePratica } from './../../../../shared/models/models';
import { LoadingService } from './../../../../shared/services/loading.service';
import { SedutaCommissioneService } from './../../../../shared/services/seduta-commissione/seduta-commissione.service';
import { ConfVerbComLoc } from './../../configurations/verb-com-loc.configuration';

@Component({
  selector: 'app-gestione-istanza-verb-com-loc-page',
  templateUrl: './gestione-istanza-verb-com-loc-page.component.html',
  styleUrls: ['./gestione-istanza-verb-com-loc-page.component.scss']
})
export class GestioneIstanzaVerbComLocPageComponent implements OnInit
{
  private _files: Array<SimpleFileCommissioneLocale> = [];
  public display: boolean = false;
  public fascicoliData: Array<SimplePratica> = [];
  public nomeSeduta: string;
  public files: Array<any> = [];

  constructor(private route: ActivatedRoute,
              private loading: LoadingService,
              private service: SedutaCommissioneService,
              private sharedData: DataService) 
  {
    this.loading.emitLoading(true);
  }

  ngOnInit()
  {
    this.loading.emitLoading(true);
    let _this = this;
    this.service.callFindAllegati(this.sharedData.idPratica).subscribe(response =>
    {
      if(response.codiceEsito === CONST.OK)
      {
        _this._files = response.payload;
        _this.files = _this._files.map(m => { return { ...m, numeroIstanze: m.pratiche.length, type: m.tipoAllegato }});
        console.log("FILES: ", _this.files);
      }
      this.loading.emitLoading(false);
    });
  }

  public download(event: SimpleFileCommissioneLocale): void 
  {
    this.loading.emitLoading(true);
    this.service.callDownloadAllegatoSeduta(event.id, event.idSeduta).subscribe(response =>
    {
      if(response.status == 200)
        downloadFile(response.body, event.nome);
      this.loading.emitLoading(false);
    });
  }
  public mostraIstanze(event: SimpleFileCommissioneLocale): void
  {
    //this.nomeSeduta = event.;
    this.fascicoliData = event.pratiche;
    this.display = true;
  }

  public close(): void
  {
    this.display = false;
    this.fascicoliData = null;
  }

  get headers(): Array<TableConfig> { return ConfVerbComLoc.headers; }
  get types()  : DocumentType[] { return CONST.DOCUMENT_CL_TYPE; }
  //get files()  : Array<any> { return this._files ? this._files.map(m => { return { ...m, numeroIstanze: m.pratiche.length }}) : [] };
}
