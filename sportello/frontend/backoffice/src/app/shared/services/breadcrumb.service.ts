import { MenuItem } from 'primeng/components/common/menuitem';
import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BreadcrumbService {

  constructor() { }

  public emitter : EventEmitter<MenuItem[]>  = new EventEmitter<MenuItem[]>();
}
