import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterWlsTestModule } from '../../../test.module';
import { MedewerkerComponent } from 'app/entities/medewerker/medewerker.component';
import { MedewerkerService } from 'app/entities/medewerker/medewerker.service';
import { Medewerker } from 'app/shared/model/medewerker.model';

describe('Component Tests', () => {
  describe('Medewerker Management Component', () => {
    let comp: MedewerkerComponent;
    let fixture: ComponentFixture<MedewerkerComponent>;
    let service: MedewerkerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [MedewerkerComponent],
      })
        .overrideTemplate(MedewerkerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedewerkerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MedewerkerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Medewerker(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.medewerkers && comp.medewerkers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
