package com.udemy.backendninja.component;

import org.springframework.stereotype.Component;

import com.udemy.backendninja.entity.Contact;
import com.udemy.backendninja.model.ContactModel;

@Component("contactConverter")
public class ContactConverter {
	
	/**
	 * Metodo que convierte de modelo a entity de Contact
	 * @param contactModel
	 * @return
	 */
	public Contact convertContactModel2Contact(ContactModel contactModel) {
		
		Contact contact = new Contact();
		contact.setId(contactModel.getId());
		contact.setFirstname(contactModel.getFirstname());
		contact.setLastname(contactModel.getLastname());
		contact.setTelephone(contactModel.getTelephone());
		contact.setCity(contactModel.getCity());
		
		return contact;
		
	} 
	
	/**
	 * Metodo que convierte de entity a modelo de contact
	 * @param contact
	 * @return
	 */
	public ContactModel convertContact2ContactModel(Contact contact) {
		
		ContactModel contactModel = new ContactModel();
		contactModel.setId(contact.getId());
		contactModel.setFirstname(contact.getFirstname());
		contactModel.setLastname(contact.getLastname());
		contactModel.setTelephone(contact.getTelephone());
		contactModel.setCity(contact.getCity());
		
		return contactModel;
		
	} 


}
