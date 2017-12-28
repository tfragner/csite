import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticlePopupService } from './article-popup.service';
import { ArticleService } from './article.service';
import { Delivery, DeliveryService } from '../delivery';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-article-dialog',
    templateUrl: './article-dialog.component.html'
})
export class ArticleDialogComponent implements OnInit {

    article: Article;
    isSaving: boolean;

    deliveries: Delivery[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleService: ArticleService,
        private deliveryService: DeliveryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.deliveryService.query()
            .subscribe((res: ResponseWrapper) => { this.deliveries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.article.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleService.update(this.article));
        } else {
            this.subscribeToSaveResponse(
                this.articleService.create(this.article));
        }
    }

    private subscribeToSaveResponse(result: Observable<Article>) {
        result.subscribe((res: Article) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Article) {
        this.eventManager.broadcast({ name: 'articleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDeliveryById(index: number, item: Delivery) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-article-popup',
    template: ''
})
export class ArticlePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articlePopupService: ArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component, params['id']);
            } else {
                this.articlePopupService
                    .open(ArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

@Component({
    selector: 'jhi-article-delivery-popup',
    template: ''
})
export class ArticleWithDeliveryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articlePopupService: ArticlePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['deliveryId'] ) {
                this.articlePopupService
                    .openWithDeliveryId(ArticleDialogComponent as Component, params['deliveryId']);
            } else {
                this.articlePopupService
                    .openWithDeliveryId(ArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
