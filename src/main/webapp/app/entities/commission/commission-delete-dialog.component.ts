import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';

@Component({
  templateUrl: './commission-delete-dialog.component.html',
})
export class CommissionDeleteDialogComponent {
  commission?: ICommission;

  constructor(
    protected commissionService: CommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commissionListModification');
      this.activeModal.close();
    });
  }
}
