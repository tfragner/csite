import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import {
    ChecklistService,
    ChecklistPopupService,
    ChecklistComponent,
    ChecklistDetailComponent,
    ChecklistDialogComponent,
    ChecklistPopupComponent,
    ChecklistDeletePopupComponent,
    ChecklistDeleteDialogComponent,
    checklistRoute,
    checklistPopupRoute,
} from './';
import {ChecklistWithDeliveryPopupComponent} from './checklist-dialog.component';

const ENTITY_STATES = [
    ...checklistRoute,
    ...checklistPopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ChecklistComponent,
        ChecklistDetailComponent,
        ChecklistDialogComponent,
        ChecklistDeleteDialogComponent,
        ChecklistPopupComponent,
        ChecklistWithDeliveryPopupComponent,
        ChecklistDeletePopupComponent,
    ],
    entryComponents: [
        ChecklistComponent,
        ChecklistDialogComponent,
        ChecklistPopupComponent,
        ChecklistWithDeliveryPopupComponent,
        ChecklistDeleteDialogComponent,
        ChecklistDeletePopupComponent,
    ],
    providers: [
        ChecklistService,
        ChecklistPopupService,
    ],
    exports: [
        ChecklistDetailComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteChecklistModule {}
