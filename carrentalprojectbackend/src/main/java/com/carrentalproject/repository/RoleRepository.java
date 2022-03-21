package com.carrentalproject.repository;

import com.carrentalproject.domain.Role;
import com.carrentalproject.domain.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(UserRole name);
}
