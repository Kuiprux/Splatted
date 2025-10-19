package com.kuiprux.splatted;

import com.kuiprux.splatted.items.SplattedItems;
import com.kuiprux.splatted.items.SplattedTintSource;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

//https://github.com/ModFest/glowcase/blob/f53571cb56cd2cdddd18f9385d57491fc31414dd/src/main/java/dev/hephaestus/glowcase/datagen/client/GlowcaseModelGenerator.java
    public class SplattedModelGenerator extends FabricModelProvider {
    public static final TexturedModel.Factory PARTICLE_FACTORY = TexturedModel.makeFactory(block -> TextureMap.all(Blocks.BEDROCK), Models.PARTICLE);

    public SplattedModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        registerColorable(itemModelGenerator, SplattedItems.SPLATTERSHOT_JR, 0xFFFFFFFF);
    }

    public final void registerColorable(ItemModelGenerator generator, Item item, int defaultColor) {
        Identifier identifier = generator.upload(item, Models.GENERATED);
        generator.output.accept(item, ItemModels.tinted(identifier, new SplattedTintSource(defaultColor)));
    }
}