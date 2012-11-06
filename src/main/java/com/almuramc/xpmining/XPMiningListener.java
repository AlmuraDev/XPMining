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

import java.util.HashMap;
import java.util.UUID;

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
	private final HashMap<UUID, Double> EXP_MAP = new HashMap<UUID, Double>();

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
		final double exp = XPMiningPlugin.getConfiguration().getExp().getExpCost(material);
		if (exp == 0) {
			return;
		}
		PlayerExpChangeEvent expEvent = new PlayerExpChangeEvent(player, (int) exp);
		Bukkit.getPluginManager().callEvent(expEvent);
		final double toGive = expEvent.getAmount();
		if (EXP_MAP.containsKey(player.getUniqueId())) {
			if (EXP_MAP.get(player.getUniqueId()) + toGive > XPMiningPlugin.getConfiguration().getThreshold()) {
				player.giveExp(1);
				EXP_MAP.put(player.getUniqueId(), 0.0);
			} else {
				EXP_MAP.put(player.getUniqueId(), toGive);
			}
		} else {
			EXP_MAP.put(player.getUniqueId(), toGive);
		}
	}
}
