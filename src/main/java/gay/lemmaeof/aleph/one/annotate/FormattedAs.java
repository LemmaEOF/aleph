package gay.lemmaeof.aleph.one.annotate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface FormattedAs {
	/**
	 * Mark and specify a stat as using a special formatter.
	 * @return The formatter to use - `default`, `divide_by_ten`, `distance`, or `time`
	 */
	String value();
}
