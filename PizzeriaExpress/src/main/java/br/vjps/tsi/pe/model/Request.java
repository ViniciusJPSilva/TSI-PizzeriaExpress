package br.vjps.tsi.pe.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa um Pedido.
 * 
 * @author Vinicius J P Silva
 * 
 * @see Item
 * 
 */
@Entity
public class Request {

	@Id
	@SequenceGenerator(name = "request_id", sequenceName = "request_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar date = Calendar.getInstance();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="request", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "client")
	private Client client;
	
	private Double value;
	
	@NotNull(message = "Forneça uma mesa")
	private Integer tableNumber;
	
	private boolean open;
	
	private boolean voucher;
	
	{
		open = true;
		voucher = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getValue() {
		if(open)
			value = items.stream().mapToDouble(Item::getTotalCost).sum();
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isVoucher() {
		return voucher;
	}

	public void setVoucher(boolean voucher) {
		this.voucher = voucher;
	}
	
}
