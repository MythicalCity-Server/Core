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

	/**
	 * Used to distinct if a method wants a specific slot equipped
	 * 
	 * @return The slot to be using when it is ran against checks
	 */
	Slot slot() default Slot.NA;
	
	/**
	 * Used to automate the descriptions of item abilities
	 * 
	 * @return The description of what this item ability does
	 */
	String description() default "";
	
	/**
	 * Used to automate the item ability of an item
	 * 
	 * @return The item ability words
	 */
	String itemAbility() default "";
}
