package com.carrentalproject.repository;

import com.carrentalproject.domain.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
