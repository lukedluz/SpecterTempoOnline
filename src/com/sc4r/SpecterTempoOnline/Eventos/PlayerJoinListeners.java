package com.sc4r.SpecterTempoOnline.Eventos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.snockmc.temponline.SnockTempOnline;

public class PlayerJoinListeners implements Listener {
	
	private SnockTempOnline plugin;

	public PlayerJoinListeners(SnockTempOnline plugin) {
		setPlugin(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		try {
			if (getPlugin().getMySQL().con.isClosed()) {
				getPlugin().getMySQL().open();
			}
			PreparedStatement ps = getPlugin().getMySQL().con.prepareStatement("SELECT * FROM TempoOnline WHERE nome=?");
			ps.setString(1, p.getName());
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				ps = getPlugin().getMySQL().con.prepareStatement("INSERT INTO `TempoOnline`(`nome`, `Tempo`, `Dias`) VALUES (?,?,?)");
				ps.setString(1, p.getName());
				ps.setLong(2, 0L);
				ps.setInt(3, 0);
				ps.execute();
				ps.close();
				rs.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		getPlugin().getTimes().put(p, Long.valueOf(System.currentTimeMillis()));
	}

	public SnockTempOnline getPlugin() {
		return this.plugin;
	}

	public void setPlugin(SnockTempOnline plugin) {
		this.plugin = plugin;
	}
}
