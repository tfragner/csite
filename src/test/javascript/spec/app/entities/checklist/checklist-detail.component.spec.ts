/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CsiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ChecklistDetailComponent } from '../../../../../../main/webapp/app/entities/checklist/checklist-detail.component';
import { ChecklistService } from '../../../../../../main/webapp/app/entities/checklist/checklist.service';
import { Checklist } from '../../../../../../main/webapp/app/entities/checklist/checklist.model';

describe('Component Tests', () => {

    describe('Checklist Management Detail Component', () => {
        let comp: ChecklistDetailComponent;
        let fixture: ComponentFixture<ChecklistDetailComponent>;
        let service: ChecklistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsiteTestModule],
                declarations: [ChecklistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ChecklistService,
                    JhiEventManager
                ]
            }).overrideTemplate(ChecklistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChecklistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChecklistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Checklist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.checklist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
