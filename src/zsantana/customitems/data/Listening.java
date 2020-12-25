package zsantana.customitems.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to denote a method that is intended to listen to an event
 * 
 * @author Zackary Santana
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listening {
	
	Slot slot() default Slot.NA;
}
