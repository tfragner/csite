import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public longitude?: number,
        public latitude?: number,
        public deliveries?: BaseEntity[],
        public constructionSiteId?: number,
    ) {
    }
}
