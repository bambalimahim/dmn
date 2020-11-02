import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'materiel',
        loadChildren: () => import('./materiel/materiel.module').then(m => m.GestionStockMaterielModule),
      },
      {
        path: 'commission',
        loadChildren: () => import('./commission/commission.module').then(m => m.GestionStockCommissionModule),
      },
      {
        path: 'appartenance',
        loadChildren: () => import('./appartenance/appartenance.module').then(m => m.GestionStockAppartenanceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GestionStockEntityModule {}
