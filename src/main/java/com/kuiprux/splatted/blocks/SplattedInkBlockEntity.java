package com.kuiprux.splatted.blocks;

import com.kuiprux.splatted.util.Pos2i;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.Optional;

public class SplattedInkBlockEntity extends BlockEntity {
    public DyeColor[][][] inkColor = new DyeColor[6][4][4];
    public BitSet[] inked = new BitSet[6];

    private int inkCount = 0;

    public SplattedInkBlockEntity(BlockPos pos, BlockState state) {
        super(SplattedBlockEntities.SPLATTED_INK, pos, state);

        for(int i = 0; i < inked.length; i++) {
            inked[i] = new BitSet(16);
        }

        for(int i = 0; i < inkColor.length; i++) {
            for(int j = 0; j < inkColor[i].length; j++) {
                for(int k = 0; k < inkColor[i][j].length; k++) {
                    inkColor[i][j][k] = DyeColor.WHITE;
                }
            }
        }
    }

    @Override
    protected void writeData(WriteView writeView) {
        writeView.putByteArray("ink_color", serializeInkColorData(inkColor));
        writeView.putByteArray("inked", serilizeInkedData(inked));

        super.writeData(writeView);
    }

    public byte[] serializeInkColorData(DyeColor[][][] inkColor) {
        byte[] data = new byte[48];
        int index = 0;
        for(int i = 0; i < inkColor.length; i++) {
            for(int j = 0; j < inkColor[i].length; j++) {
                data[index++] = (byte) (inkColor[i][j][0].getIndex() << 4 | inkColor[i][j][1].getIndex());
                data[index++] = (byte) (inkColor[i][j][2].getIndex() << 4 | inkColor[i][j][3].getIndex());
            }
        }

        return data;
    }

    public byte[] serilizeInkedData(BitSet[] inked) {
        byte[] data = new byte[12];
        int index = 0;
        for(int i = 0; i < inked.length; i++) {
            short value = 0;
            for(int j = 0; j < 16; j++) {
                if(inked[i].get(j))
                    value |= (1 << (15 - j));
            }
            data[index++] = (byte)((value >> 8) & 0xFF);
            data[index++] = (byte)(value & 0xFF);
        }

        return data;
    }

    @Override
    protected void readData(ReadView readView) {
        super.readData(readView);

        inkColor = unserializeInkColorData(readView.getOptionalByteArray("ink_color"));
        inked = unserializeInkedData(readView.getOptionalByteArray("inked"));
    }

    public DyeColor[][][] unserializeInkColorData(Optional<byte[]> optional) {
        DyeColor[][][] inkColor = new DyeColor[6][4][4];
        if(optional.isEmpty())
            return inkColor;

        byte[] rawData = optional.get();
        int index = 0;
        byte data;
        for(int i = 0; i < inkColor.length; i++) {
            for(int j = 0; j < inkColor[i].length; j++) {
                data = rawData[index++];
                inkColor[i][j][0] = DyeColor.byIndex((data >> 4) & 0xF);
                inkColor[i][j][1] = DyeColor.byIndex(data & 0b1111);
                data = rawData[index++];
                inkColor[i][j][2] = DyeColor.byIndex((data >> 4) & 0xF);
                inkColor[i][j][3] = DyeColor.byIndex(data & 0b1111);
            }
        }

        return inkColor;
    }

    public BitSet[] unserializeInkedData(Optional<byte[]> optional) {
        BitSet[] inked = new BitSet[6];
        for (int i = 0; i < 6; i++)
            inked[i] = new BitSet(16);

        if(optional.isEmpty())
            return inked;

        byte[] rawData = optional.get();

        int index = 0;
        for (int i = 0; i < 6; i++) {
            int value = ((rawData[index++] & 0xFF) << 8) | (rawData[index++] & 0xFF);
            for (int bit = 0; bit < 16; bit++) {
                if ((value & (1 << (15 - bit))) != 0) {
                    inked[i].set(bit);
                }
            }
        }

        return inked;
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

    public void washInk(Direction side, Pos2i subPos) {
        setInked(side, subPos.axis1, subPos.axis2, false);
    }

    public void setInk(Direction side, Pos2i subPos, DyeColor color) {
        if(world.isClient)
            return;

        setInked(side, subPos.axis1, subPos.axis2, true);
        setInkColor(side, subPos.axis1, subPos.axis2, color);

        markDirty();
        BlockState state = world.getBlockState(pos);
        world.updateListeners(pos, state, state, 3);
    }

    private void setInked(Direction side, int intAxis1, int intAxis2, boolean isInked) {
        int bitIndex = intAxis1 * 4 + intAxis2;
        if(inked[side.getIndex()].get(bitIndex) != isInked)
            inkCount += isInked ? 1 : -1;
        inked[side.getIndex()].set(bitIndex, isInked);

        if(inkCount == 0)
            world.removeBlock(pos, false);
    }

    private void setInkColor(Direction side, int intAxis1, int intAxis2, DyeColor color) {
        inkColor[side.getIndex()][intAxis1][intAxis2] = color;
    }

    public void washSide(Direction side) {
        if(world.isClient)
            return;

        inkCount -= inked[side.getIndex()].cardinality();
        inked[side.getIndex()].clear();

        if(inkCount == 0)
            world.removeBlock(pos, false);
        else {
            markDirty();
            BlockState state = world.getBlockState(pos);
            world.updateListeners(pos, state, state, 3);
        }
    }
    public void fillSide(Direction side, DyeColor color) {
        if(world.isClient)
            return;

        inkCount += 16 - inked[side.getIndex()].cardinality();
        inked[side.getIndex()].set(0, 16);

        markDirty();
        BlockState state = world.getBlockState(pos);
        world.updateListeners(pos, state, state, 3);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
