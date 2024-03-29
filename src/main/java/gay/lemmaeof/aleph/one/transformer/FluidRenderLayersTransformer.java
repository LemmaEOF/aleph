package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.render.RenderLayers")
public class FluidRenderLayersTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookRenderLayers(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				GETSTATIC("net/minecraft/client/render/RenderLayers", "FLUIDS", "Ljava/util/Map;"),
				INVOKESTATIC("gay/lemmaeof/aleph/one/ClientAutoRegistry", "registerFluidRenderLayers", "(Ljava/util/Map;)V")
		);
	}
}
