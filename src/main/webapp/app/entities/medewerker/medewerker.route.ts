import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMedewerker, Medewerker } from 'app/shared/model/medewerker.model';
import { MedewerkerService } from './medewerker.service';
import { MedewerkerComponent } from './medewerker.component';
import { MedewerkerDetailComponent } from './medewerker-detail.component';
import { MedewerkerUpdateComponent } from './medewerker-update.component';

@Injectable({ providedIn: 'root' })
export class MedewerkerResolve implements Resolve<IMedewerker> {
  constructor(private service: MedewerkerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedewerker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((medewerker: HttpResponse<Medewerker>) => {
          if (medewerker.body) {
            return of(medewerker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Medewerker());
  }
}

export const medewerkerRoute: Routes = [
  {
    path: '',
    component: MedewerkerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.medewerker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedewerkerDetailComponent,
    resolve: {
      medewerker: MedewerkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.medewerker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedewerkerUpdateComponent,
    resolve: {
      medewerker: MedewerkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.medewerker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedewerkerUpdateComponent,
    resolve: {
      medewerker: MedewerkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.medewerker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
