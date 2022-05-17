package csp.beerworks.cspbeerservice.services;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import csp.beerworks.cspbeerservice.web.controller.NotFoundException;
import csp.beerworks.cspbeerservice.web.mappers.BeerMapper;
import csp.beerworks.cspbeerservice.web.mappers.BeerMapperDecorator;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import csp.beerworks.cspbeerservice.web.model.BeerPagedList;
import csp.beerworks.cspbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getBeerById(UUID beerId, boolean showInventoryOnHand) {
        System.out.println("I was called");
        if (showInventoryOnHand) {
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        } else {
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }
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

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand==false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        System.out.println("I was called");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle.name())) {
            beerPage= beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle.name(), pageRequest);
        }
        else if (!StringUtils.isEmpty(beerName) ) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        }
        else if (null != beerStyle && !StringUtils.isEmpty(beerStyle.name())) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle.name(), pageRequest);
        }
        else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage.getContent()
                    .stream().map(beerMapper :: beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getNumber(), beerPage.getSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage.getContent()
                    .stream().map(beerMapper :: beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getNumber(), beerPage.getSize()),
                    beerPage.getTotalElements());
        }
        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getBeerByUpc(String upc) {

        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
