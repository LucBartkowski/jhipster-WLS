import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { CrestDetailComponent } from 'app/entities/crest/crest-detail.component';
import { Crest } from 'app/shared/model/crest.model';

describe('Component Tests', () => {
  describe('Crest Management Detail Component', () => {
    let comp: CrestDetailComponent;
    let fixture: ComponentFixture<CrestDetailComponent>;
    const route = ({ data: of({ crest: new Crest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [CrestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CrestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CrestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load crest on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.crest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
