package net.darkhax.bedbenefits;

import net.darkhax.bookshelf.util.EntityUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;

@Mod("bedbenefits")
public class BedBenefits {
    
    private final Configuration config = new Configuration();
    
    public BedBenefits() {
        
        ModLoadingContext.get().registerConfig(Type.COMMON, this.config.getSpec());
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerWakeUp);
    }
    
    private void onPlayerWakeUp (PlayerWakeUpEvent event) {
        
        if (event.getPlayer().world.isRemote) {
            
            return;
        }
        
        // Restore health when sleeping
        if (this.config.shouldRestoreHealth()) {
            
            event.getPlayer().heal((float) this.config.getHealthAmount());
        }
        
        // Clear potion effects when sleeping
        if (this.config.shouldClearBadEffects() || this.config.shouldClearGoodEffects()) {
            
            EntityUtils.clearEffects(event.getEntityLiving(), this.config.shouldClearBadEffects(), this.config.shouldClearGoodEffects());
        }
    }
}