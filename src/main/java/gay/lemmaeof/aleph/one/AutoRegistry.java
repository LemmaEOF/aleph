package gay.lemmaeof.aleph.one;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableMap;
import gay.lemmaeof.aleph.Aleph;
import gay.lemmaeof.aleph.one.annotate.Compost;
import gay.lemmaeof.aleph.one.annotate.FormattedAs;
import gay.lemmaeof.aleph.one.annotate.Fuel;
import gay.lemmaeof.aleph.one.annotate.RegisteredAs;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class AutoRegistry {

	public static void registerBlocks() {
		autoRegister(Registry.BLOCK, "blocks", Block.class);
	}

	public static void registerBlockEntities() {
		autoRegister(Registry.BLOCK_ENTITY_TYPE, "block-entities", BlockEntityType.class);
	}

	public static void registerEnchantments() {
		autoRegister(Registry.ENCHANTMENT, "enchantments", Enchantment.class);
	}

	public static void registerEntities() {
		autoRegister(Registry.ENTITY_TYPE, "entities", EntityType.class);
	}

	public static void registerFluids() {
		autoRegister(Registry.FLUID, "fluids", Fluid.class);
	}

	public static void registerGameEvents() {
		autoRegister(Registry.GAME_EVENT, "game-events", GameEvent.class);
	}

	public static void registerItemComposts(Object2FloatMap<ItemConvertible> map) {
		eachEntrypoint("items",
				(meta, holder) -> eachRegisterableField(holder, Item.class, Compost.class, (f, i, ann) -> {
					if (ann != null) {
						map.put(i, ann.value());
					}
				})
		);
	}

	public static void registerItemFuels(Map<Item, Integer> map) {
		eachEntrypoint("items",
				(meta, holder) -> eachRegisterableField(holder, Item.class, Fuel.class, (f, i, ann) -> {
					if (ann != null) {
						map.put(i, ann.value());
					}
				})
		);
	}

	public static void registerItems() {
		autoRegister(Registry.ITEM, "items", Item.class);
	}

	public static void registerPotions() {
		autoRegister(Registry.POTION, "potions", Potion.class);
	}

	public static void registerRecipeSerializers() {
		autoRegister(Registry.RECIPE_SERIALIZER, "recipe-serializers", RecipeSerializer.class);
	}

	public static void registerRecipeTypes() {
		autoRegister(Registry.RECIPE_TYPE, "recipe-types", RecipeType.class);
	}

	public static void registerScreenHandlers() {
		autoRegister(Registry.SCREEN_HANDLER, "screen-handlers", ScreenHandlerType.class);
	}

	public static void registerSoundEvents() {
		autoRegister(Registry.SOUND_EVENT, "sound-events", SoundEvent.class);
	}

	public static void registerStats() {
		ImmutableMap<String, StatFormatter> formattersByName = ImmutableMap.of(
				"default", StatFormatter.DEFAULT,
				"divide_by_ten", StatFormatter.DIVIDE_BY_TEN,
				"distance", StatFormatter.DISTANCE,
				"time", StatFormatter.TIME
		);
		autoRegister(Registry.STAT_TYPE, "stats", StatType.class); //will anyone use this?
		autoRegister(Registry.CUSTOM_STAT, "stats", Identifier.class);
		//TODO: formatters stuff
		eachEntrypoint("stats",
				(meta, holder) -> eachRegisterableField(holder, Identifier.class, FormattedAs.class, (f, id, ann) -> {
					String formatter = "default";
					if (ann != null) {
						formatter = ann.value();
					}
					Stats.CUSTOM.getOrCreateStat(id, formattersByName.getOrDefault(formatter, StatFormatter.DEFAULT));
				})
		);
	}

	public static void registerStatusEffects() {
		autoRegister(Registry.STATUS_EFFECT, "status-effects", StatusEffect.class);
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
