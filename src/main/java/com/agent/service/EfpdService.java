package com.agent.service;

import java.util.Map;

import com.agent.model.AclassDeviceInfoEntity;
import com.agent.model.CclassDeviceInfoEntity;
import com.agent.model.DeviceEntity;

public interface EfpdService {
	public void saveAclassSensorData(Map<String, String> map);
	public void saveCclassSensorData(Map<String, String> map);
	public AclassDeviceInfoEntity selectOneAclassDeviceInfo(String nodeId);
	public CclassDeviceInfoEntity selectOneCclassDeviceInfo(String nodeId);
	public DeviceEntity selectOneDeviceType(String nodeId);
	public void updateAclassFlag(String nodeId);
	public void updateCclassFlag(String nodeId);
}
