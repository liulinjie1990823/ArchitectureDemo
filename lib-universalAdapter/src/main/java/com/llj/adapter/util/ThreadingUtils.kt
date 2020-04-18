package com.llj.adapter.util

import android.os.Handler
import android.os.Looper
import android.view.View

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/10.
 */
object ThreadingUtils {
    private var uiHandler: Handler? = null

    /**
     * @return A [Handler] that is bound to the UI thread.
     */
    val uIHandler: Handler?
        get() {
            if (uiHandler == null) uiHandler = Handler(Looper.getMainLooper())
            return uiHandler
        }

    /**
     * Returns true if this function was called on the thread the given
     * [Handler] is bound to.
     *
     * @param handler The [Handler] to check the thread of.
     * @return True if this function was called on the [Handler]'s
     * thread.
     */
    fun isOnHandlerThread(handler: Handler): Boolean {
        val handlerLooper = handler.looper
        return if (handlerLooper != null) {
            handlerLooper == Looper.myLooper()
        } else false
    }

    /**
     * @return True if this function was called from the UI thread
     */
    val isOnUIThread: Boolean
        get() = Looper.getMainLooper() == Looper.myLooper()

    /**
     * Runs the given [Runnable] on the thread the given [Handler]
     * is bound to. This will execute immediately, before this function returns,
     * if this function was already called on the given [Handler]'s thread.
     * Otherwise, the [Runnable] will be posted to the [Handler].
     *
     * @param action  The [Runnable] to execute.
     * @param handler The [Handler] to run the action on.
     * @return True if the action was already executed before this funcion
     * returned, or false if the action was posted to be handled later.
     */
    fun runOnHandler(action: Runnable, handler: Handler): Boolean {
        return if (isOnHandlerThread(handler)) {
            action.run()
            true
        } else {
            handler.post(action)
            false
        }
    }

    /**
     * Runs the given [Runnable] on the UI thread. This will execute
     * immediately, before this function returns, if this function was called
     * on the UI thread. Otherwise, the [Runnable] will be posted to the
     * UI thread.
     *
     * @param action The [Runnable] to execute on the UI thread.
     * @return True if the action was already executed before this function
     * returned, or false if the action was posted to be handled later.
     * @see .runOnUIThread
     * @see .runOnUIThread
     */
    fun runOnUIThread(action: Runnable): Boolean {
        return if (isOnUIThread) {
            action.run()
            true
        } else {
            uIHandler!!.post(action)
            false
        }
    }

    /**
     * Runs the given [Runnable] on the UI thread. This will execute
     * immediately, before this function returns, if this function was called
     * on the UI thread. Otherwise, the [Runnable] will be posted using
     * the given [View].
     * <br></br><br></br>
     * NOTE: This method will attempt to force the action onto the UI thread.
     * <br></br><br></br>
     * WARNING: The action may still not be taken if the view's
     * [View.post] method returns true, but doesn't execute.
     * (This is the case when the view is not attached to a window).
     *
     * @param action The [Runnable] to execute.
     * @param v      A [View] to use to post the [Runnable] if this
     * wasn't called on the UI thread.
     * @return True if the action was already executed before this function
     * returned, or false if the action was posted.
     * @see .runOnUIThread
     * @see .runOnUIThread
     */
    fun runOnUIThread(action: Runnable, v: View): Boolean {
        return if (isOnUIThread) {
            action.run()
            true
        } else {
            if (!v.post(action)) {
                runOnUIThread(action)
            }
            false
        }
    }

    /**
     * Runs the given [Runnable] immediately if this function is called
     * on the UI thread. Otherwise, it is posted to the given [Handler]
     * and executed on its bound thread. Though it is assumed that the given
     * [Handler] is bound to the UI thread, it is not necessary, and it
     * will execute the action either way.
     *
     * @param action  The [Runnable] to execute.
     * @param handler The [Handler] to post the action to if if this
     * wasn't called on the UI thread.
     * @return True if the action was already executed before this function
     * returned, or false if the action was posted to the [Handler].
     */
    fun runOnUIThread(action: Runnable, handler: Handler): Boolean {
        return if (isOnUIThread) {
            action.run()
            true
        } else {
            if (!handler.post(action)) {
                runOnUIThread(action)
            }
            false
        }
    }
}