package com.test.spring_crud.model.repositories;

import com.test.spring_crud.model.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Integer> {

    @Query("Select c FROM City cty join cty.country c where cty.id = :city_id")
    Country getByCityId(@Param("city_id") Integer id);
}
