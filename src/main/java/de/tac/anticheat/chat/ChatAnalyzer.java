package de.tac.anticheat.chat;
import de.tac.anticheat.evidence.EvidenceStore;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class ChatAnalyzer {
 private final EvidenceStore store;
 private static final List<String> INSULTS = List.of("idiot","idiotin","dummkopf","halt die fresse","verpiss dich","hurensohn","h*rensohn","arschloch","bastard","noob");
 private static final List<String> HATE = List.of("heil hitler","verrecke wegen deiner herkunft");
 private static final List<String> THREATS = List.of("ich bring dich um","ich werde dich töten","ich mach dich fertig");
 private static final List<String> CHEAT_ADS = List.of("free hack","free cheat","kauf cheats","client zum cheaten","aimbot download","xray download");
 private static final Pattern URL = Pattern.compile("(?i)\\b(?:https?://|www\\.)\\S+");
 public ChatAnalyzer(EvidenceStore store) { this.store = store; }
 public void inspect(String raw, String sender) {
  if (raw == null || raw.isBlank()) return;
  String text = raw.toLowerCase(Locale.ROOT);
  if (contains(text, THREATS)) store.addChat(sender, raw, "Drohung", "Mögliches Drohungsmuster; Kontext prüfen");
  if (contains(text, HATE)) store.addChat(sender, raw, "Hassrede", "Mögliches Hassrede-Muster; Kontext prüfen");
  if (contains(text, INSULTS)) store.addChat(sender, raw, "Beleidigung", "Mögliche Beschimpfung; Kontext manuell prüfen");
  if (contains(text, CHEAT_ADS)) store.addChat(sender, raw, "Cheat-Werbung", "Mögliche Werbung für Cheats");
  if (URL.matcher(raw).find() && (text.contains("hack") || text.contains("cheat") || text.contains("xray") || text.contains("aimbot")))
   store.addChat(sender, raw, "Verdächtiger Link", "Link mit Cheat-Begriffen erkannt; Link allein ist kein Beweis");
 }
 private boolean contains(String text, List<String> phrases) { return phrases.stream().anyMatch(text::contains); }
}
