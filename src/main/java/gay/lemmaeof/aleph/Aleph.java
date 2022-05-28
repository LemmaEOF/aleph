package gay.lemmaeof.aleph;

import gay.lemmaeof.aleph.nll.transformer.ClientBrandRetrieverTransformer;
import gay.lemmaeof.aleph.nll.transformer.PackScreenTransformer;
import gay.lemmaeof.aleph.nll.transformer.ResourcePackManagerTransformer;
import gay.lemmaeof.aleph.nll.transformer.TitleScreenTransformer;
import gay.lemmaeof.aleph.one.transformer.*;
import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

public class Aleph implements Runnable {

	public static final NilLogger log = NilLogger.get("Aleph");

	@Override
	public void run() {
		//TODO: eventually Quilt (hashed)
		if (tryRemap("net.fabricmc.loader.api.FabricLoader", "net.fabricmc.intermediary-1.18.2")) {
			log.info("Loading Aleph alongside Fabric or Quilt beta!");
		} else if (tryRemap("cpw.mods.modlauncher.Launcher", "nilgradle.dynamic.frankenmapping-com.mojang.launcher.client-a661c6a55a0600bd391bdbbd6827654c05b2109cxde.oceanlabs.mcp.mcp_config-1.18.2-v2-")) {
			log.info("Loading Aleph alongside Forge!");
		}

		//Aleph:Null transformers
		ClassTransformer.register(new ResourcePackManagerTransformer());
		ClassTransformer.register(new TitleScreenTransformer());
		ClassTransformer.register(new ClientBrandRetrieverTransformer());
		ClassTransformer.register(new PackScreenTransformer());

		//Aleph:One transformers (oh god)
		if (shouldTransform("blocks")) {
			ClassTransformer.register(new BlockColorsTransformer()); //block colors
			ClassTransformer.register(new BlocksTransformer()); //blocks themselves
			ClassTransformer.register(new BlockRenderLayersTransformer()); //block render layers
		}
		if (shouldTransform("block-entities")) {
			ClassTransformer.register(new BlockEntityRendererFactoriesTransformer()); //block entity renderers
			ClassTransformer.register(new BlockEntityTypeTransformer()); //block entity types
		}
		if (shouldTransform("enchantments")) ClassTransformer.register(new EnchantmentsTransformer());
		if (shouldTransform("entities")) {
			ClassTransformer.register(new EntityRenderersTransformer()); //entity renderers
			ClassTransformer.register(new EntityTypeTransformer()); //entity types
		}
		if (shouldTransform("fluids")) {
			ClassTransformer.register(new FluidRenderLayersTransformer()); //fluid render layers
			ClassTransformer.register(new FluidsTransformer()); //fluids themselves
		}
		if (shouldTransform("items")) {
			ClassTransformer.register(new AbstractFurnaceBlockEntityTransformer()); //fuel times
			ClassTransformer.register(new ComposterBlockTransformer()); //composting chances
			ClassTransformer.register(new ItemColorsTransformer()); //item colors
			ClassTransformer.register(new ItemsTransformer()); //items themselves
		}
		if (shouldTransform("potions")) ClassTransformer.register(new PotionsTransformer());
		if (shouldTransform("recipe-serializers")) ClassTransformer.register(new RecipeSerializerTransformer());
		if (shouldTransform("recipe-types")) ClassTransformer.register(new RecipeTypeTransformer());
		if (shouldTransform("screen-handlers")) {
			ClassTransformer.register(new HandledScreensTransformer()); //client-side handled screens
			ClassTransformer.register(new ScreenHandlerTypeTransformer()); //screen handlers
		}
		if (shouldTransform("sound-events")) ClassTransformer.register(new SoundEventsTransformer());
		if (shouldTransform("stats")) ClassTransformer.register(new StatsTransformer());
		if (shouldTransform("status-effects")) ClassTransformer.register(new StatusEffectsTransformer());
	}

	private boolean tryRemap(String smokeTest, String target) {
		try {
			Class.forName(smokeTest, false, Aleph.class.getClassLoader());
			ModRemapper.setTargetMapping(target);
			return true;
		} catch (ClassNotFoundException t) {
			return false;
		}
	}

	private boolean shouldTransform(String entrypointName) {
		for (NilMetadata meta : NilModList.getAll()) {
			if (meta.entrypoints.containsKey(entrypointName)) return true;
		}
		return false;
	}
}
