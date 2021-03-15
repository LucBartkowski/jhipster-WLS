import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedewerker } from 'app/shared/model/medewerker.model';
import { MedewerkerService } from './medewerker.service';

@Component({
  templateUrl: './medewerker-delete-dialog.component.html',
})
export class MedewerkerDeleteDialogComponent {
  medewerker?: IMedewerker;

  constructor(
    protected medewerkerService: MedewerkerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medewerkerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('medewerkerListModification');
      this.activeModal.close();
    });
  }
}
