package com.eungaehospital.file;

import java.io.File;

import com.eungaehospital.appointment.domain.AppointmentDocument;
import com.eungaehospital.hospital.domain.HospitalImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultFileStore {
    private String folderPath;
    private String storeFileName;
    private String originalFileName;

    public String getFullPath() {
        return folderPath + File.separator + storeFileName;
    }

    public static HospitalImage toHospitalImage(ResultFileStore resultFileStore){
        return HospitalImage.builder()
            .originFileName(resultFileStore.getOriginalFileName())
            .storeFileName(resultFileStore.getStoreFileName())
            .build();
    }

    public static AppointmentDocument toAppointmentDocument(ResultFileStore resultFileStore){
        return AppointmentDocument.builder()
                .appointmentDocumentLoc(resultFileStore.getStoreFileName())
                .build();
    }
}
