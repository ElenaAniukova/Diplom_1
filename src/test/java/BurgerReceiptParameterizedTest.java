import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import praktikum.*;



@RunWith(Parameterized.class)
public class BurgerReceiptParameterizedTest {

    String bunName;
    IngredientType ingredientType;
    String ingredientName;
    float price;

    public BurgerReceiptParameterizedTest(String bunName, IngredientType ingredientType, String ingredientName, float price) {
        this.bunName = bunName;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.price = price;

    }

    @Parameterized.Parameters(name = "Сценарий: {index} - {2}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Флюоресцентная булка R2-D3", null, null, 300.0f},
                {"Флюоресцентная булка R2-D3", IngredientType.SAUCE, "СОУС", 300.0f},
                {"Флюоресцентная булка R2-D3", IngredientType.FILLING, "НАЧИНКА", 300.0f},
        };
    }

        @Test
        public void  shouldReturnCorrectReceipt() {
            Burger burger = new Burger();
            Burger burgerSpy = Mockito.spy(burger);

            Bun bunMock = Mockito.mock(Bun.class);
            Mockito.when(bunMock.getName()).thenReturn(bunName);
            burgerSpy.setBuns(bunMock);

            if (ingredientType != null){
                Ingredient ingredientMock = Mockito.mock(Ingredient.class);
                Mockito.when(ingredientMock.getType()).thenReturn(ingredientType);
                Mockito.when(ingredientMock.getName()).thenReturn(ingredientName);
                burgerSpy.addIngredient(ingredientMock);
            }

            Mockito.when(burgerSpy.getPrice()).thenReturn(price);

            String receipt = burgerSpy.getReceipt();

            Mockito.verify(bunMock, Mockito.times(2).description("Название булки должно выводиться дважды (сверху и снизу)")).getName();

            if (ingredientType != null) {
                String expectedType = ingredientType.toString().toLowerCase();
                Assert.assertTrue("Тип ингредиента в чеке должен быть в нижнем регистре",
                        receipt.contains(expectedType));
            }

            Assert.assertTrue("Цена в чеке должна быть отформатирована через %f",
                    receipt.contains(String.format("Price: %f", price)));

        }
    }