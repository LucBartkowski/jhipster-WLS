import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGeregistreerde, Geregistreerde } from 'app/shared/model/geregistreerde.model';
import { GeregistreerdeService } from './geregistreerde.service';
import { ICrest } from 'app/shared/model/crest.model';
import { CrestService } from 'app/entities/crest/crest.service';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { MedewerkerService } from 'app/entities/medewerker/medewerker.service';
import { IClassificatie } from 'app/shared/model/classificatie.model';
import { ClassificatieService } from 'app/entities/classificatie/classificatie.service';

type SelectableEntity = ICrest | IMedewerker | IClassificatie;

@Component({
  selector: 'jhi-geregistreerde-update',
  templateUrl: './geregistreerde-update.component.html',
})
export class GeregistreerdeUpdateComponent implements OnInit {
  isSaving = false;
  crests: ICrest[] = [];
  medewerkers: IMedewerker[] = [];
  classificaties: IClassificatie[] = [];

  editForm = this.fb.group({
    id: [],
    voornamen: [null, [Validators.required]],
    achternaam: [null, [Validators.required]],
    geboortedatum: [null, [Validators.required]],
    geboorteplaats: [null, [Validators.required]],
    registerNummer: [null, [Validators.required]],
    classificatie: [null, [Validators.required]],
    personeelnummer: [],
    mailadres: [],
    telefoonNummer: [],
    mobieleNummer: [],
    verantwoordelijkeCrest: [null, [Validators.required]],
    naam: [null, [Validators.required]],
    verantwoordelijkeCrest: [],
    naam: [],
    classificatie: [],
  });

  constructor(
    protected geregistreerdeService: GeregistreerdeService,
    protected crestService: CrestService,
    protected medewerkerService: MedewerkerService,
    protected classificatieService: ClassificatieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ geregistreerde }) => {
      if (!geregistreerde.id) {
        const today = moment().startOf('day');
        geregistreerde.geboortedatum = today;
      }

      this.updateForm(geregistreerde);

      this.crestService.query().subscribe((res: HttpResponse<ICrest[]>) => (this.crests = res.body || []));

      this.medewerkerService.query().subscribe((res: HttpResponse<IMedewerker[]>) => (this.medewerkers = res.body || []));

      this.classificatieService.query().subscribe((res: HttpResponse<IClassificatie[]>) => (this.classificaties = res.body || []));
    });
  }

  updateForm(geregistreerde: IGeregistreerde): void {
    this.editForm.patchValue({
      id: geregistreerde.id,
      voornamen: geregistreerde.voornamen,
      achternaam: geregistreerde.achternaam,
      geboortedatum: geregistreerde.geboortedatum ? geregistreerde.geboortedatum.format(DATE_TIME_FORMAT) : null,
      geboorteplaats: geregistreerde.geboorteplaats,
      registerNummer: geregistreerde.registerNummer,
      classificatie: geregistreerde.classificatie,
      personeelnummer: geregistreerde.personeelnummer,
      mailadres: geregistreerde.mailadres,
      telefoonNummer: geregistreerde.telefoonNummer,
      mobieleNummer: geregistreerde.mobieleNummer,
      verantwoordelijkeCrest: geregistreerde.verantwoordelijkeCrest,
      naam: geregistreerde.naam,
      verantwoordelijkeCrest: geregistreerde.verantwoordelijkeCrest,
      naam: geregistreerde.naam,
      classificatie: geregistreerde.classificatie,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const geregistreerde = this.createFromForm();
    if (geregistreerde.id !== undefined) {
      this.subscribeToSaveResponse(this.geregistreerdeService.update(geregistreerde));
    } else {
      this.subscribeToSaveResponse(this.geregistreerdeService.create(geregistreerde));
    }
  }

  private createFromForm(): IGeregistreerde {
    return {
      ...new Geregistreerde(),
      id: this.editForm.get(['id'])!.value,
      voornamen: this.editForm.get(['voornamen'])!.value,
      achternaam: this.editForm.get(['achternaam'])!.value,
      geboortedatum: this.editForm.get(['geboortedatum'])!.value
        ? moment(this.editForm.get(['geboortedatum'])!.value, DATE_TIME_FORMAT)
        : undefined,
      geboorteplaats: this.editForm.get(['geboorteplaats'])!.value,
      registerNummer: this.editForm.get(['registerNummer'])!.value,
      classificatie: this.editForm.get(['classificatie'])!.value,
      personeelnummer: this.editForm.get(['personeelnummer'])!.value,
      mailadres: this.editForm.get(['mailadres'])!.value,
      telefoonNummer: this.editForm.get(['telefoonNummer'])!.value,
      mobieleNummer: this.editForm.get(['mobieleNummer'])!.value,
      verantwoordelijkeCrest: this.editForm.get(['verantwoordelijkeCrest'])!.value,
      naam: this.editForm.get(['naam'])!.value,
      verantwoordelijkeCrest: this.editForm.get(['verantwoordelijkeCrest'])!.value,
      naam: this.editForm.get(['naam'])!.value,
      classificatie: this.editForm.get(['classificatie'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeregistreerde>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
