import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { buildForbiddenRouteslist } from '../../functions/utils';
import { StatoEnum, TipoProcedimento } from './../../../../shared/models/models';
import { UserService } from './../../../../shared/services/user.service';
import { RoutesHandler } from './../../models/routes-handler.models';
import { DataService } from './../../services/data-service/data.service';

@Directive({
  selector: '[appTabEnabler]'
})
export class TabEnablerDirective
{
  @Input("appTabEnabler") nomeTab: string;
  private unsubscribe$: Subject<void> = new Subject<void>();

  constructor(private user: UserService,
              private shared: DataService,
              private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef) 
  { }

  public ngOnInit(): void 
  {
    this.shared.statusObservable
               .pipe(takeUntil(this.unsubscribe$))
               .subscribe(() => this.checkVisibility());
  }
  public ngOnDestroy(): void 
  {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  private checkVisibility(): void
  {
    this.viewContainer.clear();
    let authorized: boolean = this.check();
    if (authorized)
      this.viewContainer.createEmbeddedView(this.templateRef);
    else
      this.viewContainer.clear();
  }

  private check(): boolean
  {
    let forbiddenRoutes: RoutesHandler = buildForbiddenRouteslist(this.status, this.shared.fascicolo, this.user.groupType);
    return forbiddenRoutes ? forbiddenRoutes.forbiddenRoutes.every(s => s != this.nomeTab) : false;
  }

  private get status()          : StatoEnum        { return this.shared.status; }
  private get tipoProcedimento(): TipoProcedimento { return this.shared.fascicolo ? this.shared.fascicolo.tipoProcedimento : null; }

}
