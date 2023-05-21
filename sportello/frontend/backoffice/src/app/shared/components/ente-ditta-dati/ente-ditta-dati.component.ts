import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { HttpDominioService } from '../../services/http-dominio.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-ente-ditta-dati',
  templateUrl: './ente-ditta-dati.component.html',
  styleUrls: ['./ente-ditta-dati.component.scss']
})
export class EnteDittaDatiComponent implements OnInit
{

  @Input() formGroupSet: FormGroup;
  //nomi dei formControl all'interno del formgroup
  @Input() nelCaso: string;
  @Input() inQualitaDi: string;
  @Input() descAltroDitta: string;
  @Input() societa: string;
  @Input() partitaIva: string;
  @Input() societaCodiceFiscale: string;
  @Input() validation: boolean;
  public tipoAziendaPrivato :boolean = false;
  public tipoAziendaPublic :boolean = false;
  public tipi_ditta$: Observable<any[]>;
  

  @Input() disable: boolean = false; //eventuale disabilitazione dall'esterno
  constructor(
    private httpService: HttpDominioService,
  ) { }

  ngOnInit()
  {
    this.tipi_ditta$ = this.httpService.getDominio('ruoloDitta');
    this.activeExtraField();
  }

  public activeExtraField(): void
  {
    if (!this.disable)
    {
      if (this.formGroupSet.get(this.nelCaso).value)
      {
        this.formGroupSet.get(this.inQualitaDi).enable();
        this.formGroupSet.get(this.inQualitaDi).setValidators([Validators.required]);
        this.formGroupSet.get(this.inQualitaDi).updateValueAndValidity();
        this.formGroupSet.get(this.societa).enable();
        this.formGroupSet.get(this.societa).setValidators([Validators.required]);
        this.formGroupSet.get(this.societa).updateValueAndValidity();
        this.formGroupSet.get(this.societaCodiceFiscale).enable();
        this.formGroupSet.get(this.societaCodiceFiscale).setValidators([Validators.required]);
        this.formGroupSet.get(this.societaCodiceFiscale).updateValueAndValidity();
        this.formGroupSet.get(this.partitaIva).enable();
        this.formGroupSet.get(this.partitaIva).setValidators([Validators.required]);
        this.formGroupSet.get(this.partitaIva).updateValueAndValidity();
      } else
      {
        this.formGroupSet.get(this.inQualitaDi).disable();
        this.formGroupSet.get(this.inQualitaDi).setValue('');
        this.formGroupSet.get(this.inQualitaDi).clearValidators()
        this.formGroupSet.get(this.inQualitaDi).updateValueAndValidity();
        this.formGroupSet.get(this.societa).disable();
        this.formGroupSet.get(this.societa).setValue('');
        this.formGroupSet.get(this.societa).clearValidators()
        this.formGroupSet.get(this.societa).updateValueAndValidity();
        this.formGroupSet.get(this.societaCodiceFiscale).disable();
        this.formGroupSet.get(this.societaCodiceFiscale).setValue('');
        this.formGroupSet.get(this.societaCodiceFiscale).clearValidators()
        this.formGroupSet.get(this.societaCodiceFiscale).updateValueAndValidity();
        this.formGroupSet.get(this.partitaIva).disable();
        this.formGroupSet.get(this.partitaIva).setValue('');
        this.formGroupSet.get(this.partitaIva).clearValidators()
        this.formGroupSet.get(this.partitaIva).updateValueAndValidity();
      }
    }
  }

  activeAltroDitta(): void
  {
    if (!this.disable)
    {
      this.formGroupSet.get(this.descAltroDitta).setValue('');
      this.formGroupSet.updateValueAndValidity();
      if (this.formGroupSet.get(this.inQualitaDi).value == 3)
      {
        this.formGroupSet.get(this.descAltroDitta).enable();
        this.formGroupSet.get(this.descAltroDitta).setValidators([Validators.required]);
        this.formGroupSet.get(this.descAltroDitta).updateValueAndValidity();
      }
      else
      {
        this.formGroupSet.get(this.descAltroDitta).disable();
        this.formGroupSet.get(this.descAltroDitta).clearValidators()
        this.formGroupSet.get(this.descAltroDitta).updateValueAndValidity();
      }
    }
  }

}
