import {Component, OnInit, OnDestroy, ElementRef, Input} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryPopupService } from './delivery-popup.service';
import { DeliveryService } from './delivery.service';
import { Checklist, ChecklistService } from '../checklist';
import { WorkPackage, WorkPackageService } from '../work-package';
import { Person, PersonService } from '../person';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';
import {Principal} from "../../shared/auth/principal.service";

@Component({
    selector: 'jhi-delivery-dialog',
    templateUrl: './delivery-dialog.component.html'
})
export class DeliveryDialogComponent implements OnInit {

    delivery: Delivery;
    isSaving: boolean;

    checklists: Checklist[];

    workpackages: WorkPackage[];

    people: Person[];

    locations: Location[];

    isSales: boolean;
    isContainer: boolean;

    @Input() csiteId;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private deliveryService: DeliveryService,
        private checklistService: ChecklistService,
        private workPackageService: WorkPackageService,
        private personService: PersonService,
        private locationService: LocationService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.checklistService.query()
            .subscribe((res: ResponseWrapper) => { this.checklists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.csiteId == null) {
            this.workPackageService.query()
                .subscribe((res: ResponseWrapper) => {
                    this.workpackages = res.json;
                }, (res: ResponseWrapper) => this.onError(res.json));
        } else {
            this.workPackageService.queryByConstructionSite(this.csiteId)
                .subscribe((res: ResponseWrapper) => {
                    this.workpackages = res.json;
                }, (res: ResponseWrapper) => this.onError(res.json));
        }
        this.personService.querySupplier()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.csiteId == null) {
            this.locationService.query()
                .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        } else {
            this.locationService.queryByConstructionSite(this.csiteId)
                .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        }

        this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_SALES'])).then(asdf => {this.isSales = !asdf;});
        this.principal.identity().then((account) => this.principal.hasAnyAuthority(['ROLE_CONTAINER'])).then(asdf => {this.isContainer = !asdf;});

    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.delivery, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.delivery.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deliveryService.update(this.delivery));
        } else {
            this.subscribeToSaveResponse(
                this.deliveryService.create(this.delivery));
        }
    }

    private subscribeToSaveResponse(result: Observable<Delivery>) {
        result.subscribe((res: Delivery) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Delivery) {
        this.eventManager.broadcast({ name: 'deliveryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackChecklistById(index: number, item: Checklist) {
        return item.id;
    }

    trackWorkPackageById(index: number, item: WorkPackage) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    isDisabled() {
        return this.isSales;
    }
}

@Component({
    selector: 'jhi-delivery-popup',
    template: ''
})
export class DeliveryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deliveryPopupService
                    .open(DeliveryDialogComponent as Component, params['id']);
            } else {
                this.deliveryPopupService
                    .open(DeliveryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-delivery-csite-popup',
    template: ''
})
export class DeliveryWithCsitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['csiteId'] && params['id']) {
                this.deliveryPopupService
                    .openWithIdAndCsiteId(DeliveryDialogComponent as Component, params['id'], params['csiteId']);
            }else if ( params['csiteId'] ) {
                this.deliveryPopupService
                    .openWithCsiteId(DeliveryDialogComponent as Component, params['csiteId']);
            } else {
                this.deliveryPopupService
                    .openWithCsiteId(DeliveryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
