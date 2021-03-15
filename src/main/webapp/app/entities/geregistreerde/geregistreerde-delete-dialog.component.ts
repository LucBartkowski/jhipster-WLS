import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeregistreerde } from 'app/shared/model/geregistreerde.model';
import { GeregistreerdeService } from './geregistreerde.service';

@Component({
  templateUrl: './geregistreerde-delete-dialog.component.html',
})
export class GeregistreerdeDeleteDialogComponent {
  geregistreerde?: IGeregistreerde;

  constructor(
    protected geregistreerdeService: GeregistreerdeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.geregistreerdeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('geregistreerdeListModification');
      this.activeModal.close();
    });
  }
}
