import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterWlsSharedModule } from 'app/shared/shared.module';
import { CrestComponent } from './crest.component';
import { CrestDetailComponent } from './crest-detail.component';
import { CrestUpdateComponent } from './crest-update.component';
import { CrestDeleteDialogComponent } from './crest-delete-dialog.component';
import { crestRoute } from './crest.route';

@NgModule({
  imports: [JhipsterWlsSharedModule, RouterModule.forChild(crestRoute)],
  declarations: [CrestComponent, CrestDetailComponent, CrestUpdateComponent, CrestDeleteDialogComponent],
  entryComponents: [CrestDeleteDialogComponent],
})
export class JhipsterWlsCrestModule {}
