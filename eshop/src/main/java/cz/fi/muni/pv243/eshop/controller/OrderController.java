package cz.fi.muni.pv243.eshop.controller;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.Identity;

import cz.fi.muni.pv243.eshop.model.Customer;
import cz.fi.muni.pv243.eshop.model.Orders;
import cz.fi.muni.pv243.eshop.service.Basket;
import cz.fi.muni.pv243.eshop.service.CustomerManager;
import cz.fi.muni.pv243.eshop.service.OrderManager;

@Model
public class OrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Identity identity;

	@Inject
	private FacesContext facesContext;

	@Inject
	Basket basket;

	@Inject
	private CustomerManager customerManager;
	@Inject
	private OrderManager orderManager;

	private Customer customer;
	private Orders newOrder;

	@Produces
	@Named
	public Orders getNewOrder() {
		return newOrder;
	}

	public void register() throws Exception {
		System.out.println("ahoj");
		System.out.println(identity.getUser());
		customer = (Customer) identity.getUser();
		newOrder.setCustomer(customer);
		System.out.println(newOrder);
		orderManager.addOrder(newOrder);
		facesContext.addMessage("testForm:testButton", new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Added!", "Order was added"));
		initNewOrder();
	}

	public void makeOrder() {
		System.out.println("Makeing order");
		HashMap<Long, Integer> kosik = basket.getBasketContent();
		for (Long key : kosik.keySet()) {
			System.out.println(key);

		}

	}

	@PostConstruct
	public void initNewOrder() {
		newOrder = new Orders();
	}

}
