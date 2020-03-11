package com.cts.common;

import com.cts.model.Transaction;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunitCommon {
    public static final String TRANSACTION_URL = "/statement";
    public static final String DUPLICATE_RECORD_FILE = "records_sample_DuplicateRecord.json";
    public static final String SUCCESS_RECORD_FILE = "records_sample_SuccessRecord.json";
    public static final String DUPLICATE_INCORRECTBALANCE_RECORD_FILE = "records_sample_Dup_bal_incorrect.json";

    public String getAbsolutePath(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }
    public String getTransactionList(String filePath) {
        filePath = getAbsolutePath(filePath);
        BufferedReader br = null;
        StringBuffer strLine = new StringBuffer();
        try {
            String temp;
            br = new BufferedReader(new FileReader(filePath));
            while ((temp = br.readLine()) != null) {
                strLine.append(temp);
            }

            br.close();
        } catch (Exception e){
            System.out.println("Exception on reading file - "+ e);
        }
        return strLine.toString();
    }

    private static BigDecimal convertToBigdecial(String str){
        double val = Double.parseDouble(str.replace("+", ""));
        BigDecimal returnVal =  new BigDecimal(val);
        return returnVal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static List<Transaction> convertTransactionsFromList(String transactionDatas) {
        Gson gson = new Gson();
        List<Transaction> transactionList = new ArrayList<>();
        List jsonTransactionList = gson.fromJson(transactionDatas, ArrayList.class);

        for(Object transaction1: jsonTransactionList){
            Map<String, Object> mapVal = (Map<String, Object>) transaction1;
            Transaction transaction = new Transaction();
            transaction.setReference(((Double)mapVal.get("reference")).longValue());
            transaction.setAccountNumber((String)mapVal.get("accountNumber"));
            transaction.setDescription((String)mapVal.get("description"));
            transaction.setStartBalance(convertToBigdecial(""+mapVal.get("startBalance")));
            transaction.setMutation(convertToBigdecial(""+ mapVal.get("mutation")));
            transaction.setEndBalance(convertToBigdecial(""+mapVal.get("endBalance")));
            transactionList.add(transaction);
        }
        return transactionList;
    }
}
