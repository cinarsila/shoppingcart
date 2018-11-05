package com.trendyol.shoppingcart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class CouponImpl implements Coupon {
	private final double minimumCartAmount;
	private final int percentage;
	private final DiscountType discountType;

	public CouponImpl(double minimumCartAmount, int percentage, DiscountType discountType) {
		this.minimumCartAmount = minimumCartAmount;
		this.percentage = percentage;
		this.discountType = discountType;
	}

	@Override
	public BigDecimal calculateDiscounts(BigDecimal totalAmount) {
		if (DiscountType.RATE.equals(this.discountType)) {
			return totalAmount.multiply(BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
		}
		return new BigDecimal(0);
	}

	@Override
	public double getMinimumCartAmount() {
		return minimumCartAmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CouponImpl coupon = (CouponImpl) o;
		return Double.compare(coupon.minimumCartAmount, minimumCartAmount) == 0 &&
				discountType == coupon.discountType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(minimumCartAmount, discountType);
	}
}
