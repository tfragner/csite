import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructionSite } from './construction-site.model';
import { ConstructionSiteService } from './construction-site.service';

@Component({
    selector: 'jhi-construction-site-detail',
    templateUrl: './construction-site-detail.component.html'
})
export class ConstructionSiteDetailComponent implements OnInit, OnDestroy {

    constructionSite: ConstructionSite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private constructionSiteService: ConstructionSiteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConstructionSites();
    }

    load(id) {
        this.constructionSiteService.find(id).subscribe((constructionSite) => {
            this.constructionSite = constructionSite;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConstructionSites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'constructionSiteListModification',
            (response) => this.load(this.constructionSite.id)
        );
    }
}
