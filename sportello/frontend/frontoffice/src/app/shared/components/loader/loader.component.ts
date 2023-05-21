import { Component, Input, OnInit } from '@angular/core';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent implements OnInit {

  @Input()
  public message:string = 'generic.loading';
  @Input()
  public isDownload:boolean = false;
 
   progress:number=-1;
   constructor(private loadingService:LoadingService) { }
 
   ngOnInit() {
     this.loadingService.subjectProgressBar.subscribe(
       (progressValue)=>{
         this.progress=progressValue;
       }
       );
   }
}
