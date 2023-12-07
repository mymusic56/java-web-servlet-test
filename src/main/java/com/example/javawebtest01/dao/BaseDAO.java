package com.example.javawebtest01.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.javawebtest01.config.DBProperties;
import com.example.javawebtest01.dto.WebsitesDO;

/**
 * 
 * @since 2023/12/7 15:48
 */
public abstract class BaseDAO {

    public List<WebsitesDO> getConnection(String sql, DBProperties dbProperties) {
        Connection conn = null;
        Statement stmt = null;
        List<WebsitesDO> doList = new ArrayList<>();

        try {
            // 注册 JDBC 驱动器
            Class.forName(dbProperties.getDriver());

            // 打开一个连接
            conn = DriverManager
                .getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());

            // 执行 SQL 查询
            stmt = conn.createStatement();
            sql = "SELECT * FROM websites";
            ResultSet rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                int alexa = rs.getInt("alexa");
                String country = rs.getString("country");
                Date createdAt = rs.getDate("created_at");
                Date updatedAt = rs.getDate("updated_at");
                WebsitesDO websitesDO = new WebsitesDO();
                websitesDO.setId(id);
                websitesDO.setName(name);
                websitesDO.setUrl(url);
                websitesDO.setAlexa(alexa);
                websitesDO.setCountry(country);
                websitesDO.setCreatedAt(createdAt);
                websitesDO.setUpdatedAt(updatedAt);
                doList.add(websitesDO);
            }
            System.out.println("doList = " + doList);

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return doList;
    }
}
