package csp.beerworks.cspbeerservice.services.brewing;

import csp.beerworks.cspbeerservice.config.JmsConfig;
import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.events.BrewBeerEvent;
import csp.beerworks.cspbeerservice.events.NewInventoryEvent;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();
        Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
        log.debug("Brewed beer: "+beer.getMinOnHand() + " :QOH: "+beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
