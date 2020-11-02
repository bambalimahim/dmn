import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppartenance } from 'app/shared/model/appartenance.model';

@Component({
  selector: 'jhi-appartenance-detail',
  templateUrl: './appartenance-detail.component.html',
})
export class AppartenanceDetailComponent implements OnInit {
  appartenance: IAppartenance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appartenance }) => (this.appartenance = appartenance));
  }

  previousState(): void {
    window.history.back();
  }
}
