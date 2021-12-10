package com.agent.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.agent.model.AclassDeviceInfoEntity;
import com.agent.model.AclassEntity;
import com.agent.model.CclassDeviceInfoEntity;
import com.agent.model.CclassEntity;
import com.agent.model.DeviceEntity;
import com.agent.repository.AclassDeviceInfoRepository;
import com.agent.repository.AclassRepository;
import com.agent.repository.CclassDeviceInfoRepository;
import com.agent.repository.CclassRepository;
import com.agent.repository.DeviceRepository;


@Service
public class EfpdServiceImpl implements EfpdService {
	@Autowired
	AclassRepository aclassRepository;
	
	@Autowired
	CclassRepository cclassRepository;
	
	@Autowired
	AclassDeviceInfoRepository aclassDeviceInfoRepository;
	
	@Autowired
	CclassDeviceInfoRepository cclassDeviceInfoRepository;
	
	@Autowired
	DeviceRepository deviceRepository;
	
	@Override
	public void saveAclassSensorData(Map<String, String> map) {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		AclassEntity aclass = mapper.convertValue(map, AclassEntity.class);
		try {
			// TODO Auto-generated method stub
			aclassRepository.save(aclass);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	public void saveCclassSensorData(Map<String, String> map) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		CclassEntity cclass = mapper.convertValue(map, CclassEntity.class);
		try {
			// TODO Auto-generated method stub
			cclassRepository.save(cclass);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public AclassDeviceInfoEntity selectOneAclassDeviceInfo(String nodeId) {
		// TODO Auto-generated method stub
		AclassDeviceInfoEntity map = aclassDeviceInfoRepository.findById(nodeId).get();
		return map;
	}
	
	@Override
	public CclassDeviceInfoEntity selectOneCclassDeviceInfo(String nodeId) {
		// TODO Auto-generated method stub
		CclassDeviceInfoEntity map = cclassDeviceInfoRepository.findById(nodeId).get();
		return map;
	}
	
	@Override
	public DeviceEntity selectOneDeviceType(String nodeId) {
		// TODO Auto-generated method stub
		DeviceEntity map = deviceRepository.findById(nodeId).get();
		return map;
	}
	
	@Override
	public void updateAclassFlag(String nodeId) {
		// TODO Auto-generated method stub
		AclassDeviceInfoEntity entity = aclassDeviceInfoRepository.findById(nodeId).get();
		entity.setResetFlag("0");
		aclassDeviceInfoRepository.save(entity);
	}
	
	@Override
	public void updateCclassFlag(String nodeId) {
		// TODO Auto-generated method stub
		CclassDeviceInfoEntity entity = cclassDeviceInfoRepository.findById(nodeId).get();
		entity.setResetFlag("0");
		cclassDeviceInfoRepository.save(entity);
	}
}
