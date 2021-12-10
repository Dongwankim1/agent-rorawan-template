package com.agent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.agent.controller.CallbackController;
import com.agent.model.AclassDeviceInfoEntity;
import com.agent.service.EfpdService;
import com.agent.util.CommonValue;

@Slf4j
@SpringBootTest
class AgentApplicationTests {
	
	 @Autowired
	 EfpdService efpdService;
	 
	 /*
	 @Test
	public void contextLoads() {
		String hexText = "021A000114991505A00002000107D00BB80FA00834089808FC057800D1D3";
		 byte[] bytes = new java.math.BigInteger(hexText, 16).toByteArray();

		 System.out.println(bytes);
			
		}
	 */
	 
	 /*
	@Test
	void contextLoads() {S
		
		Date d = new Date();
		log.info("占쎈뻻揶쏄쑴�젟癰귨옙 {}",d);
		
		String a ="1";
		
		assertEquals("1", a);
		
	}
	
	
	@Test
	public void TestConvertDataToTlve() {
		int cmd = 0x00;
		byte[] data = new byte[1];
		data[0] = 0x14;
		
		byte[] resultdata = new byte[3];
		resultdata[0] = 0x00;
		resultdata[1] = 0x01;
		resultdata[2] = 0x14;
		
		byte[] testdata = convertDataToTLV(cmd, data);
		
		
		assertEquals(Base64.encodeBase64URLSafeString(resultdata), Base64.encodeBase64URLSafeString(testdata));
		
	}
	
	public byte[] convertDataToTLV(int cmd,byte[] data) {
		byte[] convertData = new byte[data.length+2];
		
		convertData[0]= (byte) cmd;
		convertData[1] = (byte) data.length;
		
		int nSaveIndex = 2;
		for(int i=0;i<data.length;i++) {
			convertData[nSaveIndex++] = data[i];
		}
		
		return convertData;
		
	}
	
	@Test
	public void TestMergeOfTLVData() {

		
		byte[] result = new byte[28];
		result[0] = 0x00;
		result[1] = 0x01;
		result[2] = 0x14;
		result[3] = (byte) 0x98;
		result[4] = 0x17;
		result[5] = 0x05;
		result[6] = (byte) 0xA0;
		result[7] = 0x00;
		result[8] = 0x1E;
		result[9] = 0x00;
		result[10] = 0x01;
		result[11] = 0x03;
		result[12] = 0x52;
		result[13] = 0x00;
		result[14] = 0x64;
		result[15] = 0x00;
		result[16] = 0x64;
		result[17] = 0x00;
		result[18] = 0x64;
		result[19] = 0x00;
		result[20] = 0x64;
		result[21] = 0x00;
		result[22] = 0x64;
		result[23] = 0x00;
		result[24] = 0x64;
		result[25] = 0x00;
		result[26] = 0x64;
		result[27] = 0x00;
		
		byte[] tlvData0 = convertDataToTLV(0x00,getToSendOfState());
		
		String nodeId = "LW140C5BFFFF1809F6";
		AclassDeviceInfoEntity info2 = efpdService.selectOneAclassDeviceInfo(nodeId);
		ObjectMapper oMapper = new ObjectMapper();
		Map<String,String> map = oMapper.convertValue(info2, Map.class);
		
		byte[] tlvData1 = convertDataToTLV(0x98, convertDeviceInfo(23,map,CommonValue.ACLASS_DEIVCE_INFO));
		byte[] mergedata = mergeOfTLVData(tlvData0,tlvData1);
		
		log.info("data1 {} , data2 {}",result,mergedata);
		
		assertEquals(Base64.encodeBase64URLSafeString(mergedata), Base64.encodeBase64URLSafeString(result));
		
		//byte[] testdata = mergeOfData();
		
	}
	public byte[] mergeOfTLVData(byte[]...data) {
		int length = 0;
		int nSaveIndex = 0;
		
		for(byte[] st : data) {
			length+=st.length;
		}
		
		byte[] mergeMessage = new byte[length];
		
		for(byte[] st : data) {
			for(int i=0;i<st.length;i++) {
				mergeMessage[nSaveIndex++] = st[i];
			}
		}
		return mergeMessage;
	}
	
	@Test
	public void TestGetToSendOfState() {
		
		byte[] data = getToSendOfState();
		
		byte[] data1 = new byte[1];
		data1[0] = 0x14;
		
		
		log.info("data {}",data);
		assertEquals(Base64.encodeBase64URLSafeString(data), Base64.encodeBase64URLSafeString(data1));
	}
	public byte[] getToSendOfState() {
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("powerState", "0");
		map.put("transState", "0");
		map.put("funcCode", "5");
		
		byte[] data = new byte[1];
		
		String bit="";
		
		for(String st: CommonValue.RES_STATUS_INFO) {
			if(st.equals("powerState")) {
				bit += Integer.parseInt(map.get("powerState")) == 1 ? "1" :"0";
			}else if(map.get("transState").equals(st)) {
				bit += Integer.parseInt(map.get("transState")) == 1 ? "1" :"0";
			}else {
				String dummybit="";
				String convertbit = Integer.toBinaryString(Integer.parseInt(map.get("funcCode")));
				for(int i=0;i<6-convertbit.length();i++) {
					dummybit+="0";
				}
				bit+=dummybit+convertbit;
			}
		}
		data[0] = binaryStringToByte(bit);
		
		
		return data;
	}
	
	public byte binaryStringToByte(String s){
	    byte ret=0, total=0;
	    for(int i=0; i<8; ++i){        
	        ret = (s.charAt(7-i)=='1') ? (byte)(1 << i) : 0;
	        total = (byte) (ret|total);
	    }
	    return total;
	}
	
	@Test
	public void convertStatusInfo() {
		Map<String,String> map = new HashMap<>();
		
		
		
		
	}
	@Test
	public void TestConvertDeviceInfo() {
		byte[] result = new byte[23];
		result[0] = 0x05;
		result[1] = (byte) 0xA0;
		result[2] = 0x00;
		result[3] = 0x1E;
		result[4] = 0x00;
		result[5] = 0x01;
		result[6] = 0x03;
		result[7] = 0x52;
		result[8] = 0x00;
		result[9] = 0x64;
		result[10] = 0x00;
		result[11] = 0x64;
		result[12] = 0x00;
		result[13] = 0x64;
		result[14] = 0x00;
		result[15] = 0x64;
		result[16] = 0x00;
		result[17] = 0x64;
		result[18] = 0x00;
		result[19] = 0x64;
		result[20] = 0x00;
		result[21] = 0x64;
		result[22] = 0x00;
		String nodeId = "LW140C5BFFFF1809F6";
		AclassDeviceInfoEntity info2 = efpdService.selectOneAclassDeviceInfo(nodeId);
		ObjectMapper oMapper = new ObjectMapper();
		Map<String,String> map = oMapper.convertValue(info2, Map.class);
		
		int len = 23;
		
		byte[] message = convertDeviceInfo(len,map,CommonValue.ACLASS_DEIVCE_INFO);
		
		assertEquals(Base64.encodeBase64URLSafeString(message), Base64.encodeBase64URLSafeString(result));
	}
	
	
	public byte[] convertDeviceInfo(int len,Map<String,String> map,String[] info) {
		
	
		
		byte[] message = new byte[len];
    	int nsaveIndex = 0;
    	for(String st : info) {
    		if(st.equals("rqstSetTransFreq")|| st.equals("cycleRepTransFreq") || st.equals("dataMeasureFreq")) {
    			for(int i=0;i<2;i++) {
    				if(i==0) {
    					message[nsaveIndex++] = (byte)(Integer.parseInt(map.get(st)) >>> 8);
    				}else if(i==1) {
    					message[nsaveIndex++] = (byte)Integer.parseInt(map.get(st));		
    				}
    			}
    			
    		}else if(st.equals("resetFlag")) {
    			for(int i=0;i<1;i++) {
    				if(i==0) {
    					message[nsaveIndex++] = (byte)Integer.parseInt(map.get(st));
    				}
    			}
    		}else {
    			for(int i=0;i<2;i++) {
    				if(i==0) {
    					message[nsaveIndex++] = (byte)(((int)(Double.parseDouble(map.get(st))*10)) >>> 8);
    				}else if(i==1) {
    					message[nsaveIndex++] = (byte)((int)(Double.parseDouble(map.get(st))*10));
    					
    				}
    			}
    		}
		
    	}
    	
    	return message;
	}
	
	@Test
	public void TestGetCRC16() {
		byte[] result = new byte[28];
		result[0] = 0x00;
		result[1] = 0x01;
		result[2] = 0x14;
		result[3] = (byte) 0x98;
		result[4] = 0x17;
		result[5] = 0x05;
		result[6] = (byte) 0xA0;
		result[7] = 0x00;
		result[8] = 0x1E;
		result[9] = 0x00;
		result[10] = 0x01;
		result[11] = 0x03;
		result[12] = 0x52;
		result[13] = 0x64;
		result[14] = 0x00;
		result[15] = 0x00;
		result[16] = 0x64;
		result[17] = 0x00;
		result[18] = 0x64;
		result[19] = 0x00;
		result[20] = 0x64;
		result[21] = 0x00;
		result[22] = 0x64;
		result[23] = 0x00;
		result[24] = 0x64;
		result[25] = 0x00;
		result[26] = 0x64;
		result[27] = 0x00;
		
		byte[] testcrc16 = new byte[2];
		testcrc16[0] =  (byte) 0x9A;
		testcrc16[1] = 0x3F;
		
		byte[] convertData = getAddtoByteLength(result);
		
		byte[] crc16 = getCRC16(convertData);
		
		log.info("convertdata {} , data1 {} , data2 {}",convertData,testcrc16,crc16);
		
		assertEquals(Base64.encodeBase64URLSafeString(crc16), Base64.encodeBase64URLSafeString(testcrc16));
		
	}
	
	

    public byte[] getCRC16(byte[] bytes) {
    	//int[] TABLE_POLYNO=new int[256];
    	int[] TABLE_POLYNO= {
    			0x0000 ,0x1021 ,0x2042 ,0x3063 ,0x4084 ,0x50A5 ,0x60C6 ,0x70E7   //000 ~ 007
    			,0x8108 ,0x9129 ,0xA14A ,0xB16B ,0xC18C ,0xD1AD ,0xE1CE ,0xF1EF   //008 ~ 015
    			,0x1231 ,0x0210 ,0x3273 ,0x2252 ,0x52B5 ,0x4294 ,0x72F7 ,0x62D6   //016 ~ 023
    			,0x9339 ,0x8318 ,0xB37B ,0xA35A ,0xD3BD ,0xC39C ,0xF3FF ,0xE3DE   //024 ~ 031
    			,0x2462 ,0x3443 ,0x0420 ,0x1401 ,0x64E6 ,0x74C7 ,0x44A4 ,0x5485   //032 ~ 039
    			,0xA56A ,0xB54B ,0x8528 ,0x9509 ,0xE5EE ,0xF5CF ,0xC5AC ,0xD58D   //040 ~ 047
    			,0x3653 ,0x2672 ,0x1611 ,0x0630 ,0x76D7 ,0x66F6 ,0x5695 ,0x46B4   //048 ~ 055
    			,0xB75B ,0xA77A ,0x9719 ,0x8738 ,0xF7DF ,0xE7FE ,0xD79D ,0xC7BC   //056 ~ 063
    			,0x48C4 ,0x58E5 ,0x6886 ,0x78A7 ,0x0840 ,0x1861 ,0x2802 ,0x3823   //064 ~ 071
    			,0xC9CC ,0xD9ED ,0xE98E ,0xF9AF ,0x8948 ,0x9969 ,0xA90A ,0xB92B   //072 ~ 079
    			,0x5AF5 ,0x4AD4 ,0x7AB7 ,0x6A96 ,0x1A71 ,0x0A50 ,0x3A33 ,0x2A12   //080 ~ 087
    			,0xDBFD ,0xCBDC ,0xFBBF ,0xEB9E ,0x9B79 ,0x8B58 ,0xBB3B ,0xAB1A   //088 ~ 095
    			,0x6CA6 ,0x7C87 ,0x4CE4 ,0x5CC5 ,0x2C22 ,0x3C03 ,0x0C60 ,0x1C41   //096 ~ 103
    			,0xEDAE ,0xFD8F ,0xCDEC ,0xDDCD ,0xAD2A ,0xBD0B ,0x8D68 ,0x9D49   //104 ~ 111
    			,0x7E97 ,0x6EB6 ,0x5ED5 ,0x4EF4 ,0x3E13 ,0x2E32 ,0x1E51 ,0x0E70   //112 ~ 119
    			,0xFF9F ,0xEFBE ,0xDFDD ,0xCFFC ,0xBF1B ,0xAF3A ,0x9F59 ,0x8F78   //120 ~ 127
    			,0x9188 ,0x81A9 ,0xB1CA ,0xA1EB ,0xD10C ,0xC12D ,0xF14E ,0xE16F   //128 ~ 135
    			,0x1080 ,0x00A1 ,0x30C2 ,0x20E3 ,0x5004 ,0x4025 ,0x7046 ,0x6067   //136 ~ 143
    			,0x83B9 ,0x9398 ,0xA3FB ,0xB3DA ,0xC33D ,0xD31C ,0xE37F ,0xF35E   //144 ~ 151
    			,0x02B1 ,0x1290 ,0x22F3 ,0x32D2 ,0x4235 ,0x5214 ,0x6277 ,0x7256   //152 ~ 159
    			,0xB5EA ,0xA5CB ,0x95A8 ,0x8589 ,0xF56E ,0xE54F ,0xD52C ,0xC50D   //160 ~ 167
    			,0x34E2 ,0x24C3 ,0x14A0 ,0x0481 ,0x7466 ,0x6447 ,0x5424 ,0x4405   //168 ~ 175
    			,0xA7DB ,0xB7FA ,0x8799 ,0x97B8 ,0xE75F ,0xF77E ,0xC71D ,0xD73C   //176 ~ 183
    			,0x26D3 ,0x36F2 ,0x0691 ,0x16B0 ,0x6657 ,0x7676 ,0x4615 ,0x5634   //184 ~ 191
    			,0xD94C ,0xC96D ,0xF90E ,0xE92F ,0x99C8 ,0x89E9 ,0xB98A ,0xA9AB   //192 ~ 199
    			,0x5844 ,0x4865 ,0x7806 ,0x6827 ,0x18C0 ,0x08E1 ,0x3882 ,0x28A3   //200 ~ 207
    			,0xCB7D ,0xDB5C ,0xEB3F ,0xFB1E ,0x8BF9 ,0x9BD8 ,0xABBB ,0xBB9A   //208 ~ 215
    			,0x4A75 ,0x5A54 ,0x6A37 ,0x7A16 ,0x0AF1 ,0x1AD0 ,0x2AB3 ,0x3A92   //216 ~ 223
    			,0xFD2E ,0xED0F ,0xDD6C ,0xCD4D ,0xBDAA ,0xAD8B ,0x9DE8 ,0x8DC9   //224 ~ 231
    			,0x7C26 ,0x6C07 ,0x5C64 ,0x4C45 ,0x3CA2 ,0x2C83 ,0x1CE0 ,0x0CC1   //232 ~ 239
    			,0xEF1F ,0xFF3E ,0xCF5D ,0xDF7C ,0xAF9B ,0xBFBA ,0x8FD9 ,0x9FF8   //240 ~ 247
    			,0x6E17 ,0x7E36 ,0x4E55 ,0x5E74 ,0x2E93 ,0x3EB2 ,0x0ED1 ,0x1EF0
    			
    	};
    	int[] CRC = TABLE_POLYNO;
    	  int icrc = 0x0000;
    	  	
    	     for (byte b : bytes) {
    	            icrc =  CRC[((icrc>>8) ^ b) & 0xff] ^ (icrc << 8);
    	        }
    	  System.out.println("Integer " + Integer.toHexString(icrc)); // test   占쎄돌占쏙옙占쎈튊占쎈┷占쎈뮉 揶쏉옙 
    	
    	  
    
    	  return fnShortToBytes((short)icrc,0);//HtypeCast占쎌벥 short�몴占� byte嚥∽옙 癰귨옙占쎌넎占쎈퉸雅뚯눖�뮉 占쎈맙占쎈땾


    	
    }
    @Test
    public void TestCompleteSendMessage() {
    	byte[] result = new byte[28];
		result[0] = 0x00;
		result[1] = 0x01;
		result[2] = 0x14;
		result[3] = (byte) 0x98;
		result[4] = 0x17;
		result[5] = 0x05;
		result[6] = (byte) 0xA0;
		result[7] = 0x00;
		result[8] = 0x1E;
		result[9] = 0x00;
		result[10] = 0x01;
		result[11] = 0x03;
		result[12] = 0x52;
		result[13] = 0x64;
		result[14] = 0x00;
		result[15] = 0x00;
		result[16] = 0x64;
		result[17] = 0x00;
		result[18] = 0x64;
		result[19] = 0x00;
		result[20] = 0x64;
		result[21] = 0x00;
		result[22] = 0x64;
		result[23] = 0x00;
		result[24] = 0x64;
		result[25] = 0x00;
		result[26] = 0x64;
		result[27] = 0x00;
		
		byte[] trueresult = new byte[32];
		trueresult[0] = 0x02;
		trueresult[1] = 0x1C;
		trueresult[2] = 0x00;
		trueresult[3] = 0x01;
		trueresult[4] = 0x14;
		trueresult[5] = (byte) 0x98;
		trueresult[6] = 0x17;
		trueresult[7] = 0x05;
		trueresult[8] = (byte) 0xA0;
		trueresult[9] = 0x00;
		trueresult[10] = 0x1E;
		trueresult[11] = 0x00;
		trueresult[12] = 0x01;
		trueresult[13] = 0x03;
		trueresult[14] = 0x52;
		trueresult[15] = 0x64;
		trueresult[16] = 0x00;
		trueresult[17] = 0x00;
		trueresult[18] = 0x64;
		trueresult[19] = 0x00;
		trueresult[20] = 0x64;
		trueresult[21] = 0x00;
		trueresult[22] = 0x64;
		trueresult[23] = 0x00;
		trueresult[24] = 0x64;
		trueresult[25] = 0x00;
		trueresult[26] = 0x64;
		trueresult[27] = 0x00;
		trueresult[28] = 0x64;
		trueresult[29] = 0x00;
		trueresult[30] = (byte) 0x9A;
		trueresult[31] = 0x3F;

		byte[] testcrc16 = new byte[2];
		testcrc16[0] =  (byte) 0x9A;
		testcrc16[1] = 0x3F;
		
		byte[] convertData = getAddtoByteLength(result);
		
		byte[] crc16 = getCRC16(convertData);
		
		byte[] lastMessage = completeSendMessage(0x02,convertData,crc16);
		
		log.info("dddata1 {} , dddta2 {}",lastMessage,trueresult);
		
		assertEquals(Base64.encodeBase64URLSafeString(lastMessage), Base64.encodeBase64URLSafeString(trueresult));
		
    }
    
    
    public byte[] completeSendMessage(int cmd,byte[] bodydata,byte[] crc16) {
    	byte[] lastdata = new byte[bodydata.length+crc16.length+1];
    	int nSaveIndex = 1;
    	lastdata[0] = (byte) cmd;
    	for(int i=0;i<bodydata.length;i++) {
    		lastdata[nSaveIndex++] = bodydata[i];
    	}
    	
    	for(int i=0;i<crc16.length;i++) {
    		lastdata[nSaveIndex++] = crc16[i];
    	}
    	
    	
    	return lastdata;
    }
    
    public byte[] getAddtoByteLength(byte[] data) {
    	byte[] newData = new byte[data.length+1];
    	int length = data.length;
    	int nSaveIndex = 1;
    	for(int i=0;i<newData.length;i++) {
    		if(i==0) {
    			newData[0] = (byte) length;
    		}else {
    			newData[nSaveIndex++] = data[i-1];
    		}
    		
    		
    	}
    	
    	return newData; 
    }
    
    
  //short 揶쏅�れ뱽 獄쏅뗄�뵠占쎈뱜 獄쏄퀣肉닸에占� 癰귨옙占쎌넎
    public byte[] fnShortToBytes(short Value, int Order){
        byte[] temp;
        temp = new byte[]{ (byte)((Value & 0xFF00) >> 8), (byte)(Value & 0x00FF) };
        //temp = fnChangeByteOrder(temp,Order);
        return temp;
    }
     
    //占쎄맒 占쎈릭占쎌맄 癰귨옙占쎌넎
    private byte[] fnChangeByteOrder(byte[] value,int Order){
        int idx = value.length;
        byte[] Temp = new byte[idx];
     
        if(Order == 1){
            Temp = value;
        }else if(Order == 0){
            for(int i=0;i<idx;i++) {
                Temp[i] = value[idx-(i+1)];
            }
        }
        return Temp;
    }
    
    @Test
    public void TestCheckType() {
    	byte[] result = new byte[23];
    	result[0]= 0x02;
    	result[1] = 0x13;
    	result[2] = 0x00;
    	result[3] = 0x01;
    	result[4] = 0x03;
    	result[5] = 0x01;
    	result[6] = 0x08;
    	result[7] = 0x00;
    	result[8] = 0x01;
    	result[9] = (byte) 0x86;
    	result[10] = (byte) 0xA1;
    	result[11] = 0x00;
    	result[12] = 0x01;
    	result[13] = (byte) 0x86;
    	result[14] = (byte) 0xA2;
    	result[15] = 0x10;
    	result[16] = 0x04;
    	result[17] = 0x5C;
    	result[18] = (byte) 0xAD;
    	result[19] = 0x34;
    	result[20] = (byte) 0x80;
    	result[21] = 0x59;
    	result[22] = (byte) 0x8F;
    	
    	
    	String resultdata = checkType(result);
    	
    	assertEquals("000110", resultdata);
    }
    
    
    
    public String checkType(byte[] message) {
    	String result = "";
    	int nIndex = 2;
    	int totallen = message.length;
  
    	while(true) {
    		if((nIndex+2)==totallen) {
    			
    			break;
    		}
    		int cmd = message[nIndex];
    		int length = message[nIndex+1];
    		
    		result+=getCheck(cmd);
    		nIndex+=length+2;
    		
    	
    	}
    	
    	
    	
    	
    	return result;
    }
    
    public String getCheck(int message) {
    	
    	String data = "";
    	switch (message) {
		case 0x00:
			data = "00";
			break;
		case 0x01:
			data =  "01";
			break;
		case 0x10:
			data =  "10";
			break;
		case 0x99:
			data =  "99";
			break;
		case 0x98:
			data =  "98";
			break;
		default:
			break;
		}
    	
    	return data;
    }
    */
}
