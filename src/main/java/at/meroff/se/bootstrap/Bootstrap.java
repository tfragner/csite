package at.meroff.se.bootstrap;

import at.meroff.se.domain.Location;
import at.meroff.se.domain.enumeration.LKWType;
import at.meroff.se.domain.enumeration.PersonType;
import at.meroff.se.service.ConstructionSiteService;
import at.meroff.se.service.LocationService;
import at.meroff.se.service.PersonService;
import at.meroff.se.service.WorkPackageService;
import at.meroff.se.service.dto.ConstructionSiteDTO;
import at.meroff.se.service.dto.LocationDTO;
import at.meroff.se.service.dto.PersonDTO;
import at.meroff.se.service.dto.WorkPackageDTO;
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


    public Bootstrap(PersonService personService,
                     ConstructionSiteService constructionSiteService,
                     WorkPackageService workPackageService,
                     LocationService locationService) {
        this.personService = personService;
        this.constructionSiteService = constructionSiteService;
        this.workPackageService = workPackageService;
        this.locationService = locationService;

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
        customer.setLastName("Fragner");
        customer.setFirstName("Thomas");
        customer.setType(PersonType.SUPPLIER);
        supplier = personService.save(customer);

        // create construction site
        ConstructionSiteDTO site = new ConstructionSiteDTO();
        site.setPrjNumber(1);
        site.setPrjName("JKU - TNF");
        site.setKran(true);
        site.setStapler(true);
        site.setMaxLKWType(LKWType.T400);
        site.setContainerId(manInTheContainer.getId());
        site.setCustomerId(customer.getId());
        site = constructionSiteService.save(site);

        // create work packages
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        WorkPackageDTO p1 = new WorkPackageDTO();
        p1.setConstructionsiteId(site.getId());
        p1.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("30.11.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p1.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.12.2017", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p1.setName("JKU - TNF - Work Package 1");
        p1 = workPackageService.save(p1);

        WorkPackageDTO p2 = new WorkPackageDTO();
        p2.setConstructionsiteId(site.getId());
        p2.setStartDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("12.12.2017", dateFormat), LocalTime.parse("08:00", timeFormat)
        ), ZoneId.systemDefault()));
        p2.setEndDate(ZonedDateTime.of(LocalDateTime.of(
            LocalDate.parse("31.01.2018", dateFormat), LocalTime.parse("17:00", timeFormat)
        ), ZoneId.systemDefault()));
        p2.setName("JKU - TNF - Work Package 2");
        p2 = workPackageService.save(p2);

        // create locations
        LocationDTO l1 = new LocationDTO();
        l1.setConstructionSiteId(site.getId());
        l1.setLongitude(14.408922F);
        l1.setLatitude(48.038681F);
        l1.setName("Hauptentladeplatz");
        locationService.save(l1);

        LocationDTO l2 = new LocationDTO();
        l2.setConstructionSiteId(site.getId());
        l2.setLongitude(14.428922F);
        l2.setLatitude(48.068681F);
        l2.setName("Nebenentladeplatz");
        locationService.save(l2);




    }
}
