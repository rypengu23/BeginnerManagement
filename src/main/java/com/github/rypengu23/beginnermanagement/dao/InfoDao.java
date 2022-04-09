package com.github.rypengu23.beginnermanagement.dao;

import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoDao {

    /**
     * プレイヤーの情報を取得
     * @param UUID
     * @return
     */
    public PlayerDataModel getPlayerData(String UUID){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();

        PlayerDataModel playerDataModel = new PlayerDataModel();

        try {
            int p = 1;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append("BM_INFO ");
            sql.append("WHERE ");
            sql.append("UUID = ? ");

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ps.setString(p++,UUID);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                playerDataModel.setUUID(rs.getString("UUID"));
                playerDataModel.setPlayerName(rs.getString("NAME"));
                playerDataModel.setPunishmentNumberOfTimes(rs.getInt("NUMBER_OF_VIOLATIONS"));
                playerDataModel.setFirstLoginDate(rs.getTimestamp("FIRST_LOGIN_DATE"));
                if(rs.getInt("WHITELIST") == 0){
                    playerDataModel.setWhitelist(false);
                }else{
                    playerDataModel.setWhitelist(true);
                }
            }else{
                playerDataModel = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerDataModel;
    }

    /**
     * プレイヤーの情報を取得
     * @param playerName
     * @return
     */
    public PlayerDataModel getPlayerDataFromPlayerName(String playerName){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();

        PlayerDataModel playerDataModel = new PlayerDataModel();

        try {
            int p = 1;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM ");
            sql.append("BM_INFO ");
            sql.append("WHERE ");
            sql.append("NAME = ? ");

            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ps.setString(p++,playerName);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                playerDataModel.setUUID(rs.getString("UUID"));
                playerDataModel.setPlayerName(rs.getString("NAME"));
                playerDataModel.setPunishmentNumberOfTimes(rs.getInt("NUMBER_OF_VIOLATIONS"));
                playerDataModel.setFirstLoginDate(rs.getTimestamp("FIRST_LOGIN_DATE"));
                if(rs.getInt("WHITELIST") == 0){
                    playerDataModel.setWhitelist(false);
                }else{
                    playerDataModel.setWhitelist(true);
                }
            }else{
                playerDataModel = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerDataModel;
    }

    /**
     * プレイヤー情報を作成
     * @param UUID
     * @return
     */
    public int insertNewPlayerInfo(String UUID, String playerName){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();
        int result = 0;

        try {
            int p = 1;

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO ");
            insertSql.append("BM_INFO ");
            insertSql.append("(UUID, NAME) ");
            insertSql.append("VALUES (?,?)");

            PreparedStatement ps = connection.prepareStatement(insertSql.toString());
            ps.setString(p++, UUID);
            ps.setString(p++, playerName);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * プレイヤー名を変更
     * @param UUID
     * @return
     */
    public int updateNewPlayerName(String UUID, String playerName){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();
        int result = 0;

        try {
            int p = 1;

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("UPDATE ");
            insertSql.append("BM_INFO ");
            insertSql.append("SET ");
            insertSql.append("NAME = ? ");
            insertSql.append("WHERE ");
            insertSql.append("UUID = ?");

            PreparedStatement ps = connection.prepareStatement(insertSql.toString());
            ps.setString(p++, playerName);
            ps.setString(p++, UUID);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 処罰回数をカウントアップ
     * @param UUID
     * @return
     */
    public int countUpPunishmentNumberOfTimes(String UUID){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();
        int result = 0;

        try {
            int p = 1;

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("UPDATE ");
            insertSql.append("BM_INFO ");
            insertSql.append("SET ");
            insertSql.append("NUMBER_OF_VIOLATIONS = NUMBER_OF_VIOLATIONS + 1 ");
            insertSql.append("WHERE ");
            insertSql.append("UUID = ? ");

            PreparedStatement ps = connection.prepareStatement(insertSql.toString());
            ps.setString(p++, UUID);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ホワイトリストフラグをONにする
     * @param UUID
     * @return
     */
    public int enableWhiteList(String UUID){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();
        int result = 0;

        try {
            int p = 1;

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("UPDATE ");
            insertSql.append("BM_INFO ");
            insertSql.append("SET ");
            insertSql.append("WHITELIST = 1 ");
            insertSql.append("WHERE ");
            insertSql.append("UUID = ? ");

            PreparedStatement ps = connection.prepareStatement(insertSql.toString());
            ps.setString(p++, UUID);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ホワイトリストフラグをOFFにする
     * @param UUID
     * @return
     */
    public int disableWhiteList(String UUID){
        ConnectDao connectDao = new ConnectDao();
        Connection connection = connectDao.getConnection();
        int result = 0;

        try {
            int p = 1;

            StringBuilder insertSql = new StringBuilder();
            insertSql.append("UPDATE ");
            insertSql.append("BM_INFO ");
            insertSql.append("SET ");
            insertSql.append("WHITELIST = 0 ");
            insertSql.append("WHERE ");
            insertSql.append("UUID = ? ");

            PreparedStatement ps = connection.prepareStatement(insertSql.toString());
            ps.setString(p++, UUID);

            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
