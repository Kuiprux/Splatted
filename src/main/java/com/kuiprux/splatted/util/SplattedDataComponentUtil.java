package com.kuiprux.splatted.util;

import com.kuiprux.splatted.SplattedComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public class SplattedDataComponentUtil {

    public static DyeColor getColor(ItemStack stack) {
        Byte value = stack.get(SplattedComponents.COLOR);
        if(value == null)
            value = 0;
        return DyeColor.byIndex(value);
    }
}
