import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGeregistreerde, Geregistreerde } from 'app/shared/model/geregistreerde.model';
import { GeregistreerdeService } from './geregistreerde.service';
import { GeregistreerdeComponent } from './geregistreerde.component';
import { GeregistreerdeDetailComponent } from './geregistreerde-detail.component';
import { GeregistreerdeUpdateComponent } from './geregistreerde-update.component';

@Injectable({ providedIn: 'root' })
export class GeregistreerdeResolve implements Resolve<IGeregistreerde> {
  constructor(private service: GeregistreerdeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGeregistreerde> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((geregistreerde: HttpResponse<Geregistreerde>) => {
          if (geregistreerde.body) {
            return of(geregistreerde.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Geregistreerde());
  }
}

export const geregistreerdeRoute: Routes = [
  {
    path: '',
    component: GeregistreerdeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterWlsApp.geregistreerde.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GeregistreerdeDetailComponent,
    resolve: {
      geregistreerde: GeregistreerdeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.geregistreerde.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GeregistreerdeUpdateComponent,
    resolve: {
      geregistreerde: GeregistreerdeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.geregistreerde.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GeregistreerdeUpdateComponent,
    resolve: {
      geregistreerde: GeregistreerdeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.geregistreerde.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
