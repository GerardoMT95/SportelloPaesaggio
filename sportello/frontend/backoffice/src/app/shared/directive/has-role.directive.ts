import { UserService } from 'src/app/shared/services/user.service';
import { Directive, Input, ViewContainerRef, TemplateRef, OnInit } from '@angular/core';

@Directive({
  selector: '[appHasRoles]'
})
export class HasRoleDirective implements OnInit {

  @Input() appHasRoles: number[];

  isVisible = false;

  constructor(private viewContainerRef: ViewContainerRef,
              private templateRef: TemplateRef<any>,
              private userService: UserService)
  { }

  ngOnInit() {
    //const { role } = this.userService.role;

    if (!this.userService.role  ) {
      this.viewContainerRef.clear();
    }

    if (false/* this.appHasRoles.includes(this.userService.role) */) {
      if (!this.isVisible) {
        this.isVisible = true;
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      }
    } else {
      this.isVisible = false;
      this.viewContainerRef.clear();
    }
  }

}
