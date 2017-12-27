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
        public start_date?: any,
        public endDate?: any,
        public status?: WorkPackageStatus,
        public duration?: number,
        public progress?: number,
        public deliveries?: BaseEntity[],
        public constructionsiteId?: number,
    ) {
    }
}
