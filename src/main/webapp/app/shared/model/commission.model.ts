import { IAppartenance } from 'app/shared/model/appartenance.model';

export interface ICommission {
  id?: number;
  nom?: string;
  code?: string;
  appartences?: IAppartenance[];
}

export class Commission implements ICommission {
  constructor(public id?: number, public nom?: string, public code?: string, public appartences?: IAppartenance[]) {}
}
