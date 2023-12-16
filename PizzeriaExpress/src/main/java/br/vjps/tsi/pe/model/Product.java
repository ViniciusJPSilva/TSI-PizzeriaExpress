package br.vjps.tsi.pe.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.vjps.tsi.pe.enumeration.Category;
import br.vjps.tsi.pe.enumeration.ItemSize;

/**
 * Classe que representa um Produto.
 * 
 * @author Vinicius J P Silva
 * 
 * @see ItemSize
 * 
 */
@Entity
@Table(name = "product", 
	   uniqueConstraints = {
			   @UniqueConstraint(columnNames = {"name","description", "size", "category"})
	   }
)
public class Product {

	@Id
	@SequenceGenerator(name = "product_id", sequenceName = "product_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id")
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String description;
	
	@Enumerated(EnumType.STRING)
	private ItemSize size;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private Double price;
	
	private boolean available = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ItemSize getSize() {
		return size;
	}

	public void setSize(ItemSize size) {
		this.size = size;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double value) {
		this.price = value;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
