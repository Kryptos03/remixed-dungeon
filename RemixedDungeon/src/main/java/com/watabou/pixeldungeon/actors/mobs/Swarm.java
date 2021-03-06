/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.actors.mobs;

import com.nyrds.Packable;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.sprites.SwarmSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Swarm extends Mob {

	private static final float BASIC_LOOT_CHANCE = 0.2f;

	{
		spriteClass = SwarmSprite.class;
		
		hp(ht(80));
		defenseSkill = 5;
		
		maxLvl = 10;
		
		flying = true;

		lootChance = BASIC_LOOT_CHANCE;
		loot = new PotionOfHealing();
	}

	@Packable
	private int generation = 0;

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		lootChance = BASIC_LOOT_CHANCE / (generation + 1f);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 4 );
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {

		if (hp() >= damage + 2) {
			int cell = Dungeon.level.getEmptyCellNextTo(getPos());

			if (Dungeon.level.cellValid(cell)) {
				int cloneHp = split(cell, damage).hp();

				hp(hp() - cloneHp);
			}
		}
		
		return damage;
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 12;
	}

	@Override
	public Mob split(int cell, int damage) {
		Swarm clone = (Swarm) super.split(cell, damage);
		clone.generation++;
		clone.lootChance *= 1f / (generation + 1f);
		return clone;
	}
}
