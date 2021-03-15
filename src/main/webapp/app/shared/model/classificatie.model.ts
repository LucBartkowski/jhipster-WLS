import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';

export interface IClassificatie {
  id?: number;
  classificatie?: string;
  kleur?: string;
  omschrijving?: string;
  geregistreerdes?: IGeregistreerde[];
}

export class Classificatie implements IClassificatie {
  constructor(
    public id?: number,
    public classificatie?: string,
    public kleur?: string,
    public omschrijving?: string,
    public geregistreerdes?: IGeregistreerde[]
  ) {}
}
