import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsiteSharedModule } from '../../shared';
import { AgmCoreModule } from '@agm/core';

import {
    LocationService,
    LocationPopupService,
    LocationComponent,
    LocationDetailComponent,
    LocationDialogComponent,
    LocationPopupComponent,
    LocationDeletePopupComponent,
    LocationDeleteDialogComponent,
    locationRoute,
    locationPopupRoute,
} from './';
import {LocationWithCsitePopupComponent} from './location-dialog.component';

const ENTITY_STATES = [
    ...locationRoute,
    ...locationPopupRoute,
];

@NgModule({
    imports: [
        CsiteSharedModule,
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyCvrWX1LiZavmtkRPg-Yv_q4xjQTVnNmpM'
        }),
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LocationComponent,
        LocationDetailComponent,
        LocationDialogComponent,
        LocationDeleteDialogComponent,
        LocationWithCsitePopupComponent,
        LocationPopupComponent,
        LocationDeletePopupComponent,
    ],
    entryComponents: [
        LocationComponent,
        LocationDialogComponent,
        LocationPopupComponent,
        LocationWithCsitePopupComponent,
        LocationDeleteDialogComponent,
        LocationDeletePopupComponent,
    ],
    providers: [
        LocationService,
        LocationPopupService,
    ],
    exports: [
        LocationComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteLocationModule {}
