package models.connection
{
	
	import cs.*;
	
	import flash.events.*;
	import flash.net.*;
	import flash.security.*;
	import flash.xml.*;
	
	import models.*;
	
	import mx.rpc.xml.SimpleXMLDecoder;
		
	public class MessageCenter extends Object
	{
		private var contentObj:Object;
		private var idAction:int = 0;
		private var socket:XMLSocket = null;
		
		private var mTables:CSTableList = null;
		private var mTable:CSTable = null;
		
		public var loginActionId:int = 0;
		public var joinTableActionId:int = 0;
		
		private var loggedIn:Boolean = false;

		
		public function MessageCenter()
		{
			return;
		}
	
	
		public function setSocket(socket:XMLSocket):void{
			this.socket = socket;
		}
	
		public function handleDisconnect() : void
        {
            Main.showServerMessage("You have been disconnected from the server.  Please reload the application and try again...");
            Main.showConnectionBox();
            return;
        }// end function
        	
		public function parseDataIn(strIn:String):void{
			
			
			var xmlDoc:XMLDocument = new XMLDocument(strIn);
			
			var decoder:SimpleXMLDecoder=new SimpleXMLDecoder(true);
					
			try{
				contentObj=decoder.decodeXML(xmlDoc);			
			}catch(e:Error){
				trace("error decoding xml: " + strIn);
				return;
			}
			var traceContentObj:Object = contentObj;
			var objAction:Object;
			var objResult:Object;
			
	
			if (contentObj.hasOwnProperty("actionPerformedEvent") && contentObj.actionPerformedEvent.id == loginActionId){
				trace("LOGIN SUCCESSFUL!!!");
				Main.lobby.receiveLoginSucess();
				return;
			}
			
			if (contentObj.hasOwnProperty("actionPerformedEvent") && contentObj.actionPerformedEvent.id == joinTableActionId){
				trace("JOIN TABLE SUCCESSFUL!!!");
				//var tableId:int = contentObj.actionPerformedEvent.id;
				Main.table.receiveJoinTableSuccess();
				return;
			}
			
			if (contentObj.hasOwnProperty("actionPerformedEvent") && contentObj.actionPerformedEvent.hasOwnProperty("result")){
			 	trace("result returned");
			 	var resultType:String = contentObj.actionPerformedEvent.result["xsi:type"];
			 	var result:Object = contentObj.actionPerformedEvent.result;
		 		switch (resultType){	
			 		case "ns2:tableList":
						
						//mTables = new CSTableList(result.tables);
						
				  		//dispatchEvent(new csEventActions( csEventActions.OnGetTablesAction,"getTablesAction",mTables));
				  		Main.lobby.receiveTablesList(result);
				  		break;
				
					case "ns2:detailedHoldemTable":
				  		
						//mTable = new CSTable(contentObj.successfulInvocationEvent.result);
				  		//dispatchEvent(new csEventActions( csEventActions.OnGetTableAction,"getTableAction",mTable));
				  		Main.lobby.receiveDetailedTableInfo(result);
				  		
				  		break;
				
					case "ns2:playerId":
						Main.receiveClientPlayerId(int(result));
						break;
					
					case "leaveTableAction":
						objAction=contentObj.successfulInvocationEvent.action;
						objResult=contentObj.successfulInvocationEvent.result;
						//dispatchEvent(new csEventActions( csEventActions.OnLeaveTableAction,"leaveTableAction",objResult));
						break;
					
					case "ns2:createTableAction":
						Main.showServerMessage("TABLE CREATED!!!!");
						break;
				}	 
				return;
			}
			
			if(contentObj.hasOwnProperty("holdemTableTreeEventWrapper")){
				// AFTER JOIN TABLE, RECEIVE THESE....
				var event:Object = contentObj.holdemTableTreeEventWrapper.event;
				var eventType:String = event["xsi:type"];
				
				var seatId:int;
				var playerId:int;
				// SWITCH STATEMENT FOR HOLDEM TABLE EVENTS...
				switch(eventType){
					case "ns2:sitInEvent":
						seatId = event.player.seatId;
						trace("sitInEvent received!!! Seat:" + String(seatId));
						Main.table.getSeatById(seatId).receiveSitIn(event.player);
						return;
						break;
					case "ns2:newDealEvent":
						trace("newDealEvent received!!!");
						break;
						
					case "ns2:smallBlindEvent":
						trace("smallBlindEvent received...");
						var smallBlindAmount:int = event["amount"];
						playerId = event["playerId"];
						try{
							Main.table.tableModel.getPlayerByPlayerId(playerId).paySmallBlind(smallBlindAmount);
						}catch(e:Error){
							trace("bigBlind Error: " + e.message);
						}
						break;
						
					case "ns2:bigBlindEvent":
						trace("bigBlindEvent received...");
						var bigBlindAmount:int = event["amount"];
						playerId = event["playerId"];
						try{
							Main.table.tableModel.getPlayerByPlayerId(playerId).payBigBlind(bigBlindAmount);
						}catch(e:Error){
							trace("bigBlind Error: " + e.message);
						}
						return;
						
						break;
					
					
					case "ns2:newRoundEvent":
						var round:String = event.round;
						trace("new Round Event: " + round);
						Main.table.tableModel.receiveNewRoundEvent(event);
						return;
						break;
						
					case "ns2:newCommunityCardsEvent":
						trace("newCommunityCardsEvent");
						var cards:Object = event.communityCards;
						Main.table.tableModel.receiveFlopCardsEvent(cards);
						return;
						break;
					
					case "ns2:potsChangedEvent":
						trace("potsChangedEvent");
						Main.table.tableModel.receivePotsChangedEvent(event.pots);
						return;
						break;
						
					case "ns2:newPocketCardsEvent":
						trace("new pocket cards event!!!");
						Main.table.tableModel.receivePocketCardsEvent(event.pocketCards);
						return;
						break;
					
					case "ns2:nextPlayerEvent":
						trace("nextPlayerEvent received: ");
						var tempPlayerId:int = event["playerId"];
						Main.table.tableModel.getPlayerByPlayerId(tempPlayerId).receiveNextPlayerEvent(tempPlayerId);
						return;
						break;
						
					case "ns2:checkEvent":
						var tempPlayerId1:int = event["playerId"];
						Main.table.tableModel.getPlayerByPlayerId(tempPlayerId1).checkEvent();
						return;
						break;
					
					case "ns2:foldEvent":
						var tempPlayerId2:int = event["playerId"];
						Main.table.tableModel.getPlayerByPlayerId(tempPlayerId2).foldEvent();
						return;
						break;
						
					case "ns2:raiseEvent":
						var tempPlayerId3:int = event["playerId"];
						var amount:int = event["amount"];
						Main.table.tableModel.getPlayerByPlayerId(tempPlayerId3).betEvent(amount);
						return;
						break;
						
					case "ns2:allInEvent":
						var tempPlayerId4:int = event["playerId"];
						Main.table.tableModel.getPlayerByPlayerId(tempPlayerId4).allInEvent();
						return;
						break;
						
					case "ns2:winnerEvent":
						trace("winner Event received!!!");
						var winners:Object = event.winners;
						/*
						<event xsi:type="ns2:winnerEvent" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <winners>
            <gainedAmount>110</gainedAmount>
            <player xsi:type="ns2:seatedPlayer" name="guy" id="0">
                <seatId>2</seatId>
                <stackValue>145</stackValue>
                <betChipsValue>0</betChipsValue>
                <sittingIn>true</sittingIn>
            </player>
        </winners>
    </event>
    					*/
						return;
						break;
						
					case "ns2:showHandEvent":
						trace("show hand event: ");
						
						/*
						<event xsi:type="ns2:showHandEvent" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <player name="kenzo" id="1">
            <cards>
                <card suit="SPADES" rank="NINE"/>
                <card suit="CLUBS" rank="SEVEN"/>
                <card suit="DIAMONDS" rank="EIGHT"/>
                <card suit="SPADES" rank="SEVEN"/>
                <card suit="SPADES" rank="ACE"/>
            </cards>
            <description>Pair of Sevens</description>
            <handCards>
                <card suit="CLUBS" rank="SEVEN"/>
                <card suit="DIAMONDS" rank="EIGHT"/>
            </handCards>
        </player>
    </event>	
    
    <event xsi:type="ns2:showHandEvent" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <player name="guy" id="0">
            <cards>
                <card suit="SPADES" rank="NINE"/>
                <card suit="SPADES" rank="EIGHT"/>
                <card suit="SPADES" rank="DEUCE"/>
                <card suit="SPADES" rank="SEVEN"/>
                <card suit="SPADES" rank="ACE"/>
            </cards>
            <description>Ace-High Flush</description>
            <handCards>
                <card suit="SPADES" rank="EIGHT"/>
                <card suit="SPADES" rank="DEUCE"/>
            </handCards>
        </player>
    </event>			*/
    						return;
    						break;
					
				}
				
			}
			
			if(contentObj.hasOwnProperty("tableChatEvents")){
				
				try{
					var chatEvent:Object = contentObj.tableChatEvents.chatEvent;
					var player:Object = chatEvent.player;
					var message:String = chatEvent.message;
					Main.table.playerChatBox.playerChatMessage(message, player);
				}catch(e:Error){
					trace("playerChatError: " + e.message);
				}
			}
			 
			
			
			
		}// end function
		
		
		

		
	}//end class
}//end package