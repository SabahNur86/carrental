package com.carrentalproject.repository;

import com.carrentalproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {//bu repository nin user table ımıza ait oldugunu belirttik

}
