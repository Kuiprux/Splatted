package com.kuiprux.splatted.model;

import com.kuiprux.splatted.Splatted;
import com.kuiprux.splatted.entities.InkDropEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class InkDropGeoModel extends GeoModel<InkDropEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.of(Splatted.MOD_ID, "models/entity/ink_drop.png");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.of(Splatted.MOD_ID, "textures/entity/ink_drop.png");
    }

    @Override
    public Identifier getAnimationResource(InkDropEntity animatable) {
        return null;
    }
}