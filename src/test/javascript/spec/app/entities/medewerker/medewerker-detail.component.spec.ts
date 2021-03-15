import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { MedewerkerDetailComponent } from 'app/entities/medewerker/medewerker-detail.component';
import { Medewerker } from 'app/shared/model/medewerker.model';

describe('Component Tests', () => {
  describe('Medewerker Management Detail Component', () => {
    let comp: MedewerkerDetailComponent;
    let fixture: ComponentFixture<MedewerkerDetailComponent>;
    const route = ({ data: of({ medewerker: new Medewerker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [MedewerkerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MedewerkerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedewerkerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medewerker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medewerker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
