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
import java.util.HashMap;
import java.util.logging.Level;

import com.almuramc.xpmining.XPMiningPlugin;
import com.almuramc.xpmining.node.ExpNode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ExpConfiguration {
	private final FileConfiguration config;
	private final HashMap<Material, ExpNode> nodes = new HashMap<Material, ExpNode>();
	private double globalExp;

	public ExpConfiguration(File expYml) {
		config = YamlConfiguration.loadConfiguration(expYml);
		construct();
	}

	private final void construct() {
		ConfigurationSection global = config.getConfigurationSection("global");
		if (global == null) {
			throw new IllegalStateException("Missing global section in your exp.yml.");
		}
		globalExp = global.getDouble("exp", 0.0);
		//Now, we read in any material costs (if any)
		ConfigurationSection material = config.getConfigurationSection("material");
		if (material == null) {
			return;
		}
		for (String key : material.getKeys(false)) {
			if (!material.isConfigurationSection(key)) {
				Bukkit.getLogger().log(Level.WARNING, XPMiningPlugin.getPrefix() + " Found " + key + " in exp.yml but has no value specified! Skipping...");
				continue;
			}
			Material keyed = Material.getMaterial(key.toUpperCase());
			if (keyed == null) {
				Bukkit.getLogger().log(Level.WARNING, XPMiningPlugin.getPrefix() + " Found " + key + " in exp.yml but isn't a valid Minecraft material! Skipping...");
				continue;
			}
			if (nodes.containsKey(keyed)) {
				Bukkit.getLogger().log(Level.WARNING, XPMiningPlugin.getPrefix() + " Found " + key + " in exp.yml but is a duplicate! Overwriting...");
			}
			ConfigurationSection node = material.getConfigurationSection(key);
			nodes.put(keyed, new ExpNode(keyed, node.getDouble("exp", globalExp)));
		}
	}

	public void reload() {
		construct();
	}

	public double getExpCost(Material material) {
		if (material == null) {
			return globalExp;
		}
		ExpNode node = nodes.get(material);
		return node == null ? globalExp : node.getXP();
	}
}
