package com.kuiprux.splatted.items;

import com.kuiprux.splatted.WorldInkHandler;
import com.kuiprux.splatted.blocks.SplattedBlocks;
import com.kuiprux.splatted.blocks.SplattedInkBlock;
import com.kuiprux.splatted.blocks.SplattedInkBlockEntity;
import com.kuiprux.splatted.util.MathUtil;
import com.kuiprux.splatted.util.Pos2i;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DebugBrush extends Item {
    public DebugBrush(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient)
            return ActionResult.SUCCESS;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction side = context.getSide();
        Vec3d hitPos = context.getHitPos();

        System.out.println(hitPos.x + " " + hitPos.y + " " + hitPos.z + " | " + side);
        boolean isSucceed;
        if(context.getHand() == Hand.MAIN_HAND) {
                isSucceed = WorldInkHandler.splat(world, hitPos, side, DyeColor.CYAN, 3.5, 1.5);
        } else {
            Pos2i subPos = MathUtil.getIntSubPosFromCenter(side, hitPos.subtract(pos.toCenterPos()));
            isSucceed = WorldInkHandler.fillInk(world, pos, side, DyeColor.CYAN, subPos);
        }
        return /*isSucceed ? ActionResult.SUCCESS : ActionResult.PASS;*/ ActionResult.SUCCESS;
    }
}
