package com.example.OPDappointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class YourController {
    private final AppointmentService appointmentService;

    @Autowired
    public YourController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Controller methods...
}
