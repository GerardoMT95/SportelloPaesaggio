import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AllowedGroup } from 'src/app/core/models/menu.model';
import { UserService } from '../../services/user.service';
import { GroupType, GroupRole } from '../../models/models';

@Directive({
  selector: '[appControlAccess]'
})
export class ControlAccessDirective
{
  @Input('appControlAccess') appControlAccess: AllowedGroup|AllowedGroup[]|'*'|'ALL'|'all';
  private unsubscribe: Subject<void> = new Subject();
  constructor(private user: UserService,
              private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef) { }

  ngOnInit(): void 
  {
    this.apply();
    this.user.groupChangeObs
             .pipe(takeUntil(this.unsubscribe))
             .subscribe(() => this.apply()); 
  }

  ngOnDestroy(): void
  {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

  private apply(): void
  {
    this.viewContainer.clear();
    let authorized: boolean = this.doControl();
    if (authorized)
      this.viewContainer.createEmbeddedView(this.templateRef);
    else
      this.viewContainer.clear();
  }

  private doControl(): boolean
  {
    let noControlEscape = ['*', 'ALL', 'all'];
    if(!this.appControlAccess)
      return true
    if (!this.user.isLogged || !this.user.actualGroup)
      return false;
    if (typeof this.appControlAccess === "string" && noControlEscape.includes(this.appControlAccess))
      return true;
    let arrayGroup: AllowedGroup[] = this.appControlAccess instanceof Array ? <AllowedGroup[]>this.appControlAccess : [<AllowedGroup>this.appControlAccess];
    let groupType: GroupType = this.user.groupType;
    let actualRole: GroupRole = this.user.role;
    return arrayGroup.some(item => item.group === groupType && (!item.roles || item.roles.includes(actualRole)));
  }

}
