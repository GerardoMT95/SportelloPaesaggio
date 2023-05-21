import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
/**
 * service per indicare il termine del caricamento del form
 */
@Injectable()
export class IstanzaFormService 
{
  constructor(){
    this.richiedenteFormComplete$.subscribe(()=>console.log('richiedenteFormComplete'));
    this.dichiarazioniFormComplete$.subscribe(()=>console.log('dichiarazioniFormComplete'));
    this.altriTitolariFormComplete$.subscribe(()=>console.log('altriTitolariFormComplete'));
    this.localizzazioneFormComplete$.subscribe(()=>console.log('localizzazioneFormComplete'));
    this.tecnicoIncaricatoFormComplete$.subscribe(()=>console.log('tecnicoIncaricatoFormComplete'));
  }
  public richiedenteFormComplete$=new Subject<boolean>();
  public dichiarazioniFormComplete$=new Subject<boolean>();
  public altriTitolariFormComplete$=new Subject<boolean>();
  public localizzazioneFormComplete$=new Subject<boolean>();
  public tecnicoIncaricatoFormComplete$=new Subject<boolean>();
  
}

@Injectable()
export class SchedaTecnicaFormService 
{
  public descrizioneFormComplete$=new Subject<boolean>();
  public destinazioneUrbanisticaFormComplete$=new Subject<boolean>();
  public leggittimitaUrbanisticaFormComplete$=new Subject<boolean>();
  public procedureEdilizieFormComplete$=new Subject<boolean>();
  public inquadramentoFormComplete$=new Subject<boolean>();//solo per tipo_procedimento == 2
  public effettiConsMitigazioneFormComplete$=new Subject<boolean>();//solo per tipo_procedimento == 2
  public eventualiProcedimentiFormComplete$=new Subject<boolean>();
  public pareriAttiFormComplete$=new Subject<boolean>();
  public pptrFormComplete$=new Subject<boolean>();
  public vincolisticaFormComplete$=new Subject<boolean>();
  public dichiarazioneFormComplete$=new Subject<boolean>();

  public pareriTableComplete$=new Subject<boolean>();
  public attiTableComplete$=new Subject<boolean>();
  public leggittimitaTableComplete$=new Subject<boolean>();
  public emettiBuildTabella(nomeTabella:string){
    console.log('emetto'+nomeTabella)
    switch (nomeTabella) {
      case 'attiInfo':
        this.attiTableComplete$.next(true);break;
      case 'parreriInfo':
        this.pareriTableComplete$.next(true);break;
      case 'leggittimita':
          this.leggittimitaTableComplete$.next(true);break;  
        default:
        break;
    }
  }
}
