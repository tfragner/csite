import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WorkPackage } from './work-package.model';
import { WorkPackagePopupService } from './work-package-popup.service';
import { WorkPackageService } from './work-package.service';

@Component({
    selector: 'jhi-work-package-delete-dialog',
    templateUrl: './work-package-delete-dialog.component.html'
})
export class WorkPackageDeleteDialogComponent {

    workPackage: WorkPackage;

    constructor(
        private workPackageService: WorkPackageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workPackageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workPackageListModification',
                content: 'Deleted an workPackage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-work-package-delete-popup',
    template: ''
})
export class WorkPackageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workPackagePopupService: WorkPackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workPackagePopupService
                .open(WorkPackageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
