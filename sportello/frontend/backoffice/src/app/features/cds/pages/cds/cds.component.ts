import { Validators } from '@angular/forms';
import { requiredDependsOn } from 'src/app/shared/validators/customValidator';
import { DialogType, ButtonType, DialogButtons } from './../../../../shared/components/confirmation-dialog/confirmation-dialog.component';
import { LoadingService } from './../../../../shared/services/loading.service';
import { CONST } from './../../../../shared/constants';
import { Subject } from 'rxjs';

import { Subscription, combineLatest } from 'rxjs';
import { Form, FormBuilder, FormGroup, Validator } from '@angular/forms';
import { CdsSettings, CdsSelectItem, Cds, CdsListItem, CdsDocument } from './../../models/cds.model';
import { FascicoloService } from './../../../../shared/services/pratica/http-fascicolo.service';
import { CdsService } from './../../services/cds.service';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { query } from '@angular/animations';
import { Fascicolo, GroupType, StatoEnum, TipoContenuto } from 'src/app/shared/models/models';
import { CustomDialogService } from 'src/app/core/services/dialog.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from 'src/app/features/gestione-istanza/services';
import { UserService } from 'src/app/shared/services/user.service';
import { TranslateService } from '@ngx-translate/core';
import { TableConfig } from 'src/app/core/models/header.model';
import { AllegatoService } from 'src/app/shared/services/allegato.service';
import { HttpDominioService } from 'src/app/shared/services/http-dominio.service';


@Component({
  selector: 'app-cds',
  templateUrl: './cds.component.html',
  styleUrls: ['./cds.component.css']
})
export class CdsComponent implements OnInit, OnDestroy {

 public id:string;
 public fascicolo:Fascicolo;
 
 public idCds:string;

 public hasCds:boolean;
 public cdsDocuments:CdsDocument[];
 public cdsDocumentSelected:string[] = [];

 public tableHeadersCds: TableConfig[] = [];

 public disable:boolean = false;
 
 private subscriptionAction:Subscription;
 
 public enableInsert:boolean = false;
 public list:boolean = true;
 public items:CdsListItem[];
 public tableHeaders:string[] = ["CDS.TABLE.TIPO_CONFERENZA"
                                ,"CDS.TABLE.ATTIVITA"
                                ,"CDS.TABLE.AZIONE"
                                ,"CDS.TABLE.AVVIATA"
                                ,"CDS.TABLE.IDCDS"
                                ,"CDS.TABLE.AZIONI"
                                ];

  constructor(private cdsService:CdsService,
              private fascicoloService: FascicoloService,
              private fb:FormBuilder,
              private dialog:CustomDialogService,
              private loading:LoadingService,
              private route: ActivatedRoute,
              public data: DataService,
              private router: Router,
              public userSvc: UserService,
              public translate: TranslateService,
              public allegatoSvc: AllegatoService,
              public dominioSvc: HttpDominioService
  ) { }

  ngOnInit() {
    this.fascicolo = this.data.fascicolo;
    this.id = this.data.idPratica;
    this.canCreateConferenza(true);
    this.subscriptionAction = this.cdsService.action.subscribe(action =>{
      switch(action){
        case "ANNULLA":
          this.annulla();
          break;
        case "INDIETRO":
          this.backToList();
          break;
        default:
          break;
      }
    });
  }
  
  ngOnDestroy(): void {
    this.subscriptionAction.unsubscribe();
  }

  get typeGroup(): GroupType { return this.userSvc.groupType; }

  private canCreateConferenza(loading:boolean = false){
    if(loading)
      this.loading.emitLoading(true);
    this.cdsService.canCreateConferenza(this.id).subscribe(response => {
      if(CONST.OK == response.codiceEsito){
        this.disable = !(response.payload && this.fascicolo.attivitaDaEspletare === StatoEnum.InLavorazione);
        this.getList(loading);
      }
    })
  }

  private getList(loading:boolean = false):void{
    if(loading)
      this.loading.emitLoading(true);
      this.cdsService.listCds(this.id)
      .subscribe(response =>{
        if(CONST.OK == response.codiceEsito){
          this.items = response.payload;
          if(this.items){
            this.enableInsert = true;
            for(let idx = 0; idx < this.items.length && this.enableInsert == true; idx++){
              this.enableInsert = this.items[idx].completed;
            }
          }else{
            this.enableInsert = true;
          }
          if(loading){
            this.cdsService.action.next("BACK_LIST");
            this.list = true;
          }
          this.loadCds(loading)
      }

      console.log(this.enableInsert);
    })
  }

  private loadCds(loading:boolean = false):void{
    if(loading)
      this.loading.emitLoading(true);
    let obsHasCds$ = this.cdsService.hasCds(this.id);
    this.loading.emitLoading(true);
    combineLatest([obsHasCds$])
      .subscribe(([respHasCds]) => {
        if(CONST.OK == respHasCds.codiceEsito)
          this.hasCds = respHasCds.payload;
          console.log(this.hasCds);
        if(this.hasCds){
          this.cdsService.listDocumentiCds(this.id).subscribe(response =>{
            if(CONST.OK == response.codiceEsito){
              this.cdsDocuments = response.payload;
              console.log(this.cdsDocuments);
              this.loadCdsHeader();
            }
            if(loading)
              this.loading.emitLoading(false);
          });
        }else{
          if(loading)
            this.loading.emitLoading(false);
        }
      });
  }

  public salva(dto:CdsSettings):void{
    this.innerSave(dto, false);
  }

  public annulla():void{
    this.dialog.showDialog(true
                          ,"CDS.DIALOG.ANNULLA.CONTENT"
                          ,"CDS.DIALOG.ANNULLA.TITLE"
                          ,DialogButtons.CONFERMA_CHIUDI
                          ,(buttonId)=>{if(buttonId == ButtonType.OK_BUTTON){this.confirmAnnulla()}}
                          ,DialogType.INFORMATION
                          );
  }
  
  private confirmAnnulla():void{
    this.loading.emitLoading(true);
    this.cdsService.delete(this.id, this.idCds)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito){
        this.getList(true);
      }else{
        this.loading.emitLoading(false);
      }
    })
  }
  public avvia(dto:CdsSettings):void{
    this.dialog.showDialog(true
                          ,"CDS.DIALOG.CONFIRM.CONTENT"
                          ,"CDS.DIALOG.CONFIRM.TITLE"
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
      if(CONST.OK == response.codiceEsito && response.descrizioneEsito != "INVALID_USERS"){
        if(avvia){
          this.cdsService.avvia(this.id, dto.id)
          .subscribe(response =>{
            if(CONST.OK == response.codiceEsito){
              this.dialog.showDialog(true
                                    ,"CDS.DIALOG.AVVIA.CONTENT"
                                    ,"CDS.DIALOG.AVVIA.TITLE"
                                    ,DialogButtons.CHIUDI
                                    ,()=>{}
                                    ,DialogType.SUCCESS
                                    );
              this.getList();
              this.cdsService.action.next("DISABLE_EDIT");
              this.fascicoloService.reloadFascicoloEmitter.emit("");
            }else{
              this.loading.emitLoading(false);
            }
          });
        }else{
          this.dialog.showDialog(true
                                 ,"CDS.DIALOG.SAVE.CONTENT"
                                 ,"CDS.DIALOG.SAVE.TITLE"
                                 ,DialogButtons.CHIUDI
                                 ,()=>{}
                                 ,DialogType.SUCCESS
                                 );

          this.loading.emitLoading(false);
        }
      }else{
        this.dialog.showDialog(true
          ,"CDS.DIALOG.INVALID_USER.CONTENT"
          ,"CDS.DIALOG.INVALID_USER.TITLE"
          ,DialogButtons.CHIUDI
          ,(buttonId)=>{if(buttonId == ButtonType.OK_BUTTON){}}
          ,DialogType.ERROR
          );
        this.loading.emitLoading(false);
      }
    })

  }

  public nuovaConferenza():void{
    this.loading.emitLoading(true);
    this.cdsService.newCds(this.id)
    .subscribe(response =>{
      if(CONST.OK == response.codiceEsito && response.descrizioneEsito != "INVALID_CREATOR"){
        this.idCds = response.payload.id;
        this.list = false;
        this.loading.emitLoading(false);
      }else{
        this.dialog.showDialog(true
          ,"CDS.DIALOG.INVALID_CREATOR.CONTENT"
          ,"CDS.DIALOG.INVALID_CREATOR.TITLE"
          ,DialogButtons.CHIUDI
          ,(buttonId)=>{if(buttonId == ButtonType.OK_BUTTON){}}
          ,DialogType.ERROR
          );
        this.loading.emitLoading(false);
      }
    });
  }

  public downloadCds(dto:CdsDocument):void{
    this.loading.emitLoading(true);
    this.cdsService.downloadDocumentCds(this.id,dto.id).subscribe((data) => {
      console.log(data);
      if(data.statusText == CONST.OK){
        this.loading.emitLoading(false);
        this.allegatoSvc.downloadElemento(data.body, dto.nome);
      }
    });
  }

  public backToList():void{
    this.getList(true);
  }

  public detail(item:CdsListItem):void{
    this.idCds = item.id;
    this.list = false;
  }
  
  public goToConference(item:CdsListItem):void{
    this.cdsService.goToConference(item.idCds);
  }

  private loadCdsHeader():void{
    this.tableHeadersCds = [];
    this.tableHeadersCds = [
      {
        field: "idConferenza",
        header: this.translate.instant("CDS.TABLE.ID_CONFERENZA"),
      },
      {
        field: "riferimentoConferenza",
        header: this.translate.instant("CDS.TABLE.RIFERIMENTO_CONFERENZA"),
      },
      {
        field: "statoConferenza",
        header: this.translate.instant("CDS.TABLE.STATO_CONFERENZA"),
      },
      {
        field: "nome",
        header: this.translate.instant("CDS.TABLE.NOME_FILE"),
      },
      {
        field: "categoria",
        header: this.translate.instant("CDS.TABLE.CATEGORIA"),
      },
      {
        field: "tipo",
        header: this.translate.instant("CDS.TABLE.TIPO"),
      },
      {
        field: "protocollo",
        header: this.translate.instant("CDS.TABLE.PROTOCOLLO"),
      },
      {field: "azioni",header: ""}
    ];
  }


}
