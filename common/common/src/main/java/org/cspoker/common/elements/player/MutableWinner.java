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

package org.cspoker.common.elements.player;

import org.cspoker.common.elements.chips.Chips;

public class MutableWinner {

	private final MutableSeatedPlayer winner;

	private final Chips chips = new Chips();

	public MutableWinner(MutableSeatedPlayer winner) {
		this.winner = winner;
	}

	public Chips getGainedChipsPile() {
		return chips;
	}

	public void transferGainedChipsToPlayer() {
		chips.transferAllChipsTo(winner.getStack());
	}

	public boolean hasGainedChips() {
		return chips.getValue() > 0;
	}

	public Winner getSavedWinner() {
		return new Winner(winner.getId(), chips.getValue());
	}

	@Override
	public String toString() {
		return winner.getName() + " has gained " + chips + ".";
	}

}
