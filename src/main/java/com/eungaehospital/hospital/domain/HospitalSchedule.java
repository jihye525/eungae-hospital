package com.eungaehospital.hospital.domain;

import com.eungaehospital.base.BaseEntity;
import com.eungaehospital.hospital.dto.HospitalScheduleViewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "hospital_schedule")
@Entity
public class HospitalSchedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long hospitalScheduleSeq;

	@OneToOne(mappedBy = "hospitalSchedule")
	private Hospital hospital;

	@Column(nullable = false, length = 4)
	private String lunchHour;

	@Column(nullable = false, length = 4)
	private String lunchEndHour;

	@Column(nullable = false, length = 4)
	private String monOpen;

	@Column(nullable = false, length = 4)
	private String monClose;

	@Column(nullable = false, length = 4)
	private String tueOpen;

	@Column(nullable = false, length = 4)
	private String tueClose;

	@Column(nullable = false, length = 4)
	private String wedOpen;

	@Column(nullable = false, length = 4)
	private String wedClose;

	@Column(nullable = false, length = 4)
	private String thuOpen;

	@Column(nullable = false, length = 4)
	private String thuClose;

	@Column(nullable = false, length = 4)
	private String friOpen;

	@Column(nullable = false, length = 4)
	private String friClose;

	@Column(nullable = false, length = 4)
	private String satOpen;

	@Column(nullable = false, length = 4)
	private String satClose;

	@Column(nullable = false, length = 4)
	private String sunOpen;

	@Column(nullable = false, length = 4)
	private String sunClose;

	//연관관계 편의 메소드
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
		hospital.setHospitalSchedule(this);
	}

	public void update(HospitalScheduleViewDto hospitalScheduleViewDto) {
		monOpen = hospitalScheduleViewDto.getMonOpen();
		monClose = hospitalScheduleViewDto.getMonClose();
		thuOpen = hospitalScheduleViewDto.getThuOpen();
		thuClose = hospitalScheduleViewDto.getThuClose();
		wedOpen = hospitalScheduleViewDto.getWedOpen();
		wedClose = hospitalScheduleViewDto.getWedClose();
		tueOpen = hospitalScheduleViewDto.getTueOpen();
		tueClose = hospitalScheduleViewDto.getTueClose();
		friOpen = hospitalScheduleViewDto.getFriOpen();
		friClose = hospitalScheduleViewDto.getFriClose();
		satOpen = hospitalScheduleViewDto.getSatOpen();
		satClose = hospitalScheduleViewDto.getSatClose();
		sunOpen = hospitalScheduleViewDto.getSunOpen();
		sunClose = hospitalScheduleViewDto.getSunClose();
		lunchHour = hospitalScheduleViewDto.getLunchHour();
		lunchEndHour = hospitalScheduleViewDto.getLunchEndHour();
	}

}
