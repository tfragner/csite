import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ConstructionSite } from './construction-site.model';
import { ConstructionSiteService } from './construction-site.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-construction-site',
    templateUrl: './construction-site.component.html'
})
export class ConstructionSiteComponent implements OnInit, OnDestroy {
constructionSites: ConstructionSite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private constructionSiteService: ConstructionSiteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.constructionSiteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.constructionSites = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConstructionSites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ConstructionSite) {
        return item.id;
    }
    registerChangeInConstructionSites() {
        this.eventSubscriber = this.eventManager.subscribe('constructionSiteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
