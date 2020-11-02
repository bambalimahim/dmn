import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionStockTestModule } from '../../../test.module';
import { AppartenanceDetailComponent } from 'app/entities/appartenance/appartenance-detail.component';
import { Appartenance } from 'app/shared/model/appartenance.model';

describe('Component Tests', () => {
  describe('Appartenance Management Detail Component', () => {
    let comp: AppartenanceDetailComponent;
    let fixture: ComponentFixture<AppartenanceDetailComponent>;
    const route = ({ data: of({ appartenance: new Appartenance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionStockTestModule],
        declarations: [AppartenanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AppartenanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppartenanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appartenance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appartenance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
