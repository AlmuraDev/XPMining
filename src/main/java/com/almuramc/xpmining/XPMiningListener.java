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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class XPMiningListener implements Listener {
	private final XPMiningPlugin plugin;

	public XPMiningListener(XPMiningPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			event.setCancelled(true);
			return;
		}

		final Player player = event.getPlayer();
		if (!plugin.getPermissions().has(player.getWorld(), player.getName(), "xpmining.xp")) {
			return;
		}
		final Material material = event.getBlock().getType();
		final int change = XPMiningPlugin.getConfiguration().getExp().getExpCost(material);
		if (change == 0) {
			return;
		}
		PlayerExpChangeEvent expEvent = new PlayerExpChangeEvent(player, change);
		Bukkit.getPluginManager().callEvent(expEvent);
		player.giveExp(expEvent.getAmount());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			event.setCancelled(true);
			return;
		}
		if (event.getEntityType().equals(EntityType.EXPERIENCE_ORB)) {
			event.setCancelled(true);
		}
	}
}
