package com.eungaehospital.appointment.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.eungaehospital.file.FileStore;
import com.eungaehospital.file.ResultFileStore;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eungaehospital.appointment.dto.AppointmentResponseDto;
import com.eungaehospital.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AppointmentApiController {

	private final AppointmentService appointmentService;
	private final FileStore fileStore;

	@GetMapping("/appointments")
	public List<AppointmentResponseDto> getAppointmentList(
		@AuthenticationPrincipal UserDetails userDetails,
		String selectDate
	) {
		return appointmentService.getAppointmentListBySelectedDate(
			userDetails.getUsername(), selectDate);
	}

	@PostMapping("/appointments/documents")
	public String attachAppointmentDocuments(Long appointmentSeq, List<MultipartFile> appointmentDocuments, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "cannot attach appointmentDocuments";
		}


		List<ResultFileStore> resultFileStore = appointmentDocuments.stream().map(fileStore::storeFile).collect(Collectors.toList());

		appointmentService.attachAppointmentDocuments(appointmentSeq, resultFileStore);

	}

}