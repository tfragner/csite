import { BaseEntity } from './../../shared';

export class WorkPackage implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startDate?: any,
        public endDate?: any,
        public deliveries?: BaseEntity[],
        public constructionsiteId?: number,
    ) {
    }
}
