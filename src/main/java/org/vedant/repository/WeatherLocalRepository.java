package org.vedant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedant.model.WeatherLocalModel;

@Repository
public interface WeatherLocalRepository extends JpaRepository<WeatherLocalModel, Long> {
    WeatherLocalModel findByPincodeAndDate(String pincode, long date);
}
