package com.test.spring_crud;


import com.test.spring_crud.model.dto.request.CityDTO;
import com.test.spring_crud.model.dto.request.CountryDTO;
import com.test.spring_crud.model.dto.request.ServiceDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

class DictionariesTest extends CommonTest {

    @Test
    public void citiesListControllerTest() {
        List<CityDTO> cities = Arrays.asList(this.restTemplate.getForObject(url + port + "/dictionaries/cities",
                CityDTO[].class));

        assertThat("Cities dictionary should contain values",
				cities.stream().map(CityDTO::getName).collect(Collectors.toList()),
                Matchers.containsInAnyOrder("Novosibirsk", "Moscow", "Omsk", "Almaty", "Tbilisi", "Batumi", "Kiev", "Odessa", "Riga"));
    }

    @Test
    public void countriesListControllerTest() {
		List<CountryDTO> countries = Arrays.asList(this.restTemplate.getForObject(url + port + "/dictionaries/countries",
				CountryDTO[].class));

		assertThat("Countries dictionary should contain values",
				countries.stream().map(CountryDTO::getName).collect(Collectors.toList()),
				Matchers.containsInAnyOrder("Russia", "Kazakhstan", "Georgia", "Ukraine", "Latvia"));
	}

	@Test
	public void servicesListControllerTest() {
		List<ServiceDTO> countries = Arrays.asList(this.restTemplate.getForObject(url + port + "/dictionaries/services",
				ServiceDTO[].class));

		assertThat("Service dictionary should contain values",
				countries.stream().map(ServiceDTO::getName).collect(Collectors.toList()),
				Matchers.containsInAnyOrder("Services 1", "Services 2", "Services 3", "Services 4"));
	}

}
