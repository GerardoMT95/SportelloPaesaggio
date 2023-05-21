import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SelectItem } from 'primeng/components/common/selectitem';
import { SelectItemGroup } from 'primeng/primeng';
import { paths } from 'src/app/app-routing.module';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { GruppiRuoliDTO } from 'src/app/shared/components/model/logged-user';
import { CONST } from 'src/app/shared/constants';
import { GestioneGruppoService } from 'src/app/shared/services/gestione-gruppo/gestione-gruppo.service';
import { UserService } from 'src/app/shared/services/user.service';
import { LoadingService } from './../../../../shared/services/loading.service';
import { LocalSessionServiceService } from './../../../../shared/services/local-session-service.service';

@Component({
  selector: 'app-gestione-gruppo',
  templateUrl: './gestione-gruppo.component.html',
  styleUrls: ['./gestione-gruppo.component.css']
})
export class GestioneGruppoComponent implements OnInit
{
  public ruoloScelto: any;
  public gruppiERuoliAssociati: SelectItemGroup[] = [];

  constructor(private lss: LocalSessionServiceService,
              private user: UserService,
              private router: Router,
              private route: ActivatedRoute,
              private dialog: CustomDialogService,
              private loading: LoadingService,
              private gestioneGruppoService: GestioneGruppoService) { }

  ngOnInit(): void { 
    this.init(); 
  }

  public scegliGruppo(): void
  {
    this.controllaGruppo(this.ruoloScelto);
  }

  private controllaGruppo(gruppoScelto: string): void
  {
    this.loading.emitLoading(true);
    this.gestioneGruppoService.controllaGruppo(gruppoScelto).subscribe(response =>
    {
      this.loading.emitLoading(false);
      if (response.codiceEsito === CONST.OK)
      {
        let urlDiPartenza = this.route.snapshot.paramMap.get('previousUrl');
        
        this.user.setActualGroup(gruppoScelto, response.payload);
        if (urlDiPartenza)
        {
          this.router.navigateByUrl(urlDiPartenza);
        } else
        {
          //TODO if perchè a seconda del gruppo si dovrebbe vedere qualcosa di diverso
          this.router.navigate(["gestione-istanze"]);
        }
      }
      else
      {
        this.dialog.showDialog(true, "Errore", response.descrizioneEsito, DialogButtons.CHIUDI, null, DialogType.ERROR);
      }
    },
      error =>
      {
        this.loading.emitLoading(false);
        this.dialog.showDialog(true, "Errore", error.message, DialogButtons.CHIUDI, null, DialogType.ERROR);
      });
  }

  public init(): void
  {
    let _self = this;
    let groups: any[];
    _self.loading.emitLoading(true);
    _self.gestioneGruppoService.readGruppi().subscribe(response =>
    {
      _self.loading.emitLoading(false);
      if (response.codiceEsito === CONST.OK)
      {
        this.lss.storeValue(LocalSessionServiceService.USER_GROUPS_KEY, response.payload);
        if (!response.payload || response.payload.length === 0)
          _self.router.navigate([paths.richiestaAbilitazione()]);
        else if (response.payload.length > 1 || response.payload[0].ruoli.length > 1)
        {
          response.payload.forEach(elem =>
          {
            _self.gruppiERuoliAssociati.push({
              label: elem.gruppo,
              items: _self.riempiRuoli(elem.gruppo, response.payload)
            });
          });
          //let hashCode =(s: string):number => s.split('').reduce((a, b) => { a = ((a << 5) - a) + b.charCodeAt(0); return a & a }, 0)

          const compareFunction = (a: string, b: string): number =>
          {
            if(a == b)
              return 0;
            if(a === null)
              return 1;
            if(b === null)
              return -1;
            let new_a = new String(a);
            return new_a.localeCompare(b);
          } 
          _self.gruppiERuoliAssociati.sort((a, b) => { return compareFunction(a.value, b.value)  });
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
          this.ruoloScelto = response.payload[0].ruoli[0].value;
          this.scegliGruppo();
        }
        
      }
    },
    error =>
    {
      this.loading.emitLoading(false);
      this.dialog.showDialog(true, "Errore", error.message, DialogButtons.CHIUDI, null, DialogType.ERROR);
    });
  }

  private riempiRuoli(nomeGruppoScelto: string, gruppiERuoli: GruppiRuoliDTO[]): SelectItem[]
  {
    let ruoli: SelectItem[] = [];
    let gruppoScelto: GruppiRuoliDTO = gruppiERuoli.find(gruppo => gruppo.gruppo === nomeGruppoScelto);
    gruppoScelto.ruoli.forEach(ruolo =>
    {
      ruoli.push({
        label: ruolo.value.startsWith("CL_") ? "Membro commissione locale" : ruolo.description,
        value: ruolo.value
      });
    });
    return ruoli;
  }

}
