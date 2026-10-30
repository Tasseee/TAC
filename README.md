# TAC — Tassilo's Anti-Cheat (Prototype)

Client-only Fabric source project targeting Minecraft **1.21.11** and Java 21.

## Included
- Minimal TAC menu, opened with **Right Shift**.
- Chat Analyzer heuristics for possible insults, hate speech, threats, cheat advertising and suspicious links.
- Local findings in `.minecraft/config/tac/chat-findings.json`.
- Overview, Chat Analysis, Case File and Moderator placeholder tabs.
- **Spam/flooding detection is not included**, as requested.

## Important honesty notes
This is an early source prototype, not a finished or release-tested mod. Gradle/Minecraft were not run in this environment, so compilation and in-game compatibility are **not verified**; the exact Fabric API versions/mappings may need adjustment.
- The phrase lists are small examples and can miss or misinterpret sarcasm, quotes, slang, spelling variants and context.
- No automatic bans or punishment; flagged messages are only hints.
- Client-only means it can only inspect messages delivered to this client.
- Moderator screen is a placeholder, not secure password protection or server-side role authorization.
- Combat/movement/mining detections, full replay, screenshots, report bundles and advanced case management are not implemented yet.
- Chat evidence may contain personal data; review or delete `config/tac/chat-findings.json` when appropriate.

## Build
Use JDK 21 and a compatible Gradle installation, then run `gradle build`. If successful, the JAR is in `build/libs/`. Install it with Fabric Loader and Fabric API for Minecraft 1.21.11.
