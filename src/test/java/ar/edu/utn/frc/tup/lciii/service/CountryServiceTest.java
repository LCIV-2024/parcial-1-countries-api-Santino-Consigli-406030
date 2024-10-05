package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.DtoRequestNewContries;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CountryServiceTest {

    @SpyBean
    private CountryService countryService;
    @MockBean
    private CountryRepository countryRepository;
    @MockBean
    private CountryServicePeticiones countryServicePeticiones;

    List<Country> countryList = new ArrayList<>();
    @BeforeEach
    void setUp()
    {
        Country country = new Country();
        country.setArea(222.0);
        country.setName("Uruguay");
        country.setCode("2342");
        List<String> borders = new ArrayList<>();
        borders.add("BNH");
        country.setBorders(borders);
        Map<String,String> languages = new HashMap<>();
        languages.putIfAbsent("Esp","Espanol");
        country.setLanguages(languages);
        country.setCca3("9022");
        countryList.add(country);
        country.setRegion("America");
    }
    @Test
    void getCountryByNameTest() {
        CountryDTO countryDTOExpected = new CountryDTO();
        countryDTOExpected.setCode(String.valueOf(2342));
        countryDTOExpected.setName("Uruguay");
        when(countryServicePeticiones.getAllCountries()).thenReturn(countryList);
        CountryDTO countryDTOActual = countryService.getCountryByName("Uruguay");
        Assertions.assertEquals(countryDTOExpected.getName(),countryDTOActual.getName());
    }

    @Test
    void getCountryByContinentTest() {
        List<CountryDTO> countryDTOSExpected = new ArrayList<>();
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Uruguay");
        countryDTO.setCode("2342");
        countryDTOSExpected.add(countryDTO);
        when(countryServicePeticiones.getAllCountries()).thenReturn(countryList);
        List<CountryDTO> countryDTOSActual = countryService.getCountryByContinent("America");
        Assertions.assertEquals(countryDTOSExpected.get(0).getName(),countryDTOSActual.get(0).getName());
    }

    @Test
    void getCountrysByLanguageTest() {
        List<CountryDTO> countryDTOSExpected = new ArrayList<>();
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Uruguay");
        countryDTO.setCode("2342");
        countryDTOSExpected.add(countryDTO);
        when(countryServicePeticiones.getAllCountries()).thenReturn(countryList);
        List<CountryDTO> countryDTOSActual = countryService.getCountrysByLanguage("Espanol");
        Assertions.assertFalse(countryDTOSActual.isEmpty());
        Assertions.assertEquals(countryDTOSExpected.size(), countryDTOSActual.size());
        Assertions.assertEquals(countryDTOSExpected.get(0).getName(), countryDTOSActual.get(0).getName());
        Assertions.assertEquals(countryDTOSExpected.get(0).getCode(), countryDTOSActual.get(0).getCode());
    }

    /*
    @Test
    void postCountriesAmountTest() {
        DtoRequestNewContries dtoRequestNewContries = new DtoRequestNewContries();
        dtoRequestNewContries.setAmountOfCountryToSave(1);
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName("Uruguay");
        countryEntity.setCode("2342");
        when(countryServicePeticiones.getAllCountries()).thenReturn(countryList);
        when(countryRepository.save(any(CountryEntity.class))).thenReturn(countryEntity);
        List<CountryDTO> countryDTOS = countryService.postCountriesAmount(dtoRequestNewContries);
        assertEquals(1, countryDTOS.size());
        assertEquals("Uruguay", countryDTOS.get(0).getName());
        assertEquals("2342", countryDTOS.get(0).getCode());

    }*/

    @Test
    void getCountryWithMostBordersTest() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Uruguay");
        countryDTO.setCode("2342");
        when(countryServicePeticiones.getAllCountries()).thenReturn(countryList);
        CountryDTO countryDTO1 = countryService.getCountryWithMostBorders();
        assertEquals( countryDTO.getName(),countryDTO1.getName());
    }
    @Test
    void getCountryByNameTest_no_existe_pais() {
        when(countryServicePeticiones.getAllCountries()).thenReturn(new ArrayList<>());

        CountryDTO countryDTO = countryService.getCountryByName("Uruguay");
        assertNull(countryDTO);
    }

    @Test
    void getCountryByContinentTest_lista_vacia() {
        when(countryServicePeticiones.getAllCountries()).thenReturn(new ArrayList<>());

        List<CountryDTO> result = countryService.getCountryByContinent("no se conoce ese continente");
        assertTrue(result.isEmpty());
    }
}