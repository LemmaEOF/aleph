package gay.lemmaeof.aleph.nll.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.PatchContext.SearchResult;
import nilloader.api.lib.mini.annotation.Patch;
import nilloader.api.lib.mini.exception.PointerOutOfBoundsException;

@Patch.Class("net.minecraft.client.ClientBrandRetriever")
public class ClientBrandRetrieverTransformer extends MiniTransformer {

	@Patch.Method("getClientModName()Ljava/lang/String;")
	@Patch.Method.Optional
	public void patchGetClientModName(PatchContext ctx) {
		while (true) {
			SearchResult res = ctx.search(ARETURN());
			if (!res.isSuccessful()) return;
			res.jumpBefore();
			ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/nll/transformer/ClientBrandRetrieverTransformer$Hooks", "modifyBrand", "(Ljava/lang/String;)Ljava/lang/String;")
			);
			try {
				res.jumpAfter();
			} catch (PointerOutOfBoundsException e) {
				return;
			}
		}
	}
	
	@Patch.Method("m_129629_()Ljava/lang/String;") // Forge weirdness - this method is supposed to be deobf
	@Patch.Method.Optional
	public void patchGetClientModNameForge(PatchContext ctx) {
		patchGetClientModName(ctx);
	}
	
	
	public static class Hooks {
		public static String modifyBrand(String brand) {
			if (brand.contains("nil")) return brand;
			if ("vanilla".equals(brand)) return "nil";
			return brand+",nil";
		}
	}
	
}
