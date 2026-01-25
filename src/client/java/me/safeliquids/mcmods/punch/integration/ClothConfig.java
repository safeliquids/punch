package me.safeliquids.mcmods.punch.integration;

import me.safeliquids.mcmods.punch.PunchClient;
import me.safeliquids.mcmods.punch.config.ConfigData;
import me.safeliquids.mcmods.punch.config.CustomIndicatorSizes;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothConfig {
    public static Screen buildConfigScreenWithCloth(Screen parent) {
        ConfigData config = PunchClient.getConfig();
        CustomIndicatorSizes cis = config.mCustomIndicatorSizes;
        CustomIndicatorSizes.Size normal_partial = cis.mNormalPartial;
        CustomIndicatorSizes.Size target_partial = cis.mTargetPartial;
        CustomIndicatorSizes.Size target_full = cis.mTargetFull;

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
                    Text.translatable("option.punch.enabled"), config.mEnabled)
                .setDefaultValue(ConfigData.DEFAULT_ENABLED)
                .setSaveConsumer(val -> config.mEnabled = val)
                .build());

        main_category.addEntry(
            entry_builder.startBooleanToggle(
                    Text.translatable("option.punch.fast_weapon"), config.mFastWeapon)
                .setDefaultValue(ConfigData.DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON)
                .setSaveConsumer(val -> config.mFastWeapon = val)
                .build());
        
        main_category.addEntry(
            entry_builder.startBooleanToggle(
                    Text.translatable("option.punch.custom_sizes.enabled"), cis.mEnabled)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_ENABLED)
                .setSaveConsumer(val -> cis.mEnabled = val)
                .build());
        
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.normal_partial_width"),
                    normal_partial.mWidth)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> normal_partial.mWidth = val)
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.normal_partial_height"),
                    normal_partial.mHeight)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> normal_partial.mHeight = val)
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_partial_width"),
                    target_partial.mWidth)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> target_partial.mWidth = val)
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_partial_height"),
                    target_partial.mHeight)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> target_partial.mHeight = val)
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_full_width"),
                    target_full.mWidth)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_FULL_WIDTH)
                .setMin(1)
                .setSaveConsumer(val -> target_full.mWidth = val)
                .build());
        main_category.addEntry(
            entry_builder.startIntField(
                    Text.translatable("option.punch.custom_sizes.target_full_height"),
                    target_full.mHeight)
                .setDefaultValue(CustomIndicatorSizes.DEFAULT_TARGET_FULL_HEIGHT)
                .setMin(1)
                .setSaveConsumer(val -> target_full.mHeight = val)
                .build());
        
        return builder.build();
    }
}