import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommission } from 'app/shared/model/commission.model';

@Component({
  selector: 'jhi-commission-detail',
  templateUrl: './commission-detail.component.html',
})
export class CommissionDetailComponent implements OnInit {
  commission: ICommission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commission }) => (this.commission = commission));
  }

  previousState(): void {
    window.history.back();
  }
}
