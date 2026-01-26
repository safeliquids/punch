package me.safeliquids.mcmods.punch.config;


// this class enables write access to ConfigData, CustomIndicatorSizes and Size members if needed.
public class ConfigClassesSetter {
    public void setEnabled(ConfigData config, boolean enabled) {
        config.mEnabled = enabled;
    }

    public void setFastWeapon(ConfigData config, boolean fast_weapon) {
        config.mFastWeapon = fast_weapon;
    }

    public void setEnabled(CustomIndicatorSizes cis, boolean enabled) {
        cis.mEnabled = enabled;
    }

    public void setWidth(CustomIndicatorSizes.Size size, int width) {
        size.mWidth = width;
    }
    
    public void setHeight(CustomIndicatorSizes.Size size, int height) {
        size.mHeight = height;
    }
}