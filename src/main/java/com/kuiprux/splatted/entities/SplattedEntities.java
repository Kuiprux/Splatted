package com.kuiprux.splatted.entities;

import com.kuiprux.splatted.Splatted;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class SplattedEntities {

    public static final EntityType<InkDropEntity> INK_DROP = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Splatted.MOD_ID, "ink_drop"),
            EntityType.Builder.<InkDropEntity>create(InkDropEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .maxTrackingRange(64)
                    .trackingTickInterval(10)
                    .build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(Splatted.MOD_ID, "ink_drop")))
            );

    public static void initialize() {
    }
}
