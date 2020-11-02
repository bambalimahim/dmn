import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommission, Commission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';
import { CommissionComponent } from './commission.component';
import { CommissionDetailComponent } from './commission-detail.component';
import { CommissionUpdateComponent } from './commission-update.component';

@Injectable({ providedIn: 'root' })
export class CommissionResolve implements Resolve<ICommission> {
  constructor(private service: CommissionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommission> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commission: HttpResponse<Commission>) => {
          if (commission.body) {
            return of(commission.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Commission());
  }
}

export const commissionRoute: Routes = [
  {
    path: '',
    component: CommissionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gestionStockApp.commission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommissionDetailComponent,
    resolve: {
      commission: CommissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.commission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.commission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommissionUpdateComponent,
    resolve: {
      commission: CommissionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestionStockApp.commission.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
