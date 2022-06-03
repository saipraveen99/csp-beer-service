package csp.beerworks.cspbeerservice.services.brewing;

import csp.beerworks.cspbeerservice.config.JmsConfig;
import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.events.BrewBeerEvent;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import csp.beerworks.cspbeerservice.services.inventory.BeerInventoryService;
import csp.beerworks.cspbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer invQtyOH = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Minimum On Hand: " + beer.getMinOnHand());
            log.debug("Inventory Quantity On Hand: "+invQtyOH);
            if (beer.getMinOnHand() >= invQtyOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
