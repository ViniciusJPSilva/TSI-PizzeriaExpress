package br.vjps.tsi.pe.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.vjps.tsi.pe.enumeration.EmployeeType;

/**
 * Classe que representa um Usuário.
 * 
 * @author Vinicius J P Silva
 * 
 * @see EmployeeType
 * 
 */
@Entity
public class Employee {
	
	@Id
	@SequenceGenerator(name = "employee_id", sequenceName = "employee_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id")
	private Long id;
	
	private String name;
	
	private String login;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private EmployeeType type;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}
	
}
