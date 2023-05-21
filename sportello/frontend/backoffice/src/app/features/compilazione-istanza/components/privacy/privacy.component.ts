import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Fascicolo } from 'src/app/shared/models/models';

@Component({
  selector: 'app-privacy',
  templateUrl: './privacy.component.html',
  styleUrls: ['./privacy.component.scss']
})
export class PrivacyComponent implements OnInit {

  @Input() fascicolo: Fascicolo  
  @Input() mainForm: FormGroup;
  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.buildForm();    
    (this.mainForm.get("privacyAccettata") as FormControl).setValue(this.fascicolo.istanza.privacyAccettata);
  }


  buildForm() {
    this.mainForm.addControl("privacyAccettata", this.fb.control('false'));
  }
}
