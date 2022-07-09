package gay.lemmaeof.aleph.one.annotate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface ConstantColor {
	/**
	 * Used to mark and specify a color provider for a block or item with a constant color.
	 * @return the color to use for the annotated block or item as a (likely hexadecimal) integer.
	 */
	int value();
}
