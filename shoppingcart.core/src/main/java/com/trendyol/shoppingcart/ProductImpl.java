package com.trendyol.shoppingcart;

import java.util.Objects;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class ProductImpl implements Product {
	private final String title;
	private final double price;
	private final Category category;

	public ProductImpl(String title, double price, Category category) {
		this.title = title;
		this.price = price;
		this.category = category;
		this.category.addProduct(this, 0);
	}

	@Override
	public double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductImpl product = (ProductImpl) o;
		return Objects.equals(title, product.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
}
