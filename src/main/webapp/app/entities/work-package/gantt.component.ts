import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

import 'dhtmlx-gantt';
import {} from '@types/dhtmlxgantt';
import {TaskService} from './task.service';
import {LinkService} from './link.service';

@Component({
    selector: 'jhi-gantt-diagram',
    styles: [
            `
            :host{
                display: block;
                height: 300px;
                position: relative;
                width: 100%;
            }
        `],
    providers: [TaskService, LinkService],
    template: '<div #gantt_here style="width: 100%; height: 100%;"></div>',
})
export class GanttComponent implements OnInit {
    @ViewChild('gantt_here') ganttContainer: ElementRef;
    @Input() csiteid: number;

    constructor(private taskService: TaskService) {}

    ngOnInit() {
        gantt.config.xml_date = '%Y-%m-%d %H:%i';
        gantt.config.scale_unit = 'week';
        gantt.config.date_scale = 'Week #%W';
        gantt.config.select_task = false;
        gantt.config.details_on_dblclick = false;
        gantt.config.drag_progress = false;
        gantt.config.drag_resize = false;
        gantt.config.drag_move = false;
        gantt.config.show_links = false;
        gantt.config.readonly = true;
        gantt.init(this.ganttContainer.nativeElement);

        Promise.all([this.taskService.get(this.csiteid)])
            .then(([data]) => {
                gantt.clearAll();
                gantt.parse({data});
            });
    }
}
