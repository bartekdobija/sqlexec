package com.github.bartekdobija.sqlexec.core;

import com.github.bartekdobija.sqlexec.args.Arguments;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.sql.*;

public class Application implements Runnable {

  private Arguments args;
  private String SEPARATOR = ",";
  private String NEW_LINE = "\n";

  public Application(Arguments arguments){
    args = arguments;
  }

  @Override
  public void run() {
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(args.driver);
      conn = DriverManager.getConnection(
          args.url,
          args.username,
          getPassword(args.password, args.passwordFile)
      );
      stmt = conn.createStatement();
      stmt.execute(args.query);
      printResultSet(stmt.getResultSet());
    } catch (ClassNotFoundException | SQLException | IOException e) {
      System.out.println(e);
    } finally {
      try {
        if(stmt != null) stmt.close();
        if(conn != null) conn.close();
      } catch (SQLException e) {}
    }
  }

  private String getPassword(String password, String passwordFile)
      throws IOException {
    return password != null ? password : loadFromFile(passwordFile);
  }

  private void printResultSet(ResultSet rs) throws SQLException {
    if (rs == null) return;
    while (rs.next()) {
      for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
        if (i > 1) System.out.print(SEPARATOR);
        System.out.print(rs.getString(i));
      }
      System.out.print(NEW_LINE);
    }
  }

  private String loadFromFile(String path) throws IOException {
    FileSystem fs = FileSystem.get(new Configuration());
    FSDataInputStream passFile = null;
    String password = null;
    try{
      passFile = fs.open(new Path(path));
      password = passFile.readLine();
    } finally {
      if(passFile != null) passFile.close();
      fs.close();
    }
    return password;
  }
}
