
import { Component, EventEmitter, Input,  OnInit, Output} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { SelectItem } from 'primeng/primeng';
import { Assegnamento, GroupType } from '../../models/models';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-nuova-assegnazione',
  templateUrl: './nuova-assegnazione.component.html',
  styleUrls: ['./nuova-assegnazione.component.scss']
})
export class NuovaAssegnazioneComponent implements OnInit
{
  @Input("display")       display      : boolean;
  @Input("idPratica")     idPratica    : string;
  @Input("codicePratica") codicePratica: string;
  @Input("functionary")   funzionari   : SelectItem[];
  @Input("rup")           rup          : SelectItem[];

  @Output("onSave")       save : EventEmitter<Assegnamento>;
  @Output("onClose")      close: EventEmitter<boolean>;

  public formAssign: FormGroup;
  public submitted: boolean;
  public rupRemapped:SelectItem[]=[];
  public funzionariRemapped:SelectItem[]=[];


  constructor(private formBuilder: FormBuilder,
              private userService: UserService)
  { 
    this.submitted = false;
    this.save = new EventEmitter<Assegnamento>();
    this.close = new EventEmitter<boolean>();
  }


  ngOnInit(): void
  {
    console.log(this.rupRemapped)
    this.buildForm();
    //se un solo rup lo preseleziono.... indifferentemente dall'organizzazione
    if (this.rup && this.rup.length === 1)
      this.formAssign.patchValue({ usernameRup: this.rup[0].value });
  }

  public salva(): void
  {
    this.submitted = true;
    if(this.formAssign.valid)
    {
      this.submitted = false;
      let assegnazione: Assegnamento = this.formAssign.getRawValue();
      assegnazione.idFascicolo = this.idPratica;
      if(assegnazione.usernameRup){
        assegnazione.denominazioneRup = this.rup.find(f => f.value === assegnazione.usernameRup).label;
      }
      assegnazione.denominazioneFunzionario = this.funzionari.find(f => f.value === assegnazione.usernameFunzionario).label;
      this.formAssign.reset();
      this.save.emit(assegnazione);
      /* this.display = false; */
    }
  }

  public chiudi(): void
  {
    this.display = false;
    this.close.emit(true);
  }

  private buildForm(): void
  {
    let rupValidators=[];
    /**
     * rup richiesto solo per ED_ e REG_
     */
    if(this.userService.groupType === GroupType.EnteDelegato ||
        this.userService.groupType === GroupType.Regione ){
          rupValidators=[Validators.required];          
        }
    this.formAssign = this.formBuilder.group
    ({
      usernameRup: new FormControl(null, rupValidators),
      usernameFunzionario: new FormControl(null, [Validators.required])
    });
  }
}
