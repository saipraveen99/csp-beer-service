package csp.beerworks.cspbeerservice.bootstrap;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
//@Slf4j
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
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
                            .upc(BEER_1_UPC)
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .price(new BigDecimal(200))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .upc(BEER_2_UPC)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal(240))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("BIRA")
                    .beerStyle("PUNCH")
                    .upc(BEER_3_UPC)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal(260))
                    .build());

            //log.info("Loaded beers: "+beerRepository.count());
        }
    }
}
