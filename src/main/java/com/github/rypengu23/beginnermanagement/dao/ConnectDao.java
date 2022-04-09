package com.github.rypengu23.beginnermanagement.dao;

import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import org.bukkit.BanList;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDao {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public ConnectDao(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * DB初期接続処理
     * テーブルが無い場合、作成する
     */
    public boolean connectionCheck() {

        int result = 0;

        //管理テーブル
        String infoTable = ("CREATE TABLE IF NOT EXISTS BM_INFO(UUID VARCHAR(36), NAME VARCHAR(16), FIRST_LOGIN_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, NUMBER_OF_VIOLATIONS INT NOT NULL DEFAULT 0, WHITELIST INT NOT NULL DEFAULT 0, PRIMARY KEY(UUID))");

        try {
            //初回のみシーケンスに0をセット
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            //管理テーブル
            statement.executeUpdate(infoTable);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection(){

        String url = "jdbc:mysql://" + mainConfig.getHostname() + "/" + mainConfig.getDb() + "?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false";
        String user = mainConfig.getUser();
        String password = mainConfig.getPassword();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }

}
