package gay.lemmaeof.aleph.nll.transformer;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.component.TranslatableComponent;
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
	
	@Patch.Method("render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
	public void patchRender(PatchContext ctx) {
		// we do it here for compatibility with other changes to the minecraft title screen that use
		// the realms button as an anchor (e.g. Fabric ModMenu)
		ctx.jumpToStart();
		ctx.add(
			ALOAD(0),
			INVOKESTATIC("gay/lemmaeof/aleph/nll/transformer/TitleScreenTransformer$Hooks", "fixRealmsButton", "(Lnet/minecraft/client/gui/screen/TitleScreen;)V")
		);
	}
	
	@Patch.Method("switchToRealms()V")
	@Patch.Method.AffectsControlFlow
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
	
	public static class Hooks {
		public static void fixRealmsButton(TitleScreen screen) {
			for (Element e : screen.children()) {
				if (e instanceof ButtonWidget b && b.getMessage().asComponent() instanceof TranslatableComponent trans && trans.getKey().equals("menu.online")) {
					b.setMessage(Text.translatable("menu.aleph.mods"));
				}
			}
		}
	}
	
}
