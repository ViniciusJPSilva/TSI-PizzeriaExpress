package br.vjps.tsi.pe.listener;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.vjps.tsi.pe.managedbeans.ClientMB;
import br.vjps.tsi.pe.managedbeans.EmployeeMB;

/**
 * Implementação de PhaseListener para autorização de acesso a páginas no contexto do JSF.
 * 
 * @author Vinícius J P Silva
 */
public class Authorizer implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	private static final List<String> AUTHORIZED_URIS = Arrays.asList(
						"/login-client.xhtml",
						"/login-employee.xhtml",
						"/client-registration.xhtml",
						"/code-confirm.xhtml"
					);

	/**
     * Executado após a fase de RESTORE_VIEW.
     *
     * @param event O evento da fase.
     */
	@Override
	public void afterPhase(PhaseEvent event) {		
		FacesContext context = event.getFacesContext();
		
		if (AUTHORIZED_URIS.contains(context.getViewRoot().getViewId()))
			return;
		
		// Tratando os dois casos de login: User e Client. 
		Object managedBean = context.getApplication().
				evaluateExpressionGet(context, "#{employeeMB}", Object.class);
		
		// Caso seja um usuário
		if (managedBean instanceof EmployeeMB)
			if(!((EmployeeMB) managedBean).isLogged()) {
				managedBean = context.getApplication().
				evaluateExpressionGet(context, "#{clientMB}", Object.class);
		
				if (managedBean instanceof ClientMB) 
					if(!((ClientMB) managedBean).isLogged())
						handleRedirect(context);
			}
	}

	/**
     * Executado antes da fase de RESTORE_VIEW.
     *
     * @param event O evento da fase.
     */
    @Override
    public void beforePhase(PhaseEvent event) {

    }

    /**
     * Obtém a fase à qual este PhaseListener está associado (RESTORE_VIEW).
     *
     * @return A fase de RESTORE_VIEW.
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    /**
     * Manipula o redirecionamento para a página de login no contexto do JSF.
     *
     * @param context O contexto do FacesContext.
     */
    private void handleRedirect(FacesContext context) {
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "login-client?faces-redirect=true");
		context.renderResponse();
    }
}
