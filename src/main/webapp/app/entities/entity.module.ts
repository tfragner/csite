import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CsiteConstructionSiteModule } from './construction-site/construction-site.module';
import { CsiteLocationModule } from './location/location.module';
import { CsiteWorkPackageModule } from './work-package/work-package.module';
import { CsiteDeliveryModule } from './delivery/delivery.module';
import { CsiteArticleModule } from './article/article.module';
import { CsitePersonModule } from './person/person.module';
import { CsiteChecklistModule } from './checklist/checklist.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CsiteConstructionSiteModule,
        CsiteLocationModule,
        CsiteWorkPackageModule,
        CsiteDeliveryModule,
        CsiteArticleModule,
        CsitePersonModule,
        CsiteChecklistModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsiteEntityModule {}
