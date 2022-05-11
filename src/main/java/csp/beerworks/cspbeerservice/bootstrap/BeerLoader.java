package csp.beerworks.cspbeerservice.bootstrap;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (this.beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                            .beerName("Mango Bobs")
                            .beerStyle("IPA")
                            .upc("110520221529L")
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .price(new BigDecimal(200))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .upc("110520221531L")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal(240))
                    .build());

            log.info("Loaded beers: "+beerRepository.count());
        }
    }
}
