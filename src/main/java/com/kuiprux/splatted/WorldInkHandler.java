package com.kuiprux.splatted;

import com.kuiprux.splatted.blocks.SplattedBlocks;
import com.kuiprux.splatted.blocks.SplattedInkBlock;
import com.kuiprux.splatted.blocks.SplattedInkBlockEntity;
import com.kuiprux.splatted.util.MathUtil;
import com.kuiprux.splatted.util.Pos2d;
import com.kuiprux.splatted.util.Pos2i;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldInkHandler {

    private static final List<Pos2i> FULL_SUBPOS_LIST = new ArrayList<>();
    static {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                FULL_SUBPOS_LIST.add(new Pos2i(i, j));
            }
        }
    }

    public static boolean splat(World world, Vec3d centerPos, Direction side, DyeColor color, double horizontalRange, double verticalRange) {
        boolean isSucceedAtLeastOnce = false;
        boolean check;
        int intVerticalRange = (int) verticalRange;
        for(int i = -intVerticalRange; i <= intVerticalRange; i++) {
            check = splat(world, centerPos.subtract(0, i, 0), side, color, horizontalRange);
            if(check)
                isSucceedAtLeastOnce = true;
        }

        return isSucceedAtLeastOnce;
    }

    public static boolean splat(World world, Vec3d centerPos, Direction side, DyeColor color, double horizontalRange) {
        boolean isSucceedAtLeastOnce = false;
        boolean check;
        Vec3d adjustedCenterPos = centerPos;
        if(side.getDirection() == Direction.AxisDirection.POSITIVE) {
            adjustedCenterPos = centerPos.offset(side.getOpposite(), 1);
        }
        Pos2d center2dPos = MathUtil.getCenteredPos(MathUtil.get2dPos(side, adjustedCenterPos), 4);

        Pos2d from = new Pos2d(center2dPos.axis1 - horizontalRange, center2dPos.axis2 - horizontalRange);
        Pos2d to = new Pos2d(center2dPos.axis1 + horizontalRange, center2dPos.axis2 + horizontalRange);

        Pos2i fromInt = new Pos2i((int) Math.floor(from.axis1), (int) Math.floor(from.axis2));
        Pos2i toInt = new Pos2i((int) Math.ceil(to.axis1), (int) Math.ceil(to.axis2));
        for(int i = fromInt.axis1; i < toInt.axis1; i++) {
            for(int j = fromInt.axis2; j < toInt.axis2; j++) {
                Pos2i block2dPos = new Pos2i(i, j);
                BlockPos blockPos = MathUtil.getBlockPos(block2dPos, side, adjustedCenterPos);
                System.out.println(block2dPos.axis1 + " " + block2dPos.axis2);
                if(MathUtil.isTotallyOutside(center2dPos, block2dPos, horizontalRange)) {
                    System.out.println("outside");
                    continue;
                }

                if(MathUtil.isTotallyInside(center2dPos, block2dPos, horizontalRange)) {
                    System.out.println("inside");
                    check = fillInk(world, blockPos, side, color);
                    if(check)
                        isSucceedAtLeastOnce = true;
                    continue;
                }

                Pos2d testFrom = new Pos2d(Math.max(from.axis1, block2dPos.axis1), Math.max(from.axis2, block2dPos.axis2)).subtract(block2dPos.axis1, block2dPos.axis2);
                Pos2d testTo = new Pos2d(Math.min(to.axis1, block2dPos.axis1+1), Math.min(to.axis2, block2dPos.axis2+1)).subtract(block2dPos.axis1, block2dPos.axis2);
                Pos2i subFrom = MathUtil.getIntSubPosFromOrigin(new Pos2d(Math.max(from.axis1, block2dPos.axis1), Math.max(from.axis2, block2dPos.axis2)).subtract(block2dPos.axis1, block2dPos.axis2));
                Pos2i subTo = MathUtil.getIntSubPosFromOrigin(new Pos2d(Math.min(to.axis1, block2dPos.axis1+1), Math.min(to.axis2, block2dPos.axis2+1)).subtract(block2dPos.axis1, block2dPos.axis2));
                List<Pos2i> subPosesToInk = new ArrayList<>();

                System.out.println(subFrom.axis1 + " " + subFrom.axis2 + " / " + subTo.axis1 + " " + subTo.axis2);
                for(int k = subFrom.axis1; k < subTo.axis1; k++) {
                    for(int l = subFrom.axis2; l < subTo.axis2; l++) {
                        Pos2i subPos = new Pos2i(k, l);
                        Pos2d actualSubCenterPos = MathUtil.getActualCenterPosForSubPos(block2dPos, subPos);
                        System.out.println("\t" + actualSubCenterPos.axis1 + " " + actualSubCenterPos.axis2);

                        if(actualSubCenterPos.subtract(center2dPos).length() <= horizontalRange) {
                            System.out.println("\t" + "inside");
                            subPosesToInk.add(subPos);
                        }
                    }
                }
                check = fillInk(world, blockPos, side, color, subPosesToInk);
                if(check)
                    isSucceedAtLeastOnce = true;
            }
        }

        return isSucceedAtLeastOnce;
    }
    public static boolean fillInk(World world, BlockPos blockPos, Direction side, DyeColor color) {
        return fillInk(world, blockPos, side, color, FULL_SUBPOS_LIST);
    }
    public static boolean fillInk(World world, BlockPos blockPos, Direction side, DyeColor color, Pos2i subPos) {
        List<Pos2i> subPoses = new ArrayList<>();
        subPoses.add(subPos);
        return fillInk(world, blockPos, side, color, subPoses);
    }
    public static boolean fillInk(World world, BlockPos blockPos, Direction side, DyeColor color, List<Pos2i> subPosesToInk) {
        if(subPosesToInk.size() == 0)
            return false;

        BlockState blockState = world.getBlockState(blockPos);
        if(!blockState.isFullCube(world, blockPos))
            return false;

        BlockPos targPos = blockPos.add(side.getVector());
        BlockState targBlockState = world.getBlockState(targPos);
        if(!(targBlockState.getBlock() instanceof SplattedInkBlock)) {
            if(!targBlockState.isReplaceable()) {
                return false;
            }

            world.setBlockState(targPos, SplattedBlocks.SPLATTED_INK.getDefaultState());
        }

        BlockEntity blockEntity = world.getBlockEntity(targPos);
        if (blockEntity instanceof SplattedInkBlockEntity inkBlockEntity) {
            if(subPosesToInk == null) {
                inkBlockEntity.fillSide(side, color);
            } else {
                for(Pos2i subPos : subPosesToInk) {
                    inkBlockEntity.setInk(side, subPos, color);
                }
            }
            return true;
        }
        return false;
    }

}
