import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-detail',
    templateUrl: './delivery-detail.component.html'
})
export class DeliveryDetailComponent implements OnInit, OnDestroy {

    delivery: Delivery;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private deliveryService: DeliveryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeliveries();
    }

    load(id) {
        this.deliveryService.find(id).subscribe((delivery) => {
            this.delivery = delivery;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeliveries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deliveryListModification',
            (response) => this.load(this.delivery.id)
        );
    }

    handleChecklistUpdate(checklist) {
        this.load(this.delivery.id);
    }
}
