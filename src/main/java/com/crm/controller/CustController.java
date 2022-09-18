package com.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.entity.Customer;
import com.crm.service.CustService;




@Controller
@RequestMapping("/customers")
public class CustController {

	@Autowired
	private CustService custService;
	
	@RequestMapping("/list")
	public String listBooks(Model theModel) {

		// get Books from db
		List<Customer> theCustomer = custService.findAll();

		// add to the spring model
		theModel.addAttribute("Customer", theCustomer);

		return "list-Customers";
	}
	
	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer theCustomer = new Customer();

		theModel.addAttribute("Customer", theCustomer);

		return "Customer-form";
	}
	
	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("fname") String fname,
			@RequestParam("lname") String lname, @RequestParam("email") String email) {

		System.out.println(id);
		Customer theCustomer;
		if (id != 0) {
			theCustomer = custService.findById(id);
			theCustomer.setFirstName(fname);
			theCustomer.setLastName(lname);
			theCustomer.setEmail(email);
		} else
			theCustomer = new Customer(fname, lname, email);
		// save the Book
		custService.save(theCustomer);

		// use a redirect to prevent duplicate submissions
		return "redirect:/customers/list";

	}
	
	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("custId") int thecustId, Model theModel) {

		// get the Book from the service
		Customer theCustomer = custService.findById(thecustId);

		// set Book as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "Customer-form";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("custId") int thecustId) {

		// delete the Book
		custService.deleteById(thecustId);

		// redirect to /Books/list
		return "redirect:/customers/list";

	}
}
