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

const ENTITY_STATES = [
    ...deliveryRoute,
    ...deliveryPopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DeliveryComponent,
        DeliveryDetailComponent,
        DeliveryDialogComponent,
        DeliveryDeleteDialogComponent,
        DeliveryPopupComponent,
        DeliveryDeletePopupComponent,
    ],
    entryComponents: [
        DeliveryComponent,
        DeliveryDialogComponent,
        DeliveryPopupComponent,
        DeliveryDeleteDialogComponent,
        DeliveryDeletePopupComponent,
    ],
    providers: [
        DeliveryService,
        DeliveryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteDeliveryModule {}
