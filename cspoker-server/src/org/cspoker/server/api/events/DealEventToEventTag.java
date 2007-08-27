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
package org.cspoker.server.api.events;

import javax.xml.transform.sax.TransformerHandler;

import org.cspoker.server.game.events.GameEvent;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DealEventToEventTag extends EventToEventTag{

    protected void addChildren(TransformerHandler response, GameEvent event) throws SAXException{
	super.addChildren(response, event);
	AttributesImpl attrs = new AttributesImpl();
	response.startElement("", "deal", "deal", attrs);
	response.endElement("", "deal", "deal");

    }
}
