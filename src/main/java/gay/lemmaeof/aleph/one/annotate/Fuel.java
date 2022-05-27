package gay.lemmaeof.aleph.one.annotate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Fuel {
	/**
	 * Used to mark and specify an item to be usable as furnace fuel.
	 * @return the number of ticks the item should last for as fuel in a standard furnace.
	 */
	int value();
}
