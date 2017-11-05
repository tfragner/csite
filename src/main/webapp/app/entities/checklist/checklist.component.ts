import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Checklist } from './checklist.model';
import { ChecklistService } from './checklist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-checklist',
    templateUrl: './checklist.component.html'
})
export class ChecklistComponent implements OnInit, OnDestroy {
checklists: Checklist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private checklistService: ChecklistService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.checklistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.checklists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChecklists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Checklist) {
        return item.id;
    }
    registerChangeInChecklists() {
        this.eventSubscriber = this.eventManager.subscribe('checklistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
