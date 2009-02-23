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
package org.cspoker.client.bots.bot.search.node.expander;

import java.util.Set;

import org.cspoker.client.bots.bot.search.action.ActionWrapper;
import org.cspoker.client.bots.bot.search.action.EvaluatedAction;
import org.cspoker.client.bots.bot.search.node.InnerGameTreeNode;

public abstract class Expander {

	protected final InnerGameTreeNode node;
	protected final int tokens;

	public Expander(InnerGameTreeNode node, int tokens) {
		this.node = node;
		this.tokens = tokens;
	}
	
	public abstract Set<? extends EvaluatedAction<? extends ActionWrapper>> expand();

	public int getTokens() {
		return tokens;
	}
	
	public InnerGameTreeNode getNode() {
		return node;
	}
	
	public static interface Factory{
		Expander create(InnerGameTreeNode node, int tokens);
	}
	
}
