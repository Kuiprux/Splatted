package com.kuiprux.splatted.items;

import com.kuiprux.splatted.SplattedComponents;
import com.kuiprux.splatted.blocks.SplattedBlocks;
import com.kuiprux.splatted.blocks.SplattedInkBlock;
import com.kuiprux.splatted.blocks.SplattedInkBlockEntity;
import com.kuiprux.splatted.entities.InkDropEntity;
import com.kuiprux.splatted.enums.SplattedWeapon;
import com.kuiprux.splatted.util.SplattedDataComponentUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SplattershotJr extends Item {
    private static Random random = new Random();

    public SplattershotJr(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient)
            return ActionResult.PASS;
            ItemStack itemStack = user.getStackInHand(hand);
            //itemStack.set(SplattedComponents.TINT, randomColor());
            ProjectileEntity.spawn(new InkDropEntity(user, world, SplattedWeapon.SPLATTER_JR, SplattedDataComponentUtil.getColor(itemStack)), (ServerWorld) world, itemStack, (projectile) -> {
                this.shoot(user, projectile, 1, 0, 0);
            });

        return ActionResult.SUCCESS;
    }

    private int randomColor() {
        int alpha = 255;
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, float speed, float divergence, float yaw) {
        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
    }
}
