# Core

Core is my personal library that is used by other repositories in this organization 

## Features

The following features are built in to this repo:


- [Economy](src/zsantana/economy): Handles multiple banks and saving the banks, with different values compatibility
  - Handler.getCore().getMainHandler().getEconomyHandler() gives access to the EconomyHandler.
  - Then you can get the bank via EconomyHandler#getBank(String) or register a new one
- [Custom Items](src/zsantana/customitems): Allows the user to create a custom item that can hook on to events with just an annotation
  - There's 2 types of custom items: Rarity and Custom. A RarityItem/RarityArmor handles verifying the item for you and adds a custom lore/name for you. It reads all the events and auto-compiles the lore for you and uses the provided rarity to do the name.
  - For CustomItem/CustomArmor, you construct the item yourself completely and return the Item yourself as well.
  - [RarityItemTest](/src/testing/CustomRarityItemTest.java)
  - [RarityArmorTest](/src/testing/CustomRarityArmorTest.java)
  - [CustomItemTest](/src/testing/CustomItemTest.java)
  - [CustomArmorTest](/src/testing/CustomArmorTest.java)
- [Commands](src/zsantana/command): Handles constructing a command based off of annotations and compiling it to a full command.
  - Parses all the annotations in the command and compiles what should run on the command
  - [Command Test](/src/testing/CustomRarityArmorTest.java.java)
- [Congiguration](src/zsantana/configuration): Handles a configuration saver/reader on plugin load/unload.
- [Entity Drops](src/zsantana/entitydrops): Adds a custom entity drop or a custom entity that has custom gear


## Motivation

Instead of recoding and hand coding each feature, I made a library for each with flexibility to do each.

## Code style

Each feature has a master handler class that handles how the user directly uses the library. For some libraries, a fully integrated abstraction approach was taken like in the custom items library (see below).

