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
package com.almuramc.xpmining;

import com.almuramc.xpmining.configuration.XPMiningConfiguration;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class XPMiningPlugin extends JavaPlugin {
	private static XPMiningPlugin instance;
	private static XPMiningConfiguration configuration;
	private static Permission permission;

	@Override
	public void onEnable() {
		instance = this;
		if (!setupPermissions()) {
			throw new RuntimeException("Failed to initialize Vault for permissions!");
		}
		configuration = new XPMiningConfiguration(this);
		this.getServer().getPluginManager().registerEvents(new XPMiningListener(this), this);
	}

	public static XPMiningPlugin getInstance() {
		return instance;
	}

	public static XPMiningConfiguration getConfiguration() {
		return configuration;
	}

	public static String getPrefix() {
		return "[" + ChatColor.LIGHT_PURPLE + "XPMining" + ChatColor.WHITE + "]";
	}

	public static Permission getPermissions() {
		return permission;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		permission = rsp.getProvider();
		return permission != null;
	}
}
