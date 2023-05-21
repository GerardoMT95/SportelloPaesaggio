import { Pipe, PipeTransform } from '@angular/core';
import { DestinatarioComunicazioneDTO } from 'src/app/features/funzionalita-amministratore-applicazione/models/admin-functions.models';
import { translateTemplateDestToDestCom } from 'src/app/features/gestione-istanza-comunicazioni/generic-function/generic-function';

@Pipe({
  name: 'destinatari',
  pure: false
})
export class DestinatariPipe implements PipeTransform
{

  transform(value: any[]): DestinatarioComunicazioneDTO[]
  {
    if(!value)
      return [];
    return translateTemplateDestToDestCom(value);    
  }
}
