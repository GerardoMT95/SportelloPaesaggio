import { UserService } from './../../services/user.service';
import { EnteCss } from './../model/model';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  public ente:EnteCss = null;
  constructor(private userService:UserService
  ) { }

  ngOnInit() {
      this.userService.enteEmitter.subscribe(ente =>{
        this.ente = ente;
      })
  }

}
