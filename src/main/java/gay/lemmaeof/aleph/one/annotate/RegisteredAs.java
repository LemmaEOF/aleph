package gay.lemmaeof.aleph.one.annotate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface RegisteredAs {
	/**
	 * Used to mark an object to be registered with a separate identifier than its field name
	 * @return The identifier to register as, in a stringified form.
	 */
	String value();
}
