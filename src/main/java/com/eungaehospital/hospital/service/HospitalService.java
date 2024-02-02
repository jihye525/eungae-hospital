package com.eungaehospital.hospital.service;

import java.util.List;

import java.util.stream.Collectors;

import com.eungaehospital.doctor.dto.DoctorResponseDto;
import com.eungaehospital.doctor.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eungaehospital.file.ResultFileStore;
import com.eungaehospital.hospital.domain.Hospital;
import com.eungaehospital.hospital.domain.HospitalImage;
import com.eungaehospital.hospital.dto.HospitalImageResponseDto;
import com.eungaehospital.hospital.dto.HospitalScheduleViewDto;
import com.eungaehospital.hospital.dto.HospitalUpdateRequestDto;
import com.eungaehospital.hospital.dto.HospitalViewResponseDto;
import com.eungaehospital.hospital.repository.HospitalImageRepository;
import com.eungaehospital.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalService {
	private final HospitalRepository hospitalRepository;
	private final HospitalImageRepository hospitalImageRepository;

	@Transactional(readOnly = true)
	public HospitalViewResponseDto getHospitalByHospitalId(String hospitalId) {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId).get();
		List<HospitalImage> hospitalImageList = hospitalImageRepository.findAllByHospital(hospital);
    
		return HospitalViewResponseDto.toDto(hospital, hospitalImageList);
	}

	@Transactional(readOnly = true)
	public HospitalScheduleViewDto getHospitalSchedule(String hospitalId) {
		Hospital hospital = hospitalRepository.findWithSchedule(hospitalId)
			.orElseThrow(() -> new IllegalStateException("Can not found Hospital"));
		return HospitalScheduleViewDto.toDto(hospital.getHospitalSchedule());
	}

	@Transactional
	public void updateHospitalSchedule(String hospitalId, HospitalScheduleViewDto hospitalScheduleViewDto) {
		Hospital hospital = hospitalRepository.findWithSchedule(hospitalId)
			.orElseThrow(() -> new IllegalStateException("Can not found Hospital"));
		hospital.getHospitalSchedule().update(hospitalScheduleViewDto);
	}

	@Transactional
	public void updateHospital(HospitalUpdateRequestDto hospitalUpdateRequestDto, String hospitalId) {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId).get();
		hospital.updateHospitalInfo(hospitalUpdateRequestDto.getName(),
			hospitalUpdateRequestDto.getContact(),
			hospitalUpdateRequestDto.getNotice(),
			hospitalUpdateRequestDto.getAddress(),
			hospitalUpdateRequestDto.getDeposit());
	}

	@Transactional(readOnly = true)
	public List<HospitalImageResponseDto> getHospitalImagesByHospital(String hospitalId) {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId).get();
		List<HospitalImage> hospitalImageList = hospital.getHospitalImageList();

		return hospitalImageList.stream()
			.map(HospitalImageResponseDto::toDto)
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateHospitalImageList(List<ResultFileStore> resultFileStores, String hospitalId) {
		Hospital hospital = hospitalRepository.findByHospitalId(hospitalId).get();

		if (!resultFileStores.isEmpty()) {
			List<HospitalImage> newHospitalImageList = resultFileStores.stream()
				.map(ResultFileStore::toEntity)
				.collect(Collectors.toList());

			newHospitalImageList.stream().forEach(hospitalImage -> {
				hospitalImage.setHospital(hospital);
				hospitalImageRepository.save(hospitalImage);
			});
		}
	}
}
