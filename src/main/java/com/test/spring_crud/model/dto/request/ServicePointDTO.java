package com.test.spring_crud.model.dto.request;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicePointDTO implements Serializable {
    private Integer id;
    private CityDTO city;
    private CountryDTO country;
    private Set<ServiceDTO> serviceSet;
    private String name;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicePointDTO that = (ServicePointDTO) o;
        return  city.equals(that.city) &&
                Objects.equals(country, that.country) &&
                serviceSet.equals(that.serviceSet) &&
                Objects.equals(name, that.name) &&
                address.equals(that.address);
    }
}
