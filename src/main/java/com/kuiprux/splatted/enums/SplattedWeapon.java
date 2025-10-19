package com.kuiprux.splatted.enums;

import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum SplattedWeapon {
    NONE(SplattedWeaponType.NONE, 0, 0, 0),
    SPLATTER_JR(SplattedWeaponType.SHOOTER, 0, 0.5, 3);


    public final SplattedWeaponType type;
    public final int subIndex;
    public final int index;
    public final double verticalRange;
    public final double horizontalRange;
    private static final EnumIndexer<SplattedWeapon> ENUM_INDEXER = new EnumIndexer<>(SplattedWeapon::getIndex, values(), SplattedWeapon.NONE);

    SplattedWeapon(SplattedWeaponType type, int subIndex, double verticalRange, double horizontalRange) {
        this.type = type;
        this.subIndex = subIndex;
        this.index = (type.getIndex() << 8) | (subIndex & 0xFF);
        this.verticalRange = verticalRange;
        this.horizontalRange = horizontalRange;
    }

    public SplattedWeaponType getType() {
        return type;
    }

    public int getSubIndex() {
        return subIndex;
    }

    public int getIndex() {
        return index;
    }

    public static SplattedWeapon byIndex(int index) {
        return ENUM_INDEXER.byIndex(index);
    }
}
