import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup} from '@angular/forms';

@Component({
  selector: 'app-indirizzi-enti-details',
  templateUrl: './indirizzi-enti-details.component.html',
  styleUrls: ['./indirizzi-enti-details.component.scss']
})
export class IndirizziEntiDetailsComponent implements OnInit {
  @Input() form: FormGroup;
  @Output() saveEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output() resetEmitter: EventEmitter<any> = new EventEmitter<any>();
  constructor() { }

  ngOnInit() {
  }

  save() {
    this.saveEmitter.emit();
  }
  reset() {
    this.resetEmitter.emit();
  }

}
