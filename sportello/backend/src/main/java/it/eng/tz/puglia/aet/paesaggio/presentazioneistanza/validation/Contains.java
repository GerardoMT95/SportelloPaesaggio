package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.impl.ContainsValidation;

@Constraint(validatedBy=ContainsValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Contains
{
	String message() default "Valori inseriti non validi";
	String[] values() default {};
	String field() default "";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
