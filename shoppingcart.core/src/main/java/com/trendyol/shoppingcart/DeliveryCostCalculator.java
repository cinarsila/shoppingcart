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
		//Formula is ( CostPerDelivery * NumberOfDeliveries ) + (CostPerProduct * NumberOfProducts) + Fixed Cost
		//NumberOfDeliveries is calculated by the number of distinct categories in the cart.
		//If cart has products that belong to two distinct categories, number of deliveries is 2.
		//NumberOfProducts is the number of different products in the cart. It is not the quantity of products.
		BigDecimal deliveryCost = costPerDelivery.multiply(BigDecimal.valueOf(shoppingCart.getNumberOfDeliveries())).add(costPerProduct.multiply(BigDecimal.valueOf(shoppingCart.getNumberOfProducts()))).add(fixedCost);
		shoppingCart.setDeliveryCost(deliveryCost);
		return deliveryCost.toString();
	}
}
