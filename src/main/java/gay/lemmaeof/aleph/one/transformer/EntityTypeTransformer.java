package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.entity.EntityType")
public class EntityTypeTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookEntityTypes(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerEntities", "()V")
		);
		ctx.addFireEntrypoint("entitites");
	}
}
