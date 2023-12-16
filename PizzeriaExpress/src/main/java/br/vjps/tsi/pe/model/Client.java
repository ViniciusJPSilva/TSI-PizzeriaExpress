package br.vjps.tsi.pe.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Classe que representa um Cliente.
 * 
 * @author Vinicius J P Silva
 * 
 * @see Address
 * 
 */
@Entity
@Table(name = Client.TABLE_NAME)
public class Client {

	public static final String TABLE_NAME = "client", EMAIL_COLUMN_NAME = "email", PASSWD_COLUMN_NAME = "password";
	
	public static final int CODE_LENGTH = 6;

	public static final double VOUCHER_VALUE = 100,
			REDEMPTION_VALUE = 600;

	@Id
	@SequenceGenerator(name = "client_id", sequenceName = "client_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id")
	private Long id;

	@Column(unique = true)
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")	
	private String cpf;

	@NotBlank(message = "Nome inválido")
	private String name;

	@Column(name = EMAIL_COLUMN_NAME, unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "E-mail inválido")	
	private String email;
	
	@NotBlank(message = "Telefone inválido")
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address = new Address();
	
	@Pattern(regexp = "\\d{6}", message = "Código inválido")	
	private String loginCode;
	
	private Double voucher;
	
	{
		voucher = 0.0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}

}
