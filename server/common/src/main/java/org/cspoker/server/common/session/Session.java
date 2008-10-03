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
package org.cspoker.server.common.game.session;

import org.cspoker.server.common.player.GameSeatedPlayer;
import org.cspoker.server.common.player.PlayerFactory;
import org.cspoker.server.common.playercommunication.PlayerCommunicationImpl;

public class Session {

	private final String username;

	private GameSeatedPlayer player = null;
	private PlayerCommunicationImpl playerComm = null;
	private boolean killed = false;

	public Session(String username) {
		this.username = username;
	}

	public synchronized String getUserName() {
		return username;
	}

	public synchronized GameSeatedPlayer getPlayer() throws PlayerKilledExcepion {
		if (killed) {
			throw new PlayerKilledExcepion(player);
		}
		if (player == null) {
			player = PlayerFactory.global_Player_Factory
					.createNewPlayer(username);
		}
		return player;
	}

	public synchronized PlayerCommunicationImpl getPlayerCommunication()
			throws PlayerKilledExcepion {
		if (killed) {
			throw new PlayerKilledExcepion(player);
		}
		if (playerComm == null) {
			playerComm = new PlayerCommunicationImpl(getPlayer());
		}
		return playerComm;
	}

	public synchronized void kill() {
		killed = true;
		playerComm.kill();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Session other = (Session) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

}
