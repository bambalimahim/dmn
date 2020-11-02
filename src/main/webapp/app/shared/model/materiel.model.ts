import { IAppartenance } from 'app/shared/model/appartenance.model';

export interface IMateriel {
  id?: number;
  code?: string;
  libelle?: string;
  appartenances?: IAppartenance[];
}

export class Materiel implements IMateriel {
  constructor(public id?: number, public code?: string, public libelle?: string, public appartenances?: IAppartenance[]) {}
}
