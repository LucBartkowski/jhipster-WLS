import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { GeregistreerdeUpdateComponent } from 'app/entities/geregistreerde/geregistreerde-update.component';
import { GeregistreerdeService } from 'app/entities/geregistreerde/geregistreerde.service';
import { Geregistreerde } from 'app/shared/model/geregistreerde.model';

describe('Component Tests', () => {
  describe('Geregistreerde Management Update Component', () => {
    let comp: GeregistreerdeUpdateComponent;
    let fixture: ComponentFixture<GeregistreerdeUpdateComponent>;
    let service: GeregistreerdeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [GeregistreerdeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GeregistreerdeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GeregistreerdeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GeregistreerdeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Geregistreerde(123);
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
        const entity = new Geregistreerde();
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
