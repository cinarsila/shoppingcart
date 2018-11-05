package com.trendyol.shoppingcart;

import java.util.Objects;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class CampaignImpl implements Campaign {
	private final Category category;
	private final double percentage;
	private final int itemCount;
	private final DiscountType discountType;

	public CampaignImpl(Category category, double percentage, int itemCount, DiscountType discountType) {
		this.category = category;
		this.percentage = percentage;
		this.itemCount = itemCount;
		this.discountType = discountType;
	}

	@Override
	public Category getCategory() {
		return category;
	}

	@Override
	public double getPercentage() {
		return percentage;
	}

	@Override
	public int getItemCount() {
		return itemCount;
	}

	@Override
	public DiscountType getDiscountType() {
		return discountType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CampaignImpl campaign = (CampaignImpl) o;
		return itemCount == campaign.itemCount &&
				Objects.equals(category, campaign.category) &&
				discountType == campaign.discountType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, itemCount, discountType);
	}
}
