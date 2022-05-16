package csp.beerworks.cspbeerservice.web.controller;

import csp.beerworks.cspbeerservice.services.BeerService;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer/")
public class BeerController {
    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveBeer(@Validated @RequestBody BeerDto beerDto) {

        return new ResponseEntity(beerService.saveBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeer(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
