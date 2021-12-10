package com.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.agent.model.CclassDeviceInfoEntity;

@Repository
public interface CclassDeviceInfoRepository extends JpaRepository<CclassDeviceInfoEntity, String>{

}
