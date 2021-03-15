import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClassificatie, Classificatie } from 'app/shared/model/classificatie.model';
import { ClassificatieService } from './classificatie.service';

@Component({
  selector: 'jhi-classificatie-update',
  templateUrl: './classificatie-update.component.html',
})
export class ClassificatieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    classificatie: [null, [Validators.required]],
    kleur: [],
    omschrijving: [null, [Validators.required]],
  });

  constructor(protected classificatieService: ClassificatieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classificatie }) => {
      this.updateForm(classificatie);
    });
  }

  updateForm(classificatie: IClassificatie): void {
    this.editForm.patchValue({
      id: classificatie.id,
      classificatie: classificatie.classificatie,
      kleur: classificatie.kleur,
      omschrijving: classificatie.omschrijving,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classificatie = this.createFromForm();
    if (classificatie.id !== undefined) {
      this.subscribeToSaveResponse(this.classificatieService.update(classificatie));
    } else {
      this.subscribeToSaveResponse(this.classificatieService.create(classificatie));
    }
  }

  private createFromForm(): IClassificatie {
    return {
      ...new Classificatie(),
      id: this.editForm.get(['id'])!.value,
      classificatie: this.editForm.get(['classificatie'])!.value,
      kleur: this.editForm.get(['kleur'])!.value,
      omschrijving: this.editForm.get(['omschrijving'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassificatie>>): void {
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
