package com.appflow.appflow.Repository;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@Repository("DatabaseFlow")
public class DatabseFlowRepository {

    public Statement getStatement(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getData(String sql) {
        try {
            Statement statement = this.getStatement("jdbc:mysql://localhost:3306/jdbc-share", "root", "Raghav@123#");
            ResultSet resultSet = statement.executeQuery(sql);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            JSONArray output = new JSONArray();

            String str = "";
            for (int i = 1; i <= columnsNumber; i++) {
                str = str + resultSetMetaData.getColumnName(i) + ",";
            }
            str.substring(0, str.length()-1);
            str = str + "\n";
            System.out.println(columnsNumber);
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if ( i != columnsNumber && resultSet.getString(i) != null) {
                        str = str + resultSet.getString(i) + ",";
                    } else {
                        str = str + resultSet.getString(i);
                    }
                }
                str = str + '\n';
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String insertDataInCSV() {
        String sql = "select * from employees";
        String data = this.getData(sql);
        try {
            FileWriter fileWriter = new FileWriter("database.csv");
            fileWriter.write(data);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data has been inserted into the file";
    }
}
