package src.manageDetails.inputLayer;

import src.manageDetails.exception.CustomizedException;
import src.manageDetails.dataBase.DBOperation;
import src.manageDetails.pojo.AccountInfo;

import java.util.ArrayList;
import java.util.Scanner;

public class AddAccountInfo {
    Scanner input = new Scanner(System.in);
    DBOperation dbConnection = new DBOperation();
    AccountInfo accountInfo = new AccountInfo();
    ArrayList<AccountInfo> accountInfoArrayList = new ArrayList<>();

    public ArrayList<AccountInfo> accountInput( ) throws CustomizedException {

        try {
            System.out.println("Enter Account Info of " );
            System.out.println("Enter the Account Number: ");
            long accNumber = input.nextLong();
            System.out.println("Enter the Account Balance: ");
            int accBalance = input.nextInt();
            input.nextLine();
            System.out.println("Enter the Branch Name: ");
            String accBranch = input.nextLine();

            accountInfo.setAccNo(accNumber);
            accountInfo.setAccBalance(accBalance);
            accountInfo.setAccBranch(accBranch);
            accountInfoArrayList.add(accountInfo);
        } catch (Exception e) {
            throw new CustomizedException("Enter Valid input", e);
        }
        return accountInfoArrayList;
    }
        public void insertAccountDB(int[] cusId,ArrayList<AccountInfo> accountInfoArrayList) throws CustomizedException {
            dbConnection.insertAccountToDB(cusId,accountInfoArrayList);
        }
    }


