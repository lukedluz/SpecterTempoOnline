package com.sc4r.SpecterTempoOnline.Comandos;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.snockmc.temponline.SnockTempOnline;
import com.snockmc.temponline.PlayerTime;
import com.snockmc.temponline.runnable.UpdateTopTime;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TempCommand extends Command {
	
	private SnockTempOnline plugin;

	public TempCommand(SnockTempOnline plugin) {
		super("tempo");
		register();
		setPlugin(plugin);
	}

	private void register() {
		try {
			Field cmap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			cmap.setAccessible(true);
			CommandMap map = (CommandMap) cmap.get(Bukkit.getServer());
			map.register(getName(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public boolean execute(CommandSender sender, String arg1, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			long time = System.currentTimeMillis() - (getPlugin().getTimes().get(p)).longValue() + getPlugin().getManager().getTempo(p.getName()).longValue();
			p.sendMessage("�eVoc� tem: �f" + getTempo(time) + " �ede tempo on-line.");
		}
		if (args.length != 0) {
			if (args[0].equalsIgnoreCase("top")) {
				p.sendMessage("");
				p.sendMessage(" �eTop 10 �e�lTEMPOS �edo servidor:");
				p.sendMessage("  �7(Atualizado � cada 5 minutos)");
				p.sendMessage("");
				int i = 1;
				for (PlayerTime t : UpdateTopTime.getTop()) {
					String prefix = PermissionsEx.getUser(t.getName()).getGroups()[0].getPrefix().replace("&", "�");
					p.sendMessage("�f  " + i + ". " + prefix + t.getName() + ": �7" + getTempo(t.getTime().longValue()));
					i++;
				}
				p.sendMessage("");
			}
			if (args[0].equalsIgnoreCase("ajuda")) {
				p.sendMessage("");
				p.sendMessage("�2Comandos dispon�veis:");
				p.sendMessage("");
				p.sendMessage("�a/tempo �8- �7Verifique o seu tempo on-line.");
				p.sendMessage("�a/tempo top �8- �7Mostrar os top's on-line do servidor.");
				p.sendMessage("�a/tempo recompensas �8- �7Veja as recompensas que podem ser recebidas.");
				p.sendMessage("�a/tempo ver <usu�rio> �8- �7Verifique o tempo on-line de um usu�rio.");
				p.sendMessage("");
			}
			if (args[0].equalsIgnoreCase("recompensas")) {
				p.sendMessage(" ");
				p.sendMessage("�e�l* �eVoc� receber� + 1.000 de cash a cada hora online!");
				p.sendMessage(" ");
			}
			if (args[0].equalsIgnoreCase("ver")) {
				if (args.length == 1) {
					p.sendMessage("�cUtilize /tempo ver <usu�rio>.");
					return true;
				}
				Player jogador = Bukkit.getPlayer(args[1]);
				if (jogador == null) {
					p.sendMessage("�cEste usu�rio n�o est� on-line.");
					return true;
				}
				if (!getPlugin().getTimes().containsKey(jogador)) {
					p.sendMessage("�cEste usu�rio n�o tem tempo registrado no servidor.");
					return true;
				}
				long time = System.currentTimeMillis() - (getPlugin().getTimes().get(jogador)).longValue() + getPlugin().getManager().getTempo(jogador.getName()).longValue();
				p.sendMessage("�e" + jogador.getName() + " possui �f" + getTempo(time) + " �ede tempo on-line.");
				return true;
			}
			if ((!args[0].equalsIgnoreCase("top")) && (!args[0].equalsIgnoreCase("ajuda")) && (!args[0].equalsIgnoreCase("recompensas"))) {
				p.sendMessage("�cArgumento inv�lido.");
			}
		}
		return false;
	}

	public static String getTempo(long time) {
		String message = "";
		long diff = time;
		int seconds = (int) (diff / 1000L);
		if (seconds >= 86400) {
			int days = seconds / 86400;
			seconds %= 86400;
			message = message + days + "d ";
		}
		if (seconds >= 3600) {
			int hours = seconds / 3600;
			seconds %= 3600;
			message = message + hours + "h ";
		}
		if (seconds >= 60) {
			int min = seconds / 60;
			seconds %= 60;
			message = message + min + "m ";
		}
		if (seconds >= 0) {
			message = message + seconds + "s";
		}
		return message;
	}

	public SnockTempOnline getPlugin() {
		return this.plugin;
	}

	public void setPlugin(SnockTempOnline plugin) {
		this.plugin = plugin;
	}
}
