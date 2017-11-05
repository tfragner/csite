import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WorkPackage } from './work-package.model';
import { WorkPackageService } from './work-package.service';

@Component({
    selector: 'jhi-work-package-detail',
    templateUrl: './work-package-detail.component.html'
})
export class WorkPackageDetailComponent implements OnInit, OnDestroy {

    workPackage: WorkPackage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workPackageService: WorkPackageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorkPackages();
    }

    load(id) {
        this.workPackageService.find(id).subscribe((workPackage) => {
            this.workPackage = workPackage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorkPackages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workPackageListModification',
            (response) => this.load(this.workPackage.id)
        );
    }
}
