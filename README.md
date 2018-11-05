# Shopping Cart

Implementations of an e-commerce shopping cart class for [trendyol.com](https://www.trendyol.com/).

## Getting Started

Fork this project

### Prerequisites

You need at least Java version 8 and latest maven.

```
Java 8 or later versions
Latest maven
```

### Installing

You should run maven goals to build project

```
mvn clean install
```

## Running the tests

You can run tests using maven or using your ide

```
mvn test
```

### And coding style tests

* Creating a new category

```
Category food = new new Category("food");
```

* Creating a new products

```
Product apple = new Product("Apple", 100.0, category);
Product almond = new Product("Almonds", 150.0, category);
```

* Products can be added to a shopping cart with quantity

```
ShoppingCart cart = new ShoppingCart();
cart.addItem(apple,3);
cart.addItem(almond,1);
```

* Discounts
	* You can apply discounts to a category
	* Discount rules can be 20% on a category if bought more than 3 items 
	* Another campaign rule 50% on a category if bought more than 5 items 
	* Another campaign rule 5 TL amount discount on a category if bought more than  items 

```
Campaign campaign1 = new Campaign(category,20.0,3,DiscountType.Rate);
Campaign campaign2 = new Campaign(category,50.0,5,DiscountType.Rate);
Campaign campaign3 = new Campaign(category,5.0,5,DiscountType.Amount);
```

* Cart should apply the maximum amount of discount to the cart.

```
cart.applyDiscounts(discount1,discount2,discount3);
```

* You can also apply a coupon to a cart
	* Coupons have minimum amount. If the cart total is less than minimum amount, coupon is not applicable
	* Coupon for 100 TL min purchase amount for a 10% discount
	
```
Coupon coupon = new Coupon(100, 10, DiscountType.Rate)
cart.applyCoupon(coupon)
```

* Campaign Discounts are applied first, Then Coupons.

* Delivery

```
DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery,costPerProduct,fixedCost);
double deliveryCostCalculator.calculateFor(cart)
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Suat Keskin** - *Initial work* - [shoppingcart](https://github.com/suatkeskin/shoppingcart)