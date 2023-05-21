import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-modifica-testi-documentazione-page',
  templateUrl: './modifica-testi-documentazione-page.component.html',
  styleUrls: ['./modifica-testi-documentazione-page.component.scss']
})
export class ModificaTestiDocumentazionePageComponent implements OnInit {

  activeIndex=0;
  constructor(private router: Router) { }

  ngOnInit() {
      } 
}
