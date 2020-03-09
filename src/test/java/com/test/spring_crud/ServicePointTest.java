package com.test.spring_crud;

import com.test.spring_crud.model.dto.request.CityDTO;
import com.test.spring_crud.model.dto.request.CountryDTO;
import com.test.spring_crud.model.dto.request.ServiceDTO;
import com.test.spring_crud.model.dto.request.ServicePointDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ServicePointTest extends CommonTest {

    @Test
    public void createServicePointControllerTest() {
        ServicePointDTO object = ServicePointDTO.builder()
                .country(CountryDTO.builder().id(1).name("Russia").build())
                .city(CityDTO.builder().id(1).name("Novosibirsk").build())
                .address("Address")
                .name("Name")
                .serviceSet(Collections.singleton(ServiceDTO.builder().id(1).name("Service 1").build()))
                .build();
        ServicePointDTO servicePointDTO = this.restTemplate.postForObject(url + port + "/sp", object, ServicePointDTO.class);
        assertNotNull(servicePointDTO);
        assertNotNull(servicePointDTO.getId());
    }

    @Test
    public void servicePointControllerTest() {
        ServicePointDTO createDto = ServicePointDTO.builder()
                .country(CountryDTO.builder().id(1).name("Russia").build())
                .city(CityDTO.builder().id(1).name("Novosibirsk").build())
                .address("Address")
                .name("Name")
                .serviceSet(new HashSet<>(Collections.singleton(ServiceDTO.builder().id(1).name("Services 1").build())))
                .build();
        ServicePointDTO createResponseDto = this.restTemplate.postForObject(url + port + "/sp", createDto, ServicePointDTO.class);

        ServicePointDTO getResponseDto = this.restTemplate.getForObject(url + port + "/sp/{id}", ServicePointDTO.class, createResponseDto.getId());
        assertEquals(getResponseDto, createDto);
    }

    @Test
    public void servicePointUpdateControllerTest() {
        ServicePointDTO createDto = ServicePointDTO.builder()
                .country(CountryDTO.builder().id(1).name("Russia").build())
                .city(CityDTO.builder().id(1).name("Novosibirsk").build())
                .address("Address")
                .name("Name")
                .serviceSet(new HashSet<>(Collections.singleton(ServiceDTO.builder().id(1).name("Services 1").build())))
                .build();
        ServicePointDTO createResponseDto = this.restTemplate.postForObject(url + port + "/sp", createDto, ServicePointDTO.class);

        ServicePointDTO updateDto = ServicePointDTO.builder()
                .id(createResponseDto.getId())
                .name("NewName")
                .serviceSet(new HashSet<>(Arrays.asList(
                        ServiceDTO.builder().id(2).name("Services 2").build(),
                        ServiceDTO.builder().id(3).name("Services 3").build())))
                .build();

        ResponseEntity<ServicePointDTO> response =
                this.restTemplate.exchange(url + port + "/sp", HttpMethod.PUT, new HttpEntity<>(updateDto), ServicePointDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());

        ServicePointDTO updateResponseBody = response.getBody();

        assertEquals(createResponseDto.getId(), updateResponseBody.getId());

        assertEquals(createDto.getCity(), updateResponseBody.getCity());
        assertEquals(createDto.getCountry(), updateResponseBody.getCountry());
        assertEquals(createDto.getAddress(), updateResponseBody.getAddress());

        assertEquals(updateDto.getName(), updateResponseBody.getName());
        assertEquals(updateDto.getServiceSet(), updateResponseBody.getServiceSet());
    }

    @Test
    public void servicePointDeleteControllerTest() {
        ServicePointDTO createDto = ServicePointDTO.builder()
                .country(CountryDTO.builder().id(1).name("Russia").build())
                .city(CityDTO.builder().id(1).name("Novosibirsk").build())
                .address("Address")
                .name("Name")
                .serviceSet(new HashSet<>(Collections.singleton(ServiceDTO.builder().id(1).name("Services 1").build())))
                .build();

        ServicePointDTO createResponseDto = this.restTemplate.postForObject(url + port + "/sp", createDto, ServicePointDTO.class);

        this.restTemplate.delete(url + port + "/sp/{id}", createResponseDto.getId());
        ResponseEntity<Void> deleteResponse =
                this.restTemplate.exchange(url + port + "/sp/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, createResponseDto.getId());
        assertEquals(deleteResponse.getStatusCodeValue(), 500);

    }
}
