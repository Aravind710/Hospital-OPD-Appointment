package com.example.OPDappointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppointmentService {
	private final AppointmentRepositary appointmentRepository;

    public AppointmentService(AppointmentRepositary appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
	@Autowired
	private AppointmentRepositary repo;
	public List<Appointment> listAll()
	{
		return repo.findAll();
	}
	public void save(Appointment appointment)
	{
		repo.save(appointment);
	}
	public Appointment get(long id)
	{
		return repo.findById(id).get();
	}
}
