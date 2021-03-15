import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICrest, Crest } from 'app/shared/model/crest.model';
import { CrestService } from './crest.service';

@Component({
  selector: 'jhi-crest-update',
  templateUrl: './crest-update.component.html',
})
export class CrestUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    verantwoordelijkeCrest: [null, [Validators.required]],
    naamEntiteit: [null, [Validators.required]],
  });

  constructor(protected crestService: CrestService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crest }) => {
      this.updateForm(crest);
    });
  }

  updateForm(crest: ICrest): void {
    this.editForm.patchValue({
      id: crest.id,
      verantwoordelijkeCrest: crest.verantwoordelijkeCrest,
      naamEntiteit: crest.naamEntiteit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crest = this.createFromForm();
    if (crest.id !== undefined) {
      this.subscribeToSaveResponse(this.crestService.update(crest));
    } else {
      this.subscribeToSaveResponse(this.crestService.create(crest));
    }
  }

  private createFromForm(): ICrest {
    return {
      ...new Crest(),
      id: this.editForm.get(['id'])!.value,
      verantwoordelijkeCrest: this.editForm.get(['verantwoordelijkeCrest'])!.value,
      naamEntiteit: this.editForm.get(['naamEntiteit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrest>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
