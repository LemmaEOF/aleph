package gay.lemmaeof.aleph;

import net.minecraft.resource.pack.ZipResourcePack;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.resource.pack.metadata.ResourceMetadataReader;
import net.minecraft.text.LiteralText;
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
		return (T) new PackResourceMetadata(new LiteralText(meta.description), 8);
	}
}
