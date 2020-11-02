import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionStockSharedModule } from 'app/shared/shared.module';
import { MaterielComponent } from './materiel.component';
import { MaterielDetailComponent } from './materiel-detail.component';
import { MaterielUpdateComponent } from './materiel-update.component';
import { MaterielDeleteDialogComponent } from './materiel-delete-dialog.component';
import { materielRoute } from './materiel.route';

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild(materielRoute)],
  declarations: [MaterielComponent, MaterielDetailComponent, MaterielUpdateComponent, MaterielDeleteDialogComponent],
  entryComponents: [MaterielDeleteDialogComponent],
})
export class GestionStockMaterielModule {}
