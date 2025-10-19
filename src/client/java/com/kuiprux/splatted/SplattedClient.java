package com.kuiprux.splatted;

import com.kuiprux.splatted.blocks.SplattedBlockEntities;
import com.kuiprux.splatted.model.InkDropEntityModel;
import com.kuiprux.splatted.render.blocks.SplattedInkBlockEntityRenderer;
import com.kuiprux.splatted.entities.SplattedEntities;
import com.kuiprux.splatted.items.SplattedTintSource;
import com.kuiprux.splatted.render.entities.InkDropRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.util.Identifier;

public class SplattedClient implements ClientModInitializer {

	public static final EntityModelLayer INK_DROP_LAYER = new EntityModelLayer(Identifier.of(Splatted.MOD_ID, "ink_drop"), "main");


	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(SplattedBlockEntities.SPLATTED_INK, SplattedInkBlockEntityRenderer::new);
		TintSourceTypes.ID_MAPPER.put(Identifier.of(Splatted.MOD_ID, "tint"), SplattedTintSource.CODEC);
		EntityRendererRegistry.register(SplattedEntities.INK_DROP, InkDropRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(INK_DROP_LAYER, InkDropEntityModel::getTexturedModelData);
	}
}