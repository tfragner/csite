package at.meroff.se.bootstrap;

import at.meroff.se.domain.Location;
import at.meroff.se.domain.enumeration.*;
import at.meroff.se.service.*;
import at.meroff.se.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    private final PersonService personService;
    private final ConstructionSiteService constructionSiteService;
    private final WorkPackageService workPackageService;
    private final LocationService locationService;
    private final DeliveryService deliveryService;
    private final ArticleService articleService;

    public Bootstrap(PersonService personService,
                     ConstructionSiteService constructionSiteService,
                     WorkPackageService workPackageService,
                     LocationService locationService,
                     DeliveryService deliveryService,
                     ArticleService articleService) {
        this.personService = personService;
        this.constructionSiteService = constructionSiteService;
        this.workPackageService = workPackageService;
        this.locationService = locationService;
        this.deliveryService = deliveryService;
        this.articleService = articleService;

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // create persons
        PersonDTO manInTheContainer = new PersonDTO();
        manInTheContainer.setLastName("Reiser");
        manInTheContainer.setFirstName("Max");
        manInTheContainer.setType(PersonType.PERSON);
        manInTheContainer = personService.save(manInTheContainer);

        PersonDTO customer = new PersonDTO();
        customer.setLastName("Milas");
        customer.setFirstName("Antonia");
        customer.setType(PersonType.CUSTOMER);
        customer = personService.save(customer);

        PersonDTO supplier = new PersonDTO();
        supplier.setLastName("Fragner");
        supplier.setFirstName("Thomas");
        supplier.setType(PersonType.SUPPLIER);
        supplier = personService.save(supplier);

        // create construction site
        ConstructionSiteDTO site1 = new ConstructionSiteDTO();
        site1.setPrjNumber(1);
        site1.setPrjName("JKU - TNF");
        site1.setKran(true);
        site1.setStapler(true);
        site1.setMaxLKWType(LKWType.T400);
        site1.setContainerId(manInTheContainer.getId());
        site1.setCustomerId(customer.getId());
        site1 = constructionSiteService.save(site1);

        ConstructionSiteDTO site2 = new ConstructionSiteDTO();
        site2.setPrjNumber(2);
        site2.setPrjName("JKU - Science Park 4");
        site2.setKran(true);
        site2.setStapler(false);
        site2.setMaxLKWType(LKWType.T70);
        site2.setContainerId(manInTheContainer.getId());
        site2.setCustomerId(customer.getId());
        site2 = constructionSiteService.save(site2);

        // create work packages
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        WorkPackageDTO p1 = new WorkPackageDTO();
        p1.setConstructionsiteId(site1.getId());
        p1.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("30.11.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p1.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.12.2017", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p1.setName("JKU - TNF - Work Package 1");
        p1.setStatus(WorkPackageStatus.OPEN);
        p1 = workPackageService.save(p1);

        WorkPackageDTO p2 = new WorkPackageDTO();
        p2.setConstructionsiteId(site1.getId());
        p2.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("12.12.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p2.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.01.2018", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p2.setName("JKU - TNF - Work Package 2");
        p2.setStatus(WorkPackageStatus.OPEN);
        p2 = workPackageService.save(p2);

        WorkPackageDTO p3 = new WorkPackageDTO();
        p3.setConstructionsiteId(site2.getId());
        p3.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("30.11.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p3.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.12.2017", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p3.setName("JKU - Science Park - Work Package 1");
        p3.setStatus(WorkPackageStatus.OPEN);
        p3 = workPackageService.save(p3);

        WorkPackageDTO p4 = new WorkPackageDTO();
        p4.setConstructionsiteId(site2.getId());
        p4.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("12.12.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p4.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.01.2018", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p4.setName("JKU - Science Park - Work Package 2");
        p4.setStatus(WorkPackageStatus.OPEN);
        p4 = workPackageService.save(p4);

        // create locations
        LocationDTO l1 = new LocationDTO();
        l1.setConstructionSiteId(site1.getId());
        l1.setLongitude(14.408922F);
        l1.setLatitude(48.038681F);
        l1.setName("Hauptentladeplatz");
        l1 = locationService.save(l1);

        LocationDTO l2 = new LocationDTO();
        l2.setConstructionSiteId(site1.getId());
        l2.setLongitude(14.428922F);
        l2.setLatitude(48.068681F);
        l2.setName("Nebenentladeplatz");
        l2 = locationService.save(l2);

        LocationDTO l3 = new LocationDTO();
        l3.setConstructionSiteId(site2.getId());
        l3.setLongitude(14.408922F);
        l3.setLatitude(48.038681F);
        l3.setName("Hauptentladeplatz");
        l3 = locationService.save(l3);

        LocationDTO l4 = new LocationDTO();
        l4.setConstructionSiteId(site2.getId());
        l4.setLongitude(14.428922F);
        l4.setLatitude(48.068681F);
        l4.setName("Nebenentladeplatz");
        l4 = locationService.save(l4);

        // Deliveries
        DeliveryDTO d1 = new DeliveryDTO();
        d1.setOrderNumber("123");
        d1.setKalenderwoche(52);
        d1.setDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("12.12.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        d1.setLkwType(LKWType.T70);
        d1.setWorkpackageId(p1.getId());
        d1.setLocationId(l1.getId());
        d1.setUnloading(UnloadingType.STAPLER);
        d1.setAvisoType(AvisoType.MATANLIEFERUNG);
        d1.setStatus(DeliveryStatus.OPEN);
        d1.setPersonId(1L);
        d1.setLocationId(1L);
        d1 = deliveryService.save(d1);

        // Deliveries
        DeliveryDTO d2 = new DeliveryDTO();
        d2.setOrderNumber("123");
        d2.setKalenderwoche(52);
        d2.setDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("12.12.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        d2.setLkwType(LKWType.T70);
        d2.setWorkpackageId(p3.getId());
        d2.setLocationId(l3.getId());
        d2.setUnloading(UnloadingType.STAPLER);
        d2.setAvisoType(AvisoType.MATANLIEFERUNG);
        d2.setStatus(DeliveryStatus.OPEN);
        d2.setPersonId(1L);
        d2 = deliveryService.save(d2);

        ArticleDTO a1 = new ArticleDTO();
        a1.setDeliveryId(d1.getId());
        a1.setName("Holz");
        a1.setQuantity(5);
        a1 = articleService.save(a1);

        ArticleDTO a2 = new ArticleDTO();
        a2.setDeliveryId(d2.getId());
        a2.setName("Holz");
        a2.setQuantity(5);
        a2 = articleService.save(a2);

    }
}
