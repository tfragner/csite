import { BaseEntity } from './../../shared';

export class Checklist implements BaseEntity {
    constructor(
        public id?: number,
        public inTime?: boolean,
        public complete?: boolean,
        public unloadingOk?: boolean,
        public notDamaged?: boolean,
        public description?: string,
        public claim?: boolean,
        public deliveryId?: number,
    ) {
        this.inTime = false;
        this.complete = false;
        this.unloadingOk = false;
        this.notDamaged = false;
        this.claim = false;
    }
}
