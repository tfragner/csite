import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ChecklistComponent } from './checklist.component';
import { ChecklistDetailComponent } from './checklist-detail.component';
import {ChecklistPopupComponent, ChecklistWithDeliveryPopupComponent} from './checklist-dialog.component';
import { ChecklistDeletePopupComponent } from './checklist-delete-dialog.component';

export const checklistRoute: Routes = [
    {
        path: 'checklist',
        component: ChecklistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'checklist/:id',
        component: ChecklistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const checklistPopupRoute: Routes = [
    {
        path: 'checklist-new',
        component: ChecklistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'checklist-new/:deliveryId',
        component: ChecklistWithDeliveryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'checklist/:id/edit',
        component: ChecklistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'checklist/:id/delete',
        component: ChecklistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csiteApp.checklist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
