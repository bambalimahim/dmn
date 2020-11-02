import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionStockTestModule } from '../../../test.module';
import { MaterielDetailComponent } from 'app/entities/materiel/materiel-detail.component';
import { Materiel } from 'app/shared/model/materiel.model';

describe('Component Tests', () => {
  describe('Materiel Management Detail Component', () => {
    let comp: MaterielDetailComponent;
    let fixture: ComponentFixture<MaterielDetailComponent>;
    const route = ({ data: of({ materiel: new Materiel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionStockTestModule],
        declarations: [MaterielDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MaterielDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaterielDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load materiel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.materiel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
