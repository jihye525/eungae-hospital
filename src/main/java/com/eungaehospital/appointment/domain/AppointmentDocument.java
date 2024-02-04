package com.eungaehospital.appointment.domain;

import com.eungaehospital.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment_document")
public class AppointmentDocument extends BaseEntity {

	@Id
	@GeneratedValue
	private Long appointmentDocumentSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_seq")
	private Appointment appointment;

	private String appointmentDocumentLoc;

	private String note;

	public void setAppointment(Appointment appointment){
		this.appointment = appointment;
		appointment.addAppointmentDocument(this);
	}
}
