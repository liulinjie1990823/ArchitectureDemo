package com.llj.adapter

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/14.
 */
interface Delegate<Params> {
    /**
     * Executes this [Delegate] on the given parameters.
     *
     * @param params The parameters to the delegate.
     */
    fun execute(params: Params)
}