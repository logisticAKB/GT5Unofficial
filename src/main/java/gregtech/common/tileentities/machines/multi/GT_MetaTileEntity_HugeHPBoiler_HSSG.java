package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class GT_MetaTileEntity_HugeHPBoiler_HSSG
        extends GT_MetaTileEntity_HugeBoiler {
    public GT_MetaTileEntity_HugeHPBoiler_HSSG(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        processMultiplier = 10;
    }

    public GT_MetaTileEntity_HugeHPBoiler_HSSG(String aName) {
        super(aName);
        processMultiplier = 10;
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_HugeHPBoiler_HSSG(this.mName);
    }

    public String getCasingMaterial(){
        return "HSSG";
    }

    @Override
    public String getCasingBlockType() {
        return "HSSG Robust Machine Casings";
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings8;
    }

    public byte getCasingMeta() {
        return 2;
    }

    public int getCasingTextureIndex() {
        return 178;
    }

    public Block getPipeBlock() {
        return GregTech_API.sBlockCasings8;
    }

    public byte getPipeMeta() {
        return 4;
    }

    public Block getFireboxBlock() {
        return GregTech_API.sBlockCasings8;
    }

    public byte getFireboxMeta() {
        return 3;
    }

    public int getFireboxTextureIndex() {
        return 179;
    }

    public int getEUt() {
        return 1000;
    }

    public int getEfficiencyIncrease() {
        return 4;
    }

    @Override
    int runtimeBoost(int mTime) {
        return mTime * 120 / 100;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEUt > 0) {
            if (this.mSuperEfficencyIncrease > 0)
                mEfficiency = Math.max(0, Math.min(mEfficiency + mSuperEfficencyIncrease, getMaxEfficiency(mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000)));
            int tGeneratedEU = (int) (this.mEUt * 2L * this.mEfficiency / 10000L);
            if (tGeneratedEU > 0) {
                long amount = (tGeneratedEU + 160) / 160;
                if (depleteInput(Materials.Water.getFluid(amount)) || depleteInput(GT_ModHandler.getDistilledWater(amount))) {
                    addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", (tGeneratedEU)));
                } else {
                    explodeMultiblock();
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER)};
        }
        return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][50]};
    }
}