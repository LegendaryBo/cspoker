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
package org.cspoker.client.bots.bot.rule;

import java.util.concurrent.ExecutorService;

import org.cspoker.client.bots.bot.Bot;
import org.cspoker.client.bots.bot.BotFactory;
import org.cspoker.client.bots.listener.BotListener;
import org.cspoker.client.common.SmartLobbyContext;
import org.cspoker.common.elements.player.PlayerId;
import org.cspoker.common.elements.table.TableId;

public class RuleBasedBotFactory1 implements BotFactory {

	private static int copies = 0;
	private final int copy;

	public RuleBasedBotFactory1() {
		this.copy = ++copies;
	}
	
	/**
	 * @see org.cspoker.client.bots.bot.BotFactory#createBot(org.cspoker.common.elements.player.PlayerId, org.cspoker.common.elements.table.TableId, org.cspoker.client.common.SmartLobbyContext, java.util.concurrent.ExecutorService, org.cspoker.client.bots.listener.BotListener[])
	 */
	public Bot createBot(PlayerId playerId, TableId tableId,
			SmartLobbyContext lobby, int buyIn, ExecutorService executor,
			BotListener... botListeners) {
		copies++;
		return new RuleBasedBot1(playerId, tableId, lobby, buyIn, executor, botListeners);
	}

	@Override
	public String toString() {
		return "RuleBasedBot v1-"+copy;
	}
}
