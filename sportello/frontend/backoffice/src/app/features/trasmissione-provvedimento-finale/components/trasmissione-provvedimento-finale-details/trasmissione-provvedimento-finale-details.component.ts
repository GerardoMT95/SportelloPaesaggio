import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectOption } from 'src/app/shared/components/select-field/select-field.component';
import { CONST } from 'src/app/shared/constants';

@Component({
  selector: 'app-trasmissione-provvedimento-finale-details',
  templateUrl: './trasmissione-provvedimento-finale-details.component.html',
  styleUrls: ['./trasmissione-provvedimento-finale-details.component.scss']
})
export class TrasmissioneProvvedimentoFinaleDetailsComponent implements OnInit
{
  @Input() form: FormGroup;
  @Input() validation: boolean;
  const=CONST;

  esitoList: SelectOption[];

  constructor() { }

  ngOnInit()
  {
    this.esitoList = [
      { value: "AUTORIZZATO", description: 'Autorizzato' },
      { value: "NON_AUTORIZZATO", description: 'Non Autorizzato' },
      { value: "AUT_CON_PRESCRIZ", description: 'Autorizzato con Prescrizioni' }
    ];
  }

}
