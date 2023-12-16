package br.vjps.tsi.pe.managedbeans;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.vjps.tsi.pe.dao.DAO;
import br.vjps.tsi.pe.dao.RequestDAO;
import br.vjps.tsi.pe.enumeration.Category;
import br.vjps.tsi.pe.model.Client;
import br.vjps.tsi.pe.model.Item;
import br.vjps.tsi.pe.model.Product;
import br.vjps.tsi.pe.model.Request;
import br.vjps.tsi.pe.system.SystemSettings;
import br.vjps.tsi.pe.utility.EmailSender;
import br.vjps.tsi.pe.utility.Utility;
import br.vjps.tsi.pe.validator.ListSizeValidator;

@ViewScoped
@ManagedBean
public class RequestMB {

	private Request request = getOpenOrNewRequest();
	private Item item = new Item();
	private Long itemId;
	
	private Calendar searchDate = Utility.getTodayCalendar();
	private List<Request> closedRequests;
	
	private List<Request> clientHistory;
	private List<Request> voucherHistory;
	
	/**
	 * Define uma mesa para o cliente com base no ID.
	 * 
	 * @param id    O ID do cliente.
	 */
	public void setTable(Long id) {
		Client client = new DAO<Client>(Client.class).findById(id);
		Integer table = request.getTableNumber();
		
		if (client != null) {
			request = new Request();
			request.setClient(client);
			request.setDate(Utility.getTodayCalendar());
			request.setTableNumber(table);
			new DAO<Request>(Request.class).add(request);
		}
	}
	
	/**
	 * Obtém uma solicitação aberta existente ou cria uma nova.
	 * 
	 * @return A solicitação aberta ou uma nova.
	 */
	private Request getOpenOrNewRequest() {
	    Request oldRequest = null;
	    
	    try(RequestDAO dao = new RequestDAO()) {
	    	FacesContext facesContext = FacesContext.getCurrentInstance();
		    Client client = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{clientMB}", ClientMB.class).getClient();
	    	oldRequest = dao.getOpenRequestForClient(client);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	
		if(oldRequest == null) 
			oldRequest = new Request();
		
		return oldRequest;
	}

	/**
	 * Adiciona um item à solicitação.
	 */
	public void addItem() {
		DAO<Product> dao = new DAO<>(Product.class);
		Product product = dao.findById(itemId);
		item.setProduct(product);
		item.setUnitPrice(product.getPrice());	
		item.setTotalCost(item.getTotalCost());
		item.setRequest(request);
		addItemToRequest(item);
		
		item = new Item();
	}
	
	/**
	 * Finaliza a solicitação.
	 * 
	 * @param component O componente associado à solicitação.
	 */
	public void finishRequest(UIComponent component) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		final String EMAIL_TITLE = "Comanda - Pizzaria Express",
				EMAIL_BODY = "Prezado(a) %s,\nSegue a comanda do pedido %d:\n\nMesa - %d\nData - %s\nTotal - R$ %.2f\nVoucher - %s\nItens: %s\n";
		
		for(Item item : request.getItems())
			if(!item.isDelivered()) {
				facesContext.addMessage(component.getClientId(facesContext), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alguns itens ainda não foram entregues.", null));
				return;
			}
		
		
        try {
            new ListSizeValidator().validate(facesContext, component, request.getItems().size());
            request.setOpen(false);
            
            System.out.println(request.isVoucher());
            if(request.isVoucher()) {
            	Double finalValue = request.getValue() - Client.VOUCHER_VALUE;
            	request.setValue((finalValue >= 0) ? finalValue : 0.0);
            	request.setVoucher(true);
            	request.getClient().setVoucher(0.0);
            } else {
            	request.getClient().setVoucher(request.getClient().getVoucher() + request.getValue());
            }
            
            new DAO<Client>(Client.class).update(request.getClient());
            new DAO<Request>(Request.class).update(request);
    		if (SystemSettings.EMAIL_MODE != SystemSettings.EMAIL_SENDING_MODE.DO_NOT_SEND) {
    			String emailMessage = String.format(EMAIL_BODY,
    					request.getClient().getName(), request.getId(), request.getTableNumber(), Utility.calendarToReadableString(request.getDate()),
    					request.getValue(), (request.isVoucher()) ? "Sim" : "Não", itemsToString(request.getItems()));
    	
    			EmailSender.sendAsync(emailMessage, EMAIL_TITLE,
    					(SystemSettings.EMAIL_MODE == SystemSettings.EMAIL_SENDING_MODE.SEND_TO_TEST)
    							? SystemSettings.TEST_EMAIL
    							: request.getClient().getEmail());
    		}
    		request = new Request();
    		facesContext.addMessage(component.getClientId(facesContext), new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido Fechado! A comanda foi enviada para o seu e-mail.", null));
        } catch (ValidatorException e) {
            facesContext.addMessage(component.getClientId(facesContext), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
	}
	
	/**
	 * Converte uma lista de itens para uma representação de string.
	 *
	 * @param items Lista de itens a ser convertida.
	 * @return Representação de string dos itens.
	 */
	private String itemsToString(List<Item> items) {
		StringBuilder builder = new StringBuilder();
		
		for(Item item : items) 
			builder.append(String.format("\n%d - %s - %d - R$ %.2f", item.getId(), 
					(item.getProduct().getCategory() == Category.DRINK) ? item.getProduct().getName() + " " + item.getProduct().getDescription() : "Pizza de " + item.getProduct().getName(),
					item.getQuantity(), item.getUnitPrice()));
		
		return builder.toString();
	}

	/**
	 * Atualiza o voucher do pedido no banco de dados.
	 * Utiliza a classe DAO para realizar a atualização.
	 */
	public void updateRequestVoucher() {
		new DAO<Request>(Request.class).update(request);
	}
	
	/**
	 * Adiciona um novo item à solicitação ou incrementa a quantidade se o item já existe.
	 * 
	 * @param newItem O novo item a ser adicionado.
	 */
	public void addItemToRequest(Item newItem) {
		List<Item> items = request.getItems();

	    // Verifica se o item já existe na lista com base no ID
	    Optional<Item> existingItem = items.stream()
	            .filter(item -> item.getProduct().getId().equals(newItem.getProduct().getId()))
	            .findFirst();
	    DAO<Item> dao = new DAO<Item>(Item.class);
	    if (existingItem.isPresent()) {
	        // Se o item já existe, incrementa a quantidade
	        Item foundItem = existingItem.get();
	        
	        if(!foundItem.isDelivered()) {
	        	foundItem.setQuantity(foundItem.getQuantity() + newItem.getQuantity());
	        	dao.update(foundItem);
	        	return;
	    	}
	    }
	    
        // Se o item não existe ou já foi entregue, adiciona à lista
        items.add(newItem);
        dao.add(newItem);
	}
	
	/**
	 * Verifica se há uma solicitação em andamento.
	 * 
	 * @return true se houver uma solicitação em andamento, caso contrário, false.
	 */
	public boolean hasRequest() {
	    return request.getTableNumber() != null;
	}

	/**
	 * Obtém o tamanho da lista de itens da solicitação.
	 * 
	 * @return O tamanho da lista de itens da solicitação.
	 */
	public int getRequestItemsSize() {
	    return request.getItems().size();
	}
	
	/**
	 * Calcula o valor total das solicitações fechadas.
	 * 
	 * @return Valor total das solicitações fechadas.
	 */
	public Double getTotalClosedRequests() {
		return getClosedRequests()
				.stream()
				.mapToDouble(Request::getValue)
				.sum();
	}
	
	/**
	 * Calcula o valor total das solicitações com voucher.
	 * 
	 * @return Valor total das solicitações .
	 */
	public Double getTotalVoucherRequests() {
		return getVoucherHistory()
				.stream()
				.mapToDouble(Request::getValue)
				.sum();
	}
	
	/**
	 * Manipula a seleção de data.
	 */
	public void handleDateSelect() {
		getClosedRequests();
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Calendar getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Calendar searchDate) {
		this.searchDate = searchDate;
	}

	public List<Request> getClosedRequests() {
		try(RequestDAO dao = new RequestDAO()) {
			this.closedRequests = dao.getClosedRequests(searchDate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return closedRequests;
	}

	public void setClosedRequests(List<Request> closedRequests) {
		this.closedRequests = closedRequests;
	}

	public List<Request> getClientHistory() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    Client client = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{clientMB}", ClientMB.class).getClient();
		clientHistory = getClosedRequests()
				.stream()
				.filter((r) -> r.getClient().getId() == client.getId())
				.toList();
		
		return clientHistory;
	}

	public void setClientHistory(List<Request> clientHistory) {
		this.clientHistory = clientHistory;
	}

	public List<Request> getVoucherHistory() {
		voucherHistory = getClientHistory()
				.stream()
				.filter((request) -> request.isVoucher())
				.toList();
		
		return voucherHistory;
	}

	public void setVoucherHistory(List<Request> voucherHistory) {
		this.voucherHistory = voucherHistory;
	}
	
}
