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
package org.cspoker.server.rmi;

import org.cspoker.common.api.lobby.DelegatingLobbyContext;
import org.cspoker.common.api.lobby.LobbyContext;
import org.cspoker.common.api.lobby.event.LobbyListener;
import org.cspoker.common.api.lobby.holdemtable.HoldemTableContext;

public class AsynchronousLobbyContext extends DelegatingLobbyContext {

	public AsynchronousLobbyContext(LobbyContext lobbyContext) {
		super(lobbyContext);
	}
	
	@Override
	public HoldemTableContext getHoldemTableContext(long tableId) {
		return null;
		//TODO
	}
	
	@Override
	public void subscribe(LobbyListener lobbyListener) {
		//TODO
	}
	
	@Override
	public void unSubscribe(LobbyListener lobbyListener) {
		//TODO
	}
	
}
