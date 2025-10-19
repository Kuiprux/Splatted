package com.kuiprux.splatted.items;

import com.kuiprux.splatted.Splatted;
import com.kuiprux.splatted.SplattedComponents;
import com.kuiprux.splatted.blocks.SplattedInkBlock;
import com.kuiprux.splatted.util.SplattedDataComponentUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record SplattedTintSource(int defaultColor) implements TintSource {

    public static final MapCodec<SplattedTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.ARGB.fieldOf("default").forGetter(SplattedTintSource::defaultColor)).apply(instance, SplattedTintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        return SplattedDataComponentUtil.getColor(stack).getEntityColor();
    }

    @Override
    public MapCodec<SplattedTintSource> getCodec() {
        return CODEC;
    }
}