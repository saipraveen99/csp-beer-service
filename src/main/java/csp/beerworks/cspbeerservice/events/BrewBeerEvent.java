package csp.beerworks.cspbeerservice.events;

import csp.beerworks.cspbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent{

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
