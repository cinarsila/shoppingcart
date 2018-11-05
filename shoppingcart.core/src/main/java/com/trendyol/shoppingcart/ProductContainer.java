package com.trendyol.shoppingcart;

import java.util.Objects;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class ProductContainer {
	private final Product product;
	private int quantity;

	public ProductContainer(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductContainer that = (ProductContainer) o;
		return Objects.equals(product, that.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(product);
	}
}
