package com.sc4r.SpecterTempoOnline.Runnable;

import org.bukkit.scheduler.*;

import com.snockmc.temponline.*;

import org.bukkit.plugin.*;
import java.sql.*;
import java.util.stream.*;
import java.util.*;

public class UpdateTopTime extends BukkitRunnable {
	
	private SnockTempOnline plugin;
	private static List<PlayerTime> top;

	public UpdateTopTime(final SnockTempOnline plugin) {
		UpdateTopTime.top = new ArrayList<PlayerTime>();
		this.setPlugin(plugin);
		this.runTaskTimerAsynchronously((Plugin) plugin, 0L, 6000L);
	}

	public void run() {
		ArrayList<PlayerTime> tops = new ArrayList<PlayerTime>();
		try {
			if (SnockTempOnline.get().getMySQL().con.isClosed()) {
				SnockTempOnline.get().getMySQL().open();
			}
			PreparedStatement stm = SnockTempOnline.get().getMySQL().con.prepareStatement("SELECT * FROM TempoOnline;");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				tops.add(new PlayerTime(rs.getString("nome"), rs.getLong("Tempo")));
			}
			rs.close();
			stm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stream<PlayerTime> o = tops.parallelStream().sorted((x, y) -> y.getTime().compareTo(x.getTime()));
		int id = 1;
		ArrayList<PlayerTime> t = new ArrayList<PlayerTime>();
		for (PlayerTime entrada : o.collect(Collectors.toList())) {
			if (id > 10)
				break;
			t.add(entrada);
			++id;
		}
		top = t;
	}

	public static List<PlayerTime> getTop() {
		return UpdateTopTime.top;
	}

	public SnockTempOnline getPlugin() {
		return this.plugin;
	}

	public void setPlugin(final SnockTempOnline plugin) {
		this.plugin = plugin;
	}
}
