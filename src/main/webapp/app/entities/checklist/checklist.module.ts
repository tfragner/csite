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
        ChecklistDeletePopupComponent,
    ],
    entryComponents: [
        ChecklistComponent,
        ChecklistDialogComponent,
        ChecklistPopupComponent,
        ChecklistDeleteDialogComponent,
        ChecklistDeletePopupComponent,
    ],
    providers: [
        ChecklistService,
        ChecklistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteChecklistModule {}
