import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import {
    ArticleService,
    ArticlePopupService,
    ArticleComponent,
    ArticleDetailComponent,
    ArticleDialogComponent,
    ArticlePopupComponent,
    ArticleDeletePopupComponent,
    ArticleDeleteDialogComponent,
    articleRoute,
    articlePopupRoute,
} from './';
import {ArticleWithDeliveryPopupComponent} from './article-dialog.component';

const ENTITY_STATES = [
    ...articleRoute,
    ...articlePopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArticleComponent,
        ArticleDetailComponent,
        ArticleDialogComponent,
        ArticleDeleteDialogComponent,
        ArticlePopupComponent,
        ArticleWithDeliveryPopupComponent,
        ArticleDeletePopupComponent,
    ],
    entryComponents: [
        ArticleComponent,
        ArticleDialogComponent,
        ArticlePopupComponent,
        ArticleWithDeliveryPopupComponent,
        ArticleDeleteDialogComponent,
        ArticleDeletePopupComponent,
    ],
    providers: [
        ArticleService,
        ArticlePopupService,
    ],
    exports: [
        ArticleComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteArticleModule {}
