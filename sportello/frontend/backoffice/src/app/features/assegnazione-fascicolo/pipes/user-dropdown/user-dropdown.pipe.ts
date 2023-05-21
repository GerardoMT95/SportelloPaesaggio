import { SelectItem } from 'primeng/components/common/selectitem';
import { User } from '../../../../shared/models/models';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'userDropdown'
})
export class UserDropdownPipe implements PipeTransform 
{

  transform(users: User[]): SelectItem[]  
  {
    return users.map(m => { return {label: m.nome + " " + m.cognome + " [" + m.username + "]", value: m}});
  }

}
