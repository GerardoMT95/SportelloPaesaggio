import { CONST } from 'src/app/shared/constants';
import { TranslateService } from '@ngx-translate/core';
import { BreadcrumbService } from 'src/app/shared/services/breadcrumb.service';
import { MenuItem } from 'primeng/primeng';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminFunctionsService } from 'src/app/shared/services/admin/admin-functions.service';
import { paths } from 'src/app/app-routing.module';

export abstract class GenericProcedimentoConfigComponent
{
    public idTipoProcedimento: string = null;
    
    constructor(private _router: Router,
               private _route : ActivatedRoute
               ,private _breadcrumbService: BreadcrumbService
               ,private _translateService:TranslateService
               ,private menuBreadcrumb:string
               ,private _service: AdminFunctionsService
               ) 
    {
        _route.paramMap.subscribe(params =>
        {
            let id = params.get("id");
            if(id != null && id.trim() != ""){
                this.idTipoProcedimento = id;
                this.loadBreadcrumb();
            }
        });
    }

    public back(): void 
    { 
        this._router.navigateByUrl(paths.configurazioneProcedimentoDetail(this.idTipoProcedimento))
    }

    private loadBreadcrumb():void{
        this._service.getConfProcedimento(this.idTipoProcedimento).subscribe(result =>{
            if(result && CONST.OK == result.codiceEsito){
                let breadcrumbs : MenuItem[] =[];
                this._translateService.get("MENU_ITEMS.configProc").subscribe((content) => {
                    breadcrumbs.push({ label: content, routerLink: paths.configurazioneProcedimenti() });
                    breadcrumbs.push({ label: result.payload.descrizione != null ? result.payload.descrizione : result.payload.descrizione, 
                        routerLink: paths.configurazioneProcedimentoDetail(this.idTipoProcedimento) });
                    this._translateService.get(this.menuBreadcrumb).subscribe((contentInner) => {
                        breadcrumbs.push({ label: contentInner, routerLink: this._router.url });
                        this._breadcrumbService.emitter.emit(breadcrumbs)
                    });
                });
            }
        })
      }
}