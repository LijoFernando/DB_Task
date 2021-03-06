package src.manageDetails;

import src.manageDetails.dataBase.DBOperation;
import src.manageDetails.exception.CustomizedException;
import src.manageDetails.pojo.AccountInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadData {
    DBOperation dbOperation =  new DBOperation();
    HashMap <Integer, HashMap<Integer, AccountInfo> > outerHashMap = new HashMap<>();

    public String loadHashMap() throws CustomizedException {
        //load hashMap from DB
        ArrayList<AccountInfo> dbArrayList = dbOperation.accountInfoRecords();
        try {
            for (AccountInfo accountinfo : dbArrayList) {
                int cusId = accountinfo.getCusId();
                int accId = accountinfo.getAccId();
                HashMap<Integer, AccountInfo> innerHashMap = outerHashMap.get(cusId);
                if (innerHashMap == null) {
                    innerHashMap = new HashMap<>();
                    outerHashMap.put(cusId, innerHashMap);
                }
                innerHashMap.put(accId, accountinfo);
            }
            System.out.println(outerHashMap.entrySet());
            return "HashMap Loaded Successfully";
        }
        catch (Exception e) {
            throw new CustomizedException("AccountInfo HashMap not loaded");
        }
    }

    public void loadSpecific(Integer cusId) throws CustomizedException {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(outerHashMap.get(cusId).values().toString());
            System.out.println(arrayList);
        }catch (NullPointerException e){
            throw  new CustomizedException("Customer ID is Invalid");
        }
    }
}
