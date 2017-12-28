import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DeliveryComponent } from './delivery.component';
import { DeliveryDetailComponent } from './delivery-detail.component';
import {DeliveryPopupComponent, DeliveryWithCsitePopupComponent} from './delivery-dialog.component';
import { DeliveryDeletePopupComponent } from './delivery-delete-dialog.component';

export const deliveryRoute: Routes = [
    {
        path: 'delivery',
        component: DeliveryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'delivery/:id',
        component: DeliveryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliveryPopupRoute: Routes = [
    {
        path: 'delivery-new',
        component: DeliveryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery-new/:csiteId',
        component: DeliveryWithCsitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery/:id/edit',
        component: DeliveryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery/:id/edit/:csiteId',
        component: DeliveryWithCsitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'delivery/:id/delete',
        component: DeliveryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.delivery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
