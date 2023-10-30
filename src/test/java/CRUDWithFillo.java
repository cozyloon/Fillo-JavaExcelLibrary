import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.testng.annotations.Test;

public class CRUDWithFillo {

    String file = System.getProperty("user.dir") + "\\src\\main\\resources\\AutoTestData.xlsx";

    @Test
    public void select() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(file);
        String strQuery = "Select * from Sheet1 where full_name='Eloy Lesch'";
        Recordset recordset = connection.executeQuery(strQuery);

        while (recordset.next()) {
            System.out.println(recordset.getField("full_name"));
            System.out.println(recordset.getField("phone"));
            System.out.println(recordset.getField("email"));
            System.out.println(recordset.getField("region"));
            System.out.println(recordset.getField("postal"));
            System.out.println(recordset.getField("street"));
        }

        //Multiple Where conditions
        Recordset rs1 = connection.executeQuery("Select * from Sheet1 where full_name='Belle Emard' and region='Bellflower' and postal='47492-4003'");
        while (rs1.next()) {
            System.out.println(rs1.getField("STREET"));
        }

        //Where method
        Recordset rs2 = connection.executeQuery("Select * from Sheet1").where("full_name='Belle Emard'").where("region='Bellflower'");
        while (rs2.next()) {
            System.out.println(rs2.getField("STREET"));
        }
        recordset.close();
        connection.close();
    }

    @Test
    public void insert() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(file);
        String strQuery = "INSERT INTO Sheet1(full_name,phone,postal,street) VALUES('Chathumal','1234',51000,'polonnaruwa')";

        connection.executeUpdate(strQuery);

        connection.close();
    }

    @Test
    public void update() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(file);
        String strQuery = "Update Sheet1 Set postal=1234 where region='Chesterfield' and full_name='Xavier Swift'";

        connection.executeUpdate(strQuery);

        connection.close();
    }

    @Test
    public void delete() throws FilloException {
        Fillo fillo = new Fillo();
        Connection connection = fillo.getConnection(file);
        String strQuery = "DELETE FROM Sheet1 where full_name='Roel Hoeger'";

        connection.executeUpdate(strQuery);

        connection.close();
    }
}
