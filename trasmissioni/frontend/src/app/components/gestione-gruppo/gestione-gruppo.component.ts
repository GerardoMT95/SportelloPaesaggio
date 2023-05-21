import { CONST } from 'src/shared/constants';
import { GruppiRuoliDTO } from './../../../shared/models/plain-type-string-id.model';
import { UserService } from 'src/app/services/user.service';
import { GestioneGruppoService } from './../../services/gestione-gruppo.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalSessionServiceService } from './../../services/local-session-service.service';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Component, OnInit } from '@angular/core';
import { SelectItemGroup } from 'primeng/primeng';

@Component({
  selector: 'app-gestione-gruppo',
  templateUrl: './gestione-gruppo.component.html',
  styleUrls: ['./gestione-gruppo.component.css']
})
export class GestioneGruppoComponent implements OnInit
{
  public ruoloScelto: any;
  public gruppiERuoliAssociati: SelectItemGroup[] = [];

  public alertData = {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  constructor(private lss: LocalSessionServiceService,
              private user: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private gestioneGruppoService: GestioneGruppoService) { }

  ngOnInit()
  {
    this.init();
  }

  public scegliGruppo(): void
  {
    this.user.actualGroup = this.ruoloScelto;
    this.controllaGruppo(this.ruoloScelto);
  }

  private controllaGruppo(gruppoScelto: string): void
  {
    this.gestioneGruppoService.controllaGruppo(gruppoScelto).subscribe(response =>
    {
      let valid: boolean = false;
      if (response.codiceEsito === CONST.OK)
      {
        valid = true;
      }
      this.user.validGroup = valid;
      let urlDiPartenza=this.route.snapshot.paramMap.get('previousUrl');
      if(urlDiPartenza){
        this.router.navigate([urlDiPartenza]);
      }else{
        this.router.navigate(["private/fascicolo"]);  
      }
    });
  }

  public init(): void
  {
    let _self = this;
    let ugk = LocalSessionServiceService.USER_GROUPS_KEY;
    _self.gestioneGruppoService.readGruppi().subscribe(response =>
    {
      this.lss.storeValue(ugk, response.payload);
      if (response.codiceEsito === CONST.OK)
      {
        if(!response.payload || response.payload.length === 0)
          _self.router.navigate(['/richiesta-abilitazione']);
        else if(response.payload.length > 1 || response.payload[0].ruoli.length > 1)
        {
          response.payload.forEach(elem =>
          {
            _self.gruppiERuoliAssociati.push({
              label: elem.gruppo,
              items: _self.riempiRuoli(elem.gruppo, response.payload)
            });
          });
          const compareFunction = (a: string, b: string): number =>
          {
            if(a.toUpperCase() === 'AMMINISTRATORE')
              return -1;
            if(b.toUpperCase() === 'AMMINISTRATORE')
              return 1;
            if(a == b)
              return 0;
            if(a === null)
              return 1;
            if(b === null)
              return -1;
            let new_a = new String(a);
            return new_a.localeCompare(b);
          } 
          _self.gruppiERuoliAssociati.sort((a, b) => { return compareFunction(a.label, b.label)  });
          _self.gruppiERuoliAssociati.forEach(gr =>
          {
            gr.items.sort((i1: SelectItem, i2: SelectItem) =>
            {
              let utils: {[key: string]: number} = {"funzionario": 1, "dirigente": 2, "amministratore": 3};
              let v1 = 4, v2 = 4;
              if(Object.keys(utils).includes(i1.label.toLocaleLowerCase())) v1 = utils[i1.label.toLowerCase()];
              if(Object.keys(utils).includes(i2.label.toLocaleLowerCase())) v2 = utils[i2.label.toLowerCase()];
              return v1 - v2;
            });
          });
          let gruppoUtentePredefinito = this.route.snapshot.paramMap.get('gruppoUtentePredefinito');
          if(gruppoUtentePredefinito && gruppoUtentePredefinito){
            this.ruoloScelto=gruppoUtentePredefinito;
            this.scegliGruppo();
          }
        }
        else
        {
          this.ruoloScelto = response.payload[0].ruoli[0].id;
          this.scegliGruppo();
        }
      }
    },
    error =>
    {
      this.alertData = 
      {
        display: true,
        title: "Errore",
        content: error.message,
        typ: "danger",
        extraData: null,
        isConfirm: false,
      }
    });
  }

  private riempiRuoli(nomeGruppoScelto: string, gruppiERuoli: GruppiRuoliDTO[]): SelectItem[]
  {
    let ruoli: SelectItem[] = [];
    let gruppoScelto: GruppiRuoliDTO = gruppiERuoli.find(gruppo => gruppo.gruppo === nomeGruppoScelto);
    gruppoScelto.ruoli.forEach(ruolo =>
    {
      ruoli.push({
        label: ruolo.nome,
        value: ruolo.id
      });
    });
    return ruoli;
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    if (event.isChiuso)
      this.alertData.display = false;
    else if (event.isConfirm && event.extraData && event.extraData.operazione)
    {
      switch (event.extraData.operazione)
      {
        case 'deleteFascicolo':
          break;
        default:
          this.alertData.isConfirm = false;
          this.alertData.extraData = null;
          this.alertData.display = false;
          break;
      }
    }
    else
    {
      this.alertData.isConfirm = false;
      this.alertData.extraData = null;
      this.alertData.display = false;
    }

  }

}
