package com.carrentalproject.repository;

import com.carrentalproject.domain.User;
import com.carrentalproject.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {//bu repository nin user table ımıza ait oldugunu belirttik

    Optional<User> findByUserName(String userName) throws ResourceNotFoundException;
    //username ile dataya ulasma sorgusu
}
