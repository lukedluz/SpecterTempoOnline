package com.sc4r.SpecterTempoOnline.Runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.snockmc.temponline.SnockTempOnline;

public class UpdateOnlineTimeRunnable extends BukkitRunnable {
	
	private SnockTempOnline plugin;

	public UpdateOnlineTimeRunnable(SnockTempOnline plugin) {
		setPlugin(plugin);
		runTaskTimerAsynchronously(plugin, 0L, 12000L);
	}

	@SuppressWarnings("deprecation")
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			long tempo = System.currentTimeMillis() - (getPlugin().getTimes().get(p)).longValue();
			getPlugin().getManager().addTempo(p.getName(), tempo);
			getPlugin().getTimes().put(p, Long.valueOf(System.currentTimeMillis()));
			long diff = tempo;
			int seconds = (int) (diff / 1000L);
			if (seconds >= 3600) {
				int hours = seconds / 3600;
				seconds %= 3600;
				if (getPlugin().getManager().getDias(p.getName()) == 0) {
					if (hours >= 1) {
						getPlugin().getManager().addDias(p.getName(), 1);
						p.sendMessage("�aObrigado(a), nossa rede agradace por voc� ficar 1 hora on-line em nosso servidor.");
						p.sendTitle("�aVoc� ganhou", "�a+1.000 Cash.");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cash add " + p.getName() + " 1000");
					}
				} else if (hours >= 1 * getPlugin().getManager().getDias(p.getName())) {
					getPlugin().getManager().addDias(p.getName(), 1);
					p.sendMessage("�aObrigado(a), nossa rede agradace por voc� ficar +1 hora on-line em nosso servidor.");
					p.sendTitle("�aVoc� ganhou", "�a+1.000 Cash.");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cash add " + p.getName() + " 1000");
				}
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
