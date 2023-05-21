import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { CONST } from '../../constants';

@Component({
  selector: 'app-doc-riconoscimento-form',
  templateUrl: './doc-riconoscimento-form.component.html',
  styleUrls: ['./doc-riconoscimento-form.component.scss']
})
export class DocRiconoscimentoFormComponent implements OnInit {

  @Input("formGroupDocumento") form:FormGroup;
  //nomi dei formcontrol all'interno di formGroupDocument
  @Input("idTipo") idTipo :string;
  @Input("numero") numero :string;
  @Input("dataRilascio") dataRilascio :string;
  @Input("dataScadenza") dataScadenza :string;
  @Input("enteRilascio") enteRilascio :string;
  @Input("validation") validation :boolean;
  @Input() disable :boolean;
  @Input()  tipoDocumentoOptions:SelectItem[];
  const=CONST;

  constructor() { }

  ngOnInit() {
  }

}
