import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Checklist } from './checklist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ChecklistService {

    private resourceUrl = SERVER_API_URL + 'api/checklists';

    constructor(private http: Http) { }

    create(checklist: Checklist): Observable<Checklist> {
        const copy = this.convert(checklist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(checklist: Checklist): Observable<Checklist> {
        const copy = this.convert(checklist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Checklist> {
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
     * Convert a returned JSON object to Checklist.
     */
    private convertItemFromServer(json: any): Checklist {
        const entity: Checklist = Object.assign(new Checklist(), json);
        return entity;
    }

    /**
     * Convert a Checklist to a JSON which can be sent to the server.
     */
    private convert(checklist: Checklist): Checklist {
        const copy: Checklist = Object.assign({}, checklist);
        return copy;
    }
}
