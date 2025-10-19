package com.kuiprux.splatted.render.entities;

import net.minecraft.client.render.entity.state.EntityRenderState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Map;

public class InkDropEntityRenderStateGeo extends EntityRenderState implements GeoRenderState {
    @Override
    public <D> void addGeckolibData(DataTicket<D> dataTicket, @Nullable D data) {

    }

    @Override
    public boolean hasGeckolibData(DataTicket<?> dataTicket) {
        return false;
    }

    @Override
    public <D> @Nullable D getGeckolibData(DataTicket<D> dataTicket) {
        return null;
    }

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return null;
    }
}
