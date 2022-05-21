package gay.lemmaeof.aleph;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import gay.lemmaeof.aleph.annotate.RegisteredAs;
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

	@SuppressWarnings("unchecked")
	private static <T> void autoRegister(Registry<T> registry, String entrypointName, Class<? super T> type) {
		for (NilMetadata meta : NilModList.getAll()) {
			String className = meta.entrypoints.get(entrypointName);
			if (meta.entrypoints.containsKey(entrypointName)) {
				try {
					Class<?> clazz = Class.forName(className);
					if (!Runnable.class.isAssignableFrom(clazz)) {
						Aleph.log.error("Error in auto-register process: nilmod {} gives class {} for {}, but that's not Runnable!", meta.id, className, entrypointName);
					}
					eachRegisterableField(clazz, type, RegisteredAs.class, (f, v, ann) -> {
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
					});
				} catch (Exception e) {
					Aleph.log.error("Error in auto-register process: nilmod {} gives class {} for {}, but encountered an error!", meta.id, className, entrypointName, e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T, A extends Annotation> void eachRegisterableField(Class<?> holder, Class<T> type, Class<A> anno, TriConsumer<Field, T, A> cb) {
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
