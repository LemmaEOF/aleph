package gay.lemmaeof.aleph.one.annotate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Compost {
	/**
	 * Used to mark and specify an item to be usable as compost material.
	 * @return the percentage chance that the item will increase the compost layer..
	 */
	float value();
}
