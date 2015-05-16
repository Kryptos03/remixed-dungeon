package com.watabou.pixeldungeon.sprites;

import com.watabou.noosa.TextureFilm;
import com.watabou.pixeldungeon.Assets;

public class SpiderNestSprite extends MobSprite {
	
	public SpiderNestSprite() {
		super();
		
		texture( Assets.SPIDER_EGG );
		
		TextureFilm frames = new TextureFilm( texture, 16, 15 );
		
		idle = new Animation( 1, true );
		idle.frames( frames, 0 );
		
		run = new Animation( 1, true );
		run.frames( frames, 0 );
		
		attack = new Animation( 1, false );
		attack.frames( frames, 0 );
		
		zap = attack.clone();
		
		die = new Animation( 1, false );
		die.frames( frames, 0 );
		
		play( idle );
	}
}
