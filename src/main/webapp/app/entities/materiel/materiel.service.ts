import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMateriel } from 'app/shared/model/materiel.model';

type EntityResponseType = HttpResponse<IMateriel>;
type EntityArrayResponseType = HttpResponse<IMateriel[]>;

@Injectable({ providedIn: 'root' })
export class MaterielService {
  public resourceUrl = SERVER_API_URL + 'api/materiels';

  constructor(protected http: HttpClient) {}

  create(materiel: IMateriel): Observable<EntityResponseType> {
    return this.http.post<IMateriel>(this.resourceUrl, materiel, { observe: 'response' });
  }

  update(materiel: IMateriel): Observable<EntityResponseType> {
    return this.http.put<IMateriel>(this.resourceUrl, materiel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMateriel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMateriel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
