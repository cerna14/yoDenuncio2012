package cl.cxc.persistencia;

import cl.cxc.Denuncia;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;

public class DenunciaBD {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private final String SELECT_DENUNCIAS="select * from denuncias";
  private final String SELECT_DENUNCIA="select * from denuncias where id=?";
  private final String INSERT_DENUNCIA="insert into  denuncias (lat, lng, descripcion,archivo_imagen,fecha, estado, direccion) values (?, ?, ?, ?, ?,?,?)";
  private final String INSERT_DENUNCIA_ARCHIVO="insert into  denuncias (archivo_imagen) values (?)";

    public DenunciaBD() {

    }

    public LinkedList<Denuncia> obtieneDenuncias() {
        LinkedList<Denuncia> denuncias = new LinkedList<Denuncia>();
        connect = MySQLConexion.obtieneConexion();
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(SELECT_DENUNCIAS);
            while (resultSet.next()) {
                denuncias.add(new Denuncia(resultSet.getInt(1), resultSet.getDouble(2),resultSet.getDouble(3),resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6),resultSet.getString(7),resultSet.getString(8)) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      close();
       return denuncias;
    }
  public Denuncia obtieneDenuncia(int id) {
      Denuncia denuncia = null;
      connect = MySQLConexion.obtieneConexion();
      try {
         
          preparedStatement = connect.prepareStatement(SELECT_DENUNCIA);
        preparedStatement.setInt(1,id);
          resultSet= preparedStatement.executeQuery();
          while (resultSet.next()) {
              
              denuncia= new Denuncia(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getString(7), resultSet.getString(8));
            System.err.println("longitud "+resultSet.getDouble(2));
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
      MySQLConexion.cerrarConexion(connect);
    close();
     return denuncia;
  }
  
  public void insertarDenuncia(int id, double lat, double lon, String desc, String archivo, Date fech, String est, String direccion) {
      Denuncia denuncia = null;
      connect = MySQLConexion.obtieneConexion();
      try {
         
          preparedStatement = connect.prepareStatement(INSERT_DENUNCIA);
       // preparedStatement.setInt(1,id);
        preparedStatement.setDouble(1,lat);
        preparedStatement.setDouble(2,lon);
        preparedStatement.setString(3,desc);
        preparedStatement.setString(4,archivo);
        preparedStatement.setDate(5, (Date)fech);
        preparedStatement.setString(6,est);
        preparedStatement.setString(7,direccion);
         preparedStatement.executeUpdate();
          
      } catch (SQLException e) {
          e.printStackTrace();
      }
      close();
      MySQLConexion.cerrarConexion(connect);
     
  }

  public int insertarDenuncia(String archivo) {
      Denuncia denuncia = null;
      connect = MySQLConexion.obtieneConexion();
      int id=-1;
      try {
         
          preparedStatement = connect.prepareStatement(INSERT_DENUNCIA_ARCHIVO);
        preparedStatement.setString(1,archivo);
       preparedStatement.executeUpdate();
        
        statement = connect.createStatement();
        resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
        while (resultSet.next()) {
          id=resultSet.getInt(1);
        }
          
      } catch (SQLException e) {
          e.printStackTrace();
      }
      close();
      MySQLConexion.cerrarConexion(connect);
     return id;
  }

  
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
