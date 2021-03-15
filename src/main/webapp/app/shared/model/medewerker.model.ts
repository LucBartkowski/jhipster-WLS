import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';

export interface IMedewerker {
  id?: number;
  naam?: string;
  functie?: string;
  geregistreerdes?: IGeregistreerde[];
}

export class Medewerker implements IMedewerker {
  constructor(public id?: number, public naam?: string, public functie?: string, public geregistreerdes?: IGeregistreerde[]) {}
}
