package co.irexnet.MINA.MINA_PlatformAgent.controller;

import co.irexnet.MINA.MINA_PlatformAgent.dto.NetworkServerNodeInfo;
import co.irexnet.MINA.MINA_PlatformAgent.dto.NetworkServerNotifyDTO;
import co.irexnet.MINA.MINA_PlatformAgent.model.AclassDeviceInfoEntity;
import co.irexnet.MINA.MINA_PlatformAgent.model.CclassDeviceInfoEntity;
import co.irexnet.MINA.MINA_PlatformAgent.model.DeviceEntity;
import co.irexnet.MINA.MINA_PlatformAgent.scheduler.Scheduler;
import co.irexnet.MINA.MINA_PlatformAgent.service.EfpdService;
import co.irexnet.MINA.MINA_PlatformAgent.service.IOneM2MService;
import co.irexnet.MINA.MINA_PlatformAgent.util.*;
import co.irexnet.MINA.MINA_PlatformAgent.ws.SpringContext;
import co.irexnet.MINA.MINA_PlatformAgent.ws.Websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

import java.text.SimpleDateFormat;

@RestController
@EnableSwagger2
@RequiredArgsConstructor // final 媛앹껜瑜� Constructor Injection �빐以�. (Autowired �뿭�븷)
@Slf4j
public class CallbackController
{
    @Autowired
    PropertiesServiceOid propertiesServiceOid;

    @Autowired
    NetworkServerGatewayList networkServerGatewayList;

    @Autowired
    NetworkServerNodeList networkServerNodeList;
    
    @Autowired
    EfpdService efpdService;
    
    @Autowired
    Scheduler scheduler;
    
    @RequestMapping(value = "/configure",method =  RequestMethod.POST)
    public Map recvConfigure(@RequestBody Map<String,String> paramMap) {
    	IOneM2MService oneM2MService = scheduler.getOneM2MService();
    	
    	log.info("Setting Class Info");
    	String nodeId = paramMap.get("nodeId");
    	Map resultMap = new HashMap<String, String>();
    	try {
    	 for(NetworkServerNodeInfo node : networkServerNodeList.getNetworkServerNodeList())
         {
             if(node.getNode_id().equals(nodeId))
             {
            	//check device type
             	DeviceEntity deviceType = efpdService.selectOneDeviceType(nodeId);
             	
             	byte[] resultState = null;
            	byte[] diviceInfomessage  = null;
            	//transfer class info C
            	if("C".equals(deviceType.getType())) {
            		Map<String,String> stateMap = new HashMap<String, String>();
            		
            		stateMap.put("powerState", "0");
            		stateMap.put("transState", "0");
            		stateMap.put("funcCode", "5");
            		resultState = Conversion.completeSendMessage(0x00,Conversion.getAddtoByteLength(Conversion.getToSendOfState(stateMap)));
            		CclassDeviceInfoEntity info  =null;
            		try {
            			info = efpdService.selectOneCclassDeviceInfo(nodeId);
    				} catch (Exception e) {
    					log.error("Error is {}",nodeId);
    				}
            		
            		ObjectMapper oMapper = new ObjectMapper();
            		Map map = oMapper.convertValue(info, Map.class);
            		
            		
            		//怨좎븬 class C �뒗 21諛붿씠�듃 �젙蹂닿� �븘�슂
            		diviceInfomessage = Conversion.convertDeviceInfoMapToByte(0x99,21, map,CommonValue.CCLASS_DEIVCE_INFO);
            		
            	
            		//transfer class info A
            	}else {
            		Map<String,String> stateMap = new HashMap<String, String>();
            		
            		stateMap.put("powerState", "0");
            		stateMap.put("transState", "0");
            		stateMap.put("funcCode", "5");
            		resultState = Conversion.completeSendMessage(0x00,Conversion.getAddtoByteLength(Conversion.getToSendOfState(stateMap)));
            		
            		AclassDeviceInfoEntity info = null;
            		
            		try {
            			info = efpdService.selectOneAclassDeviceInfo(nodeId);
    				} catch (Exception e) {
    					log.error("Error is {}",nodeId);
    				}
            		
            		ObjectMapper oMapper = new ObjectMapper();
            		Map map = oMapper.convertValue(info, Map.class);
      
            		//���븬 class A �뒗 23諛붿씠�듃 �젙蹂닿� �븘�슂
            		diviceInfomessage = Conversion.convertDeviceInfoMapToByte(0x98,23, map,CommonValue.ACLASS_DEIVCE_INFO);
            	}
            	//
        		byte[] mergedata = Conversion.mergeOfTLVData(resultState,diviceInfomessage);
        		
        		//
        		byte[] convebyte = Conversion.getAddtoByteLength(mergedata);
        		
        		//checksum byte 
        		byte[] crc16 = Conversion.getCRC16(convebyte);
        		
        		//
        		byte[] rawData = Conversion.completeSendMessage(0x02, convebyte, crc16);
        		
        		//Change hex data
        		String rawHexData = Conversion.byteArrayToHexString(rawData);
                String base64Raw = Base64.encodeBase64String(rawData);
        		
                //send class info configure	
        		log.info("OID:[{}], contentInstance Create:[{}]",
        				nodeId,
                        oneM2MService.ContentInstanceCreate(nodeId, rawHexData));
                 break;
             }
         }
    	 resultMap.put("status", "success");
    	} catch(Exception e) {
    	 resultMap.put("status", "error");
    	}
    	
     	
     	return resultMap;
    	
    }
    
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public void recvCallback(@RequestBody NetworkServerNotifyDTO networkServerNotify)
    {

    	
        
        if(networkServerNotify.getData()!=null) {
        byte[] decoded = Base64.decodeBase64(networkServerNotify.getData().getRaw());
        double dValue = Math.random()*10;
        double dValue1 = Math.random()*10;
        double dValue2 = Math.random()*10;
       
        
        log.info("recieve data is :: {}",decoded);
        if(decoded==null) {
        	log.info("DECODED DATA IS NULL AND NODEID is :: {}",networkServerNotify.getNodeId());

        	return;
        }
        int nCmd = (byte)decoded[2];
      
        IOneM2MService oneM2MService = scheduler.getOneM2MService();
        String nodeId = networkServerNotify.getNodeId();

    	for(NetworkServerNodeInfo node : networkServerNodeList.getNetworkServerNodeList())
        {
           if(node.getNode_id().equalsIgnoreCase(networkServerNotify.getNodeId()) == true)
           {
 
		    	//
		        String checktype = Conversion.checkType(decoded);
		    	 
		        if(nCmd == 16  ||("000110".equals(checktype))) {
		        	
		        	Date d = new Date();
		        	log.info("Now new Date is {}",d);
		          	byte[] date = Conversion.dateToByteArray(d);
		          	
		          	
		          	
		          //蹂묓빀�맂 �뜲�씠�꽣 length byte濡� 蹂��솚
		    		byte[] convebyte = Conversion.getAddtoByteLength(date);
		    		//date �뜲�씠�꽣 蹂묓빀
		    		byte[] dateData = Conversion.completeSendMessage(0x20, convebyte);
		    		
		    		//蹂묓빀�맂 �뜲�씠�꽣 length byte濡� 蹂��솚
		    		byte[] dateCompletebyte = Conversion.getAddtoByteLength(dateData);
		    		
		    		//checksum byte 援ы븯湲�
		    		byte[] crc16 = Conversion.getCRC16(dateCompletebyte);
		    		
		    		//�쟾泥� �뜲�씠�꽣 蹂묓빀
		    		byte[] rawData = Conversion.completeSendMessage(0x02, dateCompletebyte, crc16);
		    		
		    		//hex �뜲�씠�꽣濡� 蹂��솚
		    		String rawHexData = Conversion.byteArrayToHexString(rawData);
		            String base64Raw = Base64.encodeBase64String(rawData);
		            
		    		   //�뜲�씠�꽣 �쟾�넚    		
		    		log.info("OID:[{}], contentInstance Create:[{}]",
		            		networkServerNotify.getNodeId(),
		                    oneM2MService.ContentInstanceCreate(networkServerNotify.getNodeId(), rawHexData));
		    		
		        }else if(nCmd == 17){
		        	//check device type
	            	DeviceEntity deviceType = efpdService.selectOneDeviceType(nodeId);
	            	
	            	if(deviceType.getType()==null || deviceType.getType().equals("")) {
	            		return;
	            	}
	            	
		        	byte[] resultState = null;
		        	byte[] diviceInfomessage  = null;
		        	//transfer class info C
		        	if("C".equals(deviceType.getType())) {
		        		Map<String,String> stateMap = new HashMap<String, String>();
		        		
		        		stateMap.put("powerState", "0");
		        		stateMap.put("transState", "0");
		        		stateMap.put("funcCode", "5");
		        		resultState = Conversion.completeSendMessage(0x00,Conversion.getAddtoByteLength(Conversion.getToSendOfState(stateMap)));
		        		CclassDeviceInfoEntity info  =null;
		        		try {
		        			info = efpdService.selectOneCclassDeviceInfo(nodeId);
						} catch (Exception e) {
							log.error("Error is {}",nodeId);
						}
		        		
		        		ObjectMapper oMapper = new ObjectMapper();
		        		Map map = oMapper.convertValue(info, Map.class);
		        		
		        		
		        		//怨좎븬 class C �뒗 21諛붿씠�듃 �젙蹂닿� �븘�슂
		        		diviceInfomessage = Conversion.convertDeviceInfoMapToByte(0x99,21, map,CommonValue.CCLASS_DEIVCE_INFO);
		        		
		        	
		        		//transfer class info A
		        	}else if("A".equals(deviceType.getType())) {
		        		Map<String,String> stateMap = new HashMap<String, String>();
		        		
		        		stateMap.put("powerState", "0");
		        		stateMap.put("transState", "0");
		        		stateMap.put("funcCode", "5");
		        		resultState = Conversion.completeSendMessage(0x00,Conversion.getAddtoByteLength(Conversion.getToSendOfState(stateMap)));
		        		
		        		AclassDeviceInfoEntity info = null;
		        		
		        		try {
		        			info = efpdService.selectOneAclassDeviceInfo(nodeId);
						} catch (Exception e) {
							log.error("Error is {}",nodeId);
						}
		        		
		        		ObjectMapper oMapper = new ObjectMapper();
		        		Map map = oMapper.convertValue(info, Map.class);
		  
		        		//���븬 class A �뒗 23諛붿씠�듃 �젙蹂닿� �븘�슂
		        		diviceInfomessage = Conversion.convertDeviceInfoMapToByte(0x98,23, map,CommonValue.ACLASS_DEIVCE_INFO);
		        	}
		        	//�뜲�씠�꽣 �젙蹂대뱾 蹂묓빀
		    		byte[] mergedata = Conversion.mergeOfTLVData(resultState,diviceInfomessage);
		    		
		    		//蹂묓빀�맂 �뜲�씠�꽣 length byte濡� 蹂��솚
		    		byte[] convebyte = Conversion.getAddtoByteLength(mergedata);
		    		
		    		//checksum byte 援ы븯湲�
		    		byte[] crc16 = Conversion.getCRC16(convebyte);
		    		
		    		//�쟾泥� �뜲�씠�꽣 蹂묓빀
		    		byte[] rawData = Conversion.completeSendMessage(0x02, convebyte, crc16);
		    		
		    		//hex �뜲�씠�꽣濡� 蹂��솚
		    		String rawHexData = Conversion.byteArrayToHexString(rawData);
		            String base64Raw = Base64.encodeBase64String(rawData);
		    		
		            //�뜲�씠�꽣 �쟾�넚    		
		    		log.info("OID:[{}], contentInstance Create:[{}]",
		            		networkServerNotify.getNodeId(),
		                    oneM2MService.ContentInstanceCreate(networkServerNotify.getNodeId(), rawHexData));
		    		
		    		
		  	
		        }else if(nCmd == 0) {
		        	//설정 응답에 대한 resetflag 0 변경
		        	if(decoded[4]==0x19) {
		        		//check device type
		            	DeviceEntity deviceType = efpdService.selectOneDeviceType(nodeId);
		            	if(deviceType.getType().equals("C")) {
		            		efpdService.updateCclassFlag(nodeId);
		            	}else if(deviceType.getType().equals("A")) {
		            		efpdService.updateAclassFlag(nodeId);
		            	}
		            	log.info("RESET RESPONSE COMPLETE");
		        	}
		        	
		        	if(decoded[5]==0x3D || decoded[5]==0x3E) {
			        	Map map = messageCostumParsing(decoded);
			        	map.put("nodeId",networkServerNotify.getNodeId());
			        	
		        		//socket�쟾�넚
		        		JSONObject js = new JSONObject(map);
		                Websocket.sendMessageToAll(js.toJSONString());
			        	if(map.get("div").equals("Aclass")) {
			        		efpdService.saveAclassSensorData(map);
			        	}else if(map.get("div").equals("Cclass")) {
			        		efpdService.saveCclassSensorData(map);
			        	}
		        	}
		        	
		        }
         	
		        break;
         	}
         }
     }
	 
    
        
    }

    @RequestMapping(value = "/poa", method = RequestMethod.POST)
    public void recvPoa(String body)
    {
        log.info("Recv recvPoa");
        log.info("body:[{}]", body);
    }
    
   
 
 
    
    
    private static Map<String, String> messageCostumParsing(byte[] message)
    {
        Map<String, String> map = new HashMap<>();

        byte[] recvMessage = new byte[message.length];
        int nSaveIndex = 0;
        int nCmd = 0x00, nLength = 0;

        for(int i = 2; i < message.length; i++)
        {
            if(nSaveIndex == 0)
            {
                nCmd = (int)message[i];
            }
            else if(nSaveIndex == 1)
            {
                nLength = (int)message[i];
            }
            recvMessage[nSaveIndex++] = message[i];

            if(nSaveIndex == nLength +2)
            {
                map.putAll(subParsing(nCmd, nLength, recvMessage));
                nCmd = 0x00;
                nLength = 0;
                recvMessage = new byte[message.length];
                nSaveIndex = 0;
            }
        }

        return map;
    }
   
    
    private static Map<String, String> subParsing(int cmd, int length, byte[] message)
    {
        Map<String, String> map = new HashMap<>();



        switch(cmd)
        {
            case 0x00:
            	if(message[2]==0x04 || message[2]==0x05 ) {
            		map.put("eventState", "0");
            	}else if(message[2]==0x0C || message[2]==0x0D) {
            		map.put("eventState", "1");
            	}
                break;

            case CommonValue.SENSOR_LOW_CLASS_A:
            	map.put("div", "Aclass");
            	int n = 2;
            	for(int i=0;i<CommonValue.SENSOR_CLASS_A_ARRAY.length;i++) {
            		byte[] dummybyte = new byte[CommonValue.SENSOR_CLASS_A_ARRAY[i]];
            		for(int k=0;k<CommonValue.SENSOR_CLASS_A_ARRAY[i];k++) {
            			dummybyte[k]= message[n++];
            		
            		}
            		String hexString = Conversion.byteArrayToHexString(dummybyte);
            		int intValue = Integer.parseInt(hexString,16);
            		String value = "";
            		if(i==0 || i==1 || i==2 ||i==3||i==12) {
            			value = Integer.toString(intValue);
            			//log.info("real data Integer {}",intValue);
            		}else if( i==13) {
            			value = String.format("%d.%d", intValue/100, abs(intValue%100));
            			//log.info("real data Integerdo {}",String.format("%d.%d", intValue/100, abs(intValue%100)));
            			
            		}else {
            			value = String.format("%d.%d", intValue/10, abs(intValue%10));
            			//log.info("real data {}",String.format("%d.%d", intValue/10, abs(intValue%10)));
            		
            		}
            		switchItem(cmd,map,i,value);
            		
            	}
       
                break;
            case CommonValue.SENSOR_HIGH_CLASS_C:
            	map.put("div", "Cclass");
            	
            	int g = 2;
            	for(int i=0;i<CommonValue.SENSOR_CLASS_C_ARRAY.length;i++) {
            		byte[] dummybyte = new byte[CommonValue.SENSOR_CLASS_C_ARRAY[i]];
            		for(int k=0;k<CommonValue.SENSOR_CLASS_C_ARRAY[i];k++) {
            			dummybyte[k]= message[g++];
            			
            		}
            		String hexString = Conversion.byteArrayToHexString(dummybyte);
            		int intValue = Integer.parseInt(hexString,16);
            		String value = "";
            		if(i==0 || i==1 || i==2 ||i==3 ||i==11) {
            			value = Integer.toString(intValue);
            			//log.info("real data Integer {}",intValue);
            		}else if( i==12) {
            			value = String.format("%d.%d", intValue/100, abs(intValue%100));
            			//log.info("real data Integerdo {}",String.format("%d.%d", intValue/100, abs(intValue%100)));
            			
            		}else {
            			value = String.format("%d.%d", intValue/10, abs(intValue%10));
            			//log.info("real data {}",String.format("%d.%d", intValue/10, abs(intValue%10)));
            		
            		}
            		switchItem(cmd,map,i,value);
            		
            	}
      
            	
            	break;
            default:
                break;
        }

        return map;
    }
    
    private static void switchItem(int cmd,Map map,int div ,String value){
    	if(cmd==1000) {
    		
    	
    		
    	// CLASS A
    	}else if(cmd==61) {
    		
    		switch (div) {
    		case 0:
    			map.put("serialNum", value);
    			break;
    		case 1:
    			map.put("mesureTime", Conversion.getTimestampToDate(value));
    			break;
    		case 2:
    			String result = Integer.toBinaryString(Integer.parseInt(value));
    			 String resultWithPadding = String.format("%8s", result).replaceAll(" ", "0");
    			 convertBitToMap("a_measureState",resultWithPadding,map);
    			map.put("measureState", resultWithPadding);
    			break;
    		case 3:
    			String result1 = Integer.toBinaryString(Integer.parseInt(value));
    			String resultWithPadding1 = String.format("%8s", result1).replaceAll(" ", "0");
    			convertBitToMap("a_measureJudge",resultWithPadding1,map);
    			map.put("measureJudge", resultWithPadding1);
    			break;	
    		case 4:
    			map.put("temp", value);
    			break;	
    		case 5:
    			map.put("tempAccumulate", value);
    			break;	
    		case 6:
    			map.put("accelerationXPeak", value);
    			break;	
    		case 7:
    			map.put("accelerationYPeak", value);
    			break;	
    		case 8:
    			map.put("accelerationZPeak", value);
    			break;	
    		case 9:
    			map.put("accelerationXFtt", value);
    			break;	
    		case 10:
    			map.put("accelerationYFtt", value);
    			break;	
    		case 11:
    			map.put("accelerationZFtt", value);
    			break;	
    		case 12:
    			map.put("driveTime", value);
    			break;	
    		case 13:
    			map.put("driveVoltage", value);
    			break;
    		default:
    			break;
    		}
    	
    	}else if(cmd==62) {
    		
    		
    		switch (div) {
    		case 0:
    			map.put("serialNum", value);
    			break;
    		case 1:
    			map.put("mesureTime", Conversion.getTimestampToDate(value));
    			break;
    		case 2:
    			String result = Integer.toBinaryString(Integer.parseInt(value));
    			 String resultWithPadding = String.format("%8s", result).replaceAll(" ", "0");
    			 convertBitToMap("c_measureState",resultWithPadding,map);
    			map.put("measureState", resultWithPadding);
    			break;
    		case 3:
    			String result1 = Integer.toBinaryString(Integer.parseInt(value));
    			String resultWithPadding1 = String.format("%8s", result1).replaceAll(" ", "0");
    			convertBitToMap("c_measureJudge",resultWithPadding1,map);
    			map.put("measureJudge", resultWithPadding1);
    			break;	
    		case 4:
    			map.put("accelerationXPeak", value);
    			break;	
    		case 5:
    			map.put("accelerationYPeak", value);
    			break;	
    		case 6:
    			map.put("accelerationZPeak", value);
    			break;	
    		case 7:
    			map.put("accelerationXFtt", value);
    			break;	
    		case 8:
    			map.put("accelerationYFtt", value);
    			break;	
    		case 9:
    			map.put("accelerationZFtt", value);
    			break;	
    		case 10:
    			map.put("waveThreshold", value);
    			break;
    		case 11:
    			map.put("driveTime", value);
    			break;	
    		case 12:
    			map.put("driveVoltage", value);
    			break;
    		default:
    			break;
    		}
    		
    	}

    	
    }
    
    /*
    bit �뜲�씠�꽣 �젙蹂� DB移쇰읆�솕 �븿�닔
    */
    private static void convertBitToMap(String column,String bit,Map map) {
    	if(column.equals("a_measureState")) {
    		int i=7;
    		for(String st : CommonValue.A_MEASURE_STATE_COLUNM_GROUP) {
    			map.put(st, Character.getNumericValue(bit.charAt(i))) ;
    			i--;
    		}

    	}else if(column.equals("a_measureJudge")) {
    		int i=7;
    		for(String st : CommonValue.A_MEASURE_JUDGE_COLUNM_GROUP) {
    			map.put(st, Character.getNumericValue(bit.charAt(i))) ;
    			i--;
    		}
    	
    	}
    	
    	if(column.equals("c_measureState")) {
    		int i=7;
    		for(String st : CommonValue.C_MEASURE_STATE_COLUNM_GROUP) {
    			map.put(st, Character.getNumericValue(bit.charAt(i))) ;
    			i--;
    		}

    	}else if(column.equals("c_measureJudge")) {
    		int i=7;
    		for(String st : CommonValue.C_MEASURE_JUDGE_COLUNM_GROUP) {
    			map.put(st, Character.getNumericValue(bit.charAt(i))) ;
    			i--;
    		}
    	
    	}
    }
    
   
    

    
    

    
    
    
   




    
}
