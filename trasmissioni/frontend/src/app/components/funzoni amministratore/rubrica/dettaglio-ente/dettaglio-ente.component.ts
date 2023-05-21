import { CONST } from 'src/shared/constants';
import { SelectItem } from 'primeng/components/common/selectitem';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Ente } from 'src/app/components/model/entity/admin.models';
import { DestinatarioIstituzionaleDto } from 'src/app/components/model/entity/corrispondenza.models';

@Component({
  selector: 'app-dettaglio-ente',
  templateUrl: './dettaglio-ente.component.html',
  styleUrls: ['./dettaglio-ente.component.css']
})
export class DettaglioEnteComponent implements OnInit/* extends AuthComponent */
{
  @Input("dettaglio") dettaglio: DestinatarioIstituzionaleDto;
  @Output("action") action = new EventEmitter<Ente>();

  // Form dettaglio
  public form: FormGroup;

  // dialog for annulla
  public displayBack: boolean = false
  public contentBack: string;
  public titleBack: string;
  public typBack: string = 'info';
  public isConfirmBack: boolean;

  // dialog for : save,  info editor
  public displayEsito: boolean = false
  public contentEsito: string;
  public titleEsito: string;
  public typEsito: string = 'success';
  public isConfirmEsito: boolean;

  public alertData =
  {
    display: false,
    title: "",
    content: "",
    typ: "",
    extraData: null,
    isConfirm: false,
  };

  public const = CONST;
  public valida: boolean = false;
  public tipologiaEnte: SelectItem[] = CONST.listaTipologieMockup;

  constructor(private router: Router,
              private translateService: TranslateService, 
              private fb: FormBuilder)
  {
    /* super(router, lss); */
  }

  ngOnInit()
  {
    this.buildForm();
  }

  /**
* Build form
*/
  buildForm(): void
  {
    this.form = this.fb.group
    ({
      id: [this.dettaglio.id],
      nome: [this.dettaglio.nome],
      tipoEnte: [this.dettaglio.tipoEnte, [Validators.required]],
      pec: [this.dettaglio.pec, [Validators.email]],
      email: [this.dettaglio.email, [Validators.email]]
    })
  }

  /**
   * Open dialog for confirm go back
   */
  public back(): void
  {
    this.alertData =
    {
      title: this.translateService.instant('indirizziEnti.dialog.back.title'),
      content: this.translateService.instant('indirizziEnti.dialog.back.content'),
      typ: "info",
      isConfirm: true,
      extraData: { operazione: "save" },
      display: true
    };
  }

  /**
  * Open dialog confirm save
  */
  public openSalva()
  {
    this.valida = true;
    if(this.form.valid)
    {
      this.valida = false;
      this.alertData =
      {
        title: this.translateService.instant('indirizziEnti.dialog.salva.title'),
        content: this.translateService.instant('indirizziEnti.dialog.salva.content'),
        typ: "info",
        isConfirm: true,
        extraData: { operazione: "save" },
        display: true
      };
    }
    
  }

  callbackAlert(event: any)
  {
    this.alertData.isConfirm = false;
    //callbackDialog(event:any):void{
    //console.log(event);
    if (event.isChiuso)
    {
      this.alertData.display = false;
    }
    else if (event.isConfirm)
    {
      if (event.extraData && event.extraData.operazione)
      {
        switch (event.extraData.operazione)
        {
          case 'goBack':
            this.action.emit(null);
            break;
          case 'save':
            this.action.emit(this.form.getRawValue());
            break;
          default:
            break;
        }
      }
      this.alertData.display = false;
    }
  }

}
