import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticleService } from './article.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-article',
    templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit, OnDestroy {
articles: Article[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleService: ArticleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.articles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Article) {
        return item.id;
    }
    registerChangeInArticles() {
        this.eventSubscriber = this.eventManager.subscribe('articleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
