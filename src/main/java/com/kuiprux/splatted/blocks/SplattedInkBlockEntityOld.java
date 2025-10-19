package com.kuiprux.splatted.blocks;

import com.kuiprux.splatted.Splatted;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class SplattedInkBlockEntityOld extends BlockEntity {

    private byte[][] inkColor = new byte[6][8];
    private byte[][] inked = new byte[6][2];

    public SplattedInkBlockEntityOld(BlockPos pos, BlockState state) {
        super(SplattedBlockEntities.SPLATTED_INK, pos, state);
    }

    @Override
    protected void writeData(WriteView writeView) {
        writeView.putByteArray("ink_color", wrapData(inkColor));
        writeView.putByteArray("inked", wrapData(inked));

        super.writeData(writeView);
    }

    @Override
    protected void readData(ReadView readView) {
        super.readData(readView);

        unwrapData(readView.getOptionalByteArray("ink_color"), inkColor);
        unwrapData(readView.getOptionalByteArray("inked"), inked);
    }

    private byte[] wrapData(byte[][] data) {
        byte[] flattenedData = new byte[data.length * data[0].length];
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[0].length; j++) {
                flattenedData[i * data[0].length + j] = data[i][j];
            }
        }
        return flattenedData;
    }

    private void unwrapData(Optional<byte[]> optional, byte[][] target) {
        if(optional.isEmpty())
            return;
        byte[] value = optional.get();
        if(value.length != inkColor.length * inkColor[0].length)
            return;

        for(int i = 0; i < value.length; i++) {
            target[i%inkColor.length][i/inkColor.length] = value[i];
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public void washInk(Direction side, Vec3d targPos) {
        Pair<Integer, Integer> intAxises = getIntSubPos(targPos.subtract(getPos().toCenterPos()));
        if(intAxises == null)
            return;

        setInked(side, intAxises.getLeft(), intAxises.getRight(), false);
    }

    public void setInk(Direction side, Vec3d targPos, DyeColor color) {
        Pair<Integer, Integer> intAxises = getIntSubPos(targPos.subtract(getPos().toCenterPos()));

        if(intAxises == null)
            return;
        setInked(side, intAxises.getLeft(), intAxises.getRight(), true);
        setInkColor(side, intAxises.getLeft(), intAxises.getRight(), color);

        Splatted.LOGGER.error(targPos.x + " " + targPos.y + " " + targPos.z + " | " + intAxises.getLeft() + " " + intAxises.getRight());
    }

    private Pair<Integer, Integer> getIntSubPos(Vec3d relPos) {
        double axis1, axis2;
        if(Math.abs(relPos.x) == 0.5) {
            axis1 = relPos.y;
            axis2 = relPos.z;
        } else if(Math.abs(relPos.y) == 0.5) {
            axis1 = relPos.x;
            axis2 = relPos.z;
        } else if(Math.abs(relPos.z) == 0.5) {
            axis1 = relPos.x;
            axis2 = relPos.y;
        } else {
            return null;
        }

        int intAxis1 = (int) (axis1 * 4 + 2);
        int intAxis2 = (int) (axis2 * 4 + 2);

        return new Pair<>(intAxis1, intAxis2);
    }

    private void setInked(Direction side, int intAxis1, int intAxis2, boolean isInked) {
        int bitsIndex = ((intAxis1 % 2) * 4 + intAxis2);

        byte onMask = (byte) (0b1 << (7 - bitsIndex));
        if(isInked) {
            inked[side.getIndex()][intAxis1 / 2] |= onMask;
        } else {
            inked[side.getIndex()][intAxis1/2] &= ~onMask;
        }

    }

    private void setInkColor(Direction side, int intAxis1, int intAxis2, DyeColor color) {
        int shiftAmount = (4 - intAxis2 % 2 * 4);
        byte onMask = (byte) (0b1111 << shiftAmount);

        inkColor[side.getIndex()][intAxis1 * 2 + intAxis2 / 2] &= ~onMask;
        inkColor[side.getIndex()][intAxis1 * 2 + intAxis2 / 2] |= (color.getIndex() << shiftAmount);
    }
}
