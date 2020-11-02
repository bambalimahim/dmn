import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppartenance } from 'app/shared/model/appartenance.model';
import { AppartenanceService } from './appartenance.service';

@Component({
  templateUrl: './appartenance-delete-dialog.component.html',
})
export class AppartenanceDeleteDialogComponent {
  appartenance?: IAppartenance;

  constructor(
    protected appartenanceService: AppartenanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appartenanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('appartenanceListModification');
      this.activeModal.close();
    });
  }
}
