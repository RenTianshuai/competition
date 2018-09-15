package cn.com.yusys.geek.subject1.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SQLUtil {

    private static String url = "jdbc:mysql://127.0.0.1:3306/message";
    private static String user = "root";
    private static String password = "admin";


    /**
     * 根据sql查询结果，以Map的形式返回
     *
     * @param sql
     * @param param
     * @return
     */
    public static List<Map<String, Object>> resultQuery(String sql, String[] param) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
        // Map<String, Object> result = new HashMap<>();

        try {
            pst = conn.prepareStatement(sql);
            if (param != null)
                for (int i = 0; i < param.length; i++) {
                    pst.setString(i + 1, param[i]);//设置条件id
                }
            rs = pst.executeQuery();

            //获得ResultSetMetaData对象
            ResultSetMetaData rsmd = rs.getMetaData();
            //获得返回此 ResultSet 对象中的列数
            int count = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> result = new HashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    //获取指定列的表目录名称
                    String label = rsmd.getColumnLabel(i);
                    //以 Java 编程语言中 Object 的形式获取此 ResultSet 对象的当前行中指定列的值
                    Object object = rs.getObject(i);
                    //把数据库中的字段名和值对应为一个map对象中的一个键值对
                    result.put(label.toUpperCase(), object);
                }
                resultList.add(result);
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConn(conn);
        }
        return resultList;

    }

    public static int insertData(String sql, Object[] param) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pre = null;
        int result = 0;
        try {
            pre = conn.prepareStatement(sql);
            if (param != null)
                for (int i = 0; i < param.length; i++) {
                    pre.setObject(i + 1, param[i]);
                }
            result = pre.executeUpdate();
        } finally {
            pre.close();
            closeConn(conn);
        }

        return result;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            //记录日志
            e.printStackTrace();
            //默认业务处理
            //向上抛出异常
            throw new RuntimeException("数据库连接获取失败！", e);
        }
    }


    public static void closeConn(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
