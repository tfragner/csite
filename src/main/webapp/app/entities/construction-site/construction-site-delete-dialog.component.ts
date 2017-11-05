import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConstructionSite } from './construction-site.model';
import { ConstructionSitePopupService } from './construction-site-popup.service';
import { ConstructionSiteService } from './construction-site.service';

@Component({
    selector: 'jhi-construction-site-delete-dialog',
    templateUrl: './construction-site-delete-dialog.component.html'
})
export class ConstructionSiteDeleteDialogComponent {

    constructionSite: ConstructionSite;

    constructor(
        private constructionSiteService: ConstructionSiteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.constructionSiteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'constructionSiteListModification',
                content: 'Deleted an constructionSite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-construction-site-delete-popup',
    template: ''
})
export class ConstructionSiteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private constructionSitePopupService: ConstructionSitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.constructionSitePopupService
                .open(ConstructionSiteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
