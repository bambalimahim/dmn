import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMateriel, Materiel } from 'app/shared/model/materiel.model';
import { MaterielService } from './materiel.service';

@Component({
  selector: 'jhi-materiel-update',
  templateUrl: './materiel-update.component.html',
})
export class MaterielUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    libelle: [],
  });

  constructor(protected materielService: MaterielService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiel }) => {
      this.updateForm(materiel);
    });
  }

  updateForm(materiel: IMateriel): void {
    this.editForm.patchValue({
      id: materiel.id,
      code: materiel.code,
      libelle: materiel.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materiel = this.createFromForm();
    if (materiel.id !== undefined) {
      this.subscribeToSaveResponse(this.materielService.update(materiel));
    } else {
      this.subscribeToSaveResponse(this.materielService.create(materiel));
    }
  }

  private createFromForm(): IMateriel {
    return {
      ...new Materiel(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateriel>>): void {
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
