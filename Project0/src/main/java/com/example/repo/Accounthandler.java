package com.example.repo;
import com.example.business.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//this class will mainly deal with creating accounts


public class Accounthandler {
    private static final Logger logger =
        LoggerFactory.getLogger(Accounthandler.class);

    public void recordTransaction(Connection con, int accountId, String type, BigDecimal amount, Integer relatedAccountId) throws SQLException {
    String sql = "INSERT INTO transactions (account_id, transaction_type, amount, related_account_id) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, accountId);
        pstmt.setString(2, type);
        pstmt.setBigDecimal(3, amount);
        if (relatedAccountId != null) {
            pstmt.setInt(4, relatedAccountId);
        } else {
            pstmt.setNull(4, Types.INTEGER);
        }
        pstmt.executeUpdate();
    }
}

// 2. Overload for standalone operations (Deposit, Withdraw)
public void recordTransaction(int accountId, String type, BigDecimal amount, Integer relatedAccountId) {
    String sql = "INSERT INTO transactions (account_id, transaction_type, amount, related_account_id) VALUES (?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getConnection(); 
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, accountId);
        pstmt.setString(2, type);
        pstmt.setBigDecimal(3, amount);
        if (relatedAccountId != null) {
            pstmt.setInt(4, relatedAccountId);
        } else {
            pstmt.setNull(4, Types.INTEGER);
        }
        pstmt.executeUpdate();
    } catch (SQLException e) {
        logger.error("Failed to record transaction history: " + e.getMessage());
    }
}

// 3. Query all transactions involving this account (sent, received, deposited, withdrawn)
public List<Transaction> getTransactionHistory(int accountId) {
    List<Transaction> list = new ArrayList<>();
    String sql = "SELECT transaction_id, account_id, transaction_type, amount, related_account_id, created_at " +
                 "FROM transactions " +
                 "WHERE account_id = ? OR related_account_id = ? " +
                 "ORDER BY created_at DESC";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        pstmt.setInt(1, accountId);
        pstmt.setInt(2, accountId);

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int transId = rs.getInt("transaction_id");
                int accId = rs.getInt("account_id");
                String type = rs.getString("transaction_type");
                BigDecimal amt = rs.getBigDecimal("amount");
                int relAcc = rs.getInt("related_account_id");
                Integer relatedAccountId = rs.wasNull() ? null : relAcc;
                java.sql.Timestamp date = rs.getTimestamp("created_at");

                list.add(new Transaction(transId, accId, type, amt, relatedAccountId, date));
            }
        }
    } catch (SQLException e) {
        logger.error("Error retrieving transaction history: " + e.getMessage());
    }
    return list;
}


    public int createAccountpin(String pin)
    {
        String sql = "INSERT INTO accounts (pin) VALUES (?) RETURNING account_id";
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, pin);
            try(ResultSet resultSet = pstmt.executeQuery()) {
                if(resultSet.next()) {
                    int accountID = resultSet.getInt("account_id");
                    System.out.println("Account id created make sure to remeber this account id: " + accountID);
                    return accountID;
                } else {
                    System.out.println("No account id was found with the related pin");
                    return -1;
                }
            }
        }
        catch(SQLException e)
        {
            logger.error("Database could not connect");
            return -1;
        }
    }

    public boolean depoist(int accountID, BigDecimal amount)
    {   // this sql statement will update the balance based on the account_id
        String sql ="UPDATE accounts\n" + //
                    "SET balance = balance + ?\n" + //
                    "WHERE account_id = ?";
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {

            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, accountID);
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected == 1)
            {
                System.out.println("Depoist was succesful");
                recordTransaction(accountID, "Depoist", amount, null);
                return true;
            }
                System.out.println("Depoist was not succesful");
                return false;
        }
        catch(SQLException e) {
            logger.error("Database encountered an error");
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    public boolean validatePin(int accountID, String pin)
    {
        String sql = "SELECT account_id\n" + //
                        "FROM accounts\n" + //
                        "WHERE account_id = ?\n" + //
                        "AND pin = ?";
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, accountID);
            pstmt.setString(2, pin);
            
            try(ResultSet rs = pstmt.executeQuery())
            {
                if(rs.next())
                {
                    return true;
                }
                else{
                    logger.info("Invalid pin or accountID");
                    return false;
                }
            }

        } catch(SQLException e)
        {
            System.out.println("Server encountered an error");
            logger.warn("Vaildate pin was not successful database encountered an error");
            return false;
        }
    }
    public BigDecimal getBalance(int accountID)
    {
        String sql = "SELECT balance\n" + //
                        "FROM accounts\n" + //
                        "WHERE account_id = ?";
        BigDecimal balance;
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, accountID);
            
            try(ResultSet rs = pstmt.executeQuery())
            {
                if(rs.next())
                {
                    return rs.getBigDecimal("balance");
                }
                else {
                    logger.info("Balance was not found for ID");
                    return null;
                }
            }

        } catch(SQLException e)
        {
            logger.error("Database could not connect error code: " + e.getErrorCode());
            return null;
        }
    }
    public boolean withdraw(int accountID, String pin, BigDecimal amount)
    {
        // adding the and statement as a double verifcation so the database could never have a negative number will also check this on the
        // business layer as well
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            // index goes in order from first ? (left to right)
            pstmt.setBigDecimal(1, amount);
            pstmt.setInt(2, accountID);
            pstmt.setBigDecimal(3, amount);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected == 1)
            {
                logger.info("Withdraw was successful");
                recordTransaction(accountID, "WITHDRAW", amount, null);
                return true;
            }
            else {
                return false;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.warn("Database could not connect error code: " + e.getErrorCode());
            return false;
        }
    }
    public boolean transfer(int senderID, String pin, int reciverID, BigDecimal amount) 
    {
    String sqlSender = "UPDATE accounts\n" + //
                "SET balance = balance - ?\n" + //
                "WHERE account_id = ?\n" + //
                "AND balance >= ?";

    String sqlReciver = "UPDATE accounts\n" + //
                "SET balance = balance + ?\n" + //
                "WHERE account_id = ?";
    
    {
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmtS = con.prepareStatement(sqlSender); PreparedStatement pstmtR = con.prepareStatement(sqlReciver))
        {
            con.setAutoCommit(false);
            pstmtS.setBigDecimal(1, amount);
            pstmtS.setInt(2, senderID);
            pstmtS.setBigDecimal(3,amount);

            pstmtR.setBigDecimal(1, amount);
            pstmtR.setInt(2, reciverID);

            int senderRows = pstmtS.executeUpdate();
            int receiverRows = pstmtR.executeUpdate();

            if(senderRows != 1) // to make sure both statements work properly
            {
                con.rollback();
                return false;
            }
            if(receiverRows != 1) // if one fails return false and call rollback
            {
                con.rollback();
                return false;
            }
            // if both if statements are false then commit the changes
            // set it up this way to ensure that if anything fails then the entire method will not work
            // so when everything passes it can commit changes successfully
            recordTransaction(con, senderID, "TRANSFER_OUT", amount, reciverID);
            recordTransaction(con, reciverID,"TRANSFER_IN", amount, senderID);
            con.commit();
            return true;

        }
        catch(SQLException e)
        {
            logger.warn("Error encountered: " + e.getErrorCode());
            return false;
        }
    }
    }
    public boolean validAccountID(int accountID)
    {
        String sql = "SELECT account_id\n" + //
                        "FROM accounts\n" + //
                        "WHERE account_id = ?\n";
        try(Connection con = DatabaseConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, accountID);
            
            try(ResultSet rs = pstmt.executeQuery())
            {
                if(rs.next())
                {
                    logger.info("Account ID does exist ");
                    return true;
                }
                else{
                    logger.info("Account does not exisit");
                    return false;
                }
            }

        } catch(SQLException e)
        {
            logger.error("Vaildate pin was not successful database encountered an error");
            return false;
        }
    }
}
