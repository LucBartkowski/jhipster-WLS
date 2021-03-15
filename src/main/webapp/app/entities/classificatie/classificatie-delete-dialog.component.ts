import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassificatie } from 'app/shared/model/classificatie.model';
import { ClassificatieService } from './classificatie.service';

@Component({
  templateUrl: './classificatie-delete-dialog.component.html',
})
export class ClassificatieDeleteDialogComponent {
  classificatie?: IClassificatie;

  constructor(
    protected classificatieService: ClassificatieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classificatieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classificatieListModification');
      this.activeModal.close();
    });
  }
}
