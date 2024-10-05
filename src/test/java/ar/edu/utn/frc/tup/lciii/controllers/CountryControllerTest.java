package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.DtoRequestNewContries;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Test
    void getAllCountries() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode("US");
        countryDTO.setName("USA");
        countries.add(countryDTO);

        when(countryService.getCountries()).thenReturn(countries);

        mockMvc.perform(get("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("USA"))
                .andExpect(jsonPath("$[0].code").value("US"));
    }

    @Test
    void getCountrysByContinent() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode("PE");
        countryDTO.setName("PERU");
        countries.add(countryDTO);

        when(countryService.getCountryByContinent("America")).thenReturn(countries);

        mockMvc.perform(get("/api/countries/America/continent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("PERU"))
                .andExpect(jsonPath("$[0].code").value("PE"));
    }

    @Test
    void getCountryByName() throws Exception {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode("JP");
        countryDTO.setName("Japan");

        when(countryService.getCountryByName("Japan")).thenReturn(countryDTO);

        mockMvc.perform(get("/api/countries/Japan/name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Japan"))
                .andExpect(jsonPath("$.code").value("JP"));
    }

    @Test
    void getCountrysByLanguage() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        CountryDTO country1 = new CountryDTO();
        country1.setCode("AR");
        country1.setName("Argentina");
        countries.add(country1);

        CountryDTO country2 = new CountryDTO();
        country2.setCode("MX");
        country2.setName("Mexico");
        countries.add(country2);

        when(countryService.getCountrysByLanguage("Spanish")).thenReturn(countries);

        mockMvc.perform(get("/api/countries/Spanish/language")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Argentina"))
                .andExpect(jsonPath("$[1].name").value("Mexico"));
    }

    @Test
    void getCountryWithMostBorders() throws Exception {
        CountryDTO country = new CountryDTO();
        country.setCode("BR");
        country.setName("Brazil");

        when(countryService.getCountryWithMostBorders()).thenReturn(country);

        mockMvc.perform(get("/api/countries/most-borders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Brazil"))
                .andExpect(jsonPath("$.code").value("BR"));
    }

    /*@Test
    void postCountries() throws Exception {
        List<CountryDTO> countries = new ArrayList<>();
        CountryDTO country1 = new CountryDTO();
        country1.setCode("CO");
        country1.setName("Columbia");
        countries.add(country1);

        DtoRequestNewContries dtoRequestNewContries = new DtoRequestNewContries();
        dtoRequestNewContries.setAmountOfCountryToSave(1);

        when(countryService.postCountriesAmount(dtoRequestNewContries)).thenReturn(countries);
        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amountOfCountryToSave\": 2}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Columbia"));

    }*/
}