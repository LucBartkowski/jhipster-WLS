import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { MedewerkerUpdateComponent } from 'app/entities/medewerker/medewerker-update.component';
import { MedewerkerService } from 'app/entities/medewerker/medewerker.service';
import { Medewerker } from 'app/shared/model/medewerker.model';

describe('Component Tests', () => {
  describe('Medewerker Management Update Component', () => {
    let comp: MedewerkerUpdateComponent;
    let fixture: ComponentFixture<MedewerkerUpdateComponent>;
    let service: MedewerkerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [MedewerkerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MedewerkerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedewerkerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedewerkerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Medewerker(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Medewerker();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
