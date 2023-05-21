package it.eng.tz.puglia.autpae.utility.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Component;

@Component
public class GenericValidator
{
	@Autowired Validator validator;

	public <T extends Object> void doValidate(T obj) throws ValidationFailureException
	{
		Set<ConstraintViolation<T>> violations = validator.validate(obj);
		if (!violations.isEmpty())
		{
			StringBuilder message = new StringBuilder("Sono stati violati i seguenti vincoli di integrità: ");
			for (ConstraintViolation<T> violation : violations)
				message.append(violation.getMessage()).append("; ").append("\n");
			throw new ValidationFailureException(message.toString());
		}
	}
}
