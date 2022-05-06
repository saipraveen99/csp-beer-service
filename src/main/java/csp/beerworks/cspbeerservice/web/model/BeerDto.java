package csp.beerworks.cspbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {
    private UUID id;

    private Integer version;

    private OffsetDateTime createdDate;

    private OffsetDateTime lastModifiedDate;

    private String beerName;

    private BeerStyleEnum beerStyle;

    private String upc;

    private String price;

    private Integer quantityOnHand;


}
