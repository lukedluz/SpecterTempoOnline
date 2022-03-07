package com.sc4r.SpecterTempoOnline.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.command.ConsoleCommandSender;

import com.snockmc.temponline.SnockTempOnline;

public class MySQL {
	
	private SnockTempOnline plugin;
	public Connection con;
	public static ConsoleCommandSender sc;

	public MySQL(SnockTempOnline plugin) {
		setPlugin(plugin);
	}

	public void open() {
		String url = "jdbc:mysql://" + getPlugin().getConfig().getString("MySQL.Host") + ":" + getPlugin().getConfig().getString("MySQL.Porta") + "/" + getPlugin().getConfig().getString("MySQL.Database");
		String user = getPlugin().getConfig().getString("MySQL.Usuario");
		String password = getPlugin().getConfig().getString("MySQL.Senha");
		try {
			this.con = DriverManager.getConnection(url, user, password);
		} catch (SQLException localSQLException) {
		}
	}

	protected void close() {
		if (this.con != null) {
			try {
				this.con.close();
			} catch (SQLException localSQLException) {
			}
		}
	}

	public void createTable() {
		if (this.con != null) {
			PreparedStatement stm = null;
			try {
				stm = this.con.prepareStatement("CREATE TABLE IF NOT EXISTS `TempoOnline` (`nome` VARCHAR(48) NULL, `Tempo` VARCHAR(48) NULL, `Dias` int(48) NULL);");
				stm.executeUpdate();
			} catch (SQLException localSQLException) {
			}
		}
	}

	public SnockTempOnline getPlugin() {
		return this.plugin;
	}

	public void setPlugin(SnockTempOnline plugin) {
		this.plugin = plugin;
	}
}
