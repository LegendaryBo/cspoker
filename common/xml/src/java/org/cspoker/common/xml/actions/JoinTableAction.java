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
package org.cspoker.common.xml.actions;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.cspoker.common.PlayerCommunication;
import org.cspoker.common.elements.table.TableId;
import org.cspoker.common.exceptions.IllegalActionException;
import org.cspoker.common.xml.eventlisteners.invocation.AllInvocationEventsListener;
import org.cspoker.common.xml.events.invocation.IllegalActionEvent;
import org.cspoker.common.xml.events.invocation.SuccessfulInvocationEvent;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JoinTableAction extends PlayerCommunicationAction {

	private static final long serialVersionUID = -6693307709200837257L;

	private TableId tableId;

	public JoinTableAction(long id, TableId tableId) {
		super(id);
		this.tableId = tableId;
	}

	protected JoinTableAction() {
		// no op
	}

	@Override
	public void perform(PlayerCommunication pc,
			AllInvocationEventsListener listener) {
		try {
			pc.joinTable(tableId);
			listener
					.onSuccessfullInvokation(new SuccessfulInvocationEvent<Void>(
							this, null));
		} catch (IllegalActionException e) {
			listener.onIllegalAction(new IllegalActionEvent(e, this));
		}
	}

}