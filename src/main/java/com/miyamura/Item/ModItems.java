package com.miyamura.Item;

import com.miyamura.Item.Cards.*;
import com.miyamura.RelicsOfArcana;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item THE_FOOL = registerNewItems("thefool", new TheFool(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_MAGICIAN = registerNewItems("themagician", new TheMagician(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_HIGH_PRIESTESS = registerNewItems("thehighpriestess", new TheHighPriestess(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_EMPRESS = registerNewItems("theempress", new TheEmpress(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_EMPEROR = registerNewItems("theemperor", new TheEmperor(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_HIEROPHANT = registerNewItems("thehierophant", new TheHierophant(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_LOVERS = registerNewItems("thelovers", new TheLovers(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_CHARIOT = registerNewItems("thechariot", new TheChariot(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item STRENGTH = registerNewItems("strength", new Strength(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_HERMIT = registerNewItems("thehermit", new TheHermit(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item WHEEL_OF_FORTUNE = registerNewItems("wheeloffortune", new WheelOfFortune(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item JUSTICE = registerNewItems("justice", new Justice(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_HANGED_MAN = registerNewItems("thehangedman", new TheHangedMan(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item DEATH = registerNewItems("death", new Death(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item TEMPERANCE = registerNewItems("temperance", new Temperance(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_DEVIL = registerNewItems("thedevil", new TheDevil(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_TOWER = registerNewItems("thetower", new TheTower(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_STAR = registerNewItems("thestar", new TheStar(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_MOON = registerNewItems("themoon", new TheMoon(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_SUN = registerNewItems("thesun", new TheSun(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item JUDGEMENT = registerNewItems("judgement", new Judgement(new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item THE_WORLD = registerNewItems("theworld", new TheWorld(new FabricItemSettings().rarity(Rarity.EPIC)));



    private static Item registerNewItems(String name, Item item) {
        return Registry.register(Registries.ITEM, RelicsOfArcana.MOD_ID + ":" + name, item);
    }

    public static void registerItems() {
        RelicsOfArcana.LOGGER.info("Registering items for Relics of Arcana...");
    }
}
