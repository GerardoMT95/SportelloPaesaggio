import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { DialogButtons, DialogType } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { IndirizziEnti } from 'src/app/shared/models/models';
import { ViewMapper } from 'src/app/shared/util/UtilViewMapper';

@Component({
  selector: 'app-modifica-indirizzi-enti-details-page',
  templateUrl: './modifica-indirizzi-enti-details-page.component.html',
  styleUrls: ['./modifica-indirizzi-enti-details-page.component.scss']
})
export class ModificaIndirizziEntiDetailsPageComponent implements OnInit {
  id: string;
  form: FormGroup;
  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private dialogService: CustomDialogService
  ) {
    this.id = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    //this.store.setBreadcrumbs([{ label: 'Dettaglio' }]);
    this.loadIndirizziEnte();
    this.buildForm();

    this.form.valueChanges.subscribe((val) => {
      console.log('Form value:', val);
    });
  }

  buildForm() {
    this.form = this.formBuilder.group({
      id: [this.id],
      denominazione: [''],
      tipologia: [{ value: '', disabled: true }],
      pec: [''],
      mail: ['']
    });
    ViewMapper.mapObjectToForm(this.form, this.indirizziEnte);
  }

  loadIndirizziEnte() {
    //this.store.getIndirizziEnte(this.id);
  }

  get indirizziEnte(): IndirizziEnti {
    return null;//this.store.state.indirizziEnte;
  }

  salva() {
    this.saveOperation();
    this.dialogService.showDialog(
      true,
      'INDIRIZZI_ENTI_FORM.INDIRIZZI_ENTI_SAVE_MESSAGE',
      'SAVE_DOCUMENT_TITLE',
      DialogButtons.CHIUDI,
      (buttonID: string): void => {
        console.log('Button callback:', buttonID);
      },
      DialogType.SUCCESS,
      'funzioni-di-amministrazione/modifica-indirizzi-enti',
      null
    );
  }

  saveOperation() {
    const indirizziEnte: IndirizziEnti = this.form.value;
    indirizziEnte.tipologia = this.indirizziEnte.tipologia;
    //this.store.editIndirizziEnte(indirizziEnte);
    this.form.markAsUntouched();
    this.form.markAsPristine();
  }

  annulla() {
    this.form.reset();
    setTimeout(() => {
      this.buildForm();
    }, 0);
    console.log('RESET FORM', this.form.value);
  }
}
