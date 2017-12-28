import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import {
    DeliveryService,
    DeliveryPopupService,
    DeliveryComponent,
    DeliveryDetailComponent,
    DeliveryDialogComponent,
    DeliveryPopupComponent,
    DeliveryDeletePopupComponent,
    DeliveryDeleteDialogComponent,
    deliveryRoute,
    deliveryPopupRoute,
} from './';
import {DeliveryWithCsitePopupComponent} from './delivery-dialog.component';
import {CsiteChecklistModule} from '../checklist/checklist.module';
import {CsiteArticleModule} from '../article/article.module';

const ENTITY_STATES = [
    ...deliveryRoute,
    ...deliveryPopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        CsiteChecklistModule,
        CsiteArticleModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DeliveryComponent,
        DeliveryDetailComponent,
        DeliveryDialogComponent,
        DeliveryDeleteDialogComponent,
        DeliveryPopupComponent,
        DeliveryWithCsitePopupComponent,
        DeliveryDeletePopupComponent,
    ],
    entryComponents: [
        DeliveryComponent,
        DeliveryDialogComponent,
        DeliveryPopupComponent,
        DeliveryWithCsitePopupComponent,
        DeliveryDeleteDialogComponent,
        DeliveryDeletePopupComponent,
    ],
    providers: [
        DeliveryService,
        DeliveryPopupService,
    ],
    exports: [
        DeliveryComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteDeliveryModule {}
