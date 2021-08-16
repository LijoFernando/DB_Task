package src.manageDetails.dataBase;

import src.manageDetails.exception.CustomizedException;
import src.manageDetails.pojo.AccountInfo;
import src.manageDetails.pojo.Customer;

import  java.sql.*;
import java.util.ArrayList;

public class DBOperation {
    private Connection con;

    private void loadConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer", "root", "Root@123");
    }

    public Connection getConnection() throws SQLException {
        if (con == null) {
            loadConnection();
        }
        return con;
    }

    public ArrayList<AccountInfo> accountInfoRecords() throws CustomizedException {
        ArrayList<AccountInfo> accountInfoArray = new ArrayList<>();
        String query = "SELECT * FROM AccountInfo";

        try {
            PreparedStatement ps  = getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        AccountInfo accountInfo = new AccountInfo();
                        accountInfo.setAccId(rs.getInt(1));
                        accountInfo.setAccNo(rs.getLong(2));
                        accountInfo.setAccBalance(rs.getInt(3));
                        accountInfo.setAccBranch(rs.getString(4));
                        accountInfo.setCusId(rs.getInt(5));
                        accountInfoArray.add(accountInfo);
                    }
                } finally {
                    rs.close();
                    ps.close();
                }
        } catch (SQLException e) {
            throw new CustomizedException("Account HashMap Loading is Unsuccessful,Invalid Query!!");
        }
    return accountInfoArray;
    }

    //insert Customer Info to Database
    public int[] insertDetailToDB (ArrayList<Customer> customerArrayList) throws CustomizedException {
        String query = "insert into CustomerInfo (CusName, CusDoB, Location) values (?, ?, ?)";
        ResultSet rs = null;
        try {
            PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int[] cusId = new int[customerArrayList.size()];
            try {
                for (int i = 0; i < customerArrayList.size(); i++) {
                    Customer customer = customerArrayList.get(i);
                    String name = customer.getName();
                    Date date = customer.getDofBirth();
                    String location = customer.getLocation();
                    ps.setString(1, name);
                    ps.setDate(2, date);
                    ps.setString(3, location);
                    ps.addBatch();
                }
                ps.executeBatch();
                rs = ps.getGeneratedKeys();
                while(rs.next()) {
                    int i = 0;
                    cusId[i]= rs.getInt(1);
                    i++;
                }
                return cusId;
            } catch (SQLException e) {
                throw new CustomizedException("SQL Exception", e);
            } finally {
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new CustomizedException("Customer Records Submission Unsuccessful", e);
        }

    }
    
    //Insert AccountInfo to Database
    public void insertAccountToDB(int[] cusID, ArrayList<AccountInfo> accountInfoArrayList) throws CustomizedException {
            String query2 = "insert into AccountInfo (AccNumber, AccBalance, Branch, CusID ) values (?, ?, ?,?)";
            try {
                PreparedStatement ps =  getConnection().prepareStatement(query2);
                try {
                    for (int i = 0; i < accountInfoArrayList.size(); i++) {
                        AccountInfo accountInfo = accountInfoArrayList.get(i);
                        long accNo = accountInfo.getAccNo();
                        int accBalance = accountInfo.getAccBalance();
                        String accBranch = accountInfo.getAccBranch();
                        int cusId = cusID[i];
                        ps.setLong(1, accNo);
                        ps.setInt(2, accBalance);
                        ps.setString(3, accBranch);
                        ps.setInt(4, cusId);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                    System.out.println("Account Record inserted");
                } finally {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new CustomizedException("Account Detail Submission Failed, Query Error");
            }
    }
}