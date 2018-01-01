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
    WorkPackageWithCsitePopupComponent,
    WorkPackageDeletePopupComponent,
    WorkPackageDeleteDialogComponent,
    workPackageRoute,
    workPackagePopupRoute,
} from './';
import {CalendarModule, DataTableModule} from 'primeng/primeng';
import {SharedModule} from 'primeng/components/common/shared';
import {GanttComponent} from './gantt.component';

const ENTITY_STATES = [
    ...workPackageRoute,
    ...workPackagePopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        DataTableModule,
        SharedModule,
        CalendarModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkPackageComponent,
        WorkPackageDetailComponent,
        WorkPackageDialogComponent,
        WorkPackageDeleteDialogComponent,
        WorkPackagePopupComponent,
        WorkPackageWithCsitePopupComponent,
        WorkPackageDeletePopupComponent,
        GanttComponent,
    ],
    entryComponents: [
        WorkPackageComponent,
        WorkPackageDialogComponent,
        WorkPackagePopupComponent,
        WorkPackageWithCsitePopupComponent,
        WorkPackageDeleteDialogComponent,
        WorkPackageDeletePopupComponent,
    ],
    providers: [
        WorkPackageService,
        WorkPackagePopupService,
    ],
    exports: [
        WorkPackageComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteWorkPackageModule {}
