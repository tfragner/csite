import { BaseEntity } from './../../shared';

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
        public deliveryId?: number,
    ) {
    }
}
