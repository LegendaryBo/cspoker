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

package org.cspoker.common.api.lobby.holdemtable.event;

import net.jcip.annotations.Immutable;

import org.cspoker.common.api.lobby.holdemtable.listener.HoldemTableListener;
import org.cspoker.common.elements.player.Player;

/**
 * A class to represent leaving player events.
 * 
 * @author Kenzo
 * 
 */
@Immutable
public class LeaveTableEvent extends HoldemTableEvent {

	private static final long serialVersionUID = -5339079807813674278L;

	private final Player player;
	
	public LeaveTableEvent(Player player) {
		this.player = player;
	}

	protected LeaveTableEvent() {
		player = null;
	}

	@Override
	public String toString() {
		return player.getName() + " has left this table.";
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void dispatch(HoldemTableListener holdemTableListener) {
		holdemTableListener.onLeaveTable(this);
	}

}
