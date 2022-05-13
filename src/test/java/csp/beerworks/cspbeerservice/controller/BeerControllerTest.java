package csp.beerworks.cspbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import csp.beerworks.cspbeerservice.web.model.BeerDto;
import csp.beerworks.cspbeerservice.web.controller.BeerController;
import csp.beerworks.cspbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"+ UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveBeer() throws Exception {
        BeerDto beerDto = this.validBeerDto();
        String JsonBeerDto = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonBeerDto))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        BeerDto beerDto = this.validBeerDto();
        String JsonBeerDto = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(put("/api/v1/beer/"+UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonBeerDto))
                .andExpect(status().isNoContent());
    }

    private BeerDto validBeerDto() {
        return BeerDto.builder().beerName("BIRA")
                .beerStyle(BeerStyleEnum.GOSE)
                .upc("130520222024")
                .price(new BigDecimal(270))
                .build();
    }
}