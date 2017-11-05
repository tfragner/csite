import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import {
    ConstructionSiteService,
    ConstructionSitePopupService,
    ConstructionSiteComponent,
    ConstructionSiteDetailComponent,
    ConstructionSiteDialogComponent,
    ConstructionSitePopupComponent,
    ConstructionSiteDeletePopupComponent,
    ConstructionSiteDeleteDialogComponent,
    constructionSiteRoute,
    constructionSitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...constructionSiteRoute,
    ...constructionSitePopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConstructionSiteComponent,
        ConstructionSiteDetailComponent,
        ConstructionSiteDialogComponent,
        ConstructionSiteDeleteDialogComponent,
        ConstructionSitePopupComponent,
        ConstructionSiteDeletePopupComponent,
    ],
    entryComponents: [
        ConstructionSiteComponent,
        ConstructionSiteDialogComponent,
        ConstructionSitePopupComponent,
        ConstructionSiteDeleteDialogComponent,
        ConstructionSiteDeletePopupComponent,
    ],
    providers: [
        ConstructionSiteService,
        ConstructionSitePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteConstructionSiteModule {}
