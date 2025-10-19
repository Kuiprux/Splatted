package com.kuiprux.splatted.render.entities;

import com.kuiprux.splatted.entities.InkDropEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class InkDropEntityRendererGeo extends GeoEntityRenderer<InkDropEntity, InkDropEntityRenderStateGeo> {
    public InkDropEntityRendererGeo(EntityRendererFactory.Context context, GeoModel<InkDropEntity> model) {
        super(context, model);
    }
}
