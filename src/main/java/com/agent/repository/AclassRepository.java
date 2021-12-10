package com.agent.repository;

import org.springframework.stereotype.Repository;

import com.agent.model.AclassEntity;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface AclassRepository extends JpaRepository<AclassEntity,String> {

	
}
