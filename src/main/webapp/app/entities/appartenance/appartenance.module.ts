import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestionStockSharedModule } from 'app/shared/shared.module';
import { AppartenanceComponent } from './appartenance.component';
import { AppartenanceDetailComponent } from './appartenance-detail.component';
import { AppartenanceUpdateComponent } from './appartenance-update.component';
import { AppartenanceDeleteDialogComponent } from './appartenance-delete-dialog.component';
import { appartenanceRoute } from './appartenance.route';

@NgModule({
  imports: [GestionStockSharedModule, RouterModule.forChild(appartenanceRoute)],
  declarations: [AppartenanceComponent, AppartenanceDetailComponent, AppartenanceUpdateComponent, AppartenanceDeleteDialogComponent],
  entryComponents: [AppartenanceDeleteDialogComponent],
})
export class GestionStockAppartenanceModule {}
