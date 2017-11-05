import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkPackage } from './work-package.model';
import { WorkPackagePopupService } from './work-package-popup.service';
import { WorkPackageService } from './work-package.service';
import { ConstructionSite, ConstructionSiteService } from '../construction-site';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-work-package-dialog',
    templateUrl: './work-package-dialog.component.html'
})
export class WorkPackageDialogComponent implements OnInit {

    workPackage: WorkPackage;
    isSaving: boolean;

    constructionsites: ConstructionSite[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private workPackageService: WorkPackageService,
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
        if (this.workPackage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workPackageService.update(this.workPackage));
        } else {
            this.subscribeToSaveResponse(
                this.workPackageService.create(this.workPackage));
        }
    }

    private subscribeToSaveResponse(result: Observable<WorkPackage>) {
        result.subscribe((res: WorkPackage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: WorkPackage) {
        this.eventManager.broadcast({ name: 'workPackageListModification', content: 'OK'});
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
    selector: 'jhi-work-package-popup',
    template: ''
})
export class WorkPackagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workPackagePopupService: WorkPackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workPackagePopupService
                    .open(WorkPackageDialogComponent as Component, params['id']);
            } else {
                this.workPackagePopupService
                    .open(WorkPackageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
