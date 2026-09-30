package net.sujiro.tntpowermod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class TNTPowerMod implements ModInitializer {

	private static final Map<BlockPos, Integer> tntPowerMap = new HashMap<>();

	@Override
	public void onInitialize() {
		System.out.println("Custom TNT Power Loaded");

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (world.isClientSide() || hand != InteractionHand.MAIN_HAND) {
				return InteractionResult.PASS;
			}

			BlockPos blocoPosicao = hitResult.getBlockPos();
			BlockState blocoClicado = world.getBlockState(blocoPosicao);
			ItemStack itemNaMao = player.getMainHandItem();

			boolean eUmaTNT = blocoClicado.is(Blocks.TNT);
			boolean eUmaPederneira = itemNaMao.is(Items.FLINT_AND_STEEL);

			if (eUmaTNT) {
				if (!eUmaPederneira) {
					int poderAgora = tntPowerMap.getOrDefault(blocoPosicao, 4);
					int novoPoder = poderAgora + 1;

					tntPowerMap.put(blocoPosicao, novoPoder);

					player.displayClientMessage(Component.literal("§c§lPower of the TNT: §e" + novoPoder), true);
					return InteractionResult.SUCCESS;
				} else {
					int poderSalvo = tntPowerMap.getOrDefault(blocoPosicao, 4);
					world.removeBlock(blocoPosicao, false);

					PrimedTnt tntAcesa = new PrimedTnt(
							world,
							blocoPosicao.getX() + 0.5,
							blocoPosicao.getY(),
							blocoPosicao.getZ() + 0.5,
							player
					) {
						@Override
						public void tick() {
							if (this.getFuse() <= 1) {
								this.discard();

								this.level().explode(
										this,
										this.getX(),
										this.getY(0.0625D),
										this.getZ(),
										(float) poderSalvo,
										Level.ExplosionInteraction.TNT
								);
							} else {
								super.tick();
							}
						}
					};

					tntAcesa.setFuse(80);
					world.addFreshEntity(tntAcesa);
					tntPowerMap.remove(blocoPosicao);

					return InteractionResult.SUCCESS;
				}
			}

			return InteractionResult.PASS;
		});
	}
}
