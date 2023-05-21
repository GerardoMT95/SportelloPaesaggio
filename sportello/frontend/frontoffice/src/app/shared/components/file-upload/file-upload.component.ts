import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { FileUpload } from "primeng/primeng";
import { environment } from "src/environments/environment";
import { CONST } from "../../constants";

@Component({
  selector: "app-file-upload",
  templateUrl: "./file-upload.component.html",
  styleUrls: ["./file-upload.component.css"],
})
export class FileUploadComponent implements OnInit {
  @Output() onChoose: EventEmitter<File[]> = new EventEmitter<File[]>();
  @Output() onAllega: EventEmitter<File[]> = new EventEmitter<File[]>();
  @Output() onRemoveFileChoose: EventEmitter<File[]> = new EventEmitter<
    File[]
  >();

  @Input() mimeTypeForScansioni: string = CONST.mimeTypeForScansioni.join();
  @Input() maxSize: number = environment.maxSizeUpload;
  @Input() fileLimit: number = 5;
  @Input() multiple: true;
  @Input() disabled: boolean = false;
  @Input() invalidFileTypeMessageDetail: string;
  @Input() showCancelButton: boolean = false;
  @Input() showUploadButton: boolean = true;
  @Input() chooseLabel: string = 'generic.cerca';
  @Input() auto: boolean = false;
  
  files = new Array<File>();
  @ViewChild("fileUpload",{static:false})  public fileUpload:FileUpload;

  constructor(private translateSvc: TranslateService) {}

  ngOnInit() {}

  onSelect(event: any): void {
    for (let i = 0; i < event.files.length; i++) {
      const file: File = event.files[i];
      this.files = this.files.filter((element) => {
        return file.name !== element.name || file.size !== element.size;
      });
      if (this.isFileOk(file)) this.files.push(event.files[i]);
    }
    this.onChoose.emit(this.files);
  }

  onBeforeUpload(): void {
    this.onAllega.emit(this.files);
  }

  onRemoveFileFromUpload(event: any) {
    const file: File = event.file;
    this.files = this.files.filter((element) => {
      return element !== file;
    });
    this.onRemoveFileChoose.emit(this.files);
  }

  isFileOk(file: File): boolean {
    if (file.size > this.maxSize) {
      return false;
    }
    console.log("file type:",file.type);
    if (!this.mimeTypeForScansioni.split(",").includes(file.type)) {
      return false;
    }
    return true;
  }

  onCancel() {
    this.files.splice(0, this.files.length);
    this.onRemoveFileChoose.emit(this.files);
  }
  public clearUpload():void{
    this.fileUpload.clear();
  }
}
