import {Component, OnInit, OnDestroy, Input, EventEmitter, Output} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Checklist } from './checklist.model';
import { ChecklistService } from './checklist.service';

@Component({
    selector: 'jhi-checklist-detail',
    templateUrl: './checklist-detail.component.html'
})
export class ChecklistDetailComponent implements OnInit, OnDestroy {

    checklist: Checklist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    @Input() blabla: number;
    @Output() checklistUpdated = new EventEmitter();

    constructor(
        private eventManager: JhiEventManager,
        private checklistService: ChecklistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {

        this.subscription = this.route.params.subscribe((params) => {
            if (this.blabla != null) {
                this.load(this.blabla);
            } else {
                this.load(params['id']);
            }

        });

        this.registerChangeInChecklists();
    }

    load(id) {
        this.checklistService.find(id).subscribe((checklist) => {
            this.checklist = checklist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChecklists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'checklistListModification',
            (response) => {this.load(this.checklist.id); this.checklistUpdated.emit(this.checklist)}
        );
    }
}
