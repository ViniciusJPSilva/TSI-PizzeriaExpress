package br.vjps.tsi.pe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe que representa um Endereço.
 * 
 * @author Vinicius J P Silva
 * 
 */
@Entity
public class Address {

	@Id
	@SequenceGenerator(name = "address_id", sequenceName = "address_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id")
	private Long id;
	
	@Pattern(regexp = "\\d{2}\\.\\d{3}-\\d{3}", message = "CEP inválido")	
	private String cep;
	
	@NotEmpty(message = "Rua inválida")
	private String street;
	
	@Pattern(regexp = "\\d+", message = "Número inválido")	
	private String number;
	
	@NotEmpty(message = "Bairro inválido")
	private String neighborhood;
	
	@NotEmpty(message = "Cidade inválida")
	private String city;
	
	@NotEmpty(message = "Estado inválido")
	private String state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
}
