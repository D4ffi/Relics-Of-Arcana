package com.miyamura;

import com.miyamura.Item.ModItemGroup;
import com.miyamura.Item.ModItems;
import com.miyamura.effect.ModEffects;
import com.miyamura.event.LoversPeaceArea;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelicsOfArcana implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("relicsofarcana");
	public static final String MOD_ID = "relicsofarcana";

	@Override
	public void onInitialize() {

		ModItemGroup.registerItemGroups();
		ModItems.registerItems();
		ModEffects.registerEffects();
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(new LoversPeaceArea());

		LOGGER.info("Hello Fabric world!");
	}
}