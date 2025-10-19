package com.kuiprux.splatted.util;

public class Pos2d extends Pos2<Double> {
    public Pos2d(Double axis1, Double axis2) {
        super(axis1, axis2);
    }

    @Override
    public Pos2d add(Pos2<Double> pos) {
        return add(pos.axis1, pos.axis2);
    }

    @Override
    public Pos2d add(Double axis1, Double axis2) {
        return new Pos2d(this.axis1 + axis1, this.axis2 + axis2);
    }

    @Override
    public Pos2d subtract(Pos2<Double> pos) {
        return subtract(pos.axis1, pos.axis2);
    }

    @Override
    public Pos2d subtract(Double axis1, Double axis2) {
        return new Pos2d(this.axis1 - axis1, this.axis2 - axis2);
    }

    @Override
    public double length() {
        return Math.sqrt(axis1 * axis1 + axis2 * axis2);
    }


    public Pos2d add(Integer axis1, Integer axis2) {
        return new Pos2d(this.axis1 + axis1, this.axis2 + axis2);
    }

    public Pos2d subtract(Integer axis1, Integer axis2) {
        return new Pos2d(this.axis1 - axis1, this.axis2 - axis2);
    }
}
