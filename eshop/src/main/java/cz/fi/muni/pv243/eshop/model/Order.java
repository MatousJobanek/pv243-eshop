package cz.fi.muni.pv243.eshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	private Customer customer;

	// private List<Product> products;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// /**
	// * @return the products
	// */
	// public List<Product> getProducts() {
	// return products;
	// }
	//
	// /**
	// * @param products
	// * the products to set
	// */
	// public void setProducts(List<Product> products) {
	// this.products = products;
	// }

}