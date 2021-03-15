import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedewerker } from 'app/shared/model/medewerker.model';

@Component({
  selector: 'jhi-medewerker-detail',
  templateUrl: './medewerker-detail.component.html',
})
export class MedewerkerDetailComponent implements OnInit {
  medewerker: IMedewerker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medewerker }) => (this.medewerker = medewerker));
  }

  previousState(): void {
    window.history.back();
  }
}
