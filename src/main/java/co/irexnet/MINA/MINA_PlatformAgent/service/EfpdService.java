package co.irexnet.MINA.MINA_PlatformAgent.service;

import java.util.Map;

import co.irexnet.MINA.MINA_PlatformAgent.model.AclassDeviceInfoEntity;
import co.irexnet.MINA.MINA_PlatformAgent.model.CclassDeviceInfoEntity;
import co.irexnet.MINA.MINA_PlatformAgent.model.DeviceEntity;

public interface EfpdService {
	public void saveAclassSensorData(Map<String, String> map);
	public void saveCclassSensorData(Map<String, String> map);
	public AclassDeviceInfoEntity selectOneAclassDeviceInfo(String nodeId);
	public CclassDeviceInfoEntity selectOneCclassDeviceInfo(String nodeId);
	public DeviceEntity selectOneDeviceType(String nodeId);
	public void updateAclassFlag(String nodeId);
	public void updateCclassFlag(String nodeId);
}
