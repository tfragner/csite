import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Location } from './location.model';
import { LocationService } from './location.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-location',
    templateUrl: './location.component.html'
})
export class LocationComponent implements OnInit, OnDestroy {
locations: Location[];
    currentAccount: any;
    eventSubscriber: Subscription;

    @Input() csiteId;

    constructor(
        private locationService: LocationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        if (this.csiteId) {
            this.locationService.queryByConstructionSite(this.csiteId).subscribe(
                (res: ResponseWrapper) => {
                    this.locations = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        } else {
            this.locationService.query().subscribe(
                (res: ResponseWrapper) => {
                    this.locations = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Location) {
        return item.id;
    }
    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe('locationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
