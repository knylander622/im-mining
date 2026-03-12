package com.murrkeat.music1;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Rarity;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IAMMUSIC.MODID)
public class IAMMUSIC
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "iammusic";
    
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> IAM_TRACK = SOUNDS.register("play_this",
            () -> new SoundEvent(new ResourceLocation(MODID, "play_this")));

    // Music disc item registration
    public static final RegistryObject<Item> IAMMUSIC_DISC = ITEMS.register("iam_music_disc",
        () -> new RecordItem(15, IAM_TRACK.get(),
                new Item.Properties()
                        .tab(CreativeModeTab.TAB_MISC)
                        .stacksTo(1)
                        .rarity(Rarity.RARE),
                3600));

                //Music Disc 2 Template:
                //Sound
    public static final RegistryObject<SoundEvent> HYESTR = SOUNDS.register("ccr_hyestr",
        () -> new SoundEvent(new ResourceLocation(MODID, "ccr_hyestr")));

        //Disc
    public static final RegistryObject<Item> HYESTR_DISC = ITEMS.register("ccr_hyestr_disc",
        () -> new RecordItem(15, HYESTR.get(),
                new Item.Properties()
                        .tab(CreativeModeTab.TAB_MISC)
                        .stacksTo(1)
                        .rarity(Rarity.RARE),
                3600));
    
    public IAMMUSIC(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        SOUNDS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("I AM THE MUSIC");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
