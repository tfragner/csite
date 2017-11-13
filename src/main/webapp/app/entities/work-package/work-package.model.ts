import { BaseEntity } from './../../shared';

export const enum WorkPackageStatus {
    'PLANNED',
    'OPEN',
    'FINISHED'
}

export class WorkPackage implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public status?: WorkPackageStatus,
        public deliveries?: BaseEntity[],
        public constructionsiteId?: number,
    ) {
    }
}
