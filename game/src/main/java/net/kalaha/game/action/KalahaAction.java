package net.kalaha.game.action;

import net.kalaha.game.logic.KalahaBoard; 

public abstract class KalahaAction extends AbstractAction {

	public abstract void perform(KalahaBoard b);

}
