/*********************************************************************************
 * Copyright 2016-present trivago GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **********************************************************************************/

package com.trivago.triava.tcache.action;

/**
 * An ActionRunner with the following behavior:
 * <ul>
 * <li> {@link #preMutate(Action)} : NOP</li> 
 * <li> {@link #postMutate(Action, boolean, Object...)} : If mutated: statistics, notifyListeners, writeThrough</li>
 * </ul>
 * @author cesken
 *
 */

public class WriteBehindActionRunner extends ActionRunner
{
	@Override
	public
	boolean preMutate(Action<?,?,?> action)
	{
		return true;
	}

	@Override
	public
	void postMutate(Action<?,?,?> action, boolean mutated, Object... args)
	{
		if (mutated)
		{
			action.statistics();
			action.notifyListeners(args);
			action.writeThrough(); // Should run async and possibly batched for efficient write-behind
		}
	}
}