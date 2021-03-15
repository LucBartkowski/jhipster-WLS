import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterWlsSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [JhipsterWlsSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class JhipsterWlsHomeModule {}
