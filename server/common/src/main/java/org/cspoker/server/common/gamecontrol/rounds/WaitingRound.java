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

package org.cspoker.server.common.gamecontrol.rounds;

import java.util.ArrayList;
import java.util.List;

import org.cspoker.common.api.lobby.holdemtable.event.NewDealEvent;
import org.cspoker.common.api.shared.exception.IllegalActionException;
import org.cspoker.common.elements.player.SeatedPlayer;
import org.cspoker.server.common.gamecontrol.Game;
import org.cspoker.server.common.gamecontrol.PokerTable;
import org.cspoker.server.common.player.MutableSeatedPlayer;

public class WaitingRound extends Round {
	
	public WaitingRound(PokerTable gameMediator, Game game) {
		super(gameMediator, game);
		game.changeCurrentPlayerToDealer();
	}

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

	public void deal(MutableSeatedPlayer player) throws IllegalActionException {
		// Check whether the given player can do this action.
		if (!onTurn(player)) {
			throw new IllegalActionException(player.getName()
					+ " can not deal in this round.");
		}

		if (!(game.getTable().getNbPlayers() > 1)) {
			throw new IllegalActionException(
					"There should at least be 2 players to begin a new deal.");
		}
		playerMadeEvent(player);
		// This will force the game control to end the waiting round
		// and change to the preflop round.
	}

	public void endRound() {
		game.dealNewHand();
		List<SeatedPlayer> players = new ArrayList<SeatedPlayer>(game
				.getNbCurrentDealPlayers());
		for (MutableSeatedPlayer player : game.getCurrentDealPlayers()) {
			players.add(player.getMemento());
		}
		gameMediator.publishNewDealEvent(new NewDealEvent(players, game
				.getDealer().getMemento()));
	}

	public Round getNextRound() {
		return new PreFlopRound(gameMediator, getGame());
	}

	public boolean isLowBettingRound() {
		return false;
	}

	public boolean isHighBettingRound() {
		return false;
	}

	public String toString() {
		return "waiting round";
	}
}
