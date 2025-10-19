package com.kuiprux.splatted.util;

import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public class MathUtil {
    private static final Pos2i[] CORNER_POSES = new Pos2i[] {new Pos2i(1, 1), new Pos2i(1, 0), new Pos2i(0, 1), new Pos2i(0, 0)};
    public static boolean isTotallyInside(Pos2d centerPos, Pos2i blockPos, double radius) {
        Pos2d centerPosToBlockCenterPosVector = blockPos.add(0.5, 0.5).subtract(centerPos);
        int checkIndex = 0;
        if(centerPosToBlockCenterPosVector.axis1 < 0)
            checkIndex |= 0b10;
        if(centerPosToBlockCenterPosVector.axis2 < 0)
            checkIndex |= 0b01;

        double distance = blockPos.add(CORNER_POSES[checkIndex]).subtract(centerPos.axis1, centerPos.axis2).length();
        return distance <= radius;
    }

    public static boolean isTotallyOutside(Pos2d centerPos, Pos2i blockPos, double radius) {
        Pos2d centerPosToBlockCenterPosVector = blockPos.add(0.5, 0.5).subtract(centerPos);
        int checkIndex = 0;
        if(centerPosToBlockCenterPosVector.axis1 > 0)
            checkIndex |= 0b10;
        if(centerPosToBlockCenterPosVector.axis2 > 0)
            checkIndex |= 0b01;

        double distance = blockPos.add(CORNER_POSES[checkIndex]).subtract(centerPos.axis1, centerPos.axis2).length();
        return distance > radius;
    }

    public static Pos2d get2dPos(Direction side, Vec3d relPos) {
        switch(side.getAxis()) {
            case X -> {
                return new Pos2d(relPos.y, relPos.z);
            }
            case Y -> {
                return new Pos2d(relPos.x, relPos.z);
            }
            case Z -> {
                return new Pos2d(relPos.x, relPos.y);
            }
        }
        return null;
    }

    public static BlockPos getBlockPos(Pos2i block2dPos, Direction side, Vec3d referencePos) {
        switch(side.getAxis()) {
            case X -> {
                return new BlockPos((int) Math.floor(referencePos.x), block2dPos.axis1, block2dPos.axis2);
            }
            case Y -> {
                return new BlockPos(block2dPos.axis1, (int) Math.floor(referencePos.y), block2dPos.axis2);
            }
            case Z -> {
                return new BlockPos(block2dPos.axis1, block2dPos.axis2, (int) Math.floor(referencePos.z));
            }
        }
        return null;
    }

    public static Pos2i getIntSubPosFromCenter(Direction side, Vec3d relPos) {
        Pos2d pos = get2dPos(side, relPos);

        return getIntSubPosFromCenter(pos);
    }

    public static Pos2i getIntSubPosFromCenter(Pos2d pos) {
        return getIntSubPosFromOrigin(pos).add(2, 2);
    }

    public static Pos2i getIntSubPosFromOrigin(Pos2d pos) {
        int intAxis1 = (int) (pos.axis1 * 4);
        int intAxis2 = (int) (pos.axis2 * 4);

        return new Pos2i(intAxis1, intAxis2);
    }

    public static Pos2d getActualCenterPosForSubPos(Pos2i pos, Pos2i subPos) {
        return pos.add(subPos.axis1 / 4.0 + 0.125, subPos.axis2 / 4.0 + 0.125);
    }

    public static Pos2d getCenteredPos(Pos2d pos, int sectors) {
        return new Pos2d(getCenteredVal(pos.axis1, sectors), getCenteredVal(pos.axis2, sectors));
    }

    public static double getCenteredVal(double value, int sectors) {
        return (int) (value * sectors) / (double) sectors + (0.5 / sectors);
    }
}
