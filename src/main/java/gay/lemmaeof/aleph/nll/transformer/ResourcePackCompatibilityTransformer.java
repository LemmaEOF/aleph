package gay.lemmaeof.aleph.nll.transformer;

import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.resource.pack.ResourcePackCompatibility")
public class ResourcePackCompatibilityTransformer extends MiniTransformer {
	@Patch.Method("from(Lnet/minecraft/resource/pack/metadata/PackResourceMetadata;Lnet/minecraft/resource/ResourceType;)Lnet/minecraft/resource/pack/ResourcePackCompatibility;")
	@Patch.Method.AffectsControlFlow
	public void patchFrom(PatchContext ctx) {
		// If it's our metadata, just assume it's compatible
		LabelNode Lnotnil = new LabelNode();
		ctx.jumpToStart();
		ctx.add(
				ALOAD(0),
				INSTANCEOF("gay/lemmaeof/aleph/nll/AlephPackProvider$NilResourceMetadata"),
				IFNE(Lnotnil),
				GETSTATIC("net/minecraft/resource/pack/ResourcePackCompatibility", "COMPATIBLE", "Lnet/minecraft/resource/pack/ResourcePackCompatibility;"),
				ARETURN(),
				Lnotnil
		);
	}
}
