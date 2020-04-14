package com.shatteredpixel.cursedpixeldungeon.effects;

import com.shatteredpixel.cursedpixeldungeon.CPDSettings;

public class Emitter extends com.watabou.noosa.particles.Emitter {
	@Override
	public void burst(Factory factory, int quantity) {
		if (quantity > 4 && CPDSettings.particles()) {
			quantity = (int) Math.sqrt(quantity);
		}
		super.burst(factory, quantity);
	}

	@Override
	public void start(Factory factory, float interval, int quantity) {
		if (interval < 0.5 && CPDSettings.particles()) {
			interval = (float) 0.5;
		}
		super.start(factory, interval, quantity);
	}
}
