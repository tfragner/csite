import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Delivery } from './delivery.model';
import { DeliveryService } from './delivery.service';

@Injectable()
export class DeliveryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private deliveryService: DeliveryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.deliveryService.find(id).subscribe((delivery) => {
                    delivery.date = this.datePipe
                        .transform(delivery.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.deliveryModalRef(component, delivery);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.deliveryModalRef(component, new Delivery());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    openWithCsiteId(component: Component, csiteId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (csiteId) {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    const temp = new Delivery();

                    this.ngbModalRef = this.deliveryModalRef(component, temp, csiteId);
                    resolve(this.ngbModalRef);
                }, 0);
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.deliveryModalRef(component, new Delivery());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    openWithIdAndCsiteId(component: Component, id?: number, csiteId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id && csiteId) {
                this.deliveryService.find(id).subscribe((delivery) => {
                    delivery.date = this.datePipe
                        .transform(delivery.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.deliveryModalRef(component, delivery, csiteId);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.deliveryModalRef(component, new Delivery());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    deliveryModalRef(component: Component, delivery: Delivery, csiteId?: number): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.delivery = delivery;
        modalRef.componentInstance.csiteId = csiteId;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
