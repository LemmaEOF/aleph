package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.world.event.GameEvent")
public class GameEventTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookGameEvents(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerGameEvents", "()V")
		);
		ctx.addFireEntrypoint("game-events");
	}
}