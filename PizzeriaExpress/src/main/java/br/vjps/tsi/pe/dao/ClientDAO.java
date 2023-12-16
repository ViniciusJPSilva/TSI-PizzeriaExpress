package br.vjps.tsi.pe.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.vjps.tsi.pe.model.Client;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos clientes no
 * banco de dados. Implementa a interface Closeable para garantir o fechamento
 * adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class ClientDAO implements Closeable {
	private EntityManager manager;

	/**
	 * Construtor padrão que inicializa o EntityManager usando a classe utilitária
	 * JPAUtil.
	 */
	public ClientDAO() {
		manager = new JPAUtil().getEntityManager();
	}

	/**
	 * Valida as credenciais de um cliente para fins de autenticação.
	 *
	 * @param client O cliente cujas credenciais estão sendo validadas.
	 * @return O cliente autenticado ou null se as credenciais forem inválidas.
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public Client validate(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Client u WHERE u.email = :email AND u.loginCode = :code", Client.class);
		query.setParameter("email", client.getEmail());
		query.setParameter("code", client.getLoginCode());
		
		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}
	
	/**
	 * Obtém um cliente pelo endereço de e-mail.
	 *
	 * @param client O objeto cliente contendo o e-mail a ser pesquisado.
	 * @return O cliente com o e-mail especificado, ou null se não encontrado.
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public Client getByEmail(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Client u WHERE u.email = :email", Client.class);
		query.setParameter("email", client.getEmail());
		
		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
	}
	
	/**
	 * Obtém um cliente pelo CPF.
	 *
	 * @param client O objeto cliente contendo o CPF a ser pesquisado.
	 * @return O cliente com o CPF especificado, ou null se não encontrado.
	 * @throws IllegalArgumentException Se o cliente fornecido for nulo.
	 */
	public Client getByCpf(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Cliente não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Client u WHERE u.cpf = :cpf", Client.class);
		query.setParameter("cpf", client.getCpf());
		
		@SuppressWarnings("unchecked")
		List<Client> clients = query.getResultList();

		return clients.isEmpty() ? null : clients.get(0);
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
