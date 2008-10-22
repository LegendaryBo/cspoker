package org.cspoker.server.common;

import org.cspoker.common.api.lobby.holdemtable.holdemplayer.context.HoldemPlayerContext;
import org.cspoker.common.api.shared.exception.IllegalActionException;
import org.cspoker.server.common.gamecontrol.PokerTable;
import org.cspoker.server.common.player.GameSeatedPlayer;

public class HoldemPlayerContextImpl
		implements HoldemPlayerContext {
	
	private GameSeatedPlayer player;
	
	private PokerTable table;
	
	public HoldemPlayerContextImpl(GameSeatedPlayer player, PokerTable table) {
		this.player = player;
		this.table = table;
	}
	
	public void betOrRaise(int amount)
			throws IllegalActionException {
		try {
			table.bet(player, amount);
		} catch (IllegalActionException e) {
			table.raise(player, amount);
		}
		
	}
	
	public void checkOrCall()
			throws IllegalActionException {
		try {
			table.check(player);
		} catch (IllegalActionException e) {
			table.call(player);
		}
		
	}
	
	public void fold()
			throws IllegalActionException {
		table.fold(player);
	}
	
	/**
	 * FIXME Leaving the Game and Sitting Out should NOT be the same!
	 * 
	 * @see org.cspoker.common.api.lobby.holdemtable.holdemplayer.context.HoldemPlayerContext#leaveGame()
	 */
	public void leaveGame() {
		table.sitOut(player);
	}
	
}