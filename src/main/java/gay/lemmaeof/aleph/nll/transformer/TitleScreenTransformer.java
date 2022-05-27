package gay.lemmaeof.aleph.nll.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.gui.screen.TitleScreen")
public class TitleScreenTransformer extends MiniTransformer {
	
	@Patch.Method("areRealmsNotificationsEnabled()Z")
	@Patch.Method.AffectsControlFlow
	public void patchAreRealmsNotificationsEnabled(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.add(
			ICONST_0(),
			IRETURN()
		);
	}
	
	@Patch.Method("initWidgetsNormal(II)V")
	public void patchInitWidgetsNormal(PatchContext ctx) {
		ctx.search(LDC("menu.online")).jumpAfter();
		ctx.add(
			POP(),
			LDC("menu.aleph.mods")
		);
	}
	
	@Patch.Method("switchToRealms()V")
	public void patchSwitchToRealms(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.add(
			ALOAD(0),
			GETFIELD("net/minecraft/client/gui/screen/TitleScreen", "client", "Lnet/minecraft/client/MinecraftClient;"),
			NEW("gay/lemmaeof/aleph/nll/AlephModsScreen"),
			DUP(),
			ALOAD(0),
			INVOKESPECIAL("gay/lemmaeof/aleph/nll/AlephModsScreen", "<init>", "(Lnet/minecraft/client/gui/screen/Screen;)V"),
			INVOKEVIRTUAL("net/minecraft/client/MinecraftClient", "setScreen", "(Lnet/minecraft/client/gui/screen/Screen;)V"),
			RETURN()
		);
	}
	
}
