package zsantana.command.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CmdData {
	
	String[] args();
	String description() default "";
	String permission() default "";
	boolean overLength() default false;
	SenderType type() default SenderType.ALL;
	
}
