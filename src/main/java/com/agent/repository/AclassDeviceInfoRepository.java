package com.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agent.model.AclassDeviceInfoEntity;
import com.agent.model.AclassEntity;

@Repository
public interface AclassDeviceInfoRepository extends JpaRepository<AclassDeviceInfoEntity, String> {
	
	
}
