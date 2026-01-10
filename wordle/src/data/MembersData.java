package data;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**该类使用 SOLite
 * 用于储存 :
 * 1.用户ID
 * 2.用户姓名
 * 3.用户密码
 * 4.用户密保问题及其答案
 * ………………
*/

public final class MembersData
{
    private static final String path = "jdbc:sqlite:src/data/Member.db";
    private static Connection connection = getConnection();

    private MembersData()
    {
    }


    private static Connection getConnection()
    {
        try
        {
            connection = DriverManager.getConnection(path);
            return connection;
        }
        catch (SQLException e)
        {
            throw new RuntimeException("初始化数据库连接失败: " + e.getMessage(), e);
        }
    }


    public static void createMembersDataTable()
    {
        String splCreate = "CREATE TABLE IF NOT EXITS memberData (" +
                "ID TEXT PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL UNIQUE (" +
                "Birthday TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "SecurityQuestion TEXT NOT NULL," +
                "SecurityAnswer TEXT NOT NULL," +
                "RegisterTime TEXT NOT NULL UNIQUE (" ;

        try (PreparedStatement stmtCreate = connection.prepareStatement(splCreate))
        {
            stmtCreate.execute();
        }
        catch(SQLException e)
        {
            throw new RuntimeException("创建用户表失败: " + e.getMessage(), e);
        }
    }


    public static void insertData(String name ,String birthday,String password,String securityQuestion,String securityAnswer)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String registerTime = dtf.format(LocalDate.now());

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String registerTime1 =  dtf1.format(LocalDateTime.now());
        String id = registerTime1 + birthday;

        String sqlInsert = "INSERT INTO memberData (ID, Name, Birthday, Password, SecurityQuestion, SecurityAnswer, RegisterTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert))
        {
            stmtInsert.setString(1,id);
            stmtInsert.setString(2, name);
            stmtInsert.setString(3,birthday);
            stmtInsert.setString(4,password);
            stmtInsert.setString(5,securityQuestion);
            stmtInsert.setString(6,securityAnswer);
            stmtInsert.setString(7,registerTime);

            stmtInsert.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("注册用户失败: " + e.getMessage(), e);
        }
    }


    public static void alterData(String currentId, String alterWhat, String newData)
    {
        String sqlAlter = String.format("UPDATE memberData SET %s = ? WHERE ID = ?", alterWhat);
        try(PreparedStatement stmtAlter = connection.prepareStatement(sqlAlter))
        {
            stmtAlter.setString(1,newData);
            stmtAlter.setString(2,currentId);
            stmtAlter.execute();
        }
        catch(SQLException e)
        {
            throw new RuntimeException("修改失败: " + e.getMessage(), e);
        }
    }


    public static String getData(String id, String getWhat)
    {
        String sqlGet = String.format("SELECT %s FROM memberData WHERE ID = ?", getWhat);
        try(PreparedStatement stmtGet = connection.prepareStatement(sqlGet))
        {
            stmtGet.setString(1,id);
            return stmtGet.executeQuery().getString(getWhat);
        }
        catch(SQLException e)
        {
            throw new RuntimeException("获取数据失败: " + e.getMessage(), e);
        }
    }

    public static void deleteData(String id)
    {
        String sqlDelete = "DELETE FROM memberData WHERE ID = ?";
        try(PreparedStatement stmtDelete = connection.prepareStatement(sqlDelete))
        {
            stmtDelete.setString(1,id);
            stmtDelete.execute();
        }
        catch(SQLException e)
        {
            throw new RuntimeException("删除数据失败: " + e.getMessage(), e);
        }
    }
}
