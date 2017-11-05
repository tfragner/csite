/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CsiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConstructionSiteDetailComponent } from '../../../../../../main/webapp/app/entities/construction-site/construction-site-detail.component';
import { ConstructionSiteService } from '../../../../../../main/webapp/app/entities/construction-site/construction-site.service';
import { ConstructionSite } from '../../../../../../main/webapp/app/entities/construction-site/construction-site.model';

describe('Component Tests', () => {

    describe('ConstructionSite Management Detail Component', () => {
        let comp: ConstructionSiteDetailComponent;
        let fixture: ComponentFixture<ConstructionSiteDetailComponent>;
        let service: ConstructionSiteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsiteTestModule],
                declarations: [ConstructionSiteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConstructionSiteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConstructionSiteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConstructionSiteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConstructionSiteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ConstructionSite(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.constructionSite).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
