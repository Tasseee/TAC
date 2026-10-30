package de.tac.anticheat;
import de.tac.anticheat.chat.ChatAnalyzer;
import de.tac.anticheat.evidence.EvidenceStore;
import de.tac.anticheat.ui.TacScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class TacClient implements ClientModInitializer {
 public static final EvidenceStore EVIDENCE = new EvidenceStore();
 public static final ChatAnalyzer CHAT = new ChatAnalyzer(EVIDENCE);
 private KeyBinding openMenu;
 @Override public void onInitializeClient() {
  EVIDENCE.load();
  openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.tac.open_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, KeyBinding.Category.MISC));
  ClientTickEvents.END_CLIENT_TICK.register(client -> { while (openMenu.wasPressed()) if (client.currentScreen == null) client.setScreen(new TacScreen()); });
  ClientReceiveMessageEvents.GAME.register((message, overlay) -> CHAT.inspect(message.getString(), "chat"));
  ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, params, timestamp) -> CHAT.inspect(message.getString(), sender == null ? "unknown" : sender.getName().getString()));
 }
}
