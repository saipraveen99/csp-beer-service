package csp.beerworks.cspbeerservice.events;

import csp.beerworks.cspbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent{

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
