import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrest } from 'app/shared/model/crest.model';
import { CrestService } from './crest.service';
import { CrestDeleteDialogComponent } from './crest-delete-dialog.component';

@Component({
  selector: 'jhi-crest',
  templateUrl: './crest.component.html',
})
export class CrestComponent implements OnInit, OnDestroy {
  crests?: ICrest[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected crestService: CrestService,
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
      this.crestService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ICrest[]>) => (this.crests = res.body || []));
      return;
    }

    this.crestService.query().subscribe((res: HttpResponse<ICrest[]>) => (this.crests = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCrests();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICrest): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCrests(): void {
    this.eventSubscriber = this.eventManager.subscribe('crestListModification', () => this.loadAll());
  }

  delete(crest: ICrest): void {
    const modalRef = this.modalService.open(CrestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.crest = crest;
  }
}
