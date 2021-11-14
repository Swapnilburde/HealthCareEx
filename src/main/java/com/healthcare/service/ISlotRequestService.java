package com.healthcare.service;

import java.util.List;

import com.healthcare.entity.SlotRequest;

public interface ISlotRequestService {
		//patient can book slot
		Long saveSlotRequest(SlotRequest sr);
		//ADMIN can view all slots
		List<SlotRequest> getAllSlotRequests();
		//ADMIN/patient can update status
		void updateSlotRequestStatus(Long id,String status);
		//PATIENT can see his slots
		List<SlotRequest> viewSlotsByPatientMail(String patientMail);
		SlotRequest getOneSlotRequest(Long id);
		//DOCTOR can see his slots
		List<SlotRequest> viewSlotsByDoctorMail(String doctorMail);
		
		List<Object []>getSlotsStatusAndCount();
}
