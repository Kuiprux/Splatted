package com.kuiprux.splatted.util;

public abstract class Pos2<T> {
    public final T axis1;
    public final T axis2;

    public Pos2(T axis1, T axis2) {
        this.axis1 = axis1;
        this.axis2 = axis2;
    }

    public abstract Pos2<T> add(Pos2<T> pos);
    public abstract Pos2<T> add(T axis1, T axis2);

    public abstract Pos2<T> subtract(Pos2<T> pos);
    public abstract Pos2<T> subtract(T axis1, T axis2);

    public abstract double length();
}
