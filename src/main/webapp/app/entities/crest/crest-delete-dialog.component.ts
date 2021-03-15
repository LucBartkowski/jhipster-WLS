import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICrest } from 'app/shared/model/crest.model';
import { CrestService } from './crest.service';

@Component({
  templateUrl: './crest-delete-dialog.component.html',
})
export class CrestDeleteDialogComponent {
  crest?: ICrest;

  constructor(protected crestService: CrestService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.crestService.delete(id).subscribe(() => {
      this.eventManager.broadcast('crestListModification');
      this.activeModal.close();
    });
  }
}
