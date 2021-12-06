package co.irexnet.MINA.MINA_PlatformAgent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.irexnet.MINA.MINA_PlatformAgent.model.DeviceEntity;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {

}
