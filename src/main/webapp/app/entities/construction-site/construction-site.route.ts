import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConstructionSiteComponent } from './construction-site.component';
import { ConstructionSiteDetailComponent } from './construction-site-detail.component';
import { ConstructionSitePopupComponent } from './construction-site-dialog.component';
import { ConstructionSiteDeletePopupComponent } from './construction-site-delete-dialog.component';

export const constructionSiteRoute: Routes = [
    {
        path: 'construction-site',
        component: ConstructionSiteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.constructionSite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'construction-site/:id',
        component: ConstructionSiteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.constructionSite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const constructionSitePopupRoute: Routes = [
    {
        path: 'construction-site-new',
        component: ConstructionSitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.constructionSite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'construction-site/:id/edit',
        component: ConstructionSitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.constructionSite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'construction-site/:id/delete',
        component: ConstructionSiteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.constructionSite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
