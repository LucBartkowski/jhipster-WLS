import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterWlsSharedModule } from 'app/shared/shared.module';
import { GeregistreerdeComponent } from './geregistreerde.component';
import { GeregistreerdeDetailComponent } from './geregistreerde-detail.component';
import { GeregistreerdeUpdateComponent } from './geregistreerde-update.component';
import { GeregistreerdeDeleteDialogComponent } from './geregistreerde-delete-dialog.component';
import { geregistreerdeRoute } from './geregistreerde.route';

@NgModule({
  imports: [JhipsterWlsSharedModule, RouterModule.forChild(geregistreerdeRoute)],
  declarations: [
    GeregistreerdeComponent,
    GeregistreerdeDetailComponent,
    GeregistreerdeUpdateComponent,
    GeregistreerdeDeleteDialogComponent,
  ],
  entryComponents: [GeregistreerdeDeleteDialogComponent],
})
export class JhipsterWlsGeregistreerdeModule {}
