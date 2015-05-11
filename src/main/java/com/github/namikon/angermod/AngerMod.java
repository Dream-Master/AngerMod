package com.github.namikon.angermod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import com.github.namikon.angermod.auxiliary.*;
import com.github.namikon.angermod.config.AngerModConfig;
import com.github.namikon.angermod.events.PlayerSpawnProtection;
import com.github.namikon.angermod.events.BlockBreakEvent;


/**
 * @author Namikon
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class AngerMod {
	private static AngerModConfig _cfgManager = null;
	public static boolean ModInitSuccessful = true;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try 
		{
			_cfgManager = new AngerModConfig(event);
			if (!_cfgManager.LoadConfig())
				ModInitSuccessful = false;
		}
	    catch (Exception e)
	    {
	    	LogHelper.error("Yeeks, I can't load my configuration. What did you do??");
	    	LogHelper.DumpStack(e);
	    }
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		if(ModInitSuccessful)
		{
			BlockBreakEvent tBreakEvent = new BlockBreakEvent(_cfgManager);
			MinecraftForge.EVENT_BUS.register(tBreakEvent);
			
			if (_cfgManager.NewPlayerProtection)
			{
				LogHelper.info("Spawn-Protection is enabled. Players will be protected until they attack");
				PlayerSpawnProtection tLUE = new PlayerSpawnProtection();
				MinecraftForge.EVENT_BUS.register(tLUE);
			}
		}
		else
			LogHelper.warn(Reference.MODID + " will NOT do anything as there where errors due the preInit event. Check the logfile!");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
