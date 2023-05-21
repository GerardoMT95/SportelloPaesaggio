import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { LoadingService } from "../../app/services/loading.service";
import { Observable } from "rxjs";
import { finalize } from "rxjs/operators";

@Injectable()
export class LoaderInterceptor implements HttpInterceptor {
  loaderCount: number = 0;
  constructor(public loadingService: LoadingService) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    
    this.loadingService.emitLoading(true);
    this.loaderCount++;
    return next.handle(req).pipe(
      finalize(() => {
        this.loaderCount--
        //console.log('LOADER COUNT', this.loaderCount)
        if (this.loaderCount === 0) {
          this.loadingService.emitLoading(false);
        }
      })
    );

  }
}