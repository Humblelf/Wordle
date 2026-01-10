package data;

import java.sql.*;

/**
 * 该类用于储存用户每个用户的对局数据
 * （关联表MemberData）
 */



public class GamesData
{
    private static final String path = "jdbc:sqlite:src/data/GamesData.db";
    private static Connection connection;


    private GamesData(){}

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

    public static void createGamesDataTable()
    {
        String sqlCreate = "CREATE TABLE IF NOT EXITS GamesData (" +
                "Name TEXT NOT NULL UNIQUE" +
                "Result TEXT NOT NULL" ;
        try(PreparedStatement stmtCreate = connection.prepareStatement(sqlCreate))
        {
            stmtCreate.execute();
        }
        catch(SQLException e)
        {
            throw new RuntimeException("创建游戏数据表失败: " + e.getMessage(), e);
        }
    }

    public static void insertGamesData(String name, String result)
    {
        String sqlInsert = "INSERT INTO GamesData (Name, Result) VALUES (?, ?)";
        try(PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert))
        {
            stmtInsert.setString(1,name);
            stmtInsert.setString(2,result);
        } catch (SQLException e)
        {
            throw new RuntimeException("插入数据失败" + e.getMessage(), e);
        }
    }

    public static void addGamesData()
    {}


}
