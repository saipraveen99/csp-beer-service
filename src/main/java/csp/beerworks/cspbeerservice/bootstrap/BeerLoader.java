package csp.beerworks.cspbeerservice.bootstrap;

import csp.beerworks.cspbeerservice.domain.Beer;
import csp.beerworks.cspbeerservice.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Slf4j
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
                            //.id(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"))
                            .beerName("Mango Bobs")
                            .beerStyle("IPA")
                            .upc(BEER_1_UPC)
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .price(new BigDecimal(12.95))
                    .build());

            beerRepository.save(Beer.builder()
                    //.id(UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd"))
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .upc(BEER_2_UPC)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal(12.95))
                    .build());

            beerRepository.save(Beer.builder()
                    //.id(UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08"))
                    .beerName("Pinball Porter")
                    .beerStyle("PORTER")
                    .upc(BEER_3_UPC)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .price(new BigDecimal(12.95))
                    .build());

            log.debug("Loaded beers: "+beerRepository.count());
        }
    }
}
