package com.sc4r.SpecterTempoOnline;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private static SnockTempOnline i;
    private MySQL mySQL;
    private Manager manager;
    private Map<Player, Long> times = new HashMap<Player, Long>();

    public void onEnable() {
        saveDefaultConfig();
        setInstance(this);
        setMySQL(new MySQL(this));
        setManager(new Manager());
        getMySQL().open();
        getMySQL().createTable();
        new PlayerJoinListeners(this);
        new TempCommand(this);
        new UpdateOnlineTimeRunnable(this);
        new UpdateTopTime(this);
    }

    public static SnockTempOnline get() {
        return SnockTempOnline.i;
    }

    public static void setInstance(SnockTempOnline i) {
        SnockTempOnline.i = i;
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public Map<Player, Long> getTimes() {
        return this.times;
    }

    public void setTimes(Map<Player, Long> times) {
        this.times = times;
    }

    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
