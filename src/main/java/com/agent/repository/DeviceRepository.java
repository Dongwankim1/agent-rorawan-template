package com.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agent.model.DeviceEntity;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {

}
