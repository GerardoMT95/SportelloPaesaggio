import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-relazione-ente',
  templateUrl: './relazione-ente.component.html',
  styleUrls: ['./relazione-ente.component.scss']
})
export class RelazioneEnteComponent implements OnInit
{
  @Input("form") form: FormGroup;
  @Input("trasmetti") trasmetti: boolean;
  @Input("blocca") blocca: boolean;

  constructor() { }

  ngOnInit()
  {

  }

}
