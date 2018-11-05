package com.trendyol.shoppingcart;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author skeskin
 * @since 4.11.2018.
 */
public class ShoppingCartTest {
	private static final double FIXED_COST = 2.99;

	@Test
	public void categoryHasNotParentCategory() {
		Category food = new CategoryImpl("food");
		Assert.assertNull(food.getParent());
	}

	@Test
	public void categoryHasParentCategory() {
		Category food = new CategoryImpl("food");
		Category desert = new CategoryImpl("desert");
		desert.setParent(food);
		Assert.assertNotNull(desert.getParent());
	}

	@Test
	public void categoryHasATitle() {
		Category food = new CategoryImpl("food");
		Assert.assertNotNull(food.getTitle());
	}

	@Test
	public void productHasATitleAndPrice() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);
		Assert.assertNotNull(apple.getTitle());
		Assert.assertNotNull(apple.getPrice());
	}

	@Test
	public void productBelongToACategory() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);
		List<Map.Entry<Product, Integer>> apples = food.getProducts().entrySet().stream().filter(p -> p.getKey().getTitle().equals(apple.getTitle())).collect(Collectors.toList());
		Assert.assertNotNull(apples);
		Assert.assertEquals(1, apples.size());
	}

	@Test
	public void productsAreAddedToTheShoppingCartWithQuantity() {
		Category food = new CategoryImpl("food");

		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 4);
		Assert.assertTrue(shoppingCart.hasProduct(apple));

		Integer productQuantity = shoppingCart.getProductQuantity(apple);
		Assert.assertNotNull(productQuantity);
		Assert.assertEquals(4, productQuantity.intValue());
	}

	@Test
	public void applyDiscountsToProductCategoryThatHasLessThan3Item() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 3);

		// discount rules can be 20% on a category if bought more than 3 items
		Campaign campaign1 = new CampaignImpl(food, 20.0, 3, DiscountType.RATE);
		shoppingCart.applyDiscounts(campaign1);
		BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();
		BigDecimal expectedDiscount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

		Assert.assertNotNull(campaignDiscount);
		Assert.assertEquals(expectedDiscount, campaignDiscount);
	}

	@Test
	public void applyDiscountsToProductCategoryThatHasMore3Item() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 4);

		// discount rules can be 20% on a category if bought more than 3 items
		Campaign campaign1 = new CampaignImpl(food, 20.0, 3, DiscountType.RATE);
		shoppingCart.applyDiscounts(campaign1);
		BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();
		BigDecimal expectedDiscount = BigDecimal.valueOf(80.00).setScale(2, BigDecimal.ROUND_HALF_UP);

		Assert.assertNotNull(campaignDiscount);
		Assert.assertEquals(expectedDiscount, campaignDiscount);
	}

	@Test
	public void applyDiscountsToProductCategoryThatHasMore5Item() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 6);

		// discount rules can be 20% on a category if bought more than 3 items
		Campaign campaign1 = new CampaignImpl(food, 20.0, 3, DiscountType.RATE);
		// another campaign rule 50% on a category if bought more than 5 items
		Campaign campaign2 = new CampaignImpl(food, 50.0, 5, DiscountType.RATE);
		// another campaign rule 5 TL amount discount on a category if bought more than  items
		Campaign campaign3 = new CampaignImpl(food, 5.0, 5, DiscountType.AMOUNT);

		shoppingCart.applyDiscounts(campaign1, campaign2, campaign3);
		BigDecimal campaignDiscount = shoppingCart.getCampaignDiscount();
		BigDecimal expectedDiscount = BigDecimal.valueOf(305.00).setScale(2, BigDecimal.ROUND_HALF_UP);

		Assert.assertNotNull(campaignDiscount);
		Assert.assertEquals(expectedDiscount, campaignDiscount);
	}

	@Test
	public void applyNotApplicableCouponToShoppingCart() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 1);

		Coupon coupon = new CouponImpl(150, 10, DiscountType.RATE);
		shoppingCart.applyCoupon(coupon);

		BigDecimal couponDiscount = shoppingCart.getCouponDiscount();
		BigDecimal expectedDiscount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

		Assert.assertNotNull(couponDiscount);
		Assert.assertEquals(expectedDiscount, couponDiscount);
	}

	@Test
	public void applyApplicableCouponToShoppingCart() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 2);

		Coupon coupon = new CouponImpl(100, 10, DiscountType.RATE);
		shoppingCart.applyCoupon(coupon);

		BigDecimal couponDiscount = shoppingCart.getCouponDiscount();
		BigDecimal expectedDiscount = BigDecimal.valueOf(20.00).setScale(2, BigDecimal.ROUND_HALF_UP);

		Assert.assertNotNull(couponDiscount);
		Assert.assertEquals(expectedDiscount, couponDiscount);
	}

	@Test
	public void calculateDeliveryCostForShoppingCart() {
		Category food = new CategoryImpl("food");
		Product apple = new ProductImpl("Apple", 100.0, food);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addProduct(apple, 2);

		final DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(10D, 2D, FIXED_COST);
		final String deliveryCost = deliveryCostCalculator.calculateFor(shoppingCart);
		String expectedDeliveryCost = "14.99";

		Assert.assertNotNull(deliveryCost);
		System.out.println(deliveryCost);
		Assert.assertEquals(expectedDeliveryCost, deliveryCost);
	}

	@Test
	public void printShoppingCard() {
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
