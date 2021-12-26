package br.edu.ifpb.dac.livraria.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "senhaValidator")
public class SenhaValidator implements Validator<String>{

	public void validate(FacesContext context, UIComponent component, String senha) throws ValidatorException {
		String numRegex   = ".*[0-9].*";
		String alphaRegex = ".*[a-zA-Z].*";
		
		if (!(senha.matches(numRegex) && senha.matches(alphaRegex))) {
	        throw new ValidatorException(new FacesMessage("Formato inválido de senha (Deve conter pelo menos uma letra e um número)."));
	    }	
		
	}

}
