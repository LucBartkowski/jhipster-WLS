import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassificatie } from 'app/shared/model/classificatie.model';

@Component({
  selector: 'jhi-classificatie-detail',
  templateUrl: './classificatie-detail.component.html',
})
export class ClassificatieDetailComponent implements OnInit {
  classificatie: IClassificatie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classificatie }) => (this.classificatie = classificatie));
  }

  previousState(): void {
    window.history.back();
  }
}
