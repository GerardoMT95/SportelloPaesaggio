import { AuthorizationService } from '../services/authorization.service';
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
 
@Directive({
  selector: '[appCanAccess]'
})

export class CanAccessDirective {

  @Input('appCanAccess') appCanAccess: string | string[];
  private permission$: Subscription;

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private workflowEvents: AuthorizationService) {
  }

  ngOnInit(): void {
    this.applyPermission();
  }

  private applyPermission(): void {
    this.permission$ = this.workflowEvents
                        .checkAuthorization(this.appCanAccess)
      .subscribe(authorized => {
        if (authorized) {
          this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
          this.viewContainer.clear();
        }
      });
  }

  ngOnDestroy(): void {
    this.permission$.unsubscribe();
  }

}
