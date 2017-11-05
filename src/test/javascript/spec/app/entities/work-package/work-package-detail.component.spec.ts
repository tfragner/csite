/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CsiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WorkPackageDetailComponent } from '../../../../../../main/webapp/app/entities/work-package/work-package-detail.component';
import { WorkPackageService } from '../../../../../../main/webapp/app/entities/work-package/work-package.service';
import { WorkPackage } from '../../../../../../main/webapp/app/entities/work-package/work-package.model';

describe('Component Tests', () => {

    describe('WorkPackage Management Detail Component', () => {
        let comp: WorkPackageDetailComponent;
        let fixture: ComponentFixture<WorkPackageDetailComponent>;
        let service: WorkPackageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsiteTestModule],
                declarations: [WorkPackageDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WorkPackageService,
                    JhiEventManager
                ]
            }).overrideTemplate(WorkPackageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkPackageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkPackageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WorkPackage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.workPackage).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
