package br.vjps.tsi.pe.dao;

import java.io.Closeable;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import br.vjps.tsi.pe.model.Client;
import br.vjps.tsi.pe.model.Request;

/**
 * Objeto de Acesso a Dados (DAO) para operações relacionadas aos pedidos no
 * banco de dados. Implementa a interface Closeable para garantir o fechamento
 * adequado do EntityManager.
 * 
 * @author Vinicius J P Silva
 */
public class RequestDAO implements Closeable {
	private EntityManager manager;

	/**
	 * Construtor padrão que inicializa o EntityManager usando a classe utilitária
	 * JPAUtil.
	 */
	public RequestDAO() {
		manager = new JPAUtil().getEntityManager();
	}
	
	/**
	 * Obtém o pedido corrente, em aberto, do cliente informado.
	 * 
	 * @param client O cliente cujo pedido será buscado.
	 * @return O pedido encontrado ou null se não encontrado.
	 * 
	 * @throws IllegalArgumentException Se o client fornecido for nulo.
	 */
	public Request getOpenRequestForClient(Client client) {
		if (client == null)
			throw new IllegalArgumentException("Login não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Request u WHERE u.client = :client AND u.open = TRUE", Request.class);
		query.setParameter("client", client);
		
		@SuppressWarnings("unchecked")
		List<Request> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * Obtém a lista de pedidos fechadas.
	 * 
	 * @return Lista de pedidos fechadas.
	 */
	public List<Request> getClosedRequests(Calendar date) {
		if(date == null)
			throw new IllegalArgumentException("Data não pode ser nulo!");
		
		Query query = manager.createQuery("SELECT u FROM Request u WHERE u.open = FALSE AND u.date = :date", Request.class);
		query.setParameter("date", date, TemporalType.DATE);
		
		@SuppressWarnings("unchecked")
		List<Request> list = query.getResultList();

		return list;
	}
	
	/**
	 * Obtém a lista de pedidos em aberto.
	 * 
	 * @return Lista de pedidos em aberto.
	 */
	public List<Request> getOpenedRequests() {
		Query query = manager.createQuery("SELECT u FROM Request u WHERE u.open = TRUE", Request.class);
		
		@SuppressWarnings("unchecked")
		List<Request> list = query.getResultList();

		return list;
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
