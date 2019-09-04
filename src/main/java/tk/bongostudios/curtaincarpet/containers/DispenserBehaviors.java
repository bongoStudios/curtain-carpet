package tk.bongostudios.curtaincarpet.containers;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import tk.bongostudios.curtaincarpet.CurtainSettings;
import tk.bongostudios.curtaincarpet.utils.FakePlayerEntity;

import java.util.List;
import java.util.Set;

public class DispenserBehaviors {

    public static void registerBehaviors() {

        DispenserBlock.registerBehavior(Items.GOLDEN_APPLE, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.GOLDEN_CARROT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SWEET_BERRIES, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.DANDELION, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SEAGRASS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.HAY_BLOCK, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.WHEAT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.CARROT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.POTATO, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEETROOT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.WHEAT_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEETROOT_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.MELON_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.PUMPKIN_SEEDS, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COD, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.SALMON, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.ROTTEN_FLESH, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.PORKCHOP, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.CHICKEN, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.RABBIT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.BEEF, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.MUTTON, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_PORKCHOP, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_CHICKEN, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_RABBIT, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_BEEF, new FeedAnimalDispenserBehavior());
        DispenserBlock.registerBehavior(Items.COOKED_MUTTON, new FeedAnimalDispenserBehavior());

    }

    public static class FeedAnimalDispenserBehavior extends ItemDispenserBehavior {

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            if(!CurtainSettings.dispensersFeedAnimals) {
                return super.dispenseSilently(source, stack);
            }

            BlockPos pos = source.getBlockPos().offset((Direction) source.getBlockState().get(DispenserBlock.FACING));
            List<AnimalEntity> list = source.getWorld().<AnimalEntity>getEntities(AnimalEntity.class, new Box(pos));
            boolean failure = false;

            for(AnimalEntity mob : list) {
                if(!mob.isBreedingItem(stack)) continue;
                if(mob.getBreedingAge() != 0 || mob.isInLove()) {
                    failure = true;
                    continue;
                }
                stack.decrement(1);
                mob.lovePlayer(null);
                return stack;
            }
            if(failure) return stack;
            return super.dispenseSilently(source, stack);
        }
    }

    public static class TogglingDispenserBehaviour extends ItemDispenserBehavior {

        private FakePlayerEntity player;
        private static Set<Block> toggleable = Sets.newHashSet(
            Blocks.STONE_BUTTON, Blocks.ACACIA_BUTTON, Blocks.BIRCH_BUTTON, Blocks.DARK_OAK_BUTTON,
            Blocks.JUNGLE_BUTTON, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.ACACIA_DOOR,
            Blocks.BIRCH_DOOR, Blocks.DARK_OAK_DOOR, Blocks.JUNGLE_DOOR, Blocks.OAK_DOOR,
            Blocks.SPRUCE_DOOR, Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR,
            Blocks.JUNGLE_TRAPDOOR, Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.ACACIA_FENCE_GATE, 
            Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE, 
            Blocks.SPRUCE_FENCE_GATE, Blocks.REPEATER, Blocks.COMPARATOR, Blocks.LEVER,
            Blocks.DAYLIGHT_DETECTOR, Blocks.NOTE_BLOCK, Blocks.REDSTONE_ORE, Blocks.BELL
        );

        @Override
        protected ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
            if(!CurtainSettings.dispensersToggleThings) {
                return super.dispenseSilently(source, stack);
            }

            World world = source.getWorld();
            if(player == null) player = new FakePlayerEntity(world, "toggling");
            Direction direction = (Direction) source.getBlockState().get(DispenserBlock.FACING);
            BlockPos pos = source.getBlockPos().offset(direction);
            BlockState state = world.getBlockState(pos);  
            if(toggleable.contains(state.getBlock())) {
                boolean bool = state.activate(
                    world, 
                    player,
                    Hand.MAIN_HAND,
                    new BlockHitResult(
                        new Vec3d(new Vec3i(pos.getX(), pos.getY(), pos.getZ())),
                        direction, 
                        pos,
                        false
                    )
                );
                if(bool) return stack;
            }
            return super.dispenseSilently(source, stack);
        }
    }
}