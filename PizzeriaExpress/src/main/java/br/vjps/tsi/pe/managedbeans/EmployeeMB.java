package br.vjps.tsi.pe.managedbeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.vjps.tsi.pe.dao.EmployeeDAO;
import br.vjps.tsi.pe.enumeration.EmployeeType;
import br.vjps.tsi.pe.model.Employee;

@SessionScoped
@ManagedBean
public class EmployeeMB {
	private Employee employee = new Employee();

	/**
	 * Método para realizar o login do funcionário.
	 * 
	 * @return A string de navegação para a próxima página.
	 */
	public String login() {
		
		try(EmployeeDAO dao = new EmployeeDAO()) {
			employee = dao.validate(employee);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (employee == null) {
			employee = new Employee();
			return "employee-client?faces-redirect=true";
		} else
			return (employee.getType() == EmployeeType.ADMIN) ? "menu-admin?faces-redirect=true" : "menu-chef?faces-redirect=true";
	}
	
	/**
	 * Verifica se um funcionário está autenticado.
	 *
	 * @return true se o funcionário estiver autenticado, false caso contrário.
	 */
	public boolean isLogged() {
		return employee.getLogin() != null;
	}
	
	/**
	 * Realiza o logout do funcionário, redefinindo as informações de usuário para uma nova instância
	 * e redireciona para a página de login.
	 *
	 * @return A string de navegação para a página de login.
	 */
	public String logout() {
		employee = new Employee();
		return "login-client?faces-redirect=true";
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
