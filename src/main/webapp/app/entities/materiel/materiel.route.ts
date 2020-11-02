import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMateriel, Materiel } from 'app/shared/model/materiel.model';
import { MaterielService } from './materiel.service';
import { MaterielComponent } from './materiel.component';
import { MaterielDetailComponent } from './materiel-detail.component';
import { MaterielUpdateComponent } from './materiel-update.component';

@Injectable({ providedIn: 'root' })
export class MaterielResolve implements Resolve<IMateriel> {
  constructor(private service: MaterielService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMateriel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((materiel: HttpResponse<Materiel>) => {
          if (materiel.body) {
            return of(materiel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Materiel());
  }
}

export const materielRoute: Routes = [
  {
    path: '',
    component: MaterielComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gestionStockApp.materiel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MaterielDetailComponent,
    resolve: {
      materiel: MaterielResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.materiel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MaterielUpdateComponent,
    resolve: {
      materiel: MaterielResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.materiel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MaterielUpdateComponent,
    resolve: {
      materiel: MaterielResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.materiel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
