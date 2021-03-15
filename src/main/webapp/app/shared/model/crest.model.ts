import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';

export interface ICrest {
  id?: number;
  verantwoordelijkeCrest?: string;
  naamEntiteit?: string;
  geregistreerdes?: IGeregistreerde[];
}

export class Crest implements ICrest {
  constructor(
    public id?: number,
    public verantwoordelijkeCrest?: string,
    public naamEntiteit?: string,
    public geregistreerdes?: IGeregistreerde[]
  ) {}
}
