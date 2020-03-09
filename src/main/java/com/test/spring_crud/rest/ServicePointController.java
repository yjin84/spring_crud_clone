package com.test.spring_crud.rest;


import com.test.spring_crud.model.dto.request.ServicePointDTO;
import com.test.spring_crud.service.ServicePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicePointController {

    @Autowired
    ServicePointService servicePointService;

    @PostMapping(path = "/sp")
    public ServicePointDTO createServicePoint(@RequestBody ServicePointDTO servicePoint) {
        return servicePointService.createServicePoint(servicePoint);
    }

    @GetMapping(path = "/sp/{id}")
    public ServicePointDTO getServicePoint(@PathVariable Integer id) {
        return servicePointService.getServicePointById(id);
    }

    @PutMapping(path = "/sp")
    public ServicePointDTO updateServicePoint(@RequestBody ServicePointDTO servicePoint) {
        return servicePointService.update(servicePoint);
    }

    @DeleteMapping(path = "/sp/{id}")
    public void deleteServicePoint(@PathVariable Integer id) {
        servicePointService.deleteServicePointById(id);
    }

}
