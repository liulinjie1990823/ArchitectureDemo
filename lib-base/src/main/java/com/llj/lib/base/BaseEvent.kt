package com.llj.lib.base

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/21
 */
data class BaseEvent<T>(var code: Int = 0,
                        var message: String = "",
                        var data: T? = null) {

    constructor(code: Int) : this()

    constructor(code: Int, message: String) : this()

    constructor(code: Int, data: T?) : this()
}
