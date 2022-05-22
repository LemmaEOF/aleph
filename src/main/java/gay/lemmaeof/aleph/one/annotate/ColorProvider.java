package gay.lemmaeof.aleph.one.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface ColorProvider {
	/**
	 * Used to mark and specify a color provider for a block or item.
	 * @return the full qualified name of the color provider class to use for the annotated block or item.
	 */
	String value();
}
