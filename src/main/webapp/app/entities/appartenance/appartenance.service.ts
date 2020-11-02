import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppartenance } from 'app/shared/model/appartenance.model';

type EntityResponseType = HttpResponse<IAppartenance>;
type EntityArrayResponseType = HttpResponse<IAppartenance[]>;

@Injectable({ providedIn: 'root' })
export class AppartenanceService {
  public resourceUrl = SERVER_API_URL + 'api/appartenances';

  constructor(protected http: HttpClient) {}

  create(appartenance: IAppartenance): Observable<EntityResponseType> {
    return this.http.post<IAppartenance>(this.resourceUrl, appartenance, { observe: 'response' });
  }

  update(appartenance: IAppartenance): Observable<EntityResponseType> {
    return this.http.put<IAppartenance>(this.resourceUrl, appartenance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppartenance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppartenance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
