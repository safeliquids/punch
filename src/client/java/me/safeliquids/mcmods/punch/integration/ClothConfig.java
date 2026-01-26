package me.safeliquids.mcmods.punch.integration;

import me.safeliquids.mcmods.punch.PunchClient;
import me.safeliquids.mcmods.punch.config.ConfigData;
import me.safeliquids.mcmods.punch.config.CustomIndicatorSizes;
import me.safeliquids.mcmods.punch.config.ConfigClassesSetter;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothConfig {
    public static Screen buildConfigScreenWithCloth(Screen parent) {
        ConfigData config = PunchClient.getConfig();
        CustomIndicatorSizes cis = config.getCustomIndicatorSizes();
        CustomIndicatorSizes.Size normal_partial = cis.getNormalPartial();
        CustomIndicatorSizes.Size target_partial = cis.getTargetPartial();
        CustomIndicatorSizes.Size target_full = cis.getTargetFull();

        // this is dirty...
        ConfigClassesSetter setter = new ConfigClassesSetter();

        ConfigBuilder builder = ConfigBuilder.create();
        builder.setParentScreen(parent);
        builder.setTitle(Text.translatable("title.punch.config"));
        builder.setSavingRunnable(() -> {
            PunchClient.setConfig(config);  // this also preforms sanitization
            PunchClient.writeConfig(PunchClient.getConfig());
        });
        
        ConfigEntryBuilder entry_builder = builder.entryBuilder();

        ConfigCategory main_category = builder.getOrCreateCategory(
            Text.translatable("title.punch.category.main"));
        main_category.addEntry(
            entry_builder.startBooleanToggle(
                    Text.translatable("option.punch.enabled"), config.getEnabled())
                .setDefaultValue(ConfigData.DEFAULT_ENABLED)
                .setSaveConsumer(val -> setter.setEnabled(config, val))
                .build());

        main_category.addEntry(
            entry_builder.startBooleanToggle(
                    Text.translatable("option.punch.fast_weapon"), config.getFastWeapon())
                .setDefaultValue(ConfigData.DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON)
                .setSaveConsumer(val -> setter.setFastWeapon(config, val))
                .build());
        
        main_category.addEntry(
            entry_builder.startBooleanToggle(
                    Text.translatable("option.punch.custom_sizes.enabled"), cis.getEnabled())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_ENABLED)
                .setSaveConsumer(val -> setter.setEnabled(cis, val))
                .build());
        
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.normal_partial_width"),
                    normal_partial.getWidth())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> setter.setWidth(normal_partial, val))
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.normal_partial_height"),
                    normal_partial.getHeight())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> setter.setHeight(normal_partial, val))
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_partial_width"),
                    target_partial.getWidth())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> setter.setWidth(target_partial, val))
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_partial_height"),
                    target_partial.getHeight())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> setter.setHeight(target_partial, val))
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_full_width"),
                    target_full.getWidth())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_FULL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> setter.setWidth(target_full, val))
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_full_height"),
                    target_full.getHeight())
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_FULL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> setter.setHeight(target_full, val))
                .build());
        
        return builder.build();
    }
}