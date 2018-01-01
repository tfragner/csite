import {Response} from '@angular/http';

export function ExtractData(res: Response): any {
    return res.json();
}

export function HandleError(error: any): Promise<any> {
    console.log(error);
    return Promise.reject(error);
}
