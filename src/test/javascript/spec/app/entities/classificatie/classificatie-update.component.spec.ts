import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { ClassificatieUpdateComponent } from 'app/entities/classificatie/classificatie-update.component';
import { ClassificatieService } from 'app/entities/classificatie/classificatie.service';
import { Classificatie } from 'app/shared/model/classificatie.model';

describe('Component Tests', () => {
  describe('Classificatie Management Update Component', () => {
    let comp: ClassificatieUpdateComponent;
    let fixture: ComponentFixture<ClassificatieUpdateComponent>;
    let service: ClassificatieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [ClassificatieUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClassificatieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassificatieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassificatieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Classificatie(123);
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
        const entity = new Classificatie();
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
