package com.kuiprux.splatted;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.component.ComponentType;
import net.minecraft.util.dynamic.Codecs;

public class SplattedComponents {
    //public static final ComponentType<Integer> TINT = ComponentType.<Integer>builder().codec(Codecs.RGB).build();

    public static final ComponentType<Byte> COLOR = ComponentType.<Byte>builder().codec(Codec.BYTE).build();
    public static void register() {
        //Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Splatted.MOD_ID, "tint"), TINT);
        Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Splatted.MOD_ID, "color"), COLOR);
    }
}