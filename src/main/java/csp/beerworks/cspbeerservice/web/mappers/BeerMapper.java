package csp.beerworks.cspbeerservice.web.mappers;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
