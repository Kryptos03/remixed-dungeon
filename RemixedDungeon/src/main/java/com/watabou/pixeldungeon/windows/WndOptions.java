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
package com.watabou.pixeldungeon.windows;

import com.nyrds.android.util.GuiProperties;
import com.nyrds.pixeldungeon.windows.VBox;
import com.watabou.noosa.StringsManager;
import com.watabou.noosa.Text;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Window;

public class WndOptions extends Window {

	private static final int WIDTH         = 120;


	public WndOptions( String title, String message, String... options ) {
		super();

        VBox vbox = new VBox();
        vbox.setGap(GAP);

		Text tfTitle = PixelScene.createMultiline(StringsManager.maybeId(title), GuiProperties.titleFontSize() );
		tfTitle.hardlight( TITLE_COLOR );
		tfTitle.x = GAP;
		tfTitle.maxWidth(WIDTH - GAP * 2);
		vbox.add( tfTitle );
		
		Text tfMessage = PixelScene.createMultiline(StringsManager.maybeId(message), GuiProperties.regularFontSize() );
		tfMessage.maxWidth(WIDTH - GAP * 2);
		tfMessage.x = GAP;
		vbox.add( tfMessage );

		VBox buttonsVbox = new VBox();
		for (int i=0; i < options.length; i++) {
			final int index = i;
			RedButton btn = new RedButton( StringsManager.maybeId(options[i]) ) {
				@Override
				protected void onClick() {
					hide();
					onSelect( index );
				}
			};

			btn.setSize(WIDTH - GAP * 2, BUTTON_HEIGHT);
			buttonsVbox.add( btn );
		}

		buttonsVbox.setRect(0,0,WIDTH,buttonsVbox.childsHeight());
		vbox.add(buttonsVbox);

		vbox.setRect(0,0,WIDTH,vbox.childsHeight());
		add(vbox);
		resize( WIDTH, (int) vbox.height());
	}

	protected void onSelect( int index ) {}
}
