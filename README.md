# SpigotPluginMalwareRemover
This is a open source project which can remove famous malware's from any spigot plugins.

## Disclaimer
This plugin is a open source project and still under development. This can be used by anyone with their own risks. I won't take any responsibility if this projects damage your plugin under any circumstance. Kindly use it at your own risk.


# How to use it
1. Clone the repository or download the jar from releases.
2. Make a folder named `plugins` in the directory of the repository/jar.
3. Make a folder named `result` in the directory of the repository/jar.
4. Put the infected plugins into the `plugins` folder
5. If you have downloaded jar:-
  - Open command prompt or powershell in the directory of the jar and run `java -jar JavaAssistBackdoorRemover.jar`. If you changed the name of the jar you downloaded then please      change the jar name in the command accordingly.
5. If you have cloned the repository:-
  - Open the repository `src` in your IDE and located the `Main.java` at `src/main/java/com/abhiram/javassist/Main.java`. Run the main class in your IDE.

**Your defragmented plugin will appear in `result` folder if it contained the hostflow javaassist backdoor.**


# Requirements
Make sure to you have **Java** installed. I recommend java 11.
