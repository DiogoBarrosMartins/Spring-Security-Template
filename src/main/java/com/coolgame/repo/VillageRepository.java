package com.coolgame.repo;

import com.coolgame.model.Account;
import com.coolgame.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// VillageRepository.java
@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

}