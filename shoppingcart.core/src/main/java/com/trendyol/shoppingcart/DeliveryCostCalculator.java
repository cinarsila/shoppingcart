package com.trendyol.shoppingcart;

import java.math.BigDecimal;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class DeliveryCostCalculator {
	private final BigDecimal costPerDelivery;
	private final BigDecimal costPerProduct;
	private final BigDecimal fixedCost;

	public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {
		this.costPerDelivery = BigDecimal.valueOf(costPerDelivery);
		this.costPerProduct = BigDecimal.valueOf(costPerProduct);
		this.fixedCost = BigDecimal.valueOf(fixedCost);
	}

	public String calculateFor(ShoppingCart shoppingCart) {
		BigDecimal deliveryCost = costPerDelivery.multiply(BigDecimal.valueOf(shoppingCart.getNumberOfDeliveries())).add(costPerProduct.multiply(BigDecimal.valueOf(shoppingCart.getNumberOfProducts()))).add(fixedCost).setScale(2, BigDecimal.ROUND_HALF_UP);
		shoppingCart.setDeliveryCost(deliveryCost);
		return deliveryCost.toString();
	}
}
