package cz.fi.muni.pv243.eshop.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import cz.fi.muni.pv243.eshop.model.Orders;

@Named("orderManager")
@Stateless
public class OrderManagerImpl implements OrderManager {

	@Inject
	private transient Logger logger;

	@Inject
	private EntityManager orderDatabase;

	@Inject
	private Event<Orders> orderEventSrc;

	@Inject
	private CustomerManager customerManager;

	@Override
	@SuppressWarnings("unchecked")
	@Produces
	@Named
	@RequestScoped
	public List<Orders> getOrders() {
		return orderDatabase
				.createQuery(
						"SELECT o FROM Orders o ORDER BY o.customer,o.creationDate ASC")
				.getResultList();
	}

	@Override
	public void addOrder(Orders orders) {
		orderDatabase.persist(orders);
		logger.info("Adding " + orders.toString());
		orderEventSrc.fire(orders);

	}

	// @Override
	// public List<Orders> getCustomerOrders(String email) {
	//
	// // TODO not tested!!!
	// List<Orders> results = orderDatabase
	// .createQuery(
	// "select o from Orders o where o.customer=:customer")
	// .setParameter("customer", customerManager.isRegistred(email))
	// .getResultList();
	// return results;
	// }

	@Override
	public Orders getOrderDetails(Long id) {
		return (Orders) orderDatabase
				.createQuery("SELECT o FROM Orders o WHERE o.id=:id")
				.setParameter("id", id).getSingleResult();
	}

}
