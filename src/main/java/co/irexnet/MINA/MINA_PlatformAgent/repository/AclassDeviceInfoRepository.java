package co.irexnet.MINA.MINA_PlatformAgent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.irexnet.MINA.MINA_PlatformAgent.model.AclassDeviceInfoEntity;
import co.irexnet.MINA.MINA_PlatformAgent.model.AclassEntity;

@Repository
public interface AclassDeviceInfoRepository extends JpaRepository<AclassDeviceInfoEntity, String> {
	
	
}
