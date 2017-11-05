/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CsiteTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ArticleDetailComponent } from '../../../../../../main/webapp/app/entities/article/article-detail.component';
import { ArticleService } from '../../../../../../main/webapp/app/entities/article/article.service';
import { Article } from '../../../../../../main/webapp/app/entities/article/article.model';

describe('Component Tests', () => {

    describe('Article Management Detail Component', () => {
        let comp: ArticleDetailComponent;
        let fixture: ComponentFixture<ArticleDetailComponent>;
        let service: ArticleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsiteTestModule],
                declarations: [ArticleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ArticleService,
                    JhiEventManager
                ]
            }).overrideTemplate(ArticleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Article(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.article).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
