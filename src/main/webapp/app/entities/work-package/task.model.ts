export class Task {
    id: number;
    name: string;
    startDate: string;
    endDate: string;
    status: string;
    duration: number;
    progress: number;
    constructionsiteId: number;
    constructionsitePrjName: string;
    start_date: string;
    text: string;
    parent?: number;
}
/*{
    "id": 1,
    "name": "JKU - TNF - Work Package 1",
    "startDate": "2017-11-30T08:00:00+01:00",
    "endDate": "2017-12-31T17:00:00+01:00",
    "status": "OPEN",
    "duration": 31,
    "progress": 0.5,
    "constructionsiteId": 1,
    "constructionsitePrjName": "JKU - TNF",
    "start_date": "2017-11-30 08:00",
    "text": "JKU - TNF - Work Package 1"
},*/
