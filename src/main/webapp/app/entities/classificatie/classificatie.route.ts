import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClassificatie, Classificatie } from 'app/shared/model/classificatie.model';
import { ClassificatieService } from './classificatie.service';
import { ClassificatieComponent } from './classificatie.component';
import { ClassificatieDetailComponent } from './classificatie-detail.component';
import { ClassificatieUpdateComponent } from './classificatie-update.component';

@Injectable({ providedIn: 'root' })
export class ClassificatieResolve implements Resolve<IClassificatie> {
  constructor(private service: ClassificatieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassificatie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classificatie: HttpResponse<Classificatie>) => {
          if (classificatie.body) {
            return of(classificatie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Classificatie());
  }
}

export const classificatieRoute: Routes = [
  {
    path: '',
    component: ClassificatieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.classificatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassificatieDetailComponent,
    resolve: {
      classificatie: ClassificatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.classificatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassificatieUpdateComponent,
    resolve: {
      classificatie: ClassificatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.classificatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassificatieUpdateComponent,
    resolve: {
      classificatie: ClassificatieResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.classificatie.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
