package com.flightsync.flightsync.repository;

import com.flightsync.flightsync.model.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAlertRepository extends JpaRepository<UserAlert, Long> {

    List<UserAlert> findByFromCityAndToCityAndActiveTrue(String fromCity, String toCity);

    List<UserAlert> findByEmailAndActiveTrue(String email);

    boolean existsByEmailAndFromCityAndToCity(String email, String fromCity, String toCity);
}