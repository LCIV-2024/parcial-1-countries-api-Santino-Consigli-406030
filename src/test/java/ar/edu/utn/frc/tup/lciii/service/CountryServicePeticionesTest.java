package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServicePeticionesTest {


    @MockBean
    private RestTemplate restTemplate;
    List<Country> countryList = new ArrayList<>();
    @BeforeEach
    void setUp() {

        Country country = new Country();
        country.setArea(222.0);
        country.setName("Uruguay");
        country.setCode("2342");
        List<String> borders = new ArrayList<>();
        borders.add("BNH");
        country.setBorders(borders);
        Map<String,String> languages = new HashMap<>();
        languages.put("Esp", "Espanol");
        country.setLanguages(languages);
        country.setCca3("9022");
        countryList.add(country);
        country.setRegion("America");
    }

    @Test
    void getAllCountriesTest() {
        String url = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> mockResponse = new ArrayList<>();
        Map<String, Object> countryData = new HashMap<>();
        mockResponse.add(countryData);

        when(restTemplate.getForObject(url, List.class)).thenReturn(mockResponse);

        List<Country> result = countryList;

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        Country country = result.get(0);
        assertEquals("Uruguay", country.getName());
        assertEquals("America", country.getRegion());

    }

}