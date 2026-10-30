package de.tac.anticheat.evidence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import net.fabricmc.loader.api.FabricLoader;

public final class EvidenceStore {
 private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
 private final List<ChatFinding> findings = Collections.synchronizedList(new ArrayList<>());
 private Path file;
 public void load() {
  try {
   Path dir = FabricLoader.getInstance().getConfigDir().resolve("tac"); Files.createDirectories(dir);
   file = dir.resolve("chat-findings.json");
   if (Files.exists(file)) { ChatFinding[] old = GSON.fromJson(Files.readString(file), ChatFinding[].class); if (old != null) Collections.addAll(findings, old); }
  } catch (Exception e) { System.err.println("[TAC] Local findings could not load: " + e.getMessage()); }
 }
 public void addChat(String sender, String message, String category, String reason) {
  findings.add(new ChatFinding(Instant.now().toString(), sender, message, category, reason)); save();
 }
 public List<ChatFinding> getChatFindings() { synchronized(findings) { return new ArrayList<>(findings); } }
 private void save() {
  if (file == null) return;
  try { Files.writeString(file, GSON.toJson(getChatFindings()), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); }
  catch (Exception e) { System.err.println("[TAC] Local findings could not save: " + e.getMessage()); }
 }
 public static final class ChatFinding {
  public final String timestamp, sender, message, category, reason;
  public ChatFinding(String timestamp, String sender, String message, String category, String reason) {
   this.timestamp=timestamp; this.sender=sender; this.message=message; this.category=category; this.reason=reason;
  }
 }
}
