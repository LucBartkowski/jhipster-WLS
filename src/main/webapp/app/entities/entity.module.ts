import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'geregistreerde',
        loadChildren: () => import('./geregistreerde/geregistreerde.module').then(m => m.JhipsterWlsGeregistreerdeModule),
      },
      {
        path: 'classificatie',
        loadChildren: () => import('./classificatie/classificatie.module').then(m => m.JhipsterWlsClassificatieModule),
      },
      {
        path: 'crest',
        loadChildren: () => import('./crest/crest.module').then(m => m.JhipsterWlsCrestModule),
      },
      {
        path: 'medewerker',
        loadChildren: () => import('./medewerker/medewerker.module').then(m => m.JhipsterWlsMedewerkerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterWlsEntityModule {}
