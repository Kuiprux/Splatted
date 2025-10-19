package com.kuiprux.splatted.enums;

import net.minecraft.util.function.ValueLists;

import java.util.HashMap;
import java.util.function.ToIntFunction;

public class EnumIndexer<T extends Enum> {
    private final HashMap<Integer, T> map = new HashMap<>();
    private final T outOfBoundsValue;

    public EnumIndexer(ToIntFunction<T> valueToIndexFunction, T[] values, T outOfBoundsValue) {
        for(T value : values) {
            map.put(valueToIndexFunction.applyAsInt(value), value);
        }
        this.outOfBoundsValue = outOfBoundsValue;
    }

    public T byIndex(int index) {
        if(map.containsKey(index))
            return map.get(index);
        return outOfBoundsValue;
    }

}
