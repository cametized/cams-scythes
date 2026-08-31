package camscythe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class ModItems {

    private static final ToolMaterial EMBERGLAIVE_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 2869, 1.0f, 0.0f, 14, ItemTags.IRON_TOOL_MATERIALS
    );
    private static final ToolMaterial PLAYTHING_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL, 2764, 1.0f, 0.0f, 14, ItemTags.IRON_TOOL_MATERIALS
    );
    private static final ToolMaterial VINECOG_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2869, 1.0f, 0.0f, 22, ItemTags.NETHERITE_TOOL_MATERIALS
    );

    public static final Item EMBERGLAIVE = register(
            "emberglaive",
            Item::new,
            (new Item.Properties().sword(EMBERGLAIVE_MATERIAL,7.0f,-2.8f))
    );

    public static final Item PLAYTHING = register(
            "plaything",
            Item::new,
            (new Item.Properties().sword(PLAYTHING_MATERIAL,8.0f,-2.0f))
    );
    public static final Item VINECOG = register(
            "vinecog",
            Item::new,
            (new Item.Properties().sword(VINECOG_MATERIAL,11.0f,-2.7f))
    );

    private static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        return register(ResourceKey.create(Registries.ITEM,Identifier.fromNamespaceAndPath(Camscythe.MOD_ID,name)),factory,settings);
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void initialize() {}
}
