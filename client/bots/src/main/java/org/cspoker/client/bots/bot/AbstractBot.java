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
package org.cspoker.client.bots.bot;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.cspoker.client.bots.listener.BotListener;
import org.cspoker.client.common.SmartHoldemPlayerContext;
import org.cspoker.client.common.SmartHoldemTableContext;
import org.cspoker.client.common.SmartLobbyContext;
import org.cspoker.client.common.gamestate.GameState;
import org.cspoker.client.common.gamestate.PlayerState;
import org.cspoker.common.api.lobby.holdemtable.event.AllInEvent;
import org.cspoker.common.api.lobby.holdemtable.event.BetEvent;
import org.cspoker.common.api.lobby.holdemtable.event.BigBlindEvent;
import org.cspoker.common.api.lobby.holdemtable.event.CallEvent;
import org.cspoker.common.api.lobby.holdemtable.event.CheckEvent;
import org.cspoker.common.api.lobby.holdemtable.event.FoldEvent;
import org.cspoker.common.api.lobby.holdemtable.event.JoinTableEvent;
import org.cspoker.common.api.lobby.holdemtable.event.LeaveSeatEvent;
import org.cspoker.common.api.lobby.holdemtable.event.LeaveTableEvent;
import org.cspoker.common.api.lobby.holdemtable.event.NewCommunityCardsEvent;
import org.cspoker.common.api.lobby.holdemtable.event.NewDealEvent;
import org.cspoker.common.api.lobby.holdemtable.event.NewRoundEvent;
import org.cspoker.common.api.lobby.holdemtable.event.NextPlayerEvent;
import org.cspoker.common.api.lobby.holdemtable.event.RaiseEvent;
import org.cspoker.common.api.lobby.holdemtable.event.ShowHandEvent;
import org.cspoker.common.api.lobby.holdemtable.event.SitInEvent;
import org.cspoker.common.api.lobby.holdemtable.event.SitOutEvent;
import org.cspoker.common.api.lobby.holdemtable.event.SmallBlindEvent;
import org.cspoker.common.api.lobby.holdemtable.event.WinnerEvent;
import org.cspoker.common.api.lobby.holdemtable.holdemplayer.event.NewPocketCardsEvent;
import org.cspoker.common.api.shared.exception.IllegalActionException;
import org.cspoker.common.elements.player.PlayerId;
import org.cspoker.common.elements.table.TableId;

public abstract class AbstractBot
		implements Bot {
	
	private final static Logger logger = Logger.getLogger(AbstractBot.class);
	
	protected final SmartLobbyContext lobbyContext;
	protected volatile SmartHoldemTableContext tableContext;
	protected volatile SmartHoldemPlayerContext playerContext;
	
	private volatile boolean started = false;
	
	public final TableId tableID;
	public final PlayerId playerId;
	
	private final BotListener[] botListeners;
	
	protected final ExecutorService executor;

	private final int buyIn;
	
	public AbstractBot(PlayerId playerId, TableId tableId, SmartLobbyContext lobby, int buyIn, ExecutorService executor,
			BotListener... botListeners) {
		this.playerId = playerId;
		this.tableID = tableId;
		this.lobbyContext = lobby;
		this.botListeners = botListeners;
		this.buyIn = buyIn;
		this.executor = executor;
	}
	
	public void start() {
		executor.execute(new Runnable() {
			
			public void run() {
				try {
					started = true;
					tableContext = lobbyContext.joinHoldemTable(tableID, AbstractBot.this);
					playerContext = tableContext.sitIn(buyIn, AbstractBot.this);
				} catch (IllegalActionException e) {
					e.printStackTrace();
					throw new IllegalStateException("Failed to join table.", e);
				} catch (RemoteException e) {
					logger.error(e);
					throw new IllegalStateException("Failed to join table.", e);
				}
			}
		});
	}
	
	public void startGame() {
		executor.execute(new Runnable() {
			
			public void run() {
				try {
					playerContext.startGame();
				} catch (IllegalActionException e) {
					e.printStackTrace();
					throw new IllegalStateException("Failed to start game.", e);
				} catch (RemoteException e) {
					logger.error(e);
					throw new IllegalStateException("Failed to start game.", e);
				}
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.cspoker.client.bots.Bot#doNextAction()
	 */
	public abstract void doNextAction();
	
	/**
	 * @see org.cspoker.common.api.lobby.holdemtable.listener.HoldemTableListener#onNextPlayer(org.cspoker.common.api.lobby.holdemtable.event.NextPlayerEvent)
	 */
	public void onNextPlayer(final NextPlayerEvent nextPlayerEvent) {
		if (started && nextPlayerEvent.getPlayerId().equals(playerId)) {
			doNextAction();
		}
	}
	
	/**
	 * @see org.cspoker.common.api.lobby.holdemtable.listener.HoldemTableListener#onNewDeal(org.cspoker.common.api.lobby.holdemtable.event.NewDealEvent)
	 */
	public void onNewDeal(NewDealEvent newDealEvent) {
		if (started) {
			for (BotListener botListener : botListeners) {
				botListener.onNewDeal();
			}
		}
	}
	
	/**
	 * @see org.cspoker.client.bots.bot.Bot#stop()
	 */
	public void stop() {
		started = false;
		executor.execute(new Runnable() {
			
			public void run() {
				try {
					tableContext.leaveTable();
				} catch (IllegalActionException e) {
					e.printStackTrace();
					throw new IllegalStateException("Failed to leave table.", e);
				} catch (RemoteException e) {
					logger.error(e);
					throw new IllegalStateException("Failed to leave table.", e);
				}
			}
		});
	}
	
	/**
	 * @see org.cspoker.client.bots.bot.Bot#getProfit()
	 */
	public int getProfit() {
		GameState state = tableContext.getGameState();
		if(state.getPreviousRoundsPotSize()>0){
			throw new IllegalStateException("There is a pot from previous rounds ("+state.getPreviousRoundsPotSize()+"). Can't calculate profit.");
		}
		PlayerState playerState = state.getPlayer(playerId);
		return playerState.getStack()+playerState.getBet() - buyIn;
	}
	
	public void onAllIn(AllInEvent allInEvent) {

	}
	
	public void onBet(BetEvent betEvent) {

	}
	
	public void onBigBlind(BigBlindEvent bigBlindEvent) {

	}
	
	public void onCall(CallEvent callEvent) {

	}
	
	public void onCheck(CheckEvent checkEvent) {

	}
	
	public void onFold(FoldEvent foldEvent) {

	}
	
	public void onJoinTable(JoinTableEvent joinTableEvent) {

	}
	
	public void onLeaveTable(LeaveTableEvent leaveGameEvent) {

	}
	
	public void onNewCommunityCards(NewCommunityCardsEvent newCommunityCardsEvent) {

	}
	
	public void onNewRound(NewRoundEvent newRoundEvent) {

	}
	
	public void onRaise(RaiseEvent raiseEvent) {

	}
	
	public void onShowHand(ShowHandEvent showHandEvent) {

	}
	
	public void onSitIn(SitInEvent sitInEvent) {

	}
	
	public void onSitOut(SitOutEvent sitOutEvent) {
		if (started) {
			for (BotListener botListener : botListeners) {
				botListener.onSitOut();
			}
		}
	}
	
	public void onSmallBlind(SmallBlindEvent smallBlindEvent) {

	}
	
	public void onWinner(WinnerEvent winnerEvent) {

	}
	
	public void onNewPocketCards(NewPocketCardsEvent newPocketCardsEvent) {

	}
	
	public void onLeaveSeat(LeaveSeatEvent leaveSeatEvent){
		
	}
	
}