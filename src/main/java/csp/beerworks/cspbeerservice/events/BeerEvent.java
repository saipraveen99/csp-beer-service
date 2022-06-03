package csp.beerworks.cspbeerservice.events;

import csp.beerworks.cspbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = -9220789347991942022L;

    private final BeerDto beerDto;
}
