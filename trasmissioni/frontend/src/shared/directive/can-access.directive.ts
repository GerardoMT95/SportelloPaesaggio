import { AuthorizationService } from './../../app/services/authorization.service';
import { Directive, Input, TemplateRef, ViewContainerRef, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs';
 
@Directive({
  selector: '[appCanAccess]'
})
export class CanAccessDirective {

  @Input('appCanAccess') appCanAccess: string | string[];
  private permission$: Subscription;

  constructor(private templateRef: TemplateRef<any>,
              private element: ElementRef,
              private viewContainer: ViewContainerRef,
              private workflowEvents: AuthorizationService) {
  }

  ngOnInit(): void {
    this.applyPermission();
  }

  /**
   * '*' == qualsiasi ruolo ovvero pubblico....
   */
  private applyPermission(): void {
    if (this.appCanAccess && this.appCanAccess.length==1 && this.appCanAccess[0]=='*'){
      this.viewContainer.createEmbeddedView(this.templateRef);
      return;
    } 
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
