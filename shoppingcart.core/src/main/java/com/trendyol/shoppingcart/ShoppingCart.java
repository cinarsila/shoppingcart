package com.trendyol.shoppingcart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class ShoppingCart {
	private final Map<String, Category> categories = new HashMap<>();
	private BigDecimal totalAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal campaignDiscount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal couponDiscount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
	private BigDecimal deliveryCost;

	public void addProduct(Product product, int quantity) {
		if (product == null || quantity < 0) {
			return;
		}

		final Category productCategory = product.getCategory();
		final String key = productCategory.getTitle();
		final Category category = this.categories.get(key);
		if (category == null) {
			productCategory.addProduct(product, quantity);
			categories.put(key, productCategory);
		} else {
			category.addProduct(product, quantity);
		}

		totalAmount = totalAmount.add(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity))).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public boolean hasProduct(Product product) {
		return getProductQuantity(product) != null;
	}

	public Integer getProductQuantity(Product product) {
		String key = product.getCategory().getTitle();
		Category category = categories.get(key);
		return category.getProducts().get(product);
	}

	public void applyDiscounts(Campaign... campaigns) {
		if (campaigns.length == 0) {
			return;
		}
		categories.forEach((key, category) -> category.applyDiscounts(campaigns));
		for (Map.Entry<String, Category> categoryEntry : categories.entrySet()) {
			Category category = categoryEntry.getValue();
			campaignDiscount = campaignDiscount.add(category.calculateDiscounts()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	public void applyCoupon(Coupon coupon) {
		if (coupon == null || this.totalAmount.compareTo(BigDecimal.valueOf(coupon.getMinimumCartAmount())) < 0) {
			return;
		}
		this.couponDiscount = coupon.calculateDiscounts(this.totalAmount);
	}

	public long getNumberOfDeliveries() {
		return categories.size();
	}

	public long getNumberOfProducts() {
		return categories.entrySet().stream().flatMap(c -> c.getValue().getProducts().entrySet().stream()).collect(Collectors.toList()).size();
	}

	public String getTotalAmountAfterDiscounts() {
		return totalAmount.subtract(campaignDiscount).subtract(couponDiscount).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public BigDecimal getCampaignDiscount() {
		return campaignDiscount;
	}

	public String getDeliveryCost() {
		return deliveryCost.toString();
	}

	public void setDeliveryCost(BigDecimal deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public void print() {
		// Group Products by Category and Print the CategoryName, ProductName, Quantity, Unit Price, Total Price, Total Discount(coupon,campaign) applied
		System.out.format("+%17s+%16s+%16s+%16s+%16s+%16s+%n", "-----------------", "----------------", "----------------", "----------------", "----------------", "----------------");
		System.out.format("| %15s |%15s |%15s |%15s |%15s |%15s |%n", "CATEGORY_NAME", "PRODUCT_NAME", "QUANTITY", "UNIT_PRICE", "TOTAL_PRICE", "TOTAL_DISCOUNT");
		System.out.format("+%17s+%16s+%16s+%16s+%16s+%16s+%n", "-----------------", "----------------", "----------------", "----------------", "----------------", "----------------");
		for (Map.Entry<String, Category> categoryEntry : categories.entrySet()) {
			Category category = categoryEntry.getValue();
			for (Map.Entry<Product, Integer> productEntry : category.getProducts().entrySet()) {
				Product product = productEntry.getKey();
				Integer quantity = productEntry.getValue();
				String totalPrice = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				String totalDiscount = getCouponDiscount().add(getCampaignDiscount()).toString();
				System.out.format("| %-15s | %-15s|%15s |%15s |%15s |%15s |%n", category.getTitle(), product.getTitle(), quantity, product.getPrice(), totalPrice, totalDiscount);
			}
		}
		System.out.format("+%17s+%16s+%16s+%16s+%16s+%16s+%n", "-----------------", "----------------", "----------------", "----------------", "----------------", "----------------");
	}
}
