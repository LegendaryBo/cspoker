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
package org.cspoker.client.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.login.LoginException;

import org.cspoker.common.RemoteLoginServer;
import org.cspoker.common.RemotePlayerCommunication;
import org.cspoker.common.elements.GameProperty;
import org.cspoker.common.elements.table.SeatId;
import org.cspoker.common.elements.table.Table;
import org.cspoker.common.elements.table.TableId;
import org.cspoker.common.elements.table.TableList;
import org.cspoker.common.eventlisteners.RemoteAllEventsListener;
import org.cspoker.common.exceptions.IllegalActionException;

public class RemoteLoginServerForRMI implements RemoteLoginServer {

	private RemoteLoginServer server;

	public RemoteLoginServerForRMI(String server) throws AccessException,
			RemoteException, NotBoundException {
		this(server, 1099);
	}

	public RemoteLoginServerForRMI(String server, int port)
			throws AccessException, RemoteException, NotBoundException {
		System.setSecurityManager(null);
		Registry registry = LocateRegistry.getRegistry(server, port);
		this.server = (RemoteLoginServer) registry.lookup("CSPokerServer");
	}

	public RemotePlayerCommunication login(String username, String password)
			throws RemoteException, LoginException {

		final RemotePlayerCommunication p = server.login(username, password);

		return new RemotePlayerCommunication() {

			private Map<RemoteAllEventsListener,RemoteAllEventsListener> listeners = new ConcurrentHashMap<RemoteAllEventsListener, RemoteAllEventsListener>();
			
			public void allIn() throws IllegalActionException, RemoteException {
				p.allIn();
			}

			public void bet(int amount) throws IllegalActionException,
					RemoteException {
				p.bet(amount);

			}

			public void call() throws IllegalActionException, RemoteException {
				p.call();
			}

			public void check() throws IllegalActionException, RemoteException {
				p.check();
			}

			public TableId createTable(String name) throws IllegalActionException,
					RemoteException {
				return p.createTable(name);
			}
			
			public TableId createTable(String name, GameProperty settings) throws IllegalActionException,
			RemoteException {
				return p.createTable(name, settings);
			}

			public void fold() throws IllegalActionException, RemoteException {
				p.fold();
			}

			public void leaveTable() throws IllegalActionException,
					RemoteException {
				p.leaveTable();
			}

			public void raise(int amount) throws IllegalActionException,
					RemoteException {
				p.raise(amount);
			}

			public void say(String message) throws RemoteException,
					IllegalActionException {
				p.say(message);
			}

			public void startGame() throws IllegalActionException,
					RemoteException {
				p.startGame();
			}

			public void kill() throws IllegalActionException, RemoteException {
				p.kill();
			}

			public void subscribeAllEventsListener(
					RemoteAllEventsListener listener) throws RemoteException {
				RemoteAllEventsListener wrapped = new RemoteifyingListener(listener);
				RemoteAllEventsListener listenerStub = (RemoteAllEventsListener) UnicastRemoteObject
						.exportObject(wrapped, 0);
				listeners.put(listener, listenerStub);
				p.subscribeAllEventsListener(listenerStub);
			}

			public void unsubscribeAllEventsListener(
					RemoteAllEventsListener listener) throws RemoteException {
				RemoteAllEventsListener listenerStub = listeners.get(listener);
				p.unsubscribeAllEventsListener(listenerStub);
				UnicastRemoteObject.unexportObject(listenerStub, true);
				listeners.remove(listener);
			}

			@Override
			public Table getTable(TableId id) throws IllegalActionException,
					RemoteException {
				return p.getTable(id);
			}

			@Override
			public TableList getTables() throws RemoteException {
				return p.getTables();
			}

			@Override
			public Table joinTable(TableId id) throws IllegalActionException,
					RemoteException {
				return p.joinTable(id);
			}

			@Override
			public Table joinTable(TableId tableId, SeatId seatId)
					throws IllegalActionException, RemoteException {
				return p.joinTable(tableId, seatId);
			}

		};
	}

}
