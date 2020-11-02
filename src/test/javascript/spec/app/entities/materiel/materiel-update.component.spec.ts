import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestionStockTestModule } from '../../../test.module';
import { MaterielUpdateComponent } from 'app/entities/materiel/materiel-update.component';
import { MaterielService } from 'app/entities/materiel/materiel.service';
import { Materiel } from 'app/shared/model/materiel.model';

describe('Component Tests', () => {
  describe('Materiel Management Update Component', () => {
    let comp: MaterielUpdateComponent;
    let fixture: ComponentFixture<MaterielUpdateComponent>;
    let service: MaterielService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestionStockTestModule],
        declarations: [MaterielUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MaterielUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaterielUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaterielService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Materiel(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Materiel();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
