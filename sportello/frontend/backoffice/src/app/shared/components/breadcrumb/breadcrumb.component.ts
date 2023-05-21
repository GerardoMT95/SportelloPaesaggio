import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter, takeUntil } from 'rxjs/operators';
import { MenuItem } from 'primeng/components/common/menuitem';
import { Subject } from 'rxjs';
import { BreadcrumbService } from '../../services/breadcrumb.service';

@Component({
	selector: 'app-breadcrumb',
	templateUrl: './breadcrumb.component.html',
	styleUrls: ['./breadcrumb.component.scss']
})

export class BreadcrumbComponent implements OnInit, OnDestroy
{
	public menuItems: MenuItem[] = [];
	public home: MenuItem;

	private unsubscribe$ = new Subject<void>();

	constructor(private router: Router
		, private activatedRoute: ActivatedRoute
		,private service:BreadcrumbService
		)
	{
		this.home = { routerLink: '/', label: 'Home' };
	}

	ngOnInit()
	{
		let _this = this;
		_this.router.events
			 .pipe(filter(event => event instanceof NavigationEnd))
			 .pipe(takeUntil(this.unsubscribe$))
			 .subscribe(() => (_this.menuItems = _this.createBreadcrumbs(_this.activatedRoute.root)));
		_this.service.emitter.subscribe(items =>{
				this.menuItems = items;
			}); 
	}

	private createBreadcrumbs(route: ActivatedRoute, routerLink: string = '', breadcrumbs: MenuItem[] = []): MenuItem[]
	{
		const children: ActivatedRoute[] = route.children;
		if (children.length === 0)
			return breadcrumbs;

		for (const child of children) 
		{
			const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
			if (routeURL !== '')
				routerLink += `/${routeURL}`;
			if (routeURL)
			{
				const label = child.snapshot.data['breadcrumb'];
				if(false !== label){
					let link : string = child.snapshot.data['link'];
					if (!this.isNullOrUndefined(label)) {
						if (this.isNullOrUndefined(link))
						breadcrumbs.push({ label, routerLink });
						else
						breadcrumbs.push({ label, routerLink: link });
					}
				}
			}
			return this.createBreadcrumbs(child, routerLink, breadcrumbs);
		}
	}

	private isNullOrUndefined<T>(obj: T | null | undefined): obj is null | undefined {
		return typeof obj === 'undefined' || obj === null;
	}

	ngOnDestroy()
	{
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

}
