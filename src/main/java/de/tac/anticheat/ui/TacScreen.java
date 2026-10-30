package de.tac.anticheat.ui;
import de.tac.anticheat.TacClient;
import de.tac.anticheat.evidence.EvidenceStore.ChatFinding;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.List;

public final class TacScreen extends Screen {
 private String section="overview"; private int scroll=0;
 public TacScreen(){ super(Text.literal("TAC — Tassilo's Anti-Cheat")); }
 @Override protected void init() {
  int left=width/2-150;
  addDrawableChild(ButtonWidget.builder(Text.literal("Übersicht"),b->{section="overview";clearAndInit();}).dimensions(left,40,95,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Chat-Analyse"),b->{section="chat";clearAndInit();}).dimensions(left+100,40,100,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Fallakte"),b->{section="cases";clearAndInit();}).dimensions(left+205,40,95,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Moderator"),b->{section="moderator";clearAndInit();}).dimensions(left,64,95,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Schließen"),b->close()).dimensions(left+205,64,95,20).build());
 }
 @Override public void render(DrawContext c,int mx,int my,float delta) {
  renderBackground(c,mx,my,delta); c.fill(0,0,width,height,0xE8141820);
  c.drawCenteredTextWithShadow(textRenderer,title,width/2,16,0xFFFFFFFF); super.render(c,mx,my,delta);
  int x=width/2-220,y=105;
  if(section.equals("overview")) {
   c.drawTextWithShadow(textRenderer,"TAC CLIENT • Lokale Analyse",x,y,0xFF75D6B3);
   c.drawTextWithShadow(textRenderer,"Hinweise sind kein automatischer Schuldbeweis.",x,y+20,0xFFE2E8F0);
   c.drawTextWithShadow(textRenderer,"Chat-Funde: "+TacClient.EVIDENCE.getChatFindings().size(),x,y+42,0xFFFFFFFF);
   c.drawTextWithShadow(textRenderer,"Rechts-Shift öffnet dieses Menü.",x,y+64,0xFFB7C0CC);
  } else if(section.equals("chat")) {
   c.drawTextWithShadow(textRenderer,"CHAT-ANALYSE • lokale Hinweise",x,y,0xFF75D6B3);
   List<ChatFinding> fs=TacClient.EVIDENCE.getChatFindings();
   if(fs.isEmpty()) c.drawTextWithShadow(textRenderer,"Noch keine markierten Nachrichten.",x,y+22,0xFFE2E8F0);
   int shown=0;
   for(int i=fs.size()-1-scroll;i>=0&&shown<12;i--,shown++) {
    ChatFinding f=fs.get(i); String line="["+f.category+"] "+f.sender+": "+f.message;
    if(line.length()>92) line=line.substring(0,89)+"...";
    c.drawTextWithShadow(textRenderer,line,x,y+22+shown*13,0xFFE2E8F0);
   }
   c.drawTextWithShadow(textRenderer,"Heuristik kann Kontext falsch bewerten.",x,height-28,0xFFFFC66D);
  } else if(section.equals("cases")) {
   c.drawTextWithShadow(textRenderer,"FALLAKTE",x,y,0xFF75D6B3);
   c.drawTextWithShadow(textRenderer,"Lokal gespeichert: config/tac/chat-findings.json",x,y+22,0xFFE2E8F0);
   c.drawTextWithShadow(textRenderer,"Replay, Screenshots und Export sind noch nicht implementiert.",x,y+44,0xFFFFC66D);
  } else {
   c.drawTextWithShadow(textRenderer,"MODERATOR-BEREICH",x,y,0xFF75D6B3);
   c.drawTextWithShadow(textRenderer,"UI-Platzhalter – kein echter Zugriffsschutz/Rollencheck.",x,y+22,0xFFFFC66D);
   c.drawTextWithShadow(textRenderer,"Echte Moderator-Rechte benötigen Server-Unterstützung.",x,y+44,0xFFE2E8F0);
  }
 }
 @Override public boolean mouseScrolled(double mx,double my,double horizontal,double vertical) {
  if(section.equals("chat")) { int max=Math.max(0,TacClient.EVIDENCE.getChatFindings().size()-12); scroll=Math.max(0,Math.min(max,scroll-(int)Math.signum(vertical)*3)); return true; }
  return super.mouseScrolled(mx,my,horizontal,vertical);
 }
}
