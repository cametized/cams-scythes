package camscythe;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.impl.creativetab.FabricCreativeModeTabBuilderImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModItemGroups {

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<@NotNull Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Camscythe.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static CreativeModeTab camscythesGroup = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup.camscythe.camscythes"))
            .icon(() -> new ItemStack(ModItems.VINECOG))
            .displayItems((context, entries) -> {
                entries.accept(ModItems.EMBERGLAIVE);
                entries.accept(ModItems.PLAYTHING);
                entries.accept(ModItems.VINECOG);
            }).build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,Identifier.fromNamespaceAndPath(Camscythe.MOD_ID,"camscythe_group"),camscythesGroup);
    }
}
