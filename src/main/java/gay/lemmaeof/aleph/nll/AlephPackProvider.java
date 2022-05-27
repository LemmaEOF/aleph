package gay.lemmaeof.aleph.nll;

import gay.lemmaeof.aleph.Aleph;
import net.minecraft.resource.pack.ResourcePack;
import net.minecraft.resource.pack.ResourcePackProfile;
import net.minecraft.resource.pack.ResourcePackProvider;
import net.minecraft.resource.pack.ResourcePackSource;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.zip.ZipFile;

public class AlephPackProvider implements ResourcePackProvider {
	
	public static final ResourcePackSource SOURCE = ResourcePackSource.nameAndSource("pack.source.nilmod");
	
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
				File assets = new File(source, "assets");
				File data = new File(source, "data");
				File mcmeta = new File(source, "pack.mcmeta");
				if (!assets.isDirectory() && !data.isDirectory() && !mcmeta.isFile()) continue;
			} else {
				packSupplier = () -> new AlephJarResourcePack(mod, source);
				try (ZipFile zf = new ZipFile(source)) {
					if (zf.getEntry("assets") == null && zf.getEntry("data") == null && zf.getEntry("pack.mcmeta") == null) {
						continue;
					}
				} catch (IOException e) {
					Aleph.log.warn("Failed to check if {} contains resources", source, e);
					continue;
				}
			}
			consumer.accept(ResourcePackProfile.of(
					mod.name,
					true,
					packSupplier,
					factory,
					ResourcePackProfile.InsertionPosition.TOP,
					SOURCE
			));
			addedPacks++;
		}
		Aleph.log.info("Added {} pack{}", addedPacks, addedPacks == 1 ? "" : "s");
	}
}
