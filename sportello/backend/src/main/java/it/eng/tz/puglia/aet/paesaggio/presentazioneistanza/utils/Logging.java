package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Notazione utilizzabile per abilitare il logging automatico di un controller {@link Logging}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging { }
