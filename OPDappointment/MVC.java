package com.example.OPDappointment;
import java.util.*;
import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
public class MVC {
	@RequestMapping("/")
	@GetMapping("/")
	public String Homepage(Model model,@ModelAttribute("user") User user)
	{	
		try {
			System.out.println("Home page");
			model.addAttribute("user", user);
			List<String> listProfession=Arrays.asList("Doctor","Patient");
			model.addAttribute("listProfession",listProfession);
			System.out.println(user);
			File r=new File("D:/OpdAppointment/Register.txt");
			r.createNewFile();
			String a[]=new String[4];
			String b="";
			a[0]=user.getEmail();
			b=b+a[0]+";";
			a[1]=user.getPassword();
			b=b+a[1]+";";
			a[2]=user.getChoose();
			b=b+a[2]+";";
			a[3]=user.getName();
			b=b+a[3]+";";
			FileWriter t=new FileWriter(r,true);
			BufferedWriter n=new BufferedWriter(t);
			if(a[3]==null&&a[1]==null&&a[0]==null&&a[2]==null)
			{
				n.close();
				t.close();
			}
			else
			{
				n.append(b);
				n.newLine();
				n.newLine();
				n.close();
				t.close();
			}
			Login login=new Login();
			model.addAttribute("login",login);
			}
		catch (IOException e) {
			e.printStackTrace();
		}
		user.setName(null);
	    user.setEmail(null);
	    user.setPassword(null);
	    user.setChoose(null);
		return "index";
	}
	@Autowired
	private AppointmentService service;
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String Saveappointment(@ModelAttribute("appointment")Appointment appointment)
	{
		service.save(appointment);
		return "redirect:/";
	}
	@RequestMapping("/new")
	public String showNewProductPage(Model model, @ModelAttribute("appointment")Appointment appointment)
	{
		List<String> speciality=Arrays.asList("Emergency and Critical Care","Dentistry","Cardiology","Dermatology","Plastic & Reconstructive Surgery","Neurology","Anesthesiology","General Surgery");
		model.addAttribute("speciality",speciality);
		model.addAttribute("appointment", appointment);
		return "Appointment";
	}
	@PostMapping("/r1")
	public String Log(Model model,@ModelAttribute("appointment")Appointment appointment,@ModelAttribute("login") Login login, BindingResult bindingResult) {
        int m = 0;
        model.addAttribute("login", login);
        System.out.println(login);
        String k="";
        try {
            File g = new File("D:/OpdAppointment/Register.txt");
            g.createNewFile();

            try (FileReader h = new FileReader(g); BufferedReader y = new BufferedReader(h)) {
                String c = y.readLine();
                String u = login.getEmail();
                String p = login.getPassword();
                String o = login.getChoose();

                while (c != null) {
                    String[] d = c.split(";");
                    if (d != null && d.length == 4 && d[0].equals(u) && d[1].equals(p) && d[2].equals(o)) {
                        k=o;
                        m = m + 1;
    
                    }
                    c = y.readLine();
                }
            }

           
        } catch (Exception e) {
            e.printStackTrace();
            m = -1;
            k="";
        }

        if (m == 1) {
        	if(k.equals("Patient"))
        	{
        		System.out.println(m);
        		return "success";
        	}
        	else
        	{
        		List<Appointment> listappointments=service.listAll();
        		model.addAttribute("listappointments", listappointments);
        		System.out.println(listappointments);
        		return "checking";
        	}
        } else {
            System.out.println(m);
            return "failed";
        }
    }
	@RequestMapping("/r2")
	@PostMapping("/r2")
	public String Appointment(Model model, @ModelAttribute("appointment") Appointment appointment, BindingResult bindingResult)
	{
		model.addAttribute("appointment", appointment);
		List<String> speciality=Arrays.asList("Emergency and Critical Care","Dentistry","Cardiology","Dermatology","Plastic & Reconstructive Surgery","Neurology","Anesthesiology","General Surgery");
		model.addAttribute("speciality",speciality);
		if(service!=null)
		{
		List<Appointment> listappointment=service.listAll();
		model.addAttribute("listappointment",listappointment);
		}
		return "appointment";
	}
	
	@RequestMapping("/r3")
	public String app(Model model,Appointment appointment)
	{
		System.out.println(appointment);
		try   
		{    
		String filename = "D:/OpdAppointment/Appointment.xlsx";  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet("Appointment");
		HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Time");
        headerRow.createCell(4).setCellValue("Speciality");
        System.out.println(sheet.getLastRowNum());
		HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);  
		row.createCell(0).setCellValue(appointment.getName());  
		row.createCell(1).setCellValue(appointment.getEmail());  
		row.createCell(2).setCellValue(appointment.getDate());  
		row.createCell(3).setCellValue(appointment.getTime());  
		row.createCell(4).setCellValue(appointment.getSpeciality());
		FileOutputStream fileOut = new FileOutputStream(filename);  
		workbook.write(fileOut);  
		fileOut.close();
		workbook.close();  
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  
		return "index";
	}
}


