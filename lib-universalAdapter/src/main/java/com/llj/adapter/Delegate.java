package com.llj.adapter;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */

public interface Delegate<Params> {
    /**
     * Executes this {@link Delegate} on the given parameters.
     *
     * @param params The parameters to the delegate.
     */
    public void execute(Params params);
}
