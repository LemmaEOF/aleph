package gay.lemmaeof.aleph.one;

import gay.lemmaeof.aleph.one.AutoRegistry;
import gay.lemmaeof.aleph.one.annotate.ColorProvider;
import gay.lemmaeof.aleph.one.annotate.ConstantColor;
import gay.lemmaeof.aleph.one.annotate.Renderer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.Item;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ClientAutoRegistry extends AutoRegistry {

	public void registerBlockRenderLayers(Map<Block, RenderLayer> map) {
		Map<String, RenderLayer> renderLayers = new HashMap<>();
		renderLayers.put("cutout", RenderLayer.getCutout());
		renderLayers.put("cutout_mipped", RenderLayer.getCutoutMipped());
		renderLayers.put("translucent", RenderLayer.getTranslucent());
		renderLayers.put("tripwire", RenderLayer.getTripwire());
		eachEntrypoint("blocks",
				(meta, holder) -> eachRegisterableField(holder, Block.class, gay.lemmaeof.aleph.one.annotate.RenderLayer.class, (f, b, ann) -> {
					if (ann != null) {
						if (!renderLayers.containsKey(ann.value())) throw new RuntimeException(meta.id+":"+f.getName().toLowerCase(Locale.ROOT)+" has an unknown @RenderLayer: "+ann.value());
						map.put(b, renderLayers.get(ann.value()));
					}
				})
		);
	}

	public void registerBlockColorProviders(BlockColors colors) {
		eachEntrypoint("blocks",
				(meta, holder) -> eachRegisterableField(holder, Block.class, null, (f, b, ann) -> {
					if (b instanceof BlockColorProvider) colors.registerColorProvider((BlockColorProvider)b, b);
					ConstantColor colAnn = f.getAnnotation(ConstantColor.class);
					if (colAnn != null) colors.registerColorProvider(((blockState, blockRenderView, blockPos, i) -> colAnn.value()), b);
					ColorProvider colorProvAnn = f.getAnnotation(ColorProvider.class);
					if (colorProvAnn != null) {
						try {
							colors.registerColorProvider((BlockColorProvider)Class.forName(colorProvAnn.value()).newInstance(), b);
						} catch (Exception e1) {
							throw new RuntimeException(e1);
						}
					}
				})
		);
	}

	public void registerItemColorProviders(ItemColors colors) {
		eachEntrypoint("items",
				(meta, holder) -> eachRegisterableField(holder, Item.class, null, (f, i, ann) -> {
					if (i instanceof ItemColorProvider) colors.register((ItemColorProvider)i, i);
					ConstantColor colAnn = f.getAnnotation(ConstantColor.class);
					if (colAnn != null) colors.register((stack, tintIndex) -> colAnn.value(), i);
					ColorProvider colProvAnn = f.getAnnotation(ColorProvider.class);
					if (colProvAnn != null) {
						try {
							colors.register((ItemColorProvider)Class.forName(colProvAnn.value()).newInstance(), i);
						} catch (Exception e1) {
							throw new RuntimeException(e1);
						}
					}
				})
		);
	}

	@SuppressWarnings("unchecked")
	public void registerBlockEntityRenderers(Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> map) {
		eachEntrypoint("blockentities",
				(meta, holder) -> eachRegisterableField(holder, BlockEntityType.class, Renderer.class, (f, type, ann) -> {
					if (ann != null) {
						try {
							MethodHandle handle = MethodHandles.publicLookup().findConstructor(Class.forName(ann.value()), MethodType.methodType(void.class, BlockEntityRendererFactory.Context.class));
							map.put(type, berd -> {
								try {
									return (BlockEntityRenderer<BlockEntity>) handle.invoke(berd);
								} catch (RuntimeException | Error e) {
									throw e;
								} catch (Throwable e) {
									throw new RuntimeException(e);
								}
							});
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				})
		);
	}
}
