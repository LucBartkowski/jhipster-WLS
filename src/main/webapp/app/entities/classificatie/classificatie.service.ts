import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IClassificatie } from 'app/shared/model/classificatie.model';

type EntityResponseType = HttpResponse<IClassificatie>;
type EntityArrayResponseType = HttpResponse<IClassificatie[]>;

@Injectable({ providedIn: 'root' })
export class ClassificatieService {
  public resourceUrl = SERVER_API_URL + 'api/classificaties';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/classificaties';

  constructor(protected http: HttpClient) {}

  create(classificatie: IClassificatie): Observable<EntityResponseType> {
    return this.http.post<IClassificatie>(this.resourceUrl, classificatie, { observe: 'response' });
  }

  update(classificatie: IClassificatie): Observable<EntityResponseType> {
    return this.http.put<IClassificatie>(this.resourceUrl, classificatie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassificatie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassificatie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassificatie[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
