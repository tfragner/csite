import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import {
    WorkPackageService,
    WorkPackagePopupService,
    WorkPackageComponent,
    WorkPackageDetailComponent,
    WorkPackageDialogComponent,
    WorkPackagePopupComponent,
    WorkPackageDeletePopupComponent,
    WorkPackageDeleteDialogComponent,
    workPackageRoute,
    workPackagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...workPackageRoute,
    ...workPackagePopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkPackageComponent,
        WorkPackageDetailComponent,
        WorkPackageDialogComponent,
        WorkPackageDeleteDialogComponent,
        WorkPackagePopupComponent,
        WorkPackageDeletePopupComponent,
    ],
    entryComponents: [
        WorkPackageComponent,
        WorkPackageDialogComponent,
        WorkPackagePopupComponent,
        WorkPackageDeleteDialogComponent,
        WorkPackageDeletePopupComponent,
    ],
    providers: [
        WorkPackageService,
        WorkPackagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteWorkPackageModule {}
