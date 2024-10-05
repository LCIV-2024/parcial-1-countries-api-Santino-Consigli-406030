package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.DtoRequestNewContries;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;


    @GetMapping
    public List<CountryDTO> getAllCountries() {
        return countryService.getCountries();
    }

    @GetMapping("{continent}/continent")
    public List<CountryDTO> getCountrysByContinent(@PathVariable String continent) {
        return countryService.getCountryByContinent(continent);
    }

    @GetMapping("{name}/name")
    public CountryDTO getCountryByName(@PathVariable String name) {
        return countryService.getCountryByName(name);
    }
    @GetMapping("{language}/language")
    public List<CountryDTO> getCountrysByLanguage(@PathVariable String language) {
        return countryService.getCountrysByLanguage(language);

    }
    @GetMapping("most-borders")
    public CountryDTO getCountryWithMostBorders() {
        return countryService.getCountryWithMostBorders();
    }
    @PostMapping("")
    public List<CountryDTO> postCountries(@RequestBody DtoRequestNewContries dtoRequestNewContries) {
        return countryService.postCountriesAmount(dtoRequestNewContries);
    }
}