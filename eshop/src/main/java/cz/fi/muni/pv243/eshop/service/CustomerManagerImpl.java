package cz.fi.muni.pv243.eshop.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import cz.fi.muni.pv243.eshop.model.Customer;

@Named("userManager")
@RequestScoped
@Stateful
public class CustomerManagerImpl implements CustomerManager {

	@Inject
	private transient Logger logger;

	@Inject
	private EntityManager customerDatabase;

	private final Customer newCustomer = new Customer();

	@Inject
	private Event<Customer> customerEventSrc;

	@Override
	@SuppressWarnings("unchecked")
	@Produces
	@Named
	@RequestScoped
	public List<Customer> getCustomers() throws Exception {
		return customerDatabase.createQuery("select c from Customer c")
				.getResultList();
	}

	@Override
	public void addCustomer(Customer customer) throws Exception {

		customerDatabase.persist(newCustomer);
		logger.info("Adding " + customer.toString());
		customerEventSrc.fire(customer);
	}

	@Override
	public Customer findCustomer(String email, String passwordCredential) {
		try {
			Customer customer = (Customer) customerDatabase
					.createQuery(
							"select c from Customer c where c.email=:email and c.password=:password")
					.setParameter("email", email)
					.setParameter("password", passwordCredential)
					.getSingleResult();
			return customer;
		} catch (Exception ex) {
			return null;
		}
	}

	// @Override
	// @Produces
	// @RequestScoped
	// @Named
	// public Customer getNewCustomer() {
	// return newCustomer;
	// }

	@Override
	public boolean isRegistred(String email) {
		try {
			customerDatabase
					.createQuery(
							"select c from Customer c where c.email=:email")
					.setParameter("email", email).getSingleResult();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
