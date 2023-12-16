package br.vjps.tsi.pe.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.vjps.tsi.pe.model.Employee;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos usuários no
 * banco de dados. Implementa a interface Closeable para garantir o fechamento
 * adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class EmployeeDAO implements Closeable {
	private EntityManager manager;

	/**
	 * Construtor padrão que inicializa o EntityManager usando a classe utilitária
	 * JPAUtil.
	 */
	public EmployeeDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
	 * Valida as credenciais de um usuário para fins de autenticação.
	 *
	 * @param employee O funcionário cujas credenciais estão sendo validadas.
	 * 
	 * @return O funcionário autenticado ou null se as credenciais forem inválidas.
	 * 
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public Employee validate(Employee employee) {
		if (employee == null)
			throw new IllegalArgumentException("Funcionário não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Employee u WHERE u.login = :login AND u.password = :password", Employee.class);
		query.setParameter("login", employee.getLogin());
		query.setParameter("password", employee.getPassword());
		
		@SuppressWarnings("unchecked")
		List<Employee> users = query.getResultList();

		return users.isEmpty() ? null : users.get(0);
	}
	
	/**
	 * Obtém um funcionário com base no login.
	 * @param login O login do funcionário a ser buscado.
	 * @return O funcionário encontrado ou null se não encontrado.
	 * @throws IllegalArgumentException Se o login fornecido for nulo.
	 */
	public Employee getByLogin(String login) {
		if (login == null)
			throw new IllegalArgumentException("Login não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Employee u WHERE u.login = :login", Employee.class);
		query.setParameter("login", login);
		
		@SuppressWarnings("unchecked")
		List<Employee> users = query.getResultList();

		return users.isEmpty() ? null : users.get(0);
	}
	
	
	/**
     * Fecha o EntityManager quando este objeto ClientDAO é fechado.
     *
     * @throws IOException Se ocorrer um erro ao fechar o EntityManager.
     */
	@Override
	public void close() throws IOException {
		manager.close();
	}
}
