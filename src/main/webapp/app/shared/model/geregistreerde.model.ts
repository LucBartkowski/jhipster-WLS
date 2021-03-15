import { Moment } from 'moment';
import { ICrest } from 'app/shared/model/crest.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IClassificatie } from 'app/shared/model/classificatie.model';

export interface IGeregistreerde {
  id?: number;
  voornamen?: string;
  achternaam?: string;
  geboortedatum?: Moment;
  geboorteplaats?: string;
  registerNummer?: number;
  classificatie?: string;
  personeelnummer?: string;
  mailadres?: string;
  telefoonNummer?: string;
  mobieleNummer?: string;
  verantwoordelijkeCrest?: string;
  naam?: string;
  verantwoordelijkeCrest?: ICrest;
  naam?: IMedewerker;
  classificatie?: IClassificatie;
}

export class Geregistreerde implements IGeregistreerde {
  constructor(
    public id?: number,
    public voornamen?: string,
    public achternaam?: string,
    public geboortedatum?: Moment,
    public geboorteplaats?: string,
    public registerNummer?: number,
    public classificatie?: string,
    public personeelnummer?: string,
    public mailadres?: string,
    public telefoonNummer?: string,
    public mobieleNummer?: string,
    public verantwoordelijkeCrest?: string,
    public naam?: string,
    public verantwoordelijkeCrest?: ICrest,
    public naam?: IMedewerker,
    public classificatie?: IClassificatie
  ) {}
}
