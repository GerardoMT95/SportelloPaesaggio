import { Component, OnInit } from '@angular/core';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
  selector: 'app-manuale',
  templateUrl: './manuale.component.html',
  styleUrls: ['./manuale.component.css']
})
export class ManualeComponent implements OnInit {

  constructor(
    private loadingService: LoadingService
    ,private allegatoService:AllegatoService
  ) { }

  ngOnInit() {
    setTimeout(() => {
      this.loadingService.emitLoading(false);
    });
  }

  public download():void{
    this.loadingService.emitLoading(true);
    this.allegatoService.downloadManuale()
    .subscribe(data =>{
      let blob:Blob = data.body;
      this.loadingService.emitLoading(false);
        let fileName : string = "Manuale Utente.pdf"
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
