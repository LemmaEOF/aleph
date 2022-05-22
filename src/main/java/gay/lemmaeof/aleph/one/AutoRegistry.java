package gay.lemmaeof.aleph.one;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.function.BiConsumer;

import gay.lemmaeof.aleph.Aleph;
import gay.lemmaeof.aleph.one.annotate.RegisteredAs;
import net.minecraft.block.entity.BlockEntityType;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class AutoRegistry {

	public static void registerBlocks() {
		autoRegister(Registry.BLOCK, "blocks", Block.class);
	}

	public static void registerItems() {
		autoRegister(Registry.ITEM, "items", Item.class);
	}

	public static void registerBlockEntities() {
		autoRegister(Registry.BLOCK_ENTITY_TYPE, "blockentities", BlockEntityType.class);
	}

	@SuppressWarnings("unchecked")
	protected static <T> void autoRegister(Registry<T> registry, String entrypointName, Class<? super T> type) {
		eachEntrypoint(entrypointName,
				(meta, holder) -> eachRegisterableField(holder, type, RegisteredAs.class, (f, v, ann) -> {
					String id;
					if (ann != null) {
						if (ann.value().contains(":")) {
							id = ann.value();
						} else {
							id = meta.id+":"+ann.value();
						}
					} else {
						id = meta.id+":"+f.getName().toLowerCase(Locale.ROOT);
					}
					Registry.register(registry, id, (T)v);
				})
		);
	}

	protected static void eachEntrypoint(String entrypointName, BiConsumer<NilMetadata, Class<?>> cb) {
		for (NilMetadata meta : NilModList.getAll()) {
			String className = meta.entrypoints.get(entrypointName);
			if (className != null) {
				try {
					Class<?> holder = Class.forName(className);
					if (!Runnable.class.isAssignableFrom(holder)) {
						Aleph.log.error("Error in auto-register process: nilmod {} gives class {} for {}, but that's not Runnable!", meta.id, className, entrypointName);
						Aleph.log.error("This is a sanity-check due to some of the ways that NilLoader works. Apologies");
						continue;
					}
					cb.accept(meta, holder);
				} catch (Exception e) {
					Aleph.log.error("Error in auto-register process: nilmod {} gives class {} for {}, but encountered an error!", meta.id, className, entrypointName, e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T, A extends Annotation> void eachRegisterableField(Class<?> holder, Class<T> type, Class<A> anno, TriConsumer<Field, T, A> cb) {
		for (Field f : holder.getDeclaredFields()) {
			if (type.isAssignableFrom(f.getType()) && Modifier.isStatic(f.getModifiers()) && !Modifier.isTransient(f.getModifiers())) {
				try {
					f.setAccessible(true);
					cb.accept(f, (T)f.get(null), anno == null ? null : f.getAnnotation(anno));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
