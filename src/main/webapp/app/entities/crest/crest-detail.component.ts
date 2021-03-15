import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICrest } from 'app/shared/model/crest.model';

@Component({
  selector: 'jhi-crest-detail',
  templateUrl: './crest-detail.component.html',
})
export class CrestDetailComponent implements OnInit {
  crest: ICrest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crest }) => (this.crest = crest));
  }

  previousState(): void {
    window.history.back();
  }
}
