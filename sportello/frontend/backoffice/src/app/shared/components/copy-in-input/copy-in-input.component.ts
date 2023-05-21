import { TranslateService } from '@ngx-translate/core';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-copy-in-input',
  templateUrl: './copy-in-input.component.html',
  styleUrls: ['./copy-in-input.component.css']
})
export class CopyInInputComponent implements OnInit {

  public visible:boolean = false;
  public title:boolean = false;
  public valore:string;
  
  @Input() form: FormGroup;
  @Input() name: string;
  @Input() label: string;
  @Input() required: boolean;
  @Input() showInfo: boolean;
  @Output("infoAction") info = new EventEmitter<string>();

  constructor(private translate:TranslateService) { }

  ngOnInit() {
    this.translate.get("BUTTONS.COPY").subscribe(label =>{
      this.title = label;
    })
  }

  public open():void{
    this.valore = this.form.controls[this.name].value;
    this.visible = true;
  }
  public closeOk():void{
    this.form.controls[this.name].setValue(this.valore);
    this.visible = false;
  }
  
  public closeKo():void{
    this.visible = false;
  }

  public clickInfo():void{
    this.info.emit("");
  }
}
