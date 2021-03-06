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

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Actor;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Charm;
import com.watabou.pixeldungeon.actors.buffs.Sleep;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.quest.DriedRose;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfLullaby;
import com.watabou.pixeldungeon.items.wands.WandOfBlink;
import com.watabou.pixeldungeon.items.weapon.enchantments.Leech;
import com.watabou.pixeldungeon.levels.Level;
import com.watabou.pixeldungeon.mechanics.Ballistica;
import com.watabou.pixeldungeon.sprites.SuccubusSprite;
import com.watabou.utils.Random;

import androidx.annotation.NonNull;

public class Succubus extends Mob {

	private static final int BLINK_DELAY = 5;

	private int delay = 0;

	public Succubus() {
		spriteClass = SuccubusSprite.class;

		hp(ht(80));
		defenseSkill = 25;

		exp = 12;
		maxLvl = 25;

		loot = new ScrollOfLullaby();
		lootChance = 0.05f;

		RESISTANCES.add(Leech.class);
		IMMUNITIES.add(Sleep.class);
	}

	@Override
	public void onSpawn(Level level) {
		super.onSpawn(level);
		viewDistance = level.getViewDistance() + 1;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25);
	}

	@Override
	public int attackProc(@NonNull Char enemy, int damage) {

		if (Random.Int(3) == 0) {
			Char target = enemy;

			if (enemy.hasBuff(DriedRose.OneWayLoveBuff.class)) {
				target = this;
			}

			float duration = Charm.durationFactor(target) * Random.IntRange(2, 5);

			Buff.affect(target, Charm.class, duration);
			enemy.getSprite().centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);

			Sample.INSTANCE.play(Assets.SND_CHARMS);
		}

		return damage;
	}

	@Override
    public boolean getCloser(int target) {
		if (Dungeon.level.fieldOfView[target] && Dungeon.level.distance(getPos(), target) > 2 && delay <= 0) {

			blink(target);
			spend(-1 / speed());
			return true;

		} else {

			delay--;
			return super.getCloser(target);

		}
	}

	private void blink(int target) {

		int cell = Ballistica.cast(getPos(), target, true, true);

		if (Actor.findChar(cell) != null && Ballistica.distance > 1) {
			cell = Ballistica.trace[Ballistica.distance - 2];
		}

		WandOfBlink.appear(this, cell);

		delay = BLINK_DELAY;
	}

	@Override
	public int attackSkill(Char target) {
		return 40;
	}

	@Override
	public int dr() {
		return 10;
	}
}
