package br.vjps.tsi.pe.managedbeans;

import java.io.IOException;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.vjps.tsi.pe.dao.ClientDAO;
import br.vjps.tsi.pe.dao.DAO;
import br.vjps.tsi.pe.model.Address;
import br.vjps.tsi.pe.model.Client;
import br.vjps.tsi.pe.system.SystemSettings;
import br.vjps.tsi.pe.utility.CepResponse;
import br.vjps.tsi.pe.utility.EmailSender;

@SessionScoped
@ManagedBean
public class ClientMB {
	public static final String EMAIL_TITLE = "Código de acesso - Pizzaria Express";
	
	private Client client = new Client();
	private Double redemptionValue = Client.REDEMPTION_VALUE;

	/**
	 * Método para realizar o login do cliente.
	 * 
	 * @return A string de navegação para a próxima página.
	 */
	public String login() {
		String email = client.getEmail();
		try(ClientDAO dao = new ClientDAO()) {
			client = dao.validate(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (client == null) {
			resetClient(email);
			return "login?faces-redirect=true";
		} else
			return "menu-client?faces-redirect=true";
	}
	
	/**
	 * Envia um código de acesso para o cliente e redireciona para a página de confirmação.
	 * @return O redirecionamento para a página adequada.
	 */
	public String sendCode() {
		String email = client.getEmail();
		try(ClientDAO dao = new ClientDAO()) {
			client = dao.getByEmail(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DAO<Client> clientDAO = new DAO<Client>(Client.class);
		
		if (client == null) {
			resetClient(email);
			return "client-registration?faces-redirect=true";
		} else {
			createAccessCodeAndSendEmail(client, EMAIL_TITLE);
			clientDAO.update(client);
			resetClient();
			return "code-confirm?faces-redirect=true";
		}
	}
	
	/**
	 * Cria um novo cliente, gera um código de acesso e redireciona para a página de confirmação.
	 * @return O redirecionamento para a página adequada.
	 */
	public String create(UIComponent component) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		try(ClientDAO dao = new ClientDAO()) {
			if(dao.getByCpf(client) == null) {
				createAccessCodeAndSendEmail(client, EMAIL_TITLE);
				new DAO<Client>(Client.class).add(client);
				resetClient();
				return "code-confirm?faces-redirect=true";
			}
		} catch (IOException e) {
		}
		
		facesContext.addMessage(component.getClientId(facesContext), new FacesMessage(FacesMessage.SEVERITY_ERROR, "O CPF já está cadastrado!", null));
		return null;
	}
	
	/**
	 * Atualiza os dados do cliente.
	 * @return O redirecionamento para a página adequada.
	 */
	public String update() {
		new DAO<Client>(Client.class).update(client);
		return "menu-client?faces-redirect=true";
	}
	
	/**
	 * Realiza a busca do CEP utilizando a API ViaCEP.
	 * 
	 * Obtém informações de endereço com base no CEP fornecido pelo cliente.
	 * As informações de endereço são então atualizadas no objeto cliente.
	 */
	public void searchCEP() {
		System.out.println(client.getAddress().getCep());
	    String url = String.format("https://viacep.com.br/ws/%s/json/", client.getAddress().getCep().replaceAll("\\D", ""));

	    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
            	 if (response.getStatusLine().getStatusCode() == 200) {
                     ObjectMapper objectMapper = new ObjectMapper();
                     CepResponse cepResponse = objectMapper.readValue(response.getEntity().getContent(), CepResponse.class);

                     client.getAddress().setStreet(cepResponse.getLogradouro());
                     client.getAddress().setNeighborhood(cepResponse.getBairro());
                     client.getAddress().setCity(cepResponse.getLocalidade());
                     client.getAddress().setState(cepResponse.getUf());
                 } else {
                	 client.setAddress(new Address());
                 }
            }

        } catch (Exception e) {
        	client.setAddress(new Address());
        }
	}

	
	/**
	 * Verifica se um cliente está autenticado.
	 *
	 * @return true se o cliente estiver autenticado, false caso contrário.
	 */
	public boolean isLogged() {
		return client.getCpf() != null;
	}
	
	/**
	 * Realiza o logout do cliente, redefinindo as informações de usuário para uma nova instância
	 * e redireciona para a página de login.
	 *
	 * @return A string de navegação para a página de login.
	 */
	public String logout() {
		client = new Client();
		return "login-client?faces-redirect=true";
	}
	
	/**
	 * Gera um token de confirmação, associa-o ao cliente e envia um e-mail de confirmação.
	 *
	 * @param client      O cliente para o qual o token está sendo gerado e o e-mail está sendo enviado.
	 * @param emailTitle  O título do e-mail de confirmação.
	 */
	private void createAccessCodeAndSendEmail(Client client, String emailTitle) {
		client.setLoginCode(generateRandomCode(Client.CODE_LENGTH));
		System.out.println(client.getLoginCode());

		if (SystemSettings.EMAIL_MODE != SystemSettings.EMAIL_SENDING_MODE.DO_NOT_SEND) {
			String emailMessage = String.format(
					"Prezado(a) %s,\nO seu código de login é: %s\n",
					client.getName(), client.getLoginCode());
	
			EmailSender.sendAsync(emailMessage, emailTitle,
					(SystemSettings.EMAIL_MODE == SystemSettings.EMAIL_SENDING_MODE.SEND_TO_TEST)
							? SystemSettings.TEST_EMAIL
							: client.getEmail());
		}
	}
	
	/**
	 * Gera um código aleatório com o número especificado de dígitos.
	 *
	 * @param digits O número de dígitos do código gerado.
	 * @return Uma string representando o código aleatório.
	 */
	private static String generateRandomCode(int digits) {
	    Random random = new Random();
	    StringBuilder code = new StringBuilder();

	    for (int i = 0; i < digits; i++)
	    	code.append(random.nextInt(10));

	    return code.toString();
	}
	
	/**
	 * Reseta os dados do cliente, mantendo o e-mail se fornecido.
	 * @param email O e-mail a ser mantido, ou null para resetar completamente.
	 */
	private void resetClient(String email) {
	    client = new Client();
	    if(email != null)
	        client.setEmail(email);
	}

	/**
	 * Reseta completamente os dados do cliente.
	 */
	private void resetClient() {
    	resetClient((client != null) ? (client.getEmail()) : null );
	}

	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getRedemptionValue() {
		return redemptionValue;
	}

	public void setRedemptionValue(Double redemptionValue) {
		this.redemptionValue = redemptionValue;
	}
	
}
