/*
 * This file is part of XPMining.
 *
 * Copyright (c) 2012, AlmuraDev <http://www.almuramc.com/>
 * XPMining is licensed under the Almura Development License.
 *
 * XPMining is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * XPMining is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuramc.xpmining.configuration;

import java.io.File;

import com.almuramc.xpmining.XPMiningPlugin;

import org.bukkit.configuration.file.FileConfiguration;

public final class XPMiningConfiguration {
	private final XPMiningPlugin plugin;
	//Configurations
	private final FileConfiguration config;
	private final ExpConfiguration expConfig;

	public XPMiningConfiguration(XPMiningPlugin plugin) {
		this.plugin = plugin;
		//Read in default config.yml
		config = plugin.getConfig();
		//Setup exp file
		File expYml = new File(plugin.getDataFolder(), "exp.yml");
		if (!expYml.exists()) {
			plugin.saveResource("exp.yml", true);
		}
		expConfig = new ExpConfiguration(expYml);
	}

	public final void reload() {
		plugin.reloadConfig();
		expConfig.reload();
	}

	public final ExpConfiguration getExp() {
		return expConfig;
	}

	public double getThreshold() {
		return config.getDouble("threshold", 1000); //1000 is the plugin default
	}
}
