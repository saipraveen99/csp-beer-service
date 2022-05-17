package csp.beerworks.cspbeerservice.services.inventory;

import csp.beerworks.cspbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@ConfigurationProperties(prefix = "csp.beerworks", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {
    private RestTemplate restTemplate;
    private String beerinventoryhost;
    private final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setBeerinventoryhost(String beerinventoryhost) {
        this.beerinventoryhost = beerinventoryhost;
    }

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        log.debug("calling Inventory service");
        ResponseEntity<List<BeerInventoryDto>> response =
                restTemplate.exchange(beerinventoryhost + INVENTORY_PATH, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<BeerInventoryDto>>(){}, (Object) beerId);
        Integer quantityOnHand = Objects.requireNonNull(response.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();
        return quantityOnHand;
    }
}
