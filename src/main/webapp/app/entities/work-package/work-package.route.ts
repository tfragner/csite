import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WorkPackageComponent } from './work-package.component';
import { WorkPackageDetailComponent } from './work-package-detail.component';
import {WorkPackagePopupComponent, WorkPackageWithCsitePopupComponent} from './work-package-dialog.component';
import { WorkPackageDeletePopupComponent } from './work-package-delete-dialog.component';

export const workPackageRoute: Routes = [
    {
        path: 'work-package',
        component: WorkPackageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'work-package/:id',
        component: WorkPackageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workPackagePopupRoute: Routes = [
    {
        path: 'work-package-new',
        component: WorkPackagePopupComponent,
        data: {
            authorities: ['ROLE_SALES'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-package-new/:csiteId',
        component: WorkPackageWithCsitePopupComponent,
        data: {
            authorities: ['ROLE_SALES'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-package/:id/edit',
        component: WorkPackagePopupComponent,
        data: {
            authorities: ['ROLE_SALES'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-package/:id/delete',
        component: WorkPackageDeletePopupComponent,
        data: {
            authorities: ['ROLE_SALES'],
            pageTitle: 'csiteApp.workPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
