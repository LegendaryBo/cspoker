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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.cspoker.common.elements.table.SeatId;

/**
 * A class of immutable players.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SeatedPlayer extends Player {

	private static final long serialVersionUID = -9200622390366978194L;

	private SeatId seatId;

	/**
	 * The stack of this player.
	 */
	private int stackValue;

	/**
	 * The chips the player has bet in this round.
	 * 
	 */
	private int betChipsValue;

	public SeatedPlayer(PlayerId id, SeatId seatId, String name, int stackValue,
			int betChipsValue) {
		super(id, name);
		this.seatId = seatId;
		this.stackValue = stackValue;
		this.betChipsValue = betChipsValue;
	}

	protected SeatedPlayer() {
		// no op
	}

	/**
	 * Returns the seat id of this player.
	 * 
	 * @return The seat id of this player.
	 */
	public SeatId getSeatId() {
		return seatId;
	}

	/**
	 * Returns the stack value of this player.
	 * 
	 * @return The stack value of this player.
	 */
	public int getStackValue() {
		return stackValue;
	}

	/**
	 * Returns the bet chips value of this player.
	 * 
	 * @return The bet chips value of this player.
	 */
	public int getBetChipsValue() {
		return betChipsValue;
	}

	@Override
	public String toString() {
		return getName()+" (#"+getId()+")";
	}

}
