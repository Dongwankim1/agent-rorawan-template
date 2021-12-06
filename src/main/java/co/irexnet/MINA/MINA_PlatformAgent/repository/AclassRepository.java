package co.irexnet.MINA.MINA_PlatformAgent.repository;

import org.springframework.stereotype.Repository;

import co.irexnet.MINA.MINA_PlatformAgent.model.AclassEntity;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface AclassRepository extends JpaRepository<AclassEntity,String> {

	
}
