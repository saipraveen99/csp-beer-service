package csp.beerworks.cspbeerservice.web.controller;

import csp.beerworks.cspbeerservice.services.BeerService;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import csp.beerworks.cspbeerservice.web.model.BeerPagedList;
import csp.beerworks.cspbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class BeerController {
    private final BeerService beerService;

    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private static final Integer DEFAULT_PAGE_NO = 0;

    @GetMapping(produces = {"application/json"}, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if (null == showInventoryOnHand) {
            showInventoryOnHand = false;
        }

        if (null == pageNumber || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NO;
        }

        if (null == pageSize || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        BeerPagedList beerPagedList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize)
        , showInventoryOnHand);
        return new ResponseEntity<>(beerPagedList, HttpStatus.OK);
    }
    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (null == showInventoryOnHand) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity(beerService.getBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping(path = "beer")
    public ResponseEntity<BeerDto> saveBeer(@Validated @RequestBody BeerDto beerDto) {

        return new ResponseEntity(beerService.saveBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc) {

        return new ResponseEntity(beerService.getBeerByUpc(upc), HttpStatus.OK);
    }
}
