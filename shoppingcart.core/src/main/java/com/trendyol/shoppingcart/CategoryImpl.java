package com.trendyol.shoppingcart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class CategoryImpl implements Category {
	private final String title;
	private Category parent;
	private Map<Product, Integer> products = new HashMap<>();
	private List<Campaign> campaigns = new ArrayList<>();

	public CategoryImpl(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Category getParent() {
		return parent;
	}

	@Override
	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Override
	public Map<Product, Integer> getProducts() {
		return products;
	}

	@Override
	public void addProduct(Product product, Integer quantity) {
		this.products.merge(product, quantity, (a, b) -> b + a);
	}

	@Override
	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	@Override
	public void applyDiscounts(Campaign... campaigns) {
		Arrays.stream(campaigns).filter(campaign -> this.equals(campaign.getCategory())).forEachOrdered(campaign -> this.campaigns.add(campaign));
	}

	@Override
	public BigDecimal calculateDiscounts() {
		Integer categoryProductQuantity = 0;
		BigDecimal categoryTotalAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		for (Map.Entry<Product, Integer> productEntry : products.entrySet()) {
			double productPrice = productEntry.getKey().getPrice();
			Integer productQuantity = productEntry.getValue();
			categoryTotalAmount = categoryTotalAmount.add(BigDecimal.valueOf(productPrice).multiply(BigDecimal.valueOf(productQuantity))).setScale(2, BigDecimal.ROUND_HALF_UP);
			categoryProductQuantity += productQuantity;
		}

		Map<DiscountType, Campaign> applicableCampaigns = new EnumMap<>(DiscountType.class);
		for (Campaign campaign : campaigns) {
			if (categoryProductQuantity > campaign.getItemCount()) {
				DiscountType key = campaign.getDiscountType();
				Campaign applicableCampaign = applicableCampaigns.get(key);
				if (applicableCampaign == null || applicableCampaign.getItemCount() < campaign.getItemCount()) {
					applicableCampaigns.put(key, campaign);
				}
			}
		}

		BigDecimal discount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		for (Map.Entry<DiscountType, Campaign> campaignEntry : applicableCampaigns.entrySet()) {
			Campaign campaign = campaignEntry.getValue();
			if (DiscountType.AMOUNT.equals(campaign.getDiscountType())) {
				discount = discount.add(BigDecimal.valueOf(5));
			}
			if (DiscountType.RATE.equals(campaign.getDiscountType())) {
				discount = discount.add(categoryTotalAmount.multiply(BigDecimal.valueOf(campaign.getPercentage())).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
			}
		}
		return discount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CategoryImpl category = (CategoryImpl) o;
		return Objects.equals(title, category.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public String toString() {
		return String.format("Category { title = '%s' }", title);
	}
}
