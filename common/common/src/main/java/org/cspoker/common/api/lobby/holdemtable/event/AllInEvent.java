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

import javax.xml.bind.annotation.XmlRootElement;

import org.cspoker.common.api.lobby.holdemtable.listener.HoldemTableListener;
import org.cspoker.common.elements.player.Player;
import org.cspoker.common.elements.pots.Pots;

@XmlRootElement
public class AllInEvent extends HoldemTableEvent {
	
	private static final long serialVersionUID = 2029273959014493873L;

	private Player player;
	
	private Pots pots;
	
	public AllInEvent(Player player, Pots pots){
		this.player = player;
		this.pots = pots;
	}
	
	protected AllInEvent(){
		// no op
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Pots getPots(){
		 return pots;
	}

	@Override
	public void dispatch(HoldemTableListener holdemTableListener) {
		holdemTableListener.onAllIn(this);
	}

}