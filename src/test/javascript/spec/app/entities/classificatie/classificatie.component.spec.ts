import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterWlsTestModule } from '../../../test.module';
import { ClassificatieComponent } from 'app/entities/classificatie/classificatie.component';
import { ClassificatieService } from 'app/entities/classificatie/classificatie.service';
import { Classificatie } from 'app/shared/model/classificatie.model';

describe('Component Tests', () => {
  describe('Classificatie Management Component', () => {
    let comp: ClassificatieComponent;
    let fixture: ComponentFixture<ClassificatieComponent>;
    let service: ClassificatieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [ClassificatieComponent],
      })
        .overrideTemplate(ClassificatieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassificatieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassificatieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Classificatie(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.classificaties && comp.classificaties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
