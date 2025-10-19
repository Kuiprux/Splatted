package com.kuiprux.splatted.entities;

import com.kuiprux.splatted.WorldInkHandler;
import com.kuiprux.splatted.enums.SplattedWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;

public class InkDropEntity extends ProjectileEntity implements GeoEntity {

    private static final TrackedData<Byte> DYE_COLOR = DataTracker.registerData(InkDropEntity.class, TrackedDataHandlerRegistry.BYTE);

    private DyeColor color = DyeColor.WHITE;
    private SplattedWeapon weapon = SplattedWeapon.NONE;

    public InkDropEntity(double x, double y, double z, World world, SplattedWeapon weapon, DyeColor color) {
        super(SplattedEntities.INK_DROP, world);

        this.setWeapon(weapon);
        this.setPosition(x, y, z);
        this.setColor(color);
    }

    public InkDropEntity(LivingEntity owner, World world, SplattedWeapon weapon, DyeColor color) {
        this(owner.getX(), owner.getEyeY() - 0.10000000149011612, owner.getZ(), world, weapon, color);
    }

    public InkDropEntity(EntityType<InkDropEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        this.applyGravity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        Vec3d vec3d;
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d = hitResult.getPos();
        } else {
            vec3d = this.getPos().add(this.getVelocity());
        }

        this.setPosition(vec3d);
        this.updateRotation();
        this.tickBlockCollision();
        super.tick();
        if (hitResult.getType() != HitResult.Type.MISS && this.isAlive()) {
            this.hitOrDeflect(hitResult);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult hit) {
        super.onBlockHit(hit);

        if(getWorld().isClient)
            return;

        Vec3d pos = hit.getPos();
        System.out.println(pos.x + " " + pos.y + " " + pos.z + " | " + hit.getSide());
        WorldInkHandler.splat(getWorld(), pos, hit.getSide(), color, weapon.horizontalRange, weapon.verticalRange);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if(getWorld().isClient)
            return;
        Entity target = entityHitResult.getEntity();
        System.out.println(target.getClass().getName());
        target.damage((ServerWorld) getWorld(), this.getDamageSources().thrown(this, this.getOwner()), 5.0F);
        this.discard();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(DYE_COLOR, (byte) 0);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putByte("color", (byte) color.getIndex());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        color = DyeColor.byIndex(view.getByte("color", (byte) 0));
    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return true;
    }

    @Override
    protected double getGravity() {
        return 0.05;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setWeapon(SplattedWeapon weapon) {
        this.weapon = weapon;
    }

    public SplattedWeapon getWeapon() {
        return weapon;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
