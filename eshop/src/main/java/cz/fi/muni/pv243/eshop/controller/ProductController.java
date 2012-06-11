package cz.fi.muni.pv243.eshop.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.fi.muni.pv243.eshop.model.Product;
import cz.fi.muni.pv243.eshop.service.ProductManager;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class ProductController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private ProductManager productManager;

	private Product newProduct;

	@Produces
	@Named
	public Product getNewProduct() {
		return newProduct;
	}

	public void register() throws Exception {
		productManager.addProduct(newProduct);
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Added!", "Product was added"));
		initNewProduct();
	}

	@PostConstruct
	public void initNewProduct() {
		newProduct = new Product();
	}
}
