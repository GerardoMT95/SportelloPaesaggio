import { DialogButtons, DialogType, ButtonType } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { CdsSettings } from './../../models/cds.model';
import { CONST } from './../../../../shared/constants';
import { OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoadingService } from './../../../../shared/services/loading.service';
import { FormBuilder } from '@angular/forms';
import { FascicoloService } from './../../../../shared/services/pratica/http-fascicolo.service';
import { CdsService } from './../../services/cds.service';
import { Component, OnInit, Input } from '@angular/core';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { Fascicolo, StatoEnum } from 'src/app/shared/models/models';

@Component({
  selector: 'app-comitato',
  templateUrl: './comitato.component.html',
  styleUrls: ['./comitato.component.css']
})
export class ComitatoComponent implements OnInit, OnDestroy {

  @Input("id")
  public id: string;
  @Input("fascicolo")
  public fascicolo: Fascicolo;

  private subscriptionAction:Subscription;
  private subscription:Subscription;
  public disable:boolean;
  public idCds:string;

  constructor(private cdsService: CdsService,
    private fascicoloService: FascicoloService,
    private fb: FormBuilder,
    private dialog: CustomDialogService,
    private loading: LoadingService

  ) { }

  ngOnInit() {
    this.disable = !this.fascicolo || !this.fascicolo.rup
                || (this.fascicolo && this.fascicolo.attivitaDaEspletare === StatoEnum.InLavorazione )
                // || (this.fascicolo && this.fascicolo.datiFascicolo && this.fascicolo.datiFascicolo.codiceStato === StatoEnum.archiviata)
                ;
    this.cdsService.getComitato(this.id).subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.idCds = response.payload.id;
      }
    });
    this.cdsService.action.next(this.disable ? "DISABLE_COMITATO_EDIT" : "ENABLE_COMITATO_EDIT");
    this.subscriptionAction = this.cdsService.action.subscribe(action =>{
      switch(action){
        case "ANNULLA_COMITATO":
          this.annulla();
          break;
          break;
        default:
          break;
      }
    });

  }
  
  ngOnDestroy(): void {
    if(this.subscription)
      this.subscription.unsubscribe();
    if(this.subscriptionAction)
      this.subscriptionAction.unsubscribe();
  }

  public salva(dto:CdsSettings):void{
    this.innerSave(dto, false);
  }

  public annulla():void{
    this.dialog.showDialog(true
                          ,"CDS.DIALOG_COMITATO.ANNULLA.CONTENT"
                          ,"CDS.DIALOG_COMITATO.ANNULLA.TITLE"
                          ,DialogButtons.CONFERMA_CHIUDI
                          ,(buttonId)=>{if(buttonId == ButtonType.OK_BUTTON){this.confirmAnnulla()}}
                          ,DialogType.INFORMATION
                          );
  }
  
  private confirmAnnulla():void{
    this.cdsService.action.next("RELOAD_COMITATO");
  }
  public avvia(dto:CdsSettings):void{
    this.dialog.showDialog(true
                          ,"CDS.DIALOG_COMITATO.CONFIRM.CONTENT"
                          ,"CDS.DIALOG_COMITATO.CONFIRM.TITLE"
                          ,DialogButtons.CONFERMA_CHIUDI
                          ,(buttonId)=>{if(buttonId == ButtonType.OK_BUTTON){this.confirmAvvia(dto)}}
                          ,DialogType.INFORMATION
                          );
  }
  private confirmAvvia(dto:CdsSettings):void{
    this.innerSave(dto, true);
  }

  private innerSave(dto:CdsSettings, avvia:boolean):void{
    this.loading.emitLoading(true);
    this.cdsService.save(this.id, dto)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        if(avvia){
          this.cdsService.avvia(this.id, dto.id)
          .subscribe(response =>{
            if(CONST.OK == response.codiceEsito){
              this.dialog.showDialog(true
                                    ,"CDS.DIALOG_COMITATO.AVVIA.CONTENT"
                                    ,"CDS.DIALOG_COMITATO.AVVIA.TITLE"
                                    ,DialogButtons.CHIUDI
                                    ,()=>{}
                                    ,DialogType.SUCCESS
                                    );
              this.cdsService.action.next("DISABLE_COMITATO_EDIT");
              this.fascicoloService.reloadFascicoloEmitter.emit("");
            }else{
              this.loading.emitLoading(false);
            }
          });
        }else{
          this.dialog.showDialog(true
                                 ,"CDS.DIALOG_COMITATO.SAVE.CONTENT"
                                 ,"CDS.DIALOG_COMITATO.SAVE.TITLE"
                                 ,DialogButtons.CHIUDI
                                 ,()=>{}
                                 ,DialogType.SUCCESS
                                 );

          this.loading.emitLoading(false);
        }
      }else{
        this.loading.emitLoading(false);
      }
    })

  }
}
