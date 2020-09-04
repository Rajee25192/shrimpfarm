package com.shrimpfarm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shrimpfarm.model.Ponds;

@Repository
public interface ShrimpPondsRepository extends JpaRepository<Ponds, String> {

	 List<Ponds> findByFarmId(String id);
	 Optional<Ponds> findByIdAndFarmId(String id, String farmId);
}
