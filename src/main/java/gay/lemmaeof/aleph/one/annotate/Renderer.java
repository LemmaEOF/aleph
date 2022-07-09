package gay.lemmaeof.aleph.one.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Renderer {
	/**
	 * Used to mark and specify a renderer for an entity or block entity.
	 * @return The full qualified name of the color provider class to use for the annotated entity or block entity.
	 */
	String value();
}
