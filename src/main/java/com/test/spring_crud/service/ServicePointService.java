package com.test.spring_crud.service;

import com.test.spring_crud.model.dto.request.CityDTO;
import com.test.spring_crud.model.dto.request.CountryDTO;
import com.test.spring_crud.model.dto.request.ServiceDTO;
import com.test.spring_crud.model.dto.request.ServicePointDTO;
import com.test.spring_crud.model.entities.ServicePoint;
import com.test.spring_crud.model.repositories.ServicePointRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class ServicePointService {

    ServicePointRepository servicePointRepository;
    DictionariesService dictionariesService;
    ModelMapper mapper;

    @Autowired
    public ServicePointService(ServicePointRepository servicePointRepository, DictionariesService dictionariesService, ModelMapper mapper) {
        this.servicePointRepository = servicePointRepository;
        this.dictionariesService = dictionariesService;
        this.mapper = mapper;
    }

    public ServicePointDTO createServicePoint(ServicePointDTO servicePoint) {
        ServicePoint entity = mapper.map(servicePoint, ServicePoint.class);
        ServicePoint sp = servicePointRepository.save(entity);
        return ServicePointDTO.builder()
                .id(sp.getId())
                .build();
    }

    public ServicePointDTO getServicePointById(Integer id) {
        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find Service point with given id: " + id));

        CityDTO city = mapper.map(servicePoint.getCity(), CityDTO.class);

        return ServicePointDTO.builder()
                .id(servicePoint.getId())
                .city(city)
                .country(getCountry(city.getId()))
                .address(servicePoint.getAddress())
                .name(servicePoint.getName())
                .serviceSet(servicePoint.getServiceSet().stream()
                        .map(s -> ServiceDTO.builder()
                                .id(s.getId())
                                .name(s.getName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Transactional
    public ServicePointDTO update(ServicePointDTO servicePoint) {
        if (null != servicePoint.getId()) {
            ServicePoint sp = servicePointRepository.findById(servicePoint.getId())
                    .orElse(new ServicePoint());
            updateEntityWithDTO(sp, servicePoint);
            ServicePointDTO dto = mapper.map(servicePointRepository.saveAndFlush(sp), ServicePointDTO.class);
            fillCountry(dto);
            return dto;
        } else {
            ServicePointDTO dto = mapper.map(
                    servicePointRepository.save(mapper.map(servicePoint, ServicePoint.class)), ServicePointDTO.class);
            fillCountry(dto);
            return dto;
        }
    }

    public void deleteServicePointById(Integer id) {
        servicePointRepository.deleteById(id);
    }

    private void updateEntityWithDTO(ServicePoint entity, ServicePointDTO dto) {
        try {
            if (dto.getCity()!=null) entity.setCity(dictionariesService.getCityById(dto.getCity().getId()));
            if (dto.getName()!=null) entity.setName(dto.getName());
            if (dto.getAddress()!=null) entity.setAddress(dto.getAddress());
            if(dto.getServiceSet()!=null) entity.setServiceSet(dto.getServiceSet().stream()
                    .map(s -> mapper.map(s, com.test.spring_crud.model.entities.Service.class))
                    .collect(Collectors.toSet()));
        } catch (Exception ex) {
            System.out.println("Oops while updating entity with dto.");
        }

    }

    private void fillCountry(ServicePointDTO sp) {
        sp.setCountry(getCountry(sp.getCity().getId()));
    }

    private CountryDTO getCountry(int id) {
        CountryDTO country = null;
        try {
            country = dictionariesService.getCountryByCityId(id);
        } catch (Exception ex) {
            System.out.println("Can't find country by given city id: " + id);
        }
        return country;
    }
}
