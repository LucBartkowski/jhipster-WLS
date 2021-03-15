import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICrest, Crest } from 'app/shared/model/crest.model';
import { CrestService } from './crest.service';
import { CrestComponent } from './crest.component';
import { CrestDetailComponent } from './crest-detail.component';
import { CrestUpdateComponent } from './crest-update.component';

@Injectable({ providedIn: 'root' })
export class CrestResolve implements Resolve<ICrest> {
  constructor(private service: CrestService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICrest> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((crest: HttpResponse<Crest>) => {
          if (crest.body) {
            return of(crest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Crest());
  }
}

export const crestRoute: Routes = [
  {
    path: '',
    component: CrestComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.crest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CrestDetailComponent,
    resolve: {
      crest: CrestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.crest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CrestUpdateComponent,
    resolve: {
      crest: CrestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.crest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CrestUpdateComponent,
    resolve: {
      crest: CrestResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterWlsApp.crest.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
