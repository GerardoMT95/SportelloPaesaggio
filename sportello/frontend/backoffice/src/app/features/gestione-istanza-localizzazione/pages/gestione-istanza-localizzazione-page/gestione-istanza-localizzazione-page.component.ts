import { Localizzazione, Particella } from './../../../../core/models/fascicolo.model';
import { FascicoloState } from './../../../../core/models/fascicolo-store';
import { FormGroup, FormControl, FormBuilder, FormArray } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Fascicolo } from 'src/app/shared/models/models';
import { DataService } from 'src/app/features/gestione-istanza/services/data-service/data.service';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';
import { AbstractInputPage } from 'src/app/core/pages/abstract-input-page';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestione-istanza-localizzazione-page',
  templateUrl: './gestione-istanza-localizzazione-page.component.html',
  styleUrls: ['./gestione-istanza-localizzazione-page.component.scss']
})
export class GestioneIstanzaLocalizzazionePageComponent extends AbstractInputPage implements OnInit {

  public fascicolo=null;
  public validation=null;
  constructor(  private sharedData: DataService,
                public dialogService: CustomDialogService,
                public router: Router,
                public fb: FormBuilder) {
    super(dialogService, fb, router);
  }

  ngOnInit() {
    this.mainForm = this.fb.group({
      valida: [false],
      altriTitolari: this.fb.array([])
    });
    /** TODO. Delete this when everything is tested. */
    this.mainForm.disable();
   this.fascicolo=this.sharedData.fascicolo;
   ViewMapper.mapObjectToForm(this.mainForm, this.fascicolo);
  }


}
