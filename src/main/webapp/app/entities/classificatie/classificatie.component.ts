import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassificatie } from 'app/shared/model/classificatie.model';
import { ClassificatieService } from './classificatie.service';
import { ClassificatieDeleteDialogComponent } from './classificatie-delete-dialog.component';

@Component({
  selector: 'jhi-classificatie',
  templateUrl: './classificatie.component.html',
})
export class ClassificatieComponent implements OnInit, OnDestroy {
  classificaties?: IClassificatie[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected classificatieService: ClassificatieService,
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
      this.classificatieService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IClassificatie[]>) => (this.classificaties = res.body || []));
      return;
    }

    this.classificatieService.query().subscribe((res: HttpResponse<IClassificatie[]>) => (this.classificaties = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClassificaties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClassificatie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClassificaties(): void {
    this.eventSubscriber = this.eventManager.subscribe('classificatieListModification', () => this.loadAll());
  }

  delete(classificatie: IClassificatie): void {
    const modalRef = this.modalService.open(ClassificatieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.classificatie = classificatie;
  }
}
