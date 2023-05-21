import { TemplateComunicazioni, TipoTemplate } from './../../../../../components/model/entity/admin.models';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-templates',
  templateUrl: './admin-templates.component.html',
  styleUrls: ['./admin-templates.component.css']
})
export class AdminTemplatesComponent implements OnInit {

  public colonneTabella: any[] =
  [
    { header: "Nome" },
    { header: "Descrizione" },
  ];
  public listaTemplate: Array<TemplateComunicazioni> = [];
  public modifica: boolean = false;

  public page: number = 1;
  public rows: number = 5;

  constructor() { }

  ngOnInit() {
    this.initTable();
  }

  public initTable(): void {
    let template: TemplateComunicazioni = {
      codice: TipoTemplate.INVIO_COMUNICAZIONI,
      nome: "nome template di prova",
      descrizione: "descrizione template di prova",
      oggetto: "",
      testo: ""
    };
    this.listaTemplate.push(template);
  }

  public updateData(event: any): void {
    console.log(event);
  }

  public edit(event: any): void {
    this.modifica = true;
  }

  public changeRows(rows: number) 
  {
    this.rows = rows;
    this.updateData({}); 
  }

  public changePage(event: any)
  {
    this.page = event;
    this.updateData({});
  }

  get listaTemplateTR(): number { return this.listaTemplate ? this.listaTemplate.length : 0 }

}
