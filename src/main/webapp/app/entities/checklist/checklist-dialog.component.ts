import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Checklist } from './checklist.model';
import { ChecklistPopupService } from './checklist-popup.service';
import { ChecklistService } from './checklist.service';
import { Delivery, DeliveryService } from '../delivery';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-checklist-dialog',
    templateUrl: './checklist-dialog.component.html'
})
export class ChecklistDialogComponent implements OnInit {

    checklist: Checklist;
    isSaving: boolean;

    deliveries: Delivery[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private checklistService: ChecklistService,
        private deliveryService: DeliveryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.deliveryService
            .query({filter: 'checklist-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.checklist.deliveryId) {
                    this.deliveries = res.json;
                } else {
                    this.deliveryService
                        .find(this.checklist.deliveryId)
                        .subscribe((subRes: Delivery) => {
                            this.deliveries = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.checklist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.checklistService.update(this.checklist));
        } else {
            this.subscribeToSaveResponse(
                this.checklistService.create(this.checklist));
        }
    }

    private subscribeToSaveResponse(result: Observable<Checklist>) {
        result.subscribe((res: Checklist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Checklist) {
        this.eventManager.broadcast({ name: 'checklistListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDeliveryById(index: number, item: Delivery) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-checklist-popup',
    template: ''
})
export class ChecklistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private checklistPopupService: ChecklistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.checklistPopupService
                    .open(ChecklistDialogComponent as Component, params['id']);
            } else {
                this.checklistPopupService
                    .open(ChecklistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-checklist-popup',
    template: ''
})
export class ChecklistWithDeliveryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private checklistPopupService: ChecklistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['deliveryId'] ) {
                this.checklistPopupService
                    .openWithDeliveryId(ChecklistDialogComponent as Component, params['deliveryId']);
            } else {
                this.checklistPopupService
                    .openWithDeliveryId(ChecklistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
