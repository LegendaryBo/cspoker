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
package org.cspoker.client.common.gamestate.modifiers;

import org.cspoker.client.common.gamestate.ForwardingGameState;
import org.cspoker.client.common.gamestate.ForwardingPlayerState;
import org.cspoker.client.common.gamestate.GameState;
import org.cspoker.client.common.gamestate.PlayerState;
import org.cspoker.common.api.lobby.holdemtable.event.HoldemTableEvent;
import org.cspoker.common.api.lobby.holdemtable.event.RaiseEvent;
import org.cspoker.common.elements.player.PlayerId;

public class RaiseState extends ForwardingGameState {

	private final RaiseEvent event;
	private final int newBetSize;
	private final int newPotSize;
	private final PlayerState playerState;

	public RaiseState(GameState gameState, RaiseEvent event) {
		super(gameState);
		this.event = event;
		PlayerState oldPlayerState = super.getPlayer(event.getPlayerId());
		this.newBetSize = super.getLargestBet()+event.getAmount();
		int chipsMoved = newBetSize-oldPlayerState.getBet();
		final int newStack = oldPlayerState.getStack()-chipsMoved;
		this.newPotSize = super.getRoundPotSize()+chipsMoved;
		playerState = new ForwardingPlayerState(oldPlayerState){
			@Override
			public int getBet() {
				return RaiseState.this.newBetSize;
			}
			
			@Override
			public PlayerId getPlayerId() {
				return RaiseState.this.event.getPlayerId();
			}
			
			@Override
			public int getStack() {
				return newStack;
			}
			
			@Override
			public boolean hasFolded() {
				return false;
			}
			
			@Override
			public boolean sitsIn() {
				return true;
			}
		};
	}
	
	@Override
	public PlayerState getPlayer(PlayerId playerId) {
		if(event.getPlayerId().equals(playerId)){
			return playerState;
		}
		return super.getPlayer(playerId);
	}

	@Override
	public int getLargestBet() {
		return newBetSize;
	}

	@Override
	public int getMinNextRaise() {
		return event.getAmount();
	}

	@Override
	public int getRoundPotSize() {
		return newPotSize;
	}
	
	public HoldemTableEvent getLastEvent() {
		return event;
	}
	
	@Override
	public PlayerId getLastBettor() {
		return event.getPlayerId();
	}
	
}