package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.GetFieldsUtils;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.validation.RequiredDependsOn.Condition;
import it.eng.tz.puglia.util.string.StringUtil;

public class RequiredDependsOnValidation implements ConstraintValidator<RequiredDependsOn, Object>
{
	private String field;
	private String dependsOn;
	private String dependsOnSubField;
	private String dependsOnValue;
	private Condition condition;
	
	public void initialize(RequiredDependsOn annotation) 
	{
        this.field = annotation.field();
        this.dependsOn = annotation.dependsOn();
        this.dependsOnSubField = annotation.dependsOnSubField();
        this.dependsOnValue = annotation.dependsOnValue();
        this.condition = annotation.condition();
    }
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		Boolean valid;
		Object fieldContent = GetFieldsUtils.getField(value, field);
		Object dependsOnContent = GetFieldsUtils.getField(value, dependsOn);
		if(StringUtil.isNotEmpty(dependsOnSubField))
			dependsOnContent = GetFieldsUtils.getField(dependsOnContent, dependsOnSubField);
		
		if(condition.equals(Condition.find))
		{
			if(StringUtil.isEmpty(dependsOnValue))
				valid = (dependsOnContent == null || StringUtil.isEmpty(dependsOnContent.toString())) || (fieldContent != null && StringUtil.isNotEmpty(fieldContent.toString()));
			else
				valid = (dependsOnContent == null || !dependsOnContent.toString().equals(dependsOnValue)) || (fieldContent != null && StringUtil.isNotEmpty(fieldContent.toString()));
		}
		else
		{
			if(StringUtil.isEmpty(dependsOnValue))
				valid = (dependsOnContent != null && StringUtil.isNotEmpty(dependsOnContent.toString())) || (fieldContent != null && StringUtil.isNotEmpty(fieldContent.toString()));
			else
				valid = (dependsOnContent == null || dependsOnContent.toString().equals(dependsOnValue)) || (fieldContent != null && StringUtil.isNotEmpty(fieldContent.toString()));
		}
		return valid;
	}

}
