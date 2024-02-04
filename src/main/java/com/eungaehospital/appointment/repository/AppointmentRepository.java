package com.eungaehospital.appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eungaehospital.appointment.domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query("select a from Appointment a"
		+ " where a.hospital.hospitalSeq = :hospitalSeq"
		+ " and a.appointmentDate = :appointmentDate"
		+ " and a.status = 'APPOINTMENT'"
		+ " and a.deleteYN = 'N'"
		+ " order by a.appointmentHHMM")
	List<Appointment> getAppointmentByAppointmentDateAndHospitalId(
		Long hospitalSeq,
		@Param("appointmentDate") LocalDate appointmentDate);

	@Query("select a from Appointment a"
		+ " where a.hospital.hospitalSeq = :hospitalSeq"
		+ " and a.appointmentDate = :appointmentDate"
		+ " and a.status = 'DIAGNOSIS'"
		+ " and a.deleteYN = 'N'"
		+ " order by a.appointmentHHMM")
	List<Appointment> getDiagnosisAppointmentByAppointmentDateAndHospitalId(
		Long hospitalSeq,
		@Param("appointmentDate") LocalDate appointmentDate);

	@Query("select a from Appointment a"
			+ " where a.doctor.doctorSeq = :doctorSeq"
			+ " and a.status = 'APPOINTMENT'"
			+ " and a.deleteYN = 'N'")
	List<Appointment> findAllByDoctorDoctorSeq(Long doctorSeq);


	@Query("select  a from Appointment a" +
			" where a.appointmentSeq = :appointmentSeq" +
			" and a.deleteYN = 'N'")
	Optional<Appointment> findByAppointmentSeq(@Param("appointmentSeq") Long appointmentSeq);

}
