import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterWlsSharedModule } from 'app/shared/shared.module';
import { ClassificatieComponent } from './classificatie.component';
import { ClassificatieDetailComponent } from './classificatie-detail.component';
import { ClassificatieUpdateComponent } from './classificatie-update.component';
import { ClassificatieDeleteDialogComponent } from './classificatie-delete-dialog.component';
import { classificatieRoute } from './classificatie.route';

@NgModule({
  imports: [JhipsterWlsSharedModule, RouterModule.forChild(classificatieRoute)],
  declarations: [ClassificatieComponent, ClassificatieDetailComponent, ClassificatieUpdateComponent, ClassificatieDeleteDialogComponent],
  entryComponents: [ClassificatieDeleteDialogComponent],
})
export class JhipsterWlsClassificatieModule {}
