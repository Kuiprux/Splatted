package com.kuiprux.splatted.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.ArrowEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public abstract class ProjEntityRenderer<T extends ProjectileEntity, S extends ProjectileEntityRenderState> extends EntityRenderer<T, S> {
    private final EntityModel<S> model;

    public ProjEntityRenderer(EntityRendererFactory.Context context, EntityModel<S> model) {
        super(context);
        this.model = model;
    }

    public void render(S projectileEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(projectileEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(projectileEntityRenderState.pitch));
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(projectileEntityRenderState)));
        this.model.setAngles(projectileEntityRenderState);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, getColor(projectileEntityRenderState));
        matrixStack.pop();
        super.render(projectileEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    protected abstract Identifier getTexture(S state);

    public void updateRenderState(T persistentProjectileEntity, S projectileEntityRenderState, float f) {
        super.updateRenderState(persistentProjectileEntity, projectileEntityRenderState, f);
        projectileEntityRenderState.pitch = persistentProjectileEntity.getLerpedPitch(f);
        projectileEntityRenderState.yaw = persistentProjectileEntity.getLerpedYaw(f);
    }

    public abstract int getColor(S projectileEntityRenderState);
}