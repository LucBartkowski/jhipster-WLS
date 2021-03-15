import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { CrestUpdateComponent } from 'app/entities/crest/crest-update.component';
import { CrestService } from 'app/entities/crest/crest.service';
import { Crest } from 'app/shared/model/crest.model';

describe('Component Tests', () => {
  describe('Crest Management Update Component', () => {
    let comp: CrestUpdateComponent;
    let fixture: ComponentFixture<CrestUpdateComponent>;
    let service: CrestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [CrestUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CrestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CrestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Crest(123);
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
        const entity = new Crest();
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
