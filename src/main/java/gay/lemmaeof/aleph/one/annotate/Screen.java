package gay.lemmaeof.aleph.one.annotate;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Screen {
	/**
	 * Used to mark and specify a handled screen for a screen handler.
	 * @return The class for the handled screen associated with the screen handler.
	 */
	Class<? extends HandledScreen<?>> value();
}