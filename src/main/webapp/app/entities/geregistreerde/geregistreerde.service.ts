import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';

type EntityResponseType = HttpResponse<IGeregistreerde>;
type EntityArrayResponseType = HttpResponse<IGeregistreerde[]>;

@Injectable({ providedIn: 'root' })
export class GeregistreerdeService {
  public resourceUrl = SERVER_API_URL + 'api/geregistreerdes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/geregistreerdes';

  constructor(protected http: HttpClient) {}

  create(geregistreerde: IGeregistreerde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(geregistreerde);
    return this.http
      .post<IGeregistreerde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(geregistreerde: IGeregistreerde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(geregistreerde);
    return this.http
      .put<IGeregistreerde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGeregistreerde>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGeregistreerde[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGeregistreerde[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(geregistreerde: IGeregistreerde): IGeregistreerde {
    const copy: IGeregistreerde = Object.assign({}, geregistreerde, {
      geboortedatum:
        geregistreerde.geboortedatum && geregistreerde.geboortedatum.isValid() ? geregistreerde.geboortedatum.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.geboortedatum = res.body.geboortedatum ? moment(res.body.geboortedatum) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((geregistreerde: IGeregistreerde) => {
        geregistreerde.geboortedatum = geregistreerde.geboortedatum ? moment(geregistreerde.geboortedatum) : undefined;
      });
    }
    return res;
  }
}
