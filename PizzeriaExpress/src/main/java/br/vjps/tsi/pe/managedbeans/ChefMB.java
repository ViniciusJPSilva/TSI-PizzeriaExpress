package br.vjps.tsi.pe.managedbeans;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.vjps.tsi.pe.dao.DAO;
import br.vjps.tsi.pe.dao.RequestDAO;
import br.vjps.tsi.pe.model.Item;
import br.vjps.tsi.pe.model.Request;

@SessionScoped
@ManagedBean
public class ChefMB {
	
	private Request chefRequest;
	private List<Request> openRequests;
	
	/**
	 * Define o pedido que está sendo servido pelo chef e redireciona para a página de serviço.
	 *
	 * @param request Pedido que está sendo servido.
	 * @return Redireciona para a página de serviço.
	 */
	public String serve(Request request) {
		setChefRequest(request);
		return"serve-request?faces-redirect=true";
	}
	
	/**
	 * Atualiza o estado do pedido.
	 * Imprime no console o estado de entrega de cada item do pedido do chef.
	 */
	public void updateRequest() {
		for(Item item : chefRequest.getItems())
			System.out.println(item.isDelivered());
	}
	
	/**
	 * Atualiza o estado de entrega de um item no banco de dados.
	 *
	 * @param newItem Novo estado do item a ser atualizado.
	 */
	public void updateItemDeliveredState(Item newItem) {
		new DAO<Item>(Item.class).update(newItem);
	}
	
	public Request getChefRequest() {
		return chefRequest;
	}

	public void setChefRequest(Request chefRequest) {
		this.chefRequest = chefRequest;
	}

	public List<Request> getOpenRequests() {
		try(RequestDAO dao = new RequestDAO()) {
			this.openRequests = dao.getOpenedRequests();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return openRequests;
	}

	public void setOpenRequests(List<Request> openRequests) {
		this.openRequests = openRequests;
	}
	
}
