package camscythe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;

import java.util.logging.Logger;

public class Camscythe implements ModInitializer {
    public static final String MOD_ID = "camscythe";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModItemGroups.initialize();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(world instanceof ServerLevel serverWorld)) return InteractionResult.PASS;
            if (!(entity instanceof LivingEntity livingTarget)) return InteractionResult.PASS;
            var heldItem = player.getMainHandItem().getItem();
            double x = entity.getX();
            double y = entity.getY() + entity.getBoundingBox().getYsize() * 0.5;
            double z = entity.getZ();

            if (heldItem.equals(ModItems.EMBERGLAIVE)) {
                spawnSlash(serverWorld, livingTarget, true, false, 0xFFFF4400); // orange-red
                if (player.getCurrentItemAttackStrengthDelay() > 0.9f) {
                    livingTarget.setRemainingFireTicks(8*20);
                    serverWorld.playSound(null, x, y, z,
                            SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
                }
            } else if (heldItem.equals(ModItems.PLAYTHING)) {
                spawnSlash(serverWorld, livingTarget, true, true, 0xFFFFD700); // gold
                if (!(player.getCurrentItemAttackStrengthDelay() > 0.9f)) {
                    player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP,0.75f,1.125f);
                }
                Logger.getGlobal().info(String.valueOf(player.getAttributeValue(Attributes.ATTACK_SPEED)));
            } else if (heldItem.equals(ModItems.VINECOG)) {
                spawnSlash(serverWorld, livingTarget, true, false, 0xFF33CC00); // green
            }

            return InteractionResult.PASS;
        });
    }

    private static void spawnSlash(ServerLevel world, LivingEntity target, boolean dust, boolean sweep, int color) {
        // Sweep arc for the slash shape
        if (sweep) {
            world.sendParticles(
                    ParticleTypes.SWEEP_ATTACK,
                    target.getX(), target.getY()+target.getY()/2, target.getZ(),
                    1,
                    target.getBoundingBox().getXsize()/2, target.getBoundingBox().getYsize()/2, target.getBoundingBox().getZsize()/2,
                    0.0
            );
        }
        // Colored dust cloud for the scythe's color
        if (dust) {
            world.sendParticles(
                    new DustParticleOptions(color,1.8f), //particle
                    target.getX(), target.getY()+target.getBoundingBox().getYsize()/2, target.getZ(), //position
                    (int) Math.round(12*(target.getBoundingBox().getXsize()*target.getBoundingBox().getZsize()*target.getBoundingBox().getYsize())), //count
                    target.getBoundingBox().getXsize()/2, target.getBoundingBox().getYsize()/2, target.getBoundingBox().getZsize()/2, //offset
                    0.0 //speed
            );
        }
    }
}
