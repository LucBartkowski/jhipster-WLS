import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { GeregistreerdeDetailComponent } from 'app/entities/geregistreerde/geregistreerde-detail.component';
import { Geregistreerde } from 'app/shared/model/geregistreerde.model';

describe('Component Tests', () => {
  describe('Geregistreerde Management Detail Component', () => {
    let comp: GeregistreerdeDetailComponent;
    let fixture: ComponentFixture<GeregistreerdeDetailComponent>;
    const route = ({ data: of({ geregistreerde: new Geregistreerde(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [GeregistreerdeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GeregistreerdeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GeregistreerdeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load geregistreerde on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.geregistreerde).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
