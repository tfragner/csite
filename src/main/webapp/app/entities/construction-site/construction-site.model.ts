import { BaseEntity } from './../../shared';

export const enum LKWType {
    'T35',
    'T70',
    'T400'
}

export class ConstructionSite implements BaseEntity {
    constructor(
        public id?: number,
        public prjNumber?: number,
        public prjName?: string,
        public kran?: boolean,
        public stapler?: boolean,
        public maxLKWType?: LKWType,
        public workpackages?: BaseEntity[],
        public locations?: BaseEntity[],
        public containerId?: number,
        public customerId?: number,
    ) {
        this.kran = false;
        this.stapler = false;
    }
}
