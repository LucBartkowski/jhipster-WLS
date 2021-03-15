import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';

@Component({
  selector: 'jhi-geregistreerde-detail',
  templateUrl: './geregistreerde-detail.component.html',
})
export class GeregistreerdeDetailComponent implements OnInit {
  geregistreerde: IGeregistreerde | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geregistreerde }) => (this.geregistreerde = geregistreerde));
  }

  previousState(): void {
    window.history.back();
  }
}
