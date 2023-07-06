package com.stefanini.pizzariabackend.domain;

import com.stefanini.pizzariabackend.domain.enums.Category;
import com.stefanini.pizzariabackend.domain.enums.Size;
import com.stefanini.pizzariabackend.domain.enums.Subcategory;
import com.stefanini.pizzariabackend.domain.enums.Thickness;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "MenuItem")
@Table(name = "Menu_Item")
public class MenuItem {

    @Id
    @SequenceGenerator(
            name = "menu_item_sequence",
            sequenceName = "menu_item_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "menu_item_sequence"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column(name = "sizes_and_prices")
    @ElementCollection
    private Map<Size, Double> sizesAndPrices;

    @Column(
            name = "category",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "subcategory")
    @Enumerated(EnumType.STRING)
    private Subcategory subcategory;

    @Column(name = "thickness")
    @Enumerated(EnumType.STRING)
    private Thickness thickness;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private MenuItem(String name, byte[] image, String ingredients,
                     Map<Size, Double> sizesAndPrices, Category category, Subcategory subcategory,
                     Thickness thickness, Order order) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.sizesAndPrices = sizesAndPrices;
        this.category = category;
        this.subcategory = subcategory;
        this.thickness = thickness;
        this.order = order;
    }

    private MenuItem(String name, byte[] image, String ingredients, Map<Size, Double> sizesAndPrices,
                     Category category, Thickness thickness) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.sizesAndPrices = sizesAndPrices;
        this.category = category;
        this.thickness = thickness;
    }

    private MenuItem(String name, byte[] image, String ingredients, Map<Size, Double> sizesAndPrices,
                     Category category) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.sizesAndPrices = sizesAndPrices;
        this.category = category;
    }

    private MenuItem(String name, byte[] image, String ingredients, Map<Size, Double> sizesAndPrices,
                     Category category, Subcategory subcategory) {
        this.name = name;
        this.image = image;
        this.ingredients = ingredients;
        this.sizesAndPrices = sizesAndPrices;
        this.category = category;
        this.subcategory = subcategory;
    }

    public static MenuItem createPizzaWithDefaultValues(String name, byte[] image, String ingredients,
                                                        double smallSizePrice, double mediumSizePrice,
                                                        double largeSizePrice, Thickness thickness) {

        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.PIZZA, thickness
        );
    }

    public static MenuItem createBreakfastWithDefaultCategory(String name, byte[] image, String ingredients,
                                                              double smallSizePrice, double mediumSizePrice,
                                                              double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.BREAKFAST
        );
    }

    public static MenuItem createSnackWithDefaultCategory(String name, byte[] image, String ingredients,
                                                          double smallSizePrice, double mediumSizePrice,
                                                          double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.SNACKS
        );
    }

    public static MenuItem createSaladWithDefaultCategory(String name, byte[] image, String ingredients,
                                                          double smallSizePrice, double mediumSizePrice,
                                                          double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.SALADS
        );
    }

    public static MenuItem createSoupWithDefaultCategory(String name, byte[] image, String ingredients,
                                                         double smallSizePrice, double mediumSizePrice,
                                                         double largeSizePrice, Subcategory subcategory) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.SOUPS, subcategory
        );
    }

    public static MenuItem createMainDishWithDefaultCategory(String name, byte[] image, String ingredients,
                                                             double smallSizePrice, double mediumSizePrice,
                                                             double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.MAIN_DISHES
        );
    }

    public static MenuItem createDessertWithDefaultCategory(String name, byte[] image, String ingredients,
                                                            double smallSizePrice, double mediumSizePrice,
                                                            double largeSizePrice, Subcategory subcategory) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.DESSERT, subcategory
        );
    }

    public static MenuItem createBeveragesWithDefaultCategory(String name, byte[] image, String ingredients,
                                                              double smallSizePrice, double mediumSizePrice,
                                                              double largeSizePrice, Subcategory subcategory) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.BEVERAGES, subcategory
        );
    }

    public static MenuItem createSupplementsWithDefaultCategory(String name, byte[] image, String ingredients,
                                                                double smallSizePrice, double mediumSizePrice,
                                                                double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = getMapOfSizesAndPricesByPricesForEachSize(
                smallSizePrice, mediumSizePrice, largeSizePrice
        );

        return new MenuItem(
                name, image, ingredients, sizesAndPrices, Category.SUPPLEMENTS
        );
    }

    private static Map<Size, Double> getMapOfSizesAndPricesByPricesForEachSize(double smallSizePrice,
                                                                               double mediumSizePrice,
                                                                               double largeSizePrice) {
        Map<Size, Double> sizesAndPrices = new HashMap<>();
        sizesAndPrices.put(Size.SMALL, smallSizePrice);
        sizesAndPrices.put(Size.MEDIUM, mediumSizePrice);
        sizesAndPrices.put(Size.LARGE, largeSizePrice);

        return sizesAndPrices;
    }
}
