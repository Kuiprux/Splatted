package com.kuiprux.splatted.render.blocks;

import com.kuiprux.splatted.blocks.SplattedInkBlockEntity;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.BitSet;

import static net.minecraft.client.gl.RenderPipelines.TRANSFORMS_PROJECTION_FOG_SNIPPET;

public class SplattedInkBlockEntityRenderer implements BlockEntityRenderer<SplattedInkBlockEntity> {

    public static final RenderLayer RENDER_LAYER = RenderLayer.of(
            "splatted_ink",
            256,
            RenderPipeline.builder(TRANSFORMS_PROJECTION_FOG_SNIPPET)
                    .withVertexShader("core/terrain")
                    .withFragmentShader("core/terrain")
                    .withSampler("Sampler0")
                    .withSampler("Sampler2")
                    .withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS)
                    .withLocation(Identifier.of("splatted", "splatted_ink_pipeline"))
                    .build(),
            RenderLayer.MultiPhaseParameters.builder()
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                    .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .target(RenderPhase.MAIN_TARGET)
                    .texturing(RenderPhase.DEFAULT_TEXTURING)
                    .lineWidth(RenderPhase.FULL_LINE_WIDTH)
                    
                    .texture(new RenderPhase.Texture(
                            Identifier.of("splatted", "textures/block/splatted_ink.png"), false
                    ))
                    .build(false)
    );


    public SplattedInkBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(SplattedInkBlockEntity blockEntity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        matrices.push();

        VertexConsumer consumer = vertexConsumers.getBuffer(RENDER_LAYER);

        Matrix4f modelMat = matrices.peek().getPositionMatrix();

        int r = 255, g = 255, b = 255;
        int a = 255;

        BitSet[] inked = blockEntity.inked;
        DyeColor[][][] inkColor = blockEntity.inkColor;

        for(int i = 0; i < 6; i++) {
            drawSide(Direction.byIndex(i), consumer, modelMat, overlay, light, inked[i], inkColor[i]);
        }
        /*consumer.vertex(modelMat, 0.0f, 0.0f, 0.0f)
                .texture(0f, 0f)
                .color(r, g, b, a)
                .overlay(overlay)
                .light(light)
                .normal(0, 0, 1);
        consumer.vertex(modelMat, 1.0f, 0.0f, 0.0f)
                .texture(1f, 0f)
                .color(r, g, b, a)
                .overlay(overlay)
                .light(light)
                .normal(0, 0, 1);
        consumer.vertex(modelMat, 0.5f, 1.0f, 0.0f)
                .texture(0.5f, 1f)
                .color(r, g, b, a)
                .overlay(overlay)
                .light(light)
                .normal(0, 0, 1);*/


        matrices.pop();
    }

    private void drawSide(Direction side, VertexConsumer consumer, Matrix4f modelMat, int overlay, int light, BitSet bitSet, DyeColor[][] dyeColors) {
         for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(!bitSet.get(i * 4 + j))
                    continue;

                float from1 = i / 4.0f;
                float from2 = j / 4.0f;
                float to1 = (i+1) / 4.0f;
                float to2 = (j+1) / 4.0f;

                if((side.getDirection() == Direction.AxisDirection.POSITIVE) == (side.getAxis() == Direction.Axis.Y)) {
                    drawVertex(consumer, modelMat, overlay, light, side, from1, from2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, from1, to2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, to1, to2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, to1, from2, dyeColors[i][j].getEntityColor());
                } else {
                    drawVertex(consumer, modelMat, overlay, light, side, from1, from2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, to1, from2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, to1, to2, dyeColors[i][j].getEntityColor());
                    drawVertex(consumer, modelMat, overlay, light, side, from1, to2, dyeColors[i][j].getEntityColor());
                }
            }
        }
    }

    private void drawVertex(VertexConsumer consumer, Matrix4f modelMat, int overlay, int light, Direction side, float axis1, float axis2, int color) {
        Vector3f sidePos = getSidePos(side, axis1, axis2);
        consumer.vertex(modelMat, sidePos.x, sidePos.y, sidePos.z)
                .texture(axis1, axis2)
                .color(color)
                .overlay(overlay)
                .light(light)
                .normal(side.getOffsetX(), side.getOffsetY(), side.getOffsetZ());
    }

    private Vector3f getSidePos(Direction side, float axis1, float axis2) {
        if(side.getAxis() == Direction.Axis.X)
            return new Vector3f(
                    side.getOffsetX() == -1 ? 1 : 0,
                    axis1, axis2
            );
        if(side.getAxis() == Direction.Axis.Y)
            return new Vector3f(
                    axis1,
                    side.getOffsetY() == -1 ? 1 : 0,
                    axis2
            );
        if(side.getAxis() == Direction.Axis.Z)
            return new Vector3f(
                    axis1, axis2,
                    side.getOffsetZ() == -1 ? 1 : 0
            );

        return new Vector3f();
    }


}