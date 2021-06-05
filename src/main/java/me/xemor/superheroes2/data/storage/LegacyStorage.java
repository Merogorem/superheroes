package me.xemor.superheroes2.data.storage;

import me.xemor.superheroes2.Superhero;
import me.xemor.superheroes2.Superheroes2;
import me.xemor.superheroes2.data.HeroHandler;
import me.xemor.superheroes2.data.SuperheroPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LegacyStorage implements Storage {

    private final Superheroes2 superheroes2;
    private final YamlConfiguration currentDataYAML;
    private final File currentDataFile;
    private final HeroHandler heroHandler;

    public LegacyStorage(HeroHandler heroHandler) {
        this.superheroes2 = heroHandler.getPlugin();
        this.heroHandler = heroHandler;
        currentDataFile = new File(superheroes2.getDataFolder(), "data.yml");
        try {
            currentDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentDataYAML = YamlConfiguration.loadConfiguration(currentDataFile);
    }

    public Map<UUID, SuperheroPlayer> getValues() {
        Map<UUID, SuperheroPlayer> map = new HashMap<>();
        for (Map.Entry<String, Object> item : currentDataYAML.getValues(false).entrySet()) {
            if (item.getValue() instanceof String) {
                UUID uuid = UUID.fromString(item.getKey());
                map.put(uuid, loadSuperheroPlayer(uuid));
            }
        }
        return map;
    }

    public File getCurrentDataFile() {
        return currentDataFile;
    }

    /**
     * Does nothing as LegacyStorage is not a supported storage type and is only here for conversion to YAMLStorage
     * @param superheroPlayer
     */
    @Override
    public void saveSuperheroPlayer(SuperheroPlayer superheroPlayer) { }

    @Override
    public SuperheroPlayer loadSuperheroPlayer(UUID uuid) {
        String superheroName = currentDataYAML.getString(uuid.toString());
        Superhero superhero = heroHandler.getSuperhero(superheroName);
        return new SuperheroPlayer(uuid, superhero == null ? heroHandler.getNoPower() : superhero, 0);
    }

    @Override
    public CompletableFuture<SuperheroPlayer> loadSuperheroPlayerAsync(@NotNull UUID uuid) {
        CompletableFuture<SuperheroPlayer> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(superheroes2, () -> future.complete(loadSuperheroPlayer(uuid)));
        return future;
    }

}
