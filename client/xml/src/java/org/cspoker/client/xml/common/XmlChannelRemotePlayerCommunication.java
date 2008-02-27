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
package org.cspoker.client.xml.common;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.cspoker.common.RemotePlayerCommunication;
import org.cspoker.common.elements.table.TableId;
import org.cspoker.common.eventlisteners.RemoteAllEventsListener;
import org.cspoker.common.exceptions.IllegalActionException;
import org.cspoker.common.util.SpreadingAllEventsListener;
import org.cspoker.common.xml.actions.AllInAction;
import org.cspoker.common.xml.actions.BetAction;
import org.cspoker.common.xml.actions.CallAction;
import org.cspoker.common.xml.actions.CheckAction;
import org.cspoker.common.xml.actions.CreateTableAction;
import org.cspoker.common.xml.actions.DealAction;
import org.cspoker.common.xml.actions.FoldAction;
import org.cspoker.common.xml.actions.JoinTableAction;
import org.cspoker.common.xml.actions.KillAction;
import org.cspoker.common.xml.actions.LeaveTableAction;
import org.cspoker.common.xml.actions.RaiseAction;
import org.cspoker.common.xml.actions.SayAction;
import org.cspoker.common.xml.actions.StartGameAction;

public class XmlChannelRemotePlayerCommunication implements
		RemotePlayerCommunication {

	private final Set<RemoteAllEventsListener> listeners = Collections
			.synchronizedSet(new HashSet<RemoteAllEventsListener>());
	private final XmlChannelMarshaller marshaller;
	private AtomicLong id = new AtomicLong(1);
	private final XmlChannel c;

	public XmlChannelRemotePlayerCommunication(XmlChannel c) {
		marshaller = new XmlChannelMarshaller(c,
				new SpreadingAllEventsListener(listeners));
		this.c = c;
	}

	public void subscribeAllEventsListener(RemoteAllEventsListener listener)
			throws RemoteException {
		listeners.add(listener);
	}

	public void unsubscribeAllEventsListener(RemoteAllEventsListener listener)
			throws RemoteException {
		listeners.remove(listener);
	}

	private long getId() {
		return id.getAndIncrement();
	}

	public Set<RemoteAllEventsListener> getListeners() {
		return listeners;
	}

	public XmlChannelMarshaller getMarshaller() {
		return marshaller;
	}

	public XmlChannel getChannel() {
		return c;
	}

	@Override
	public void allIn() throws IllegalActionException, RemoteException {
		marshaller.perform(new AllInAction(getId()));
	}

	@Override
	public void bet(int amount) throws IllegalActionException, RemoteException {
		marshaller.perform(new BetAction(getId(), amount));
	}

	@Override
	public void call() throws IllegalActionException, RemoteException {
		marshaller.perform(new CallAction(getId()));
	}

	@Override
	public void check() throws IllegalActionException, RemoteException {
		marshaller.perform(new CheckAction(getId()));
	}

	@Override
	public TableId createTable() throws IllegalActionException, RemoteException {
		return marshaller.perform(new CreateTableAction(getId()));
	}

	@Override
	public void deal() throws IllegalActionException, RemoteException {
		marshaller.perform(new DealAction(getId()));
	}

	@Override
	public void fold() throws IllegalActionException, RemoteException {
		marshaller.perform(new FoldAction(getId()));
	}

	@Override
	public void joinTable(TableId id) throws IllegalActionException,
			RemoteException {
		marshaller.perform(new JoinTableAction(getId(), id));
	}

	@Override
	public void kill() throws IllegalActionException, RemoteException {
		marshaller.perform(new KillAction(getId()));
		c.close();
	}

	@Override
	public void leaveTable() throws IllegalActionException, RemoteException {
		marshaller.perform(new LeaveTableAction(getId()));
	}

	@Override
	public void raise(int amount) throws IllegalActionException,
			RemoteException {
		marshaller.perform(new RaiseAction(getId(), amount));
	}

	@Override
	public void say(String message) throws RemoteException,
			IllegalActionException {
		marshaller.perform(new SayAction(getId(), message));
	}

	@Override
	public void startGame() throws IllegalActionException, RemoteException {
		marshaller.perform(new StartGameAction(getId()));
	}
}
