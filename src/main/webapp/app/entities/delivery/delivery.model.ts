import { BaseEntity } from './../../shared';
import {Checklist} from '../checklist/checklist.model';

export const enum UnloadingType {
    'KRAN',
    'STAPLER',
    'HAND'
}

export const enum AvisoType {
    'MATANLIEFERUNG',
    'ENTSORGUNG'
}

export const enum DeliveryStatus {
    'OPEN',
    'CLOSED',
    'RECLAMATION'
}

export const enum LKWType {
    'T35',
    'T70',
    'T400'
}

export class Delivery implements BaseEntity {
    constructor(
        public id?: number,
        public orderNumber?: string,
        public kalenderwoche?: number,
        public date?: any,
        public unloading?: UnloadingType,
        public avisoType?: AvisoType,
        public status?: DeliveryStatus,
        public imageContentType?: string,
        public image?: any,
        public lkwType?: LKWType,
        public articles?: BaseEntity[],
        public checklistId?: number,
        public workpackageId?: number,
        public personId?: number,
        public locationId?: number,
        public checklist?: Checklist
    ) {
    }
}
