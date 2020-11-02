import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAppartenance, Appartenance } from 'app/shared/model/appartenance.model';
import { AppartenanceService } from './appartenance.service';
import { ICommission } from 'app/shared/model/commission.model';
import { CommissionService } from 'app/entities/commission/commission.service';
import { IMateriel } from 'app/shared/model/materiel.model';
import { MaterielService } from 'app/entities/materiel/materiel.service';

type SelectableEntity = ICommission | IMateriel;

@Component({
  selector: 'jhi-appartenance-update',
  templateUrl: './appartenance-update.component.html',
})
export class AppartenanceUpdateComponent implements OnInit {
  isSaving = false;
  commissions: ICommission[] = [];
  materiels: IMateriel[] = [];

  editForm = this.fb.group({
    id: [],
    designation: [null, [Validators.required]],
    commission: [],
    materiel: [],
  });

  constructor(
    protected appartenanceService: AppartenanceService,
    protected commissionService: CommissionService,
    protected materielService: MaterielService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appartenance }) => {
      this.updateForm(appartenance);

      this.commissionService.query().subscribe((res: HttpResponse<ICommission[]>) => (this.commissions = res.body || []));

      this.materielService.query().subscribe((res: HttpResponse<IMateriel[]>) => (this.materiels = res.body || []));
    });
  }

  updateForm(appartenance: IAppartenance): void {
    this.editForm.patchValue({
      id: appartenance.id,
      designation: appartenance.designation,
      commission: appartenance.commission,
      materiel: appartenance.materiel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appartenance = this.createFromForm();
    if (appartenance.id !== undefined) {
      this.subscribeToSaveResponse(this.appartenanceService.update(appartenance));
    } else {
      this.subscribeToSaveResponse(this.appartenanceService.create(appartenance));
    }
  }

  private createFromForm(): IAppartenance {
    return {
      ...new Appartenance(),
      id: this.editForm.get(['id'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      commission: this.editForm.get(['commission'])!.value,
      materiel: this.editForm.get(['materiel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppartenance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
