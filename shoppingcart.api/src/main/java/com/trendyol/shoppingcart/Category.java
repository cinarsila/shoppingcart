package com.trendyol.shoppingcart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public interface Category {

	String getTitle();

	Category getParent();

	void setParent(Category parent);

	Map<Product, Integer> getProducts();

	void addProduct(Product product, Integer quantity);

	void applyDiscounts(Campaign... campaigns);

	List<Campaign> getCampaigns();

	BigDecimal calculateDiscounts();
}
