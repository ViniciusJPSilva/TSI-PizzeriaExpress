package br.vjps.tsi.pe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;

import br.vjps.tsi.pe.enumeration.ItemSize;

/**
 * Classe que representa um Item do cardápio.
 * 
 * @author Vinicius J P Silva
 * 
 * @see ItemSize
 * 
 */
@Entity
public class Item {
	
	@Id
	@SequenceGenerator(name = "item_id", sequenceName = "item_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id")
	private Long id;
	
	@Min(value = 1L, message = "A quantidade deve ser maior ou igual a 1")
	private Integer quantity;
	
	private Double unitPrice;
	
	private Double totalCost;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private Request request;
	
	private boolean delivered;
	
	// Inicia o valor do atributo no momento da instanciação do objeto.
	{
		unitPrice = 0.0;
		totalCost = 0.0;
		delivered = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalCost() {
		totalCost = (unitPrice != null && quantity != null) ? (unitPrice * quantity) : 0.0;
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	
}
