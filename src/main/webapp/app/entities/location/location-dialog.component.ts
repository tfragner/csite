import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Location } from './location.model';
import { LocationPopupService } from './location-popup.service';
import { LocationService } from './location.service';
import { ConstructionSite, ConstructionSiteService } from '../construction-site';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-location-dialog',
    templateUrl: './location-dialog.component.html'
})
export class LocationDialogComponent implements OnInit {

    location: Location;
    isSaving: boolean;

    constructionsites: ConstructionSite[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private locationService: LocationService,
        private constructionSiteService: ConstructionSiteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.constructionSiteService.query()
            .subscribe((res: ResponseWrapper) => { this.constructionsites = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location));
        }
    }

    updatePosition() {
        // navigator.geolocation.getCurrentPosition(this.setPosition);
        this.location.longitude = this.location.longitude + 1;
        this.location.latitude = this.location.latitude + 1;
    }

    // geht nicht weil nur über https
    setPosition(position) {
        this.location.longitude = position.longitude + 1;
        this.location.latitude = position.latitude + 1;

        console.log(position.coords);
    }

    private subscribeToSaveResponse(result: Observable<Location>) {
        result.subscribe((res: Location) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Location) {
        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConstructionSiteById(index: number, item: ConstructionSite) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-location-popup',
    template: ''
})
export class LocationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationPopupService
                    .open(LocationDialogComponent as Component, params['id']);
            } else {
                this.locationPopupService
                    .open(LocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-location-csite-popup',
    template: ''
})
export class LocationWithCsitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['csiteId'] ) {
                this.locationPopupService
                    .openWithCsiteId(LocationDialogComponent as Component, params['csiteId']);
            } else {
                this.locationPopupService
                    .openWithCsiteId(LocationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
