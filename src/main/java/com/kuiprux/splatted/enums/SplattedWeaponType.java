package com.kuiprux.splatted.enums;

import net.minecraft.util.DyeColor;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.Direction;

import java.util.function.IntFunction;

public enum SplattedWeaponType {
    NONE(-1),
    SHOOTER(0), ROLLER(1), CHARGER(2), SLOSHER(3), SPLATLING(4), DUALIE(5), BRELLA(6), BLASTER(7), BRUSH(8), STRINGER(9), SPLATANA(10);

    private static final EnumIndexer<SplattedWeaponType> ENUM_INDEXER = new EnumIndexer<>(SplattedWeaponType::getIndex, values(), SplattedWeaponType.NONE);

    public final int index;
    SplattedWeaponType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static SplattedWeaponType byIndex(int index) {
        return ENUM_INDEXER.byIndex(index);
    }
}
