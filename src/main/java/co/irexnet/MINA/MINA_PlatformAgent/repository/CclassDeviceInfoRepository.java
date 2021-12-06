package co.irexnet.MINA.MINA_PlatformAgent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import co.irexnet.MINA.MINA_PlatformAgent.model.CclassDeviceInfoEntity;

@Repository
public interface CclassDeviceInfoRepository extends JpaRepository<CclassDeviceInfoEntity, String>{

}
