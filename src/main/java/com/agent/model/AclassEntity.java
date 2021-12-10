package com.agent.model;
import lombok.*;

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
@Builder // 빌더를 사용할 수 있게함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="tb_class_a")
public class AclassEntity {
	
	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	@Column(length=45)
	private String nodeId;
	
	@Column(length=45)
	private String serialNum;
	
	@Column(length=45)
	private String mesureTime;

	@Column(length=45)
	private String accelerationSensorState;

	@Column(length=45)
	private String smokeSensorState;
	
	@Column(length=45)
	private String tempSensorState;
	
	@Column(length=45)
	private String accelerationFttJudge;
	
	@Column(length=45)
	private String accelerationPeakJudge;
	
	@Column(length=45)
	private String smokeJudge;
	
	@Column(length=45)
	private String tempJudge;
	
	@Column(length=45)
	private String tempAccJudge;
	
	@Column(length=45)
	private String temp;
	
	@Column(length=45)
	private String tempAccumulate;
	
	@Column(length=45)
	private String accelerationXPeak;
	
	@Column(length=45)
	private String accelerationYPeak;
	
	@Column(length=45)
	private String accelerationZPeak;
	
	@Column(length=45)
	private String accelerationXFtt;
	
	@Column(length=45)
	private String accelerationYFtt;
	
	@Column(length=45)
	private String accelerationZFtt;
	
	@Column(length=45)
	private String driveTime;
	
	@Column(length=45)
	private String driveVoltage;
	
	@Column(length=1)
	private String eventState;
	
	
}
