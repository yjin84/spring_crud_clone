package com.test.spring_crud.rest;

import com.test.spring_crud.model.dto.request.CityDTO;
import com.test.spring_crud.model.dto.request.CountryDTO;
import com.test.spring_crud.model.dto.request.ServiceDTO;
import com.test.spring_crud.service.DictionariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/dictionaries")
public class DictionariesController {

    @Autowired
    private DictionariesService service;

    @GetMapping(path = "/countries")
    public List<CountryDTO> listAllCountries() {
        return service.getCountriesList();
    }

    @GetMapping(path = "/cities")
    public List<CityDTO> listAllCities() {
        return service.getCitiesList();
    }

    @GetMapping(path = "/services")
    public Set<ServiceDTO> listAllServices() {
        return service.getServicesList();
    }
}
