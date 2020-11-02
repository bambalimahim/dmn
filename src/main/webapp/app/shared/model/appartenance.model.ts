import { ICommission } from 'app/shared/model/commission.model';
import { IMateriel } from 'app/shared/model/materiel.model';

export interface IAppartenance {
  id?: number;
  designation?: string;
  commission?: ICommission;
  materiel?: IMateriel;
}

export class Appartenance implements IAppartenance {
  constructor(public id?: number, public designation?: string, public commission?: ICommission, public materiel?: IMateriel) {}
}
