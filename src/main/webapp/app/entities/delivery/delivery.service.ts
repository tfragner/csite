import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DeliveryService {

    private resourceUrl = SERVER_API_URL + 'api/deliveries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(delivery: Delivery): Observable<Delivery> {
        const copy = this.convert(delivery);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(delivery: Delivery): Observable<Delivery> {
        const copy = this.convert(delivery);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Delivery> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Delivery.
     */
    private convertItemFromServer(json: any): Delivery {
        const entity: Delivery = Object.assign(new Delivery(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Delivery to a JSON which can be sent to the server.
     */
    private convert(delivery: Delivery): Delivery {
        const copy: Delivery = Object.assign({}, delivery);

        copy.date = this.dateUtils.toDate(delivery.date);
        return copy;
    }
}
