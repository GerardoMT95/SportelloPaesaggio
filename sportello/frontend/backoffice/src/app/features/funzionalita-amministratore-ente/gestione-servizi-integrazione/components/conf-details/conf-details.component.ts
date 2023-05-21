import { SimpleFormControlData } from './../../models/ente-admin.models';
import { FormGroup } from '@angular/forms';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-conf-details',
  templateUrl: './conf-details.component.html',
  styleUrls: ['./conf-details.component.scss']
})
export class ConfDetailsComponent
{
  public configuration: SimpleFormControlData[] = [];
  public categories: SimpleFormControlData[] = [];

  @Input("form") form: FormGroup;
  @Input("validatiON") validation: boolean = false;
  @Input("conf") set conf(conf: SimpleFormControlData[]) { if (!this.configuration || this.configuration.length === 0) this.handleConfiguration(conf); }

  constructor() { }

  private handleConfiguration(baseconf: SimpleFormControlData[]): void
  {
    if(baseconf)
    {
      this.categories = [];
      this.configuration = [];
      baseconf.forEach(row =>
      {
        if(row.type === "category")
          this.categories.push(row);
        else
          this.configuration.push(row);
      });
      console.log("categories: ", this.categories, " | configuration", this.configuration);
    }
  }
}
