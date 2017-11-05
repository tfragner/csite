import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ConstructionSite } from './construction-site.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConstructionSiteService {

    private resourceUrl = SERVER_API_URL + 'api/construction-sites';

    constructor(private http: Http) { }

    create(constructionSite: ConstructionSite): Observable<ConstructionSite> {
        const copy = this.convert(constructionSite);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(constructionSite: ConstructionSite): Observable<ConstructionSite> {
        const copy = this.convert(constructionSite);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ConstructionSite> {
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
     * Convert a returned JSON object to ConstructionSite.
     */
    private convertItemFromServer(json: any): ConstructionSite {
        const entity: ConstructionSite = Object.assign(new ConstructionSite(), json);
        return entity;
    }

    /**
     * Convert a ConstructionSite to a JSON which can be sent to the server.
     */
    private convert(constructionSite: ConstructionSite): ConstructionSite {
        const copy: ConstructionSite = Object.assign({}, constructionSite);
        return copy;
    }
}
