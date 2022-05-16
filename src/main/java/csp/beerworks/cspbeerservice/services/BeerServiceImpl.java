package csp.beerworks.cspbeerservice.services;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import csp.beerworks.cspbeerservice.web.controller.NotFoundException;
import csp.beerworks.cspbeerservice.web.mappers.BeerMapper;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(
                        beerMapper.beerDtoToBeer(beerDto)
                )
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setUpc(beerDto.getUpc());
        beer.setPrice(beerDto.getPrice());
        return beerMapper.beerToBeerDto(
                beerRepository.save(beer)
        );
    }
}
