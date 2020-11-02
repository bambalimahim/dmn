import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommission, Commission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';

@Component({
  selector: 'jhi-commission-update',
  templateUrl: './commission-update.component.html',
})
export class CommissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    code: [],
  });

  constructor(protected commissionService: CommissionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commission }) => {
      this.updateForm(commission);
    });
  }

  updateForm(commission: ICommission): void {
    this.editForm.patchValue({
      id: commission.id,
      nom: commission.nom,
      code: commission.code,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commission = this.createFromForm();
    if (commission.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionService.update(commission));
    } else {
      this.subscribeToSaveResponse(this.commissionService.create(commission));
    }
  }

  private createFromForm(): ICommission {
    return {
      ...new Commission(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommission>>): void {
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
}
