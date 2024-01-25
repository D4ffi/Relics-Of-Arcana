package com.miyamura;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelicsOfArcana implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("relicsofarcana");
	public static final String MOD_ID = "relicsofarcana";

	@Override
	public void onInitialize() {


		LOGGER.info("Hello Fabric world!");
	}
}