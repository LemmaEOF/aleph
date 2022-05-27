package gay.lemmaeof.aleph.nll;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import gay.lemmaeof.aleph.Aleph;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import nilloader.api.NilMetadata;
import nilloader.api.NilModList;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget.Entry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Language;

public class AlephModsScreen extends Screen {

	private static final Identifier UNKNOWN_PACK = new Identifier("textures/misc/unknown_pack.png");

	private final Screen parent;
	private final Map<String, Identifier> icons = new HashMap<>();
	private ModListWidget modsList;

	public AlephModsScreen(Screen parent) {
		super(new TranslatableText("menu.aleph.mods"));
		this.parent = parent;
	}

	@Override
	public void onClose() {
		client.setScreen(parent);
	}

	@Override
	public void init() {
		addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 25, 200, 20, ScreenTexts.DONE, btn -> onClose()));
		modsList = new ModListWidget(this.client, this.width, this.height, new LiteralText(""));
		modsList.setLeftPos(0);
		addSelectableChild(this.modsList);
		modsList.clearEntries();
		for (NilMetadata meta : NilModList.getAll()) {
			modsList.addEntry(new NilMetadataEntry(meta));
			URL u = ClassLoader.getSystemResource(meta.id+".icon.png");
			if (u != null) {
				try (InputStream in = u.openStream()) {
					NativeImage img = NativeImage.read(in);
					Identifier id = new Identifier("aleph", "modicon/"+meta.id+".png");
					NativeImageBackedTexture tex = new NativeImageBackedTexture(img);
					tex.setFilter(true, false);
					client.getTextureManager().registerTexture(id, tex);
					icons.put(meta.id, id);
				} catch (IOException e) {
					Aleph.log.warn("Failed to load icon for {}", meta.name, e);
					icons.put(meta.id, UNKNOWN_PACK);
				}
			} else {
				icons.put(meta.id, UNKNOWN_PACK);
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackgroundTexture(0);
		modsList.render(matrices, mouseX, mouseY, delta);
		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
		super.render(matrices, mouseX, mouseY, delta);
	}

	public class ModListWidget extends AlwaysSelectedEntryListWidget<NilMetadataEntry> {
		private final Text title;

		public ModListWidget(MinecraftClient client, int width, int height, Text title) {
			super(client, width, height, 32, height - 30, 36);
			this.title = title;
			this.centerListVertically = false;
			Objects.requireNonNull(client.textRenderer);
			this.setRenderHeader(true, (int)(9 * 1.5f));
		}

		@Override
		public void renderHeader(MatrixStack matrices, int x, int y, Tessellator tess) {
			Text header = (new LiteralText("")).append(this.title).formatted(Formatting.UNDERLINE, Formatting.BOLD);
			this.client.textRenderer.draw(matrices, header, x + this.width / 2 - this.client.textRenderer.getWidth(header) / 2, Math.min(this.top + 3, y), 0xFFFFFF);
		}

		@Override
		public int getRowWidth() {
			return this.width;
		}

		@Override
		public int getScrollbarPositionX() {
			return this.right - 6;
		}
	}

	public class NilMetadataEntry extends Entry<NilMetadataEntry> {
		private final NilMetadata meta;
		private final OrderedText displayName;
		private final MultilineText description;

		public NilMetadataEntry(NilMetadata meta) {
			this.meta = meta;
			this.displayName = trimTextToWidth(client, new LiteralText(meta.name).append(new LiteralText(" by "+meta.authors).formatted(Formatting.GRAY)));
			this.description = createMultilineText(client, new LiteralText(meta.description).formatted(Formatting.DARK_GRAY));
		}

		private OrderedText trimTextToWidth(MinecraftClient client, Text text) {
			int w = client.textRenderer.getWidth(text);
			if (w > width-80) {
				return Language.getInstance().reorder(StringVisitable.concat(client.textRenderer.trimToWidth(text, width - 80 - client.textRenderer.getWidth("...")), StringVisitable.plain("...")));
			} else {
				return text.asOrderedText();
			}
		}

		private MultilineText createMultilineText(MinecraftClient client, Text text) {
			return MultilineText.create(client.textRenderer, text, width-80, 2);
		}

		@Override
		public Text getNarration() {
			return new TranslatableText("narrator.select", this.meta.name+" by "+this.meta.authors);
		}

		@Override
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, icons.get(meta.id));
			RenderSystem.setShaderColor(1, 1, 1, 1);
			DrawableHelper.drawTexture(matrices, x, y, 0, 0, 32, 32, 32, 32);
			client.textRenderer.drawWithShadow(matrices, displayName, x + 32 + 2, y + 1, 16777215);
			description.drawWithShadow(matrices, x + 32 + 2, y + 12, 10, 8421504);
			RenderSystem.disableBlend();
		}
	}


}
