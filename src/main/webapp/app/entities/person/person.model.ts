import { BaseEntity } from './../../shared';

export const enum PersonType {
    'SUPPLIER',
    'CUSTOMER',
    'PERSON'
}

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public lastName?: string,
        public firstName?: string,
        public type?: PersonType,
        public deliveries?: BaseEntity[],
        public constructionsites?: BaseEntity[],
        public constsitecustomers?: BaseEntity[],
    ) {
    }
}
