package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.impl.RequiredDependsOnValidation;

@Constraint(validatedBy = RequiredDependsOnValidation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredDependsOn
{
	String message() default "Valori inseriti non validi";
	String field();
	String dependsOn();
	String dependsOnSubField() default "";
	String dependsOnValue() default "";
	Condition condition() default Condition.find;
	
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface List { RequiredDependsOn[] value(); }
	
	public enum Condition
	{
		find,
		notFound
	}
}
