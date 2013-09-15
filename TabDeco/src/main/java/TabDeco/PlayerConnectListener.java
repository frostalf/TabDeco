package TabDeco;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerConnectListener implements Listener
{
	private TabDeco plugin;
	
	public PlayerConnectListener(TabDeco plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		
		/**Resetting local variables**/
		TabDeco.setMetadata(player, "killCounter", new FixedMetadataValue(plugin, 0), plugin);
		TabDeco.setMetadata(player, "mobkills", new FixedMetadataValue(plugin, 0), plugin);
		TabDeco.setMetadata(player, "deathCounter", new FixedMetadataValue(plugin, 0), plugin);
		
		plugin.displayAllPlayerForPlayer(player, false, true);
		if(TabDeco.debugMode) {
            TabDeco.debugLogger.info(new StringBuilder().append("Player ").append(player.getDisplayName()).append(" is logging in! Sending packets.").toString());
        }
		ArrayList<String> lastTabData = new ArrayList<>();
		for(int i = 0; i < this.plugin.tabData.size(); i++)
		{
			String text = this.plugin.tabData.get(i).toString();
			text = this.plugin.replaceAllWords(text, player, true);
			plugin.sendSpecificPacketToPlayer(player, true, text.replaceAll("\\[\\@\\]", ""));
			lastTabData.add(text);
			if(TabDeco.debugMode) {
                TabDeco.debugLogger.info(new StringBuilder().append("Sent packet with text: ").append(text).toString());
            }
		}
		plugin.setPlayerData(player, "playerTabData", lastTabData);
		plugin.displayAllPlayerForPlayer(player, true, true);
	}
}
