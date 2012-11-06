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
package com.almuramc.xpmining.node;

import org.bukkit.Material;

/**
 * An "EXP" node
 */
public final class ExpNode {
	private Material material;
	private double xp;

	public ExpNode(Material material, double xp) {
		this.material = material;
		this.xp = xp;
	}

	public Material getMaterial() {
		return material;
	}

	public double getXP() {
		return xp;
	}
}
