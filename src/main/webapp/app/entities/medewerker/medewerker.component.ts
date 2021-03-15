import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedewerker } from 'app/shared/model/medewerker.model';
import { MedewerkerService } from './medewerker.service';
import { MedewerkerDeleteDialogComponent } from './medewerker-delete-dialog.component';

@Component({
  selector: 'jhi-medewerker',
  templateUrl: './medewerker.component.html',
})
export class MedewerkerComponent implements OnInit, OnDestroy {
  medewerkers?: IMedewerker[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected medewerkerService: MedewerkerService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.medewerkerService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IMedewerker[]>) => (this.medewerkers = res.body || []));
      return;
    }

    this.medewerkerService.query().subscribe((res: HttpResponse<IMedewerker[]>) => (this.medewerkers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMedewerkers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMedewerker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMedewerkers(): void {
    this.eventSubscriber = this.eventManager.subscribe('medewerkerListModification', () => this.loadAll());
  }

  delete(medewerker: IMedewerker): void {
    const modalRef = this.modalService.open(MedewerkerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medewerker = medewerker;
  }
}
