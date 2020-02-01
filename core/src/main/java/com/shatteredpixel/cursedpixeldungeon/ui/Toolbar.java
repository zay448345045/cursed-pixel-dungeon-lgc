/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.cursedpixeldungeon.ui;

import com.shatteredpixel.cursedpixeldungeon.Assets;
import com.shatteredpixel.cursedpixeldungeon.Constants;
import com.shatteredpixel.cursedpixeldungeon.Dungeon;
import com.shatteredpixel.cursedpixeldungeon.CPDSettings;
import com.shatteredpixel.cursedpixeldungeon.QuickSlot;
import com.shatteredpixel.cursedpixeldungeon.items.Item;
import com.shatteredpixel.cursedpixeldungeon.messages.Messages;
import com.shatteredpixel.cursedpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.cursedpixeldungeon.scenes.GameScene;
import com.shatteredpixel.cursedpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.cursedpixeldungeon.tiles.DungeonTerrainTilemap;
import com.shatteredpixel.cursedpixeldungeon.windows.WndBag;
import com.shatteredpixel.cursedpixeldungeon.windows.WndJournal;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Gizmo;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class Toolbar extends Component {

	private Tool btnWait;
	private Tool btnSearch;
	private Tool btnInventory;
	private QuickslotTool[] btnQuick;
	
	private PickedUpItem pickedUp;
	
	private boolean lastEnabled = true;
	public boolean examining = false;

	private static Toolbar instance;

	public enum Mode {
		SPLIT,
		GROUP,
		CENTER
	}
	
	public Toolbar() {
		super();

		instance = this;

		height = btnInventory.height();
	}
	
	@Override
	protected void createChildren() {
		
		add(btnWait = new Tool(24, 0, 20, 26) {
			@Override
			protected void onClick() {
				examining = false;
				Dungeon.hero.rest(false);
			}

			protected boolean onLongClick() {
				examining = false;
				Dungeon.hero.rest(true);
				return true;
			}

			;
		});
		
		add(btnSearch = new Tool(44, 0, 20, 26) {
			@Override
			protected void onClick() {
				if (!examining) {
					GameScene.selectCell(informer);
					examining = true;
				} else {
					informer.onSelect(null);
					Dungeon.hero.search(true);
				}
			}

			@Override
			protected boolean onLongClick() {
				Dungeon.hero.search(true);
				return true;
			}
		});

		btnQuick = new QuickslotTool[QuickSlot.SIZE];
		for(int i = 0; i < QuickSlot.SIZE; i++) {
			add(btnQuick[i] = new QuickslotTool(64, 0, 22, 24, i));

			/*add(btnQuick[3] = new QuickslotTool(64, 0, 22, 24, 3));

			add(btnQuick[2] = new QuickslotTool(64, 0, 22, 24, 2));

			add(btnQuick[1] = new QuickslotTool(64, 0, 22, 24, 1));

			add(btnQuick[0] = new QuickslotTool(64, 0, 22, 24, 0));*/
		}

		
		add( btnInventory = new Tool( 0, 0, 24, 26 ) {
			private GoldIndicator gold;

			@Override
			protected void onClick() {
				GameScene.show(new WndBag(Dungeon.hero.belongings.backpack, null, WndBag.Mode.ALL, null));
			}
			
			@Override
			protected boolean onLongClick() {
				WndJournal.last_index = 3; //catalog page
				GameScene.show(new WndJournal());
				return true;
			}

			@Override
			protected void createChildren() {
				super.createChildren();
				gold = new GoldIndicator();
				add(gold);
			}

			@Override
			protected void layout() {
				super.layout();
				gold.fill(this);
			}
		});

		add(pickedUp = new PickedUpItem());
	}
	
	@Override
	protected void layout() {

		int[] visible = new int[QuickSlot.SIZE];
		boolean[] isVisible = new boolean[QuickSlot.SIZE];
		int slots = CPDSettings.quickSlots();

		int horizontal;
		int vertical;
		if (CPDSettings.landscape()) {
			horizontal = 8;
			vertical   = 4;
		} else {
			horizontal = 4;
			vertical   = 8;
		}

		if ((horizontal + vertical) != QuickSlot.SIZE) throw new AssertionError("Horizontal and vertical quickslot values must add to total");

		for(int i = 0; i < QuickSlot.SIZE; i++) {
			//visible[i] = (int) ((slots > i) ? y + 2 : y + 25);
			isVisible[i] = (slots > i);
		}

		for(int i = 0; i < QuickSlot.SIZE; i++) {
			btnQuick[i].visible = btnQuick[i].active = slots > i;
			//decides on quickslot layout, depending on available screen size.
			if (slots >= 4 && width < 152){
				if (width < 138){
					if ((CPDSettings.flipToolbar() && i == 3) ||
							(!CPDSettings.flipToolbar() && i == 0)) {
						btnQuick[i].border(0, 0);
						btnQuick[i].frame(88, 0, 17, 24);
					} else {
						btnQuick[i].border(0, 1);
						btnQuick[i].frame(88, 0, 18, 24);
					}
				} else {
					if (i == 0 && !CPDSettings.flipToolbar() ||
						i == 3 && CPDSettings.flipToolbar()){
						btnQuick[i].border(0, 2);
						btnQuick[i].frame(106, 0, 19, 24);
					} else if (i == 0 && CPDSettings.flipToolbar() ||
							i == 3 && !CPDSettings.flipToolbar()){
						btnQuick[i].border(2, 1);
						btnQuick[i].frame(86, 0, 20, 24);
					} else {
						btnQuick[i].border(0, 1);
						btnQuick[i].frame(88, 0, 18, 24);
					}
				}
			} else {
				btnQuick[i].border(2, 2);
				btnQuick[i].frame(64, 0, 22, 24);
			}

		}

		float right = width;
		switch(Mode.valueOf(CPDSettings.toolbarMode())){
			case SPLIT:
				btnWait.setPos(x, y);
				btnSearch.setPos(btnWait.right(), y);

				btnInventory.setPos(right - btnInventory.width(), y);
				btnQuick[0].setPos(btnInventory.left() - btnQuick[0].width(), isVisible[0] ? y + 2 : y + 25);
				for(int i = 1; i < horizontal; i++) {
					int visiblePos = (int) (isVisible[i] ? y + 2 : y + 25);
					btnQuick[i].setPos(btnQuick[i-1].left() - btnQuick[i].width(), visiblePos );
					/*btnQuick[1].setPos(btnQuick[0].left() - btnQuick[1].width(), visible[1]);
					btnQuick[2].setPos(btnQuick[1].left() - btnQuick[2].width(), visible[2]);
					btnQuick[3].setPos(btnQuick[2].left() - btnQuick[3].width(), visible[3]);

					btnQuick[4].setPos(btnQuick[3].left() - btnQuick[4].width(), visible[4]);*/
				}
				btnQuick[horizontal].setPos(width-btnQuick[horizontal].width(), 0+btnQuick[horizontal].height());
				for(int i = horizontal+1; i < QuickSlot.SIZE; i++) {
					btnQuick[i].border(2, 2);
					btnQuick[i].frame(64, 0, 22, 24);
					btnQuick[i].setPos(width-btnQuick[i].width(), btnQuick[i-1].bottom());
				}
				break;

			//center = group but.. well.. centered, so all we need to do is pre-emptively set the right side further in.
			case CENTER:
				float toolbarWidth = btnWait.width() + btnSearch.width() + btnInventory.width();
				for(Button slot : btnQuick){
					if (slot.visible) toolbarWidth += slot.width();
				}
				right = (width + toolbarWidth)/2;

			case GROUP:
				btnWait.setPos(right - btnWait.width(), y);
				btnSearch.setPos(btnWait.left() - btnSearch.width(), y);
				btnInventory.setPos(btnSearch.left() - btnInventory.width(), y);
				btnQuick[0].setPos(btnInventory.left() - btnQuick[0].width(), isVisible[0] ? y + 2 : y + 25);
				for(int i = 1; i < horizontal; i++) {
					int visiblePos = (int) (isVisible[i] ? y + 2 : y + 25);
					btnQuick[i].setPos(btnQuick[i-1].left() - btnQuick[i].width(), visiblePos);
					/*btnQuick[2].setPos(btnQuick[1].left() - btnQuick[2].width(), visible[2]);
					btnQuick[3].setPos(btnQuick[2].left() - btnQuick[3].width(), visible[3]);
					btnQuick[4].setPos(btnQuick[3].left() - btnQuick[4].width(), visible[4]);*/
				}
				btnQuick[horizontal].setPos(width-btnQuick[horizontal].width(), 0+btnQuick[horizontal].height());
				for(int i = horizontal+1; i < QuickSlot.SIZE; i++) {
					btnQuick[i].border(2, 2);
					btnQuick[i].frame(64, 0, 22, 24);
					btnQuick[i].setPos(width-btnQuick[i].width(), btnQuick[i-1].bottom());
				}
				break;
		}
		right = width;

		if (CPDSettings.flipToolbar()) {

			btnWait.setPos( (right - btnWait.right()), y);
			btnSearch.setPos( (right - btnSearch.right()), y);
			btnInventory.setPos( (right - btnInventory.right()), y);

			for(int i = 0; i < QuickSlot.SIZE; i++) {
				btnQuick[i].setPos( right - btnQuick[i].right(), isVisible[i] ? y + 2 : height + 40);
			}

		}

	}

	public static void updateLayout(){
		if (instance != null) instance.layout();
	}
	
	@Override
	public void update() {
		super.update();
		
		if (lastEnabled != (Dungeon.hero.ready && Dungeon.hero.isAlive())) {
			lastEnabled = (Dungeon.hero.ready && Dungeon.hero.isAlive());
			
			for (Gizmo tool : members) {
				if (tool instanceof Tool) {
					((Tool)tool).enable( lastEnabled );
				}
			}
		}
		
		if (!Dungeon.hero.isAlive()) {
			btnInventory.enable(true);
		}
	}
	
	public void pickup( Item item, int cell ) {
		pickedUp.reset( item,
			cell,
			btnInventory.centerX(),
			btnInventory.centerY());
	}
	
	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			instance.examining = false;
			GameScene.examineCell( cell );
		}
		@Override
		public String prompt() {
			return Messages.get(Toolbar.class, "examine_prompt");
		}
	};
	
	private static class Tool extends Button {
		
		private static final int BGCOLOR = 0x7B8073;
		
		private Image base;
		
		public Tool( int x, int y, int width, int height ) {
			super();

			hotArea.blockWhenInactive = true;
			frame(x, y, width, height);
		}

		public void frame( int x, int y, int width, int height) {
			base.frame( x, y, width, height );

			this.width = width;
			this.height = height;
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			base = new Image( Assets.TOOLBAR );
			add( base );
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			base.x = x;
			base.y = y;
		}
		
		@Override
		protected void onTouchDown() {
			base.brightness( 1.4f );
		}
		
		@Override
		protected void onTouchUp() {
			if (active) {
				base.resetColor();
			} else {
				base.tint( BGCOLOR, 0.7f );
			}
		}
		
		public void enable( boolean value ) {
			if (value != active) {
				if (value) {
					base.resetColor();
				} else {
					base.tint( BGCOLOR, 0.7f );
				}
				active = value;
			}
		}
	}
	
	private static class QuickslotTool extends Tool {
		
		private QuickSlotButton slot;
		private int borderLeft = 2;
		private int borderRight = 2;
		
		public QuickslotTool( int x, int y, int width, int height, int slotNum ) {
			super( x, y, width, height );

			slot = new QuickSlotButton( slotNum );
			add( slot );
		}

		public void border( int left, int right ){
			borderLeft = left;
			borderRight = right;
			layout();
		}
		
		@Override
		protected void layout() {
			super.layout();
			slot.setRect( x + borderLeft, y + 2, width - borderLeft-borderRight, height - 4 );
		}
		
		@Override
		public void enable( boolean value ) {
			super.enable( value );
			slot.enable( value );
		}
	}
	
	public static class PickedUpItem extends ItemSprite {
		
		private static final float DURATION = 0.5f;
		
		private float startScale;
		private float startX, startY;
		private float endX, endY;
		private float left;
		
		public PickedUpItem() {
			super();
			
			originToCenter();
			
			active =
			visible =
				false;
		}
		
		public void reset( Item item, int cell, float endX, float endY ) {
			view( item );
			
			active =
			visible =
				true;
			
			PointF tile = DungeonTerrainTilemap.raisedTileCenterToWorld(cell);
			Point screen = Camera.main.cameraToScreen(tile.x, tile.y);
			PointF start = camera().screenToCamera(screen.x, screen.y);
			
			x = this.startX = start.x - ItemSprite.SIZE / 2;
			y = this.startY = start.y - ItemSprite.SIZE / 2;
			
			this.endX = endX - ItemSprite.SIZE / 2;
			this.endY = endY - ItemSprite.SIZE / 2;
			left = DURATION;
			
			scale.set( startScale = Camera.main.zoom / camera().zoom );
			
		}
		
		@Override
		public void update() {
			super.update();
			
			if ((left -= Game.elapsed) <= 0) {
				
				visible =
				active =
					false;
				if (emitter != null) emitter.on = false;
				
			} else {
				float p = left / DURATION;
				scale.set( startScale * (float)Math.sqrt( p ) );
				
				x = startX*p + endX*(1-p);
				y = startY*p + endY*(1-p);
			}
		}
	}
}
