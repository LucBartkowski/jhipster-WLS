import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterWlsTestModule } from '../../../test.module';
import { CrestComponent } from 'app/entities/crest/crest.component';
import { CrestService } from 'app/entities/crest/crest.service';
import { Crest } from 'app/shared/model/crest.model';

describe('Component Tests', () => {
  describe('Crest Management Component', () => {
    let comp: CrestComponent;
    let fixture: ComponentFixture<CrestComponent>;
    let service: CrestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [CrestComponent],
      })
        .overrideTemplate(CrestComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrestComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CrestService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Crest(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.crests && comp.crests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
