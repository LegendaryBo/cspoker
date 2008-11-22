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
package org.cspoker.common.util.threading;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

public class LoggingThreadPool extends ScheduledThreadPoolExecutor {

	public LoggingThreadPool(int corePoolSize, String name) {
		super(corePoolSize, new LoggingThreadFactory(name));
		setMaximumPoolSize(2*corePoolSize);
	}

	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();
	private static Logger logger = Logger.getLogger(LoggingThreadPool.class);

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		logger.trace(String.format("Thread %s: starting %s", t, r));
		startTime.set(System.nanoTime());
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			logger.trace(String.format("Thread %s: done with %s, time=%dns", t,
					r, taskTime));
		} finally {
			super.afterExecute(r, t);
		}
	}

	@Override
	protected void terminated() {
		try {
			if (numTasks.get()>0) {
				logger.trace(String.format("Terminated: avg time=%dns",
						totalTime.get() / numTasks.get()));
			}
		} finally {
			super.terminated();
		}
	}
}
