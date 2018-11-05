package com.trendyol.shoppingcart;

import java.math.BigDecimal;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public interface Coupon {

	BigDecimal calculateDiscounts(BigDecimal totalAmount);

	double getMinimumCartAmount();
}
