import {Injectable} from '@angular/core';
import {Task} from './task.model';
import {Http} from '@angular/http';
import {ExtractData, HandleError} from './service-helper';
import { SERVER_API_URL } from '../../app.constants';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class TaskService {

    constructor(private http: Http) {}

    get(csiteid: number): Promise<Task[]> {
        return this.http.get(SERVER_API_URL + '/api/work-packages/construction_site/' + csiteid)
            .toPromise()
            .then(ExtractData)
            .catch(HandleError);
    }
}
