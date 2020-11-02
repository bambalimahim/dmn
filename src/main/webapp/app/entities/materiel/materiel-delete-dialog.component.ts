import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMateriel } from 'app/shared/model/materiel.model';
import { MaterielService } from './materiel.service';

@Component({
  templateUrl: './materiel-delete-dialog.component.html',
})
export class MaterielDeleteDialogComponent {
  materiel?: IMateriel;

  constructor(protected materielService: MaterielService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.materielService.delete(id).subscribe(() => {
      this.eventManager.broadcast('materielListModification');
      this.activeModal.close();
    });
  }
}
