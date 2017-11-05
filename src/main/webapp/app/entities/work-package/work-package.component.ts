import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { WorkPackage } from './work-package.model';
import { WorkPackageService } from './work-package.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-work-package',
    templateUrl: './work-package.component.html'
})
export class WorkPackageComponent implements OnInit, OnDestroy {
workPackages: WorkPackage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private workPackageService: WorkPackageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.workPackageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.workPackages = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWorkPackages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: WorkPackage) {
        return item.id;
    }
    registerChangeInWorkPackages() {
        this.eventSubscriber = this.eventManager.subscribe('workPackageListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
