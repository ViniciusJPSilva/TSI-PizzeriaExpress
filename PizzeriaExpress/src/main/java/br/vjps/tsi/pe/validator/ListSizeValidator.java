package br.vjps.tsi.pe.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("listSizeValidator")
public class ListSizeValidator implements Validator {
	
	public void validate(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		Integer size = (Integer) value;
		if (size <= 0)
			throw new ValidatorException(new FacesMessage("Selecione um item antes de fechar o pedido!"));
	}
}