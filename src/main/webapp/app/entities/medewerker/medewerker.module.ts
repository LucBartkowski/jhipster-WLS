import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterWlsSharedModule } from 'app/shared/shared.module';
import { MedewerkerComponent } from './medewerker.component';
import { MedewerkerDetailComponent } from './medewerker-detail.component';
import { MedewerkerUpdateComponent } from './medewerker-update.component';
import { MedewerkerDeleteDialogComponent } from './medewerker-delete-dialog.component';
import { medewerkerRoute } from './medewerker.route';

@NgModule({
  imports: [JhipsterWlsSharedModule, RouterModule.forChild(medewerkerRoute)],
  declarations: [MedewerkerComponent, MedewerkerDetailComponent, MedewerkerUpdateComponent, MedewerkerDeleteDialogComponent],
  entryComponents: [MedewerkerDeleteDialogComponent],
})
export class JhipsterWlsMedewerkerModule {}
