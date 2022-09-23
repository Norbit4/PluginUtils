# PluginUtils
[![](https://jitpack.io/v/Norbit4/PluginUtils.svg)](https://jitpack.io/#Norbit4/PluginUtils)

<h3>Add lib to project:</h3>

*Gradle:*

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```


```gradle

dependencies {
    implementation 'com.github.Norbit4:PluginUtils:Tag'
}

```

*Maven:*

```xml
<repositories>
   <repository>
       <id>jitpack.io</id>
       <url>https://jitpack.io</url>
   </repository>
</repositories>
```

```xml

<dependency>
    <groupId>com.github.Norbit4</groupId>
    <artifactId>PluginUtils</artifactId>
    <version>Tag</version>
</dependency>
```
#

<h3>Option 1:</h3>



- add depend to plugin.yml

```yml
#plugin.yml

depend: [PluginUtils]
```
- [download](https://github.com/Norbit4/PluginUtils/releases/) and add plugin to server

#

<h3>Option 2:</h3>

*with gradle*:

- add [shadow](https://github.com/johnrengelman/shadow) plugin 
```gradle

plugins {
    id 'java'
    id "com.github.johnrengelman.shadow" version "7.1.2"
```

- reload gradle project


- build with shadow:

![image](https://user-images.githubusercontent.com/46154743/192019288-4b66c1de-f81c-4889-a53e-44afbba02fb4.png)

*shadow>shadowJar*

<h3>Start</h3>

```java
//register plugin
@Override
public void onEnable() {
    PluginUtilsRegistry.register(this);       
}
```

<h3>Commands builder</h3>

```java

String[] permissions = new String[] {"*", "example.*"};

CommandUtil
        .builder()
        .commandName("ping") //<- /ping
        .commandExecutor(new PingCmd())
        .delay(30)
        .timeUnits(CommandUtil.TimeUnits.SECONDS)
        .permissions(permissions)
        .build()
        .register();
```       
  
```java
public class PingCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //this execute when player has permission && cooldown is down

        Player p = (Player)sender;

        p.sendMessage("pong");
        return false;
    }
}
```
- *custom perm & cooldown message:*

```java

CommandUtil.setPermMessage("Perm!"); 
CommandUtil.setCooldownMessage("Cooldown: {COOLDOWN}"); 
```

- *add command to plugin.yml:*

```yml
#plugin.yml

commands:
  ping:
```

#

<h3>Task builder</h3>

*Later task:*

```java
TaskBuilder
        .builder()
        .taskType(TaskType.LATER)
        .asynchronous(false)
        .delay(10)
        .delayUnit(TaskUnit.SECONDS) 
        .runnable(() ->{
            System.out.println("This will start afte 10 seconds!");
        })
        .build() //return task
        .start(); //start task
```

*Timer task:*

```java
TaskBuilder
        .builder()
        .taskType(TaskType.TIMER)
        .asynchronous(false) //default: false
        .delay(10)
        .period(1)
        .delayUnit(TaskUnit.SECONDS) 
        .runnable(() ->{
            System.out.println("This is execute every 1 second!");
        })
        .build() 
        .start();
```

<h3>Formatter</h3>

```java

String message = "&7Hi!";

String formatMessage = Formatter.format(message); //this change '&7' to gray color
```

<h3>Permission Util</h3>

```java
String[] permissions =  new String[]{"*", "player.*"};

PermissionUtil permissionUtil = new PermissionUtil(player);

boolean hasPerm = permissionUtil.hasPermission(permissions); //checks if the player has a permission
```

<h3>MongoDB</h3>

*connect to database:*

```java
MongoDB mongo = MongoDB.builder()
        .user("root")
        .host("host")
        .useSSL(false) //default: false
        .port(27017) //default: 27017
        .password("password")
        .build();

//start connection
mongo.openConnection();

//close connection
mongo.closeConnection();
```
