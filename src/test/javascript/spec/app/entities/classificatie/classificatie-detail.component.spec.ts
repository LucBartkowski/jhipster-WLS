import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterWlsTestModule } from '../../../test.module';
import { ClassificatieDetailComponent } from 'app/entities/classificatie/classificatie-detail.component';
import { Classificatie } from 'app/shared/model/classificatie.model';

describe('Component Tests', () => {
  describe('Classificatie Management Detail Component', () => {
    let comp: ClassificatieDetailComponent;
    let fixture: ComponentFixture<ClassificatieDetailComponent>;
    const route = ({ data: of({ classificatie: new Classificatie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterWlsTestModule],
        declarations: [ClassificatieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ClassificatieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassificatieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classificatie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classificatie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
