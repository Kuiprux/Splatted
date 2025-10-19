package com.kuiprux.splatted.model;

import com.kuiprux.splatted.render.entities.InkDropEntityRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class InkDropEntityModel extends EntityModel<InkDropEntityRenderState> {
	public InkDropEntityModel(ModelPart root) {
		super(root);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(32, 17).cuboid(-2.5F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(36, 10).cuboid(-1.5F, -0.5F, -2.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(28, 37).cuboid(-1.5F, -0.5F, 1.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 23.5F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone2 = modelPartData.addChild("bone2", ModelPartBuilder.create().uv(24, 29).cuboid(-3.5F, -0.5F, -2.5F, 7.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(36, 3).cuboid(-2.5F, -0.5F, -3.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(36, 5).cuboid(-2.5F, -0.5F, 2.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 22.5F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone3 = modelPartData.addChild("bone3", ModelPartBuilder.create().uv(0, 21).cuboid(-4.5F, -0.5F, -3.5F, 9.0F, 1.0F, 7.0F, new Dilation(0.0F))
		.uv(32, 27).cuboid(-3.5F, -0.5F, -4.5F, 7.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 35).cuboid(-3.5F, -0.5F, 3.5F, 7.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 21.5F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone4 = modelPartData.addChild("bone4", ModelPartBuilder.create().uv(0, 12).cuboid(-4.5F, -1.0F, -3.5F, 9.0F, 2.0F, 7.0F, new Dilation(0.0F))
		.uv(32, 21).cuboid(-3.5F, -1.0F, -4.5F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(32, 24).cuboid(-3.5F, -1.0F, 3.5F, 7.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 17.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone5 = modelPartData.addChild("bone5", ModelPartBuilder.create().uv(0, 29).cuboid(-3.5F, -1.0F, -2.5F, 7.0F, 2.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 36).cuboid(-2.5F, -1.0F, -3.5F, 5.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(36, 0).cuboid(-2.5F, -1.0F, 2.5F, 5.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone6 = modelPartData.addChild("bone6", ModelPartBuilder.create().uv(32, 12).cuboid(-2.5F, -1.0F, -1.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(12, 36).cuboid(-1.5F, -1.0F, -2.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 37).cuboid(-1.5F, -1.0F, 1.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 13.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bone7 = modelPartData.addChild("bone7", ModelPartBuilder.create().uv(36, 7).cuboid(-1.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 39).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 39).cuboid(-0.5F, -1.0F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -6.0F, -4.5F, 9.0F, 3.0F, 9.0F, new Dilation(0.0F))
		.uv(36, 37).cuboid(-0.5F, -16.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(InkDropEntityRenderState renderState) {
		super.setAngles(renderState);
		if (renderState.shake > 0.0F) {
			float f = -MathHelper.sin(renderState.shake * 3.0F) * renderState.shake;
			ModelPart var10000 = this.root;
			var10000.roll += f * 0.017453292F;
		}

	}
}