import { Component, OnInit, Input, OnDestroy } from "@angular/core";
import { ShowAlertService } from "src/app/services/show-alert.service";
import { Alert } from "src/app/components/model/alert.model";
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

@Component({
  selector: "app-alert-dialog",
  templateUrl: "./alert-dialog.component.html",
  styleUrls: ["./alert-dialog.component.scss"]
})

export class AlertDialogComponent implements OnInit, OnDestroy {
  constructor(private alertService: ShowAlertService, private router: Router) {}

  display: boolean;
  successfully: boolean;
  message: string;

  private unsubscribe$ = new Subject<void>();

  close() {
    if (this.successfully) {
      this.alertService.success.emit(true);
    } else if(!this.successfully || this.successfully === null) {
      this.display = false;
    }
  }

  ngOnInit() {
    if(this.alertService.alertObject){
      this.alertService.alertObject
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((response: Alert) => {
        this.display = response.showAlert;
        this.successfully = response.formCreated;
        this.message = response.message;
      });
    }

  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
