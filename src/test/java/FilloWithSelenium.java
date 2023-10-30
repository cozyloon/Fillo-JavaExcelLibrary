import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FilloWithSelenium {

    String file = System.getProperty("user.dir") + "\\src\\main\\resources\\sauceDemoCredentials.xlsx";

    @Test(dataProvider = "getDataFromExcelUsingFillo")
    public void testUsingSelenium(String username, String password) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        driver.quit();
    }

    @DataProvider
    public Object[][] getDataFromExcelUsingFillo() {
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(file);
            Recordset recordset = connection.executeQuery("SELECT * FROM Sheet1");
            int numberOfRows = recordset.getCount();
            Object[][] data = new Object[numberOfRows][2]; // 2 columns: username and password

            int row = 0;
            while (recordset.next()) {
                String username = recordset.getField("username");
                String password = recordset.getField("password");
                data[row][0] = username;
                data[row][1] = password;
                row++;
            }

            recordset.close();
            connection.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
