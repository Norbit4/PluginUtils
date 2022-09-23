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
- download and add plugin to server

#

<h3>Option 2:</h3>

*with gradle*:

- add shadow plugin
```gradle

plugins {
    id 'java'
    id "com.github.johnrengelman.shadow" version "7.1.2"
```

- reload gradle project


- build with shadow:

![image](https://user-images.githubusercontent.com/46154743/192019288-4b66c1de-f81c-4889-a53e-44afbba02fb4.png)

*shadow>shadowJar*



