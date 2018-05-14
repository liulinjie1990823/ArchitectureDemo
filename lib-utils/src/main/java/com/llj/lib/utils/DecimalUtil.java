package com.llj.lib.utils;

/**
 * <p>
 * <li>
 * BigDecimal.ROUND_HALF_UP,四舍五入，一般用这个</li>
 * <li>
 * BigDecimal.ROUND_HALF_DOWN,四舍五舍</li>
 * <li>
 * BigDecimal.ROUND_UP,舍入远离零的舍入模式。在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。注意,
 * 此舍入模式始终不会减少计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_DOWN,接近零的舍入模式。在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1,即截短)。注意,
 * 此舍入模式始终不会增加计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_CEILING,接近正无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_UP
 * 相同;如果为负,则舍入行为与 ROUND_DOWN 相同。注意,此舍入模式始终不会减少计算值。</li>
 * <li>
 * BigDecimal.ROUND_FLOOR,接近负无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_DOWN
 * 相同;如果为负,则舍入行为与 ROUND_UP 相同。注意,此舍入模式始终不会增加计算值。</li>
 * </p>
 *
 * @author liulj
 */
public class DecimalUtil {

}
