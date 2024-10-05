package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.DtoRequestNewContries;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
        @Autowired
        private CountryServicePeticiones countryServicePeticiones;
        @Autowired
        private final CountryRepository countryRepository;


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public List<CountryDTO> getCountries() {
                List<Country> countries =countryServicePeticiones.getAllCountries();
                List<CountryDTO> dtos = new ArrayList<>();
                for(Country country : countries) {
                       CountryDTO  countryDTO = mapToDTO(country);
                       dtos.add(countryDTO);
                }
                return dtos;
        }

        public CountryDTO getCountryByName(String name) {
                List<Country> countries = countryServicePeticiones.getAllCountries();
                for(Country country : countries)
                {
                        if(Objects.equals(country.getName(), name))
                        {
                                CountryDTO countryDTO = new CountryDTO();
                                countryDTO.setName(name);
                                countryDTO.setCode(country.getCode());
                                return countryDTO;
                        }
                }
                return  null;

        }

        public List<CountryDTO> getCountryByContinent(String continent) {
                List<Country> countries = countryServicePeticiones.getAllCountries();
                List<CountryDTO> countryDTOS = new ArrayList<>();
                for(Country country : countries)
                {
                        if(Objects.equals(country.getRegion(), continent)){

                                CountryDTO countryDTO = new CountryDTO();
                                countryDTO.setCode(country.getCode());
                                countryDTO.setName(country.getName());
                                countryDTOS.add(countryDTO);

                        }
                }
                return countryDTOS;
        }

        public List<CountryDTO> getCountrysByLanguage(String language) {
                List<Country> countries = countryServicePeticiones.getAllCountries();
                List<CountryDTO> countryDTOS = new ArrayList<>();

                for (Country country : countries) {
                        Map<String, String> languages = country.getLanguages();
                        if (languages != null && languages.values().stream().anyMatch(value -> value.equalsIgnoreCase(language))) {
                                CountryDTO countryDTO = new CountryDTO();
                                countryDTO.setCode(country.getCode());
                                countryDTO.setName(country.getName());
                                countryDTOS.add(countryDTO);
                        }
                }
                return countryDTOS;
        }

        public List<CountryDTO> postCountriesAmount(DtoRequestNewContries dtoRequestNewContries) {
                List<Country> countries = countryServicePeticiones.getAllCountries();
                List<CountryDTO> countryDTOS = new ArrayList<>();
                int contadorCountries = 0;

                for(Country country : countries){
                        if(contadorCountries<dtoRequestNewContries.getAmountOfCountryToSave())
                        {
                                CountryEntity countryEntity = new CountryEntity();
                                countryEntity.setCode(country.getCode());
                                countryEntity.setName(country.getName());
                                countryRepository.save(countryEntity);
                                CountryDTO countryDTO = new CountryDTO();
                                countryDTO.setCode(country.getCode());
                                countryDTO.setName(country.getName());
                                countryDTOS.add(countryDTO);
                        }
                        else {
                                return countryDTOS;
                        }
                        contadorCountries++;

                }
                return null;

        }
        public CountryDTO getCountryWithMostBorders() {
                List<Country> countries = countryServicePeticiones.getAllCountries();
                if (countries == null) {
                        throw new IllegalArgumentException("Lista de countries es nula");
                }
                CountryDTO countryDTO = new CountryDTO();
                int countOfBordersMax = 0;
                for (Country country : countries) {
                        if (country.getBorders() != null && country.getBorders().size() > countOfBordersMax) {
                                countOfBordersMax = country.getBorders().size();
                        }
                }
                for (Country country : countries) {

                        if (country.getBorders() != null && country.getBorders().size() == countOfBordersMax) {
                                countryDTO.setName(country.getName());
                                countryDTO.setCode(country.getCode());
                                break;
                        }
                }

                return countryDTO;
        }


}