import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd, ActivatedRoute} from '@angular/router';
import { filter, takeUntil } from 'rxjs/operators';
import { MenuItem } from 'primeng/components/common/menuitem';
import { Subject } from 'rxjs';

@Component({
	selector: 'app-breadcrumb',
	templateUrl: './breadcrumb.component.html',
	styleUrls: ['./breadcrumb.component.scss']
})

export class BreadcrumbComponent implements OnInit, OnDestroy {
	menuItems: MenuItem[] = [];
	home: MenuItem;

	private unsubscribe$ = new Subject<void>();

	constructor(private router: Router, private activatedRoute: ActivatedRoute) {
		this.home = { routerLink: '/', label: 'Home' };
	}

	ngOnInit() {
		this.router.events
			.pipe(filter(event => event instanceof NavigationEnd))
			.pipe(takeUntil(this.unsubscribe$))
			.subscribe(() => (this.menuItems = this.createBreadcrumbs(this.activatedRoute.root)));
	}

	private createBreadcrumbs(route: ActivatedRoute, routerLink: string = '', breadcrumbs: MenuItem[] = []): MenuItem[] {
		const children: ActivatedRoute[] = route.children;
		if (children.length === 0) {
			return breadcrumbs;
		}

		for (const child of children) {
			const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
			if (routeURL !== '') {
				routerLink += `/${routeURL}`;
			}

			const label = child.snapshot.data['breadcrumb'];
			const breadcrumbIndex= breadcrumbs.map(function(e) { return e.label; }).indexOf(label);
			if (!this.isNullOrUndefined(label) && breadcrumbIndex==-1) {

				breadcrumbs.push({ label, routerLink });
			}

			return this.createBreadcrumbs(child, routerLink, breadcrumbs);
		}
	}

	private isNullOrUndefined<T>(obj: T | null | undefined): obj is null | undefined {
		return typeof obj === 'undefined' || obj === null;
	}

	ngOnDestroy() {
		this.unsubscribe$.next();
		this.unsubscribe$.complete();
	}

}
