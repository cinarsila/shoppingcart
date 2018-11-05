package com.trendyol.shoppingcart;

import org.junit.Test;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class ShoppingCartTest {
	private static final double FIXED_COST = 2.99;

	@Test
	public void testShoppingCart() {
		Category food = new CategoryImpl("food");
		Category home = new CategoryImpl("home");

		Product apple = new ProductImpl("Apple", 100.0, food);
		Product almond = new ProductImpl("Almonds", 150.0, food);

		Product couch = new ProductImpl("Couch", 200.0, home);
		Product tv = new ProductImpl("TV", 3400.0, home);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 4);
		shoppingCart.addProduct(almond, 2);
		shoppingCart.addProduct(couch, 3);
		shoppingCart.addProduct(tv, 1);

		// discount rules can be 20% on a category if bought more than 3 items
		Campaign campaign1 = new CampaignImpl(food, 20.0, 3, DiscountType.RATE);
		// another campaign rule 50% on a category if bought more than 5 items
		Campaign campaign2 = new CampaignImpl(food, 50.0, 5, DiscountType.RATE);
		// another campaign rule 5 TL amount discount on a category if bought more than  items
		Campaign campaign3 = new CampaignImpl(food, 5.0, 5, DiscountType.AMOUNT);

		// discount rules can be 20% on a category if bought more than 3 items
		Campaign campaign4 = new CampaignImpl(home, 20.0, 3, DiscountType.RATE);
		// another campaign rule 50% on a category if bought more than 5 items
		Campaign campaign5 = new CampaignImpl(home, 50.0, 5, DiscountType.RATE);
		// another campaign rule 5 TL amount discount on a category if bought more than  items
		Campaign campaign6 = new CampaignImpl(home, 5.0, 5, DiscountType.AMOUNT);

		shoppingCart.applyDiscounts(campaign1, campaign2, campaign3, campaign4, campaign5, campaign6);

		// Coupons have minimum amount. If the cart total is less than minimum amount, coupon is not applicable
		// Coupon for 100 TL min purchase amount for a 10% discount
		Coupon coupon = new CouponImpl(100, 10, DiscountType.RATE);
		shoppingCart.applyCoupon(coupon);

		final DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(10D, 2D, FIXED_COST);
		final String deliveryCost = deliveryCostCalculator.calculateFor(shoppingCart);

		// At the final line print total amount and the delivery cost.
		shoppingCart.print();
		System.out.format("| %-83s |%15s |%n", "Total Amount", shoppingCart.getTotalAmountAfterDiscounts());
		System.out.format("| %-83s |%15s |%n", "Delivery Cost", deliveryCost);
		System.out.format("+%85s+%15s +%n", "-------------------------------------------------------------------------------------", "---------------");
	}
}
