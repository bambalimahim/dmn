import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionStockTestModule } from '../../../test.module';
import { CommissionDetailComponent } from 'app/entities/commission/commission-detail.component';
import { Commission } from 'app/shared/model/commission.model';

describe('Component Tests', () => {
  describe('Commission Management Detail Component', () => {
    let comp: CommissionDetailComponent;
    let fixture: ComponentFixture<CommissionDetailComponent>;
    const route = ({ data: of({ commission: new Commission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionStockTestModule],
        declarations: [CommissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commission on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
