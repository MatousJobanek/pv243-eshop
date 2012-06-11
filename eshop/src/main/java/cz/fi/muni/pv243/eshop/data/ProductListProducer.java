package cz.fi.muni.pv243.eshop.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.fi.muni.pv243.eshop.model.Product;

@RequestScoped
public class ProductListProducer {
	@Inject
	private EntityManager em;

	private List<Product> products;

	// @Named provides access the return value via the EL variable name
	// "products" in the UI (e.g.,
	// Facelets or JSP view)
	// @Produces
	// @Named
	// public List<Product> getProducts() {
	// return products;
	// }

	public void onProductListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Product product) {
		retrieveAllProductsOrderedByName();
	}

	@PostConstruct
	public void retrieveAllProductsOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(product).orderBy(cb.asc(product.get("id")));
		products = em.createQuery(criteria).getResultList();

	}
}
