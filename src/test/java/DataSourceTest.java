import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wen.shop.utils.DataSourceUtils;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourceTest {

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = DataSourceUtils.getDataSource();
        Connection connection = dataSource.getConnection();
//        System.out.println(connection.getMetaData().getDatabaseProductName());
        PreparedStatement smt = connection.prepareStatement("select * FROM user ");
        ResultSet resultSet = smt.executeQuery();
        resultSet.next();
        System.out.println(resultSet.getString("username"));
    }

}
