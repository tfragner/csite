import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConstructionSite } from './construction-site.model';
import { ConstructionSitePopupService } from './construction-site-popup.service';
import { ConstructionSiteService } from './construction-site.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-construction-site-dialog',
    templateUrl: './construction-site-dialog.component.html'
})
export class ConstructionSiteDialogComponent implements OnInit {

    constructionSite: ConstructionSite;
    isSaving: boolean;

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private constructionSiteService: ConstructionSiteService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.constructionSite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.constructionSiteService.update(this.constructionSite));
        } else {
            this.subscribeToSaveResponse(
                this.constructionSiteService.create(this.constructionSite));
        }
    }

    private subscribeToSaveResponse(result: Observable<ConstructionSite>) {
        result.subscribe((res: ConstructionSite) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ConstructionSite) {
        this.eventManager.broadcast({ name: 'constructionSiteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-construction-site-popup',
    template: ''
})
export class ConstructionSitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private constructionSitePopupService: ConstructionSitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.constructionSitePopupService
                    .open(ConstructionSiteDialogComponent as Component, params['id']);
            } else {
                this.constructionSitePopupService
                    .open(ConstructionSiteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
