package com.trendyol.shoppingcart;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public interface Campaign {

	Category getCategory();

	double getPercentage();

	int getItemCount();

	DiscountType getDiscountType();
}
