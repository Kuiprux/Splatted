package com.kuiprux.splatted.render.entities;

import com.kuiprux.splatted.Splatted;
import com.kuiprux.splatted.SplattedClient;
import com.kuiprux.splatted.entities.InkDropEntity;
import com.kuiprux.splatted.model.InkDropEntityModel;
import com.kuiprux.splatted.render.ProjEntityRenderer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.item.BowItem;
import net.minecraft.util.Identifier;

public class InkDropRenderer extends ProjEntityRenderer<InkDropEntity, InkDropEntityRenderState> {

    public InkDropRenderer(EntityRendererFactory.Context context) {
        super(context, new InkDropEntityModel(context.getPart(SplattedClient.INK_DROP_LAYER)));
    }

    @Override
    protected Identifier getTexture(InkDropEntityRenderState renderState) {
        return Identifier.of(Splatted.MOD_ID, "textures/entity/ink_drop");
    }

    @Override
    public InkDropEntityRenderState createRenderState() {
        return new InkDropEntityRenderState();
    }

    @Override
    public void updateRenderState(InkDropEntity inkDropEntity, InkDropEntityRenderState renderState, float f) {
        super.updateRenderState(inkDropEntity, renderState, f);
        renderState.color = inkDropEntity.getColor();
    }

    @Override
    public int getColor(InkDropEntityRenderState renderState) {
        return renderState.color.getEntityColor();
    }
}
