package com.udemy.backendninja.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.backendninja.constant.ViewConstant;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {
	
	private static final Log LOG = LogFactory.getLog(ContactController.class);
	
	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;
	
	
	//esta anotacion puede ser a nivel de metodo o a nivel de clase
	//permite expresiones or, and, permitAll()
	//estas anotaciones pueden ir en controladodes o servicios
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/contactform")
	public String redirectContractform(@RequestParam(name = "id", required=false) int id,
			Model model) {
		
		ContactModel contactModel = new ContactModel();
		
		if (id != 0) {
			contactModel = contactService.findContactModelById(id);
		}		
		
		model.addAttribute("contactModel", contactModel);
		
		return ViewConstant.CONTACTFORM;
	}
	
	@GetMapping("/cancel")
	public String cancel(){
		
		return "redirect:/contacts/showcontacts";
	}
	
	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute(name="contactModel") ContactModel contactModel,
			Model model) {
		
		LOG.info("METHOD: addContact() -- PARAMS: " + contactModel.toString());
		
		if (contactService.addContact(contactModel) != null) {
			model.addAttribute("result",1);
		}else {
			model.addAttribute("result",0);		
			
		}
		
		return "redirect:/contacts/showcontacts";	
		
	}
	
	@GetMapping("/showcontacts")
	public ModelAndView showContacts() {
		ModelAndView modelAndView = new ModelAndView(ViewConstant.CONTACTS);
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		modelAndView.addObject("contacts",contactService.listAllContacts() );
		modelAndView.addObject("username",user.getUsername());
		
		return modelAndView;
	}
	
	@GetMapping("removecontact")
	public ModelAndView removeContact(@RequestParam(name = "id", required=true) int id) {
		
		contactService.removeContact(id);
		
		return showContacts();
	}

}
