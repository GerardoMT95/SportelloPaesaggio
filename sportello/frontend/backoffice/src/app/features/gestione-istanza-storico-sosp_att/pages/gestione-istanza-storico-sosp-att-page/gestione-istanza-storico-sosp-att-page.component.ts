import { Component, OnInit } from '@angular/core';
import { downloadFile } from 'src/app/core/functions/generic.utils';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { Allegato, StoricoASR } from './../../../../shared/models/models';
import { HttpAllegatoService } from './../../../../shared/services/http-allegato.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { DataService } from './../../../gestione-istanza/services/data-service/data.service';

@Component({
  selector: 'app-gestione-istanza-storico-sosp-att-page',
  templateUrl: './gestione-istanza-storico-sosp-att-page.component.html',
  styleUrls: ['./gestione-istanza-storico-sosp-att-page.component.scss']
})
export class GestioneIstanzaStoricoSospAttPageComponent implements OnInit
{
  public richieste: StoricoASR[] = [];

  constructor(private sharedData: DataService,
              private loading: LoadingService,
              private allegatoService: AllegatoService) { }

  public ngOnInit(): void
  {
    this.richieste = this.lavoraDati();
    console.log("Richieste: ", this.richieste);
  }

  private lavoraDati(): StoricoASR[] 
  {
    let richieste = this.sharedData.fascicolo.storicoASR;
    if(richieste)
    {
      richieste = richieste.sort((arg1, arg2)=> 
      {
        let direction: number = 1;
        if(arg1.data === arg2.data)
        {
          if(arg1.type != arg2.type)
          {
            if (arg1.type === 'ARCHIVIAZIONE' && arg2.type !== 'ARCHIVIAZIONE' ||
                arg1.type === 'ATTIVAZIONE' && arg2.type === 'SOSPENSIONE')
              direction = -1;
            else if (arg2.type === 'ARCHIVIAZIONE' && arg1.type !== 'ARCHIVIAZIONE' ||
              arg2.type === 'ATTIVAZIONE' && arg1.type === 'SOSPENSIONE')
              direction = 1;
          }
        }
        else
          direction = arg1.data >= arg2.data ? -1 : 1;
        return direction;
      });
    } 
    return richieste;
  }

  public downloadAttachment(event: Allegato): void
  {
    this.loading.emitLoading(true);
    
    //this.allegatoService.callDownloadAllegato(event.id as string).subscribe(response =>
    this.allegatoService.downloadAllegatoFascicolo(event.id as string,this.sharedData.idPratica, '/istruttoria/allegati/download.pjson')
    .subscribe(response=>
    {
      if(response.status == 200)
        downloadFile(response.body, event.nome);
      this.loading.emitLoading(false);
    });
  }
}
