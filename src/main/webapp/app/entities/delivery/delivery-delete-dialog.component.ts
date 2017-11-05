import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryPopupService } from './delivery-popup.service';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-delete-dialog',
    templateUrl: './delivery-delete-dialog.component.html'
})
export class DeliveryDeleteDialogComponent {

    delivery: Delivery;

    constructor(
        private deliveryService: DeliveryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deliveryListModification',
                content: 'Deleted an delivery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-delivery-delete-popup',
    template: ''
})
export class DeliveryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.deliveryPopupService
                .open(DeliveryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
