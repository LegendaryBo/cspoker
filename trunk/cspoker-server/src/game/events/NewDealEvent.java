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

package game.events;

import game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent new deal events.
 * 
 * @author Kenzo
 *
 */
public class NewDealEvent extends GameEvent {
	
	private final List<Player> players;
	
	private final Player dealer;
	
	public NewDealEvent(List<Player> players, Player dealer){
		this.players = Collections.unmodifiableList(new ArrayList<Player>(players));
		this.dealer = dealer;
	}
	
	public Player getDealer(){
		return dealer;
	}

	@Override
	public String[] getAction() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(){
		String toReturn ="A new deal with ";
		for(Player player:players){
			toReturn+=player.getName();
			toReturn+=", ";
		}
		return toReturn.substring(0, toReturn.length()-2)+". "+dealer.getName()+" is dealer.";
	}

}