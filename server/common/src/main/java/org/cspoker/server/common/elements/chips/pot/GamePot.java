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

package org.cspoker.server.common.game.elements.chips.pot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cspoker.server.common.game.elements.chips.Chips;
import org.cspoker.server.common.game.elements.chips.IllegalValueException;
import org.cspoker.server.common.game.player.GameSeatedPlayer;

/**
 * The pot contains both a list of players and the chips that should be divided
 * between them.
 * 
 * There can be several pots, especially with the case of an all-in player.
 * 
 * 
 * @author Kenzo
 * 
 * @invar The chips of this pot is effective. |getChips()!=null
 * 
 */
public class GamePot {

	/**
	 * All the players that can take a share of this pot.
	 */
	private final List<GameSeatedPlayer> players;

	/**
	 * The pile of chips in this pot.
	 */
	private final Chips chips;

	/***************************************************************************
	 * Constructor
	 **************************************************************************/

	/**
	 * Construct an empty pot.
	 * 
	 * @post The initial amount of chips is zero. |getChips().getValue()==0
	 */
	public GamePot() {
		chips = new Chips();
		players = new ArrayList<GameSeatedPlayer>();
	}

	/**
	 * Get the pile of chips in this pot.
	 */
	public Chips getChips() {
		return chips;
	}

	public List<GameSeatedPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public void addShowdownPlayer(GameSeatedPlayer player) {
		players.add(player);
	}

	public void transferAmountToPot(int amount, GamePot pot)
			throws IllegalValueException {
		getChips().transferAmountTo(amount, pot.getChips());
	}

	public void transferAllChipsTo(GamePot pot) {
		try {
			getChips().transferAmountTo(getChips().getValue(), pot.getChips());
		} catch (IllegalValueException e) {
			assert false;
		}
	}

	/**
	 * Returns the value of the chips in this post
	 */
	public int getValue() {
		return getChips().getValue();
	}

	public String toString() {
		String toReturn = getValue() + " chips in this pot. Showdown players: ";
		for (GameSeatedPlayer player : players) {
			toReturn += player.getName() + " ";
		}
		return toReturn;
	}
}
