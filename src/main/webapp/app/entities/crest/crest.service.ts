import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICrest } from 'app/shared/model/crest.model';

type EntityResponseType = HttpResponse<ICrest>;
type EntityArrayResponseType = HttpResponse<ICrest[]>;

@Injectable({ providedIn: 'root' })
export class CrestService {
  public resourceUrl = SERVER_API_URL + 'api/crests';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/crests';

  constructor(protected http: HttpClient) {}

  create(crest: ICrest): Observable<EntityResponseType> {
    return this.http.post<ICrest>(this.resourceUrl, crest, { observe: 'response' });
  }

  update(crest: ICrest): Observable<EntityResponseType> {
    return this.http.put<ICrest>(this.resourceUrl, crest, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICrest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrest[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
