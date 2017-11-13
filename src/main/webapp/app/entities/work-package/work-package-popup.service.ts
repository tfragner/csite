import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WorkPackage } from './work-package.model';
import { WorkPackageService } from './work-package.service';

@Injectable()
export class WorkPackagePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private workPackageService: WorkPackageService

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
                this.workPackageService.find(id).subscribe((workPackage) => {
                    workPackage.startDate = this.datePipe
                        .transform(workPackage.startDate, 'yyyy-MM-ddTHH:mm:ss');
                    workPackage.endDate = this.datePipe
                        .transform(workPackage.endDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.workPackageModalRef(component, workPackage);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workPackageModalRef(component, new WorkPackage());
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
                    const temp = new WorkPackage();
                    temp.constructionsiteId = csiteId;

                    this.ngbModalRef = this.workPackageModalRef(component, temp);
                    resolve(this.ngbModalRef);
                    console.log('id gefunden');
                }, 0);
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workPackageModalRef(component, new WorkPackage());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    workPackageModalRef(component: Component, workPackage: WorkPackage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.workPackage = workPackage;
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
