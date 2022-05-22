package gay.lemmaeof.aleph.one.annotate;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface RenderLayer {
	/**
	 * Used to mark and specify a block to use a different render layer.
	 * @return The name of the render layer to use - `cutout`, `cutout_mipped`, `translucent`, or `tripwire`.
	 */
	String value();
}