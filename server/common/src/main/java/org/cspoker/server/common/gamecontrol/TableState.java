/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package org.cspoker.server.common.gamecontrol;

import java.util.List;

import org.cspoker.common.api.shared.exception.IllegalActionException;
import org.cspoker.common.elements.player.SeatedPlayer;
import org.cspoker.server.common.elements.id.SeatId;
import org.cspoker.server.common.player.GameSeatedPlayer;

public abstract class TableState {
	
	protected final PokerTable table;

	public TableState(PokerTable table){
		this.table = table;
	}

	/***************************************************************************
	 * Player methods
	 **************************************************************************/

	/**
	 * The player puts money in the pot.
	 * 
	 * @param player
	 *            The player who puts a bet.
	 * @param amount
	 *            The amount of the bet.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void bet(GameSeatedPlayer player, int amount) throws IllegalActionException;

	/**
	 * To put into the pot an amount of money equal to the most recent bet or
	 * raise.
	 * 
	 * @param player
	 *            The player who calls.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void call(GameSeatedPlayer player)
			throws IllegalActionException;

	/**
	 * If there is no bet on the table and you do not wish to place a bet. You
	 * may only check when there are no prior bets.
	 * 
	 * @param player
	 *            The player who checks.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void check(GameSeatedPlayer player)
			throws IllegalActionException;

	/**
	 * Raise the bet with given amount.
	 * 
	 * @param player
	 *            The player who raises the current bet.
	 * @param amount
	 *            The amount with which to raise the bet.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void raise(GameSeatedPlayer player, int amount)
			throws IllegalActionException;

	/**
	 * The given player folds the cards.
	 * 
	 * The player will not be able to take any actions in the coming rounds of
	 * the current deal.
	 * 
	 * @param player
	 *            The player who folds.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void fold(GameSeatedPlayer player)
			throws IllegalActionException;

	/**
	 * The player who the dealer-button has been dealt to can choose to start
	 * the deal. From that moment, new players can not join the on-going deal.
	 * 
	 * @param player
	 *            The player who deals.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void deal(GameSeatedPlayer player)
			throws IllegalActionException;

	/**
	 * The given player goes all-in.
	 * 
	 * @param player
	 *            The player who goes all-in.
	 * @throws IllegalActionException
	 *             [must] It's not the turn of the given player.
	 * @throws IllegalActionException
	 *             [must] The action performed is not a valid action.
	 */
	public abstract void allIn(GameSeatedPlayer player)
			throws IllegalActionException;

	public abstract void joinTable(SeatId seatId, GameSeatedPlayer player)
			throws IllegalActionException;

	public abstract void leaveTable(GameSeatedPlayer player)
			throws IllegalActionException;

	
	public abstract List<SeatedPlayer> getSeatedPlayers();
	
	public abstract List<GameSeatedPlayer> getSeatedServerPlayers();

	/**
	 * Check whether players are playing or not at this table.
	 * 
	 * @return True if players are playing at this table, False otherwise.
	 */
	public abstract boolean isPlaying();
}
