package com.test.spring_crud.service;

import com.test.spring_crud.model.dto.request.CityDTO;
import com.test.spring_crud.model.dto.request.CountryDTO;
import com.test.spring_crud.model.dto.request.ServiceDTO;
import com.test.spring_crud.model.entities.City;
import com.test.spring_crud.model.entities.Country;
import com.test.spring_crud.model.repositories.CitiesRepository;
import com.test.spring_crud.model.repositories.CountriesRepository;
import com.test.spring_crud.model.repositories.ServicesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DictionariesService {

    private CountriesRepository countriesRepository;
    private ServicesRepository servicesRepository;
    private CitiesRepository citiesRepository;
    private ModelMapper mapper;

    @Autowired
    public DictionariesService(CountriesRepository countriesRepository,
                               ServicesRepository servicesRepository,
                               CitiesRepository citiesRepository,
                               ModelMapper mapper) {
        this.countriesRepository = countriesRepository;
        this.servicesRepository = servicesRepository;
        this.citiesRepository = citiesRepository;
        this.mapper = mapper;
    }

    public List<CountryDTO> getCountriesList() {
        return countriesRepository.findAll().stream()
                .map(country -> mapper.map(country, CountryDTO.class))
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryByCityId(Integer cityId) throws Exception {
        if (!citiesRepository.existsById(cityId)) {
            throw new Exception("No cities found by given id: " + cityId);
        }
        Country country = countriesRepository.getByCityId(cityId);
        return mapper.map(country, CountryDTO.class);
    }

    public List<CityDTO> getCitiesList() {
        return citiesRepository.findAll().stream()
                .map(city -> mapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    public City getCityById(Integer id) throws Exception {
        return citiesRepository.findById(id).orElseThrow(() -> new Exception("No cities found by given id: " + id));
    }

    public Set<ServiceDTO> getServicesList() {
        return servicesRepository.findAll().stream()
                .map(service -> mapper.map(service, ServiceDTO.class))
                .collect(Collectors.toSet());
    }
}
