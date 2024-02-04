package com.eungaehospital.appointment.repository;

import com.eungaehospital.appointment.domain.AppointmentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentDocumentRepository extends JpaRepository<AppointmentDocument, Long> {
}
