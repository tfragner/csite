import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { WorkPackage } from './work-package.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WorkPackageService {

    private resourceUrl = SERVER_API_URL + 'api/work-packages';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(workPackage: WorkPackage): Observable<WorkPackage> {
        const copy = this.convert(workPackage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(workPackage: WorkPackage): Observable<WorkPackage> {
        const copy = this.convert(workPackage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<WorkPackage> {
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
     * Convert a returned JSON object to WorkPackage.
     */
    private convertItemFromServer(json: any): WorkPackage {
        const entity: WorkPackage = Object.assign(new WorkPackage(), json);
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a WorkPackage to a JSON which can be sent to the server.
     */
    private convert(workPackage: WorkPackage): WorkPackage {
        const copy: WorkPackage = Object.assign({}, workPackage);

        copy.startDate = this.dateUtils.toDate(workPackage.startDate);

        copy.endDate = this.dateUtils.toDate(workPackage.endDate);
        return copy;
    }
}
