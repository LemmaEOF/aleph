package gay.lemmaeof.aleph.nll;

import net.minecraft.resource.pack.ZipResourcePack;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.resource.pack.metadata.PackResourceMetadataReader;
import net.minecraft.resource.pack.metadata.ResourceMetadataReader;
import net.minecraft.text.Text;
import nilloader.api.NilMetadata;

import java.io.File;
import java.io.IOException;

public class AlephJarResourcePack extends ZipResourcePack {
	private final NilMetadata meta;

	public AlephJarResourcePack(NilMetadata meta, File file) {
		super(file);
		this.meta = meta;
	}

	@Override
	public <T> T parseMetadata(ResourceMetadataReader<T> reader) throws IOException {
		if (containsFile("pack.mcmeta")) return super.parseMetadata(reader);
		if (reader instanceof PackResourceMetadataReader) {
			//noinspection unchecked
			return (T) new AlephPackProvider.NilResourceMetadata(Text.literal(meta.description));
		}
		return null;
	}
}
