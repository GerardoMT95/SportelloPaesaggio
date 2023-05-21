import { Component, OnInit, Input, OnChanges, Output, EventEmitter, OnDestroy, Renderer } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnChanges, OnInit, OnDestroy {
  @Input("modal") modal: boolean = true;
  @Input("visible") visible:boolean;
  @Input("type") type:any;
  @Input("extraData") extraData:any;
  @Output("action") action = new EventEmitter<any>();
  @Input("title") title:string = "Informazioni";
  @Input("content") content:string = "Informazioni";
  @Input("hasConfirm") hasConfirm:boolean;
  //gestione dinamica delle label dei bottoni, alla chiusura della finestra vengono rimesse quelle default, quindi 
  //ogni volta che voglio una label (da tradurre) personalizzata, 
  //la setto prima di display=true, in modo che alla chiusura della finestra 
  // questo componente reimmette le label default.
  private static readonly  genericLabelChiudi='generic.chiudi';
  private static readonly  genericLabelOk='generic.ok';
  @Input() labelChiudi:string=AlertComponent.genericLabelChiudi;
  //@Output() labelChiudiChange = new EventEmitter<string>();
  @Input() labelOk:string=AlertComponent.genericLabelOk;
  //@Output() labelOkChange = new EventEmitter<string>();

  constructor(
    private renderer:Renderer,
  ) { }

  getIcon(type:string):string{
    if(type=="success")
      return "check";
    else if(type=="none")
      return "";
    else if(type=="info")
      return "info";
    else if(type=="danger")
      return "info";
    else
      return "exclamation-triangle";
  }

  ngOnChanges(changes) {
    if(this.title=='')
      this.title = "Informazioni";
    if(this.content=='')
      this.content = "Informazioni";
  }
  
  ngOnDestroy(){
    this.renderer.setElementClass(document.body, 'modal-open', false);
  }

  onShow(){
    this.renderer.setElementClass(document.body, 'modal-open', true);
  }

  closeOk(event?: any){
      this.action.emit({
        'isChiuso':false,
        'isConfirm':true,
        'extraData':this.extraData
      });
  }

  close(){
    console.log("CLOSE");
    this.renderer.setElementClass(document.body, 'modal-open', false);
    this.action.emit({'isChiuso':true,'isConfirm':false,'extraData':this.extraData});
  }

  nascondi(){
    this.renderer.setElementClass(document.body, 'modal-open', false);
      this.action.emit({
        'isChiuso':true,
        'isConfirm':false,
        'extraData':this.extraData
      });
  }

  ngOnInit() {
  }

}
