import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMateriel } from 'app/shared/model/materiel.model';

@Component({
  selector: 'jhi-materiel-detail',
  templateUrl: './materiel-detail.component.html',
})
export class MaterielDetailComponent implements OnInit {
  materiel: IMateriel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiel }) => (this.materiel = materiel));
  }

  previousState(): void {
    window.history.back();
  }
}
