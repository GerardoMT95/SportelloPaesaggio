import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-modifica-testi-comunicazioni-page',
  templateUrl: './modifica-testi-comunicazioni-page.component.html',
  styleUrls: ['./modifica-testi-comunicazioni-page.component.scss']
})
export class ModificaTestiComunicazioniPageComponent implements OnInit {

  activeIndex=0;
  constructor(private router: Router) { }

  ngOnInit() {
    
  }

  
}
