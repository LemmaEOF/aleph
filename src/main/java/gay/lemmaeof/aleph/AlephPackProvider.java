package gay.lemmaeof.aleph;

import net.minecraft.resource.pack.ResourcePack;
import net.minecraft.resource.pack.ResourcePackProfile;
import net.minecraft.resource.pack.ResourcePackProvider;
import net.minecraft.resource.pack.ResourcePackSource;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AlephPackProvider implements ResourcePackProvider {
	public static ResourcePackProvider[] appendNilPacks(ResourcePackProvider[] oldPacks) {
		var result = Arrays.copyOf(oldPacks, oldPacks.length + 1);
		result[result.length - 1] = new AlephPackProvider();
		return result;
	}

	@Override
	public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
		int addedPacks = 0;
		for (NilMetadata mod : NilModList.getAll()) {
			var source = mod.source;
			Supplier<ResourcePack> packSupplier;
			if (source.isDirectory()) {
				packSupplier = () -> new AlephDirectoryResourcePack(mod, source);
			} else {
				packSupplier = () -> new AlephJarResourcePack(mod, source);
			}
			consumer.accept(ResourcePackProfile.of(
					mod.name,
					true,
					packSupplier,
					factory,
					ResourcePackProfile.InsertionPosition.TOP,
					ResourcePackSource.nameAndSource("pack.source.nilmod"))
			);
		}
		Aleph.log.info("Added " + addedPacks + " packs");
	}
}
