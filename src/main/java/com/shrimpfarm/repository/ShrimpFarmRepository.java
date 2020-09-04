package com.shrimpfarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shrimpfarm.model.Farms;

@Repository
public interface ShrimpFarmRepository extends JpaRepository<Farms,String> {

}
