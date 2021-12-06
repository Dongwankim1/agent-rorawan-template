package co.irexnet.MINA.MINA_PlatformAgent.model;


import lombok.*;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // getter 메소드 생성
@Setter
@Builder // 빌더를 사용할 수 있게함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="tb_deviceinfo_c")
public class CclassDeviceInfoEntity {
	
	
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@Id
	@Column(length=45)
	private String nodeId;
	
	@Column(length=45)
	private String serialNum;
	
	@Column(length=45)
	private Date installDt;

	@Column(length=50)
	private String installInfo;

	@Column(length=50)
	private String fileId;
	
	@Column(length=1)
	private String useYn;
	
	@Column(length=1)
	private String classType;
	
	@Column(length=45)
	private String rqstSetTransFreq;
	
	@Column(length=45)
	private String cycleRepTransFreq;
	
	@Column(length=45)
	private String dataMeasureFreq;

	
	@Column(length=45)
	private String accelerationXPeak;
	
	@Column(length=45)
	private String accelerationYPeak;
	
	@Column(length=45)
	private String accelerationZPeak;
	
	@Column(length=45)
	private String accelerationXthreshold;
	
	@Column(length=45)
	private String accelerationYthreshold;
	
	@Column(length=45)
	private String accelerationZthreshold;
	
	@Column(length=45)
	private String waveThreshold;
	
	@Column(length=1)
	private String resetFlag;
	
	
}
