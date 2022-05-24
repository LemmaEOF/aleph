package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.render.entity.EntityRenderers")
public class EntityRenderersTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookEntityRenderers(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				GETSTATIC("net/minecraft/client/render/entity/EntityRenderers", "RENDERER_FACTORIES", "()Ljava/util/Map;"),
				INVOKESTATIC("gay/lemmaeof/aleph/ClientAutoRegistry", "registerEntityRenderers", "(Ljava/util/Map;)V")
		);
	}
}
