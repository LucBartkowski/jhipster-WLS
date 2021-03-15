import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMedewerker, Medewerker } from 'app/shared/model/medewerker.model';
import { MedewerkerService } from './medewerker.service';

@Component({
  selector: 'jhi-medewerker-update',
  templateUrl: './medewerker-update.component.html',
})
export class MedewerkerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    naam: [null, [Validators.required]],
    functie: [null, [Validators.required]],
  });

  constructor(protected medewerkerService: MedewerkerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medewerker }) => {
      this.updateForm(medewerker);
    });
  }

  updateForm(medewerker: IMedewerker): void {
    this.editForm.patchValue({
      id: medewerker.id,
      naam: medewerker.naam,
      functie: medewerker.functie,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medewerker = this.createFromForm();
    if (medewerker.id !== undefined) {
      this.subscribeToSaveResponse(this.medewerkerService.update(medewerker));
    } else {
      this.subscribeToSaveResponse(this.medewerkerService.create(medewerker));
    }
  }

  private createFromForm(): IMedewerker {
    return {
      ...new Medewerker(),
      id: this.editForm.get(['id'])!.value,
      naam: this.editForm.get(['naam'])!.value,
      functie: this.editForm.get(['functie'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedewerker>>): void {
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
