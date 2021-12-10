package com.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agent.model.CclassEntity;

@Repository
public interface CclassRepository extends JpaRepository<CclassEntity, String>{

}
