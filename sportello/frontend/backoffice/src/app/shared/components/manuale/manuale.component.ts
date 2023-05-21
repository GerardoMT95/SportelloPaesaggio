import { Component, OnInit } from '@angular/core';
import * as LabelClass from 'esri/layers/support/LabelClass';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { DownloadManualeBean } from '../model/model';
import { ManualeService } from './manuale.service';

@Component({
  selector: 'app-manuale',
  templateUrl: './manuale.component.html',
  styleUrls: ['./manuale.component.css']
})
export class ManualeComponent implements OnInit {

  listManuali: DownloadManualeBean[] = [];

  constructor(
    private loadingService: LoadingService
    ,private allegatoService:AllegatoService
    ,private manualeService: ManualeService
  ) { }

  ngOnInit() {
    this.loadingService.emitLoading(true);
    this.manualeService.getDownloadUrl().subscribe(response => {
      if(response && response.payload){
        this.listManuali = response.payload;
      }
      this.loadingService.emitLoading(false);
    });
  }

  public download(label: string, url: string):void{
    this.loadingService.emitLoading(true);
    this.manualeService.downloadManuale(url)
    .subscribe(data =>{
      console.log(data);
      let blob:Blob = data.body;
      this.loadingService.emitLoading(false);
        let fileName = label.replace('Download ', '') + '.pdf';
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob, fileName);
        } else {
          var link = document.createElement("a");
          if (link.download !== undefined) {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", fileName);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          }
      }
    }, error => {
      this.loadingService.emitLoading(false);
    }, () => {
      this.loadingService.emitLoading(false);
    });
    ;
  }
}
