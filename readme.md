Project name: **Random Dungeon Crawler**

Author: myLewysG (Delwys Glokpor)

*This project is a refactor of the original project (ref @ https://github.com/dnglokpor/RandomDungeonCrawler) for use as a maven GUI application using FXGL (ref @ https://github.com/AlmasB/FXGL).*

1 - Overall Description:
    `RanDunCrawl` is a fantasy dungeon crawler that is based on the discord explorer bot created and updated till September 2020 (ref @ https://github.com/dnglokpor/IsekaiBot). The idea is a single player (or a party but that is for later) that explores a dungeon divided in floors (for convenience). The goal is to completely explore the dungeon which means exploring all the floors. To do this the player must conquer all the dangers that lurk in the floors. My current goal is 50 floors. The game world will feature a time system which means there will be days that will be broken in time periods. This will somewhat affect the town and the dungeon. The player will have to be conscienscious of this.
    Yeah this is pretty much just a smaller EtXXXX Odyssey.

2 - Implementation:
    The implementation of this suppose a desktop 2D application allowing a single player mode.
    A story isn't necessary but the game has to be able to start and be played. The game must feature a first person like perspective of the city and the dungeon with a complete background an battle scene must feature the monsters sprites (no special animation required). 
    The player must be able to:
    - create a save file with his custom gamertag;
    - select an explorer job and receive basic equipment;
    - be given a quick intro to the game world;
    - access the city and all the facilities in the city;
    - access the dungeon with at least a stratum available (see definition of stratum below);
    - explore all the floors of the said stratum and gather all existing collectibles;
    - see changes in time period as he plays the game;
    - quit the application and load his file with all his progress at next launch of the application.

3 - Ressources:
    The details on resources used for the project are left to the developer.
    The author decided to use FXGL as a graphics library since it is the most suited to 2D game development from source code in Java. The author will also intensively use *.json* files for recording permanent information like resources packs because they are easy to edit in a non development workspace.


4 - Detailed description of game elements:
    The following are a guidelines on what I expect specific game elements to look like by the end of the project. These are likely to change as the development progresses. It is not fundamentally important for someone looking at my code but could help understand why some things have been implemented a certain way.

- Dungeon:
    
    A dungeon is made of floors and a floor is composed of blocks. To explore a floor, the player must explore each block one by one and more importantly he must reach the stairs block and complete it (there are conditions). The player will then have that floor done and will be able to make it to the next floor because the access to any floor except the first one requires that the previous level has been completely explored. More over floors will be grouped in a bigger order called a stratum. Strata will have 5 floors each (current idea) and each strata will display a different natural ecosystem.

    A block is a semirandom event waiting to happen. The game program will decide what will be occuring to the player in most of the blocks. The random events the player can get into are as following:
    - Empty blocks, where the player will be narrated the description of the natural landscape of the block. This will change depending on the floor and the strata.

    - Danger blocks, where the player will be assaulted by monsters. This will feature a semi turn-based rpg fight system where the player and the monsters take turn at inflicting damage to each other. The "semi" comes from the fact that the player doesn't choose a fighting strategy each turn. The system determine the winner by calculating the damages inflicted by each party once the battle state has been established (meaning the player decides to fight or fails to run). Monsters will occasionally drop something that the player will collect and be given the occasion to exchange against some in-game world currency. Defeating monsters will also provide the player with experience points that allows him to evolve as he explores the dungeon and get stronger. In the case the player dies, they lose their current floor exploration results and get to start back from the town.

    - Hazard blocks, a derived block from danger blocks, they're blocks where the player encounters a special monster of the dungeon that wouldn't usually wander around. The player gets the choice of fighting them or escaping. The escape rate will be a 100% because they're special occurences and the player doesn't have to confront them. But their drop rates are also 100% because they will drop items that are so useful the player can't resist the reward and will try to get it by fighting. Same winning/losing conditions as Danger blocks will apply to these blocks.

    - Scavenging blocks, where the player gets a chance to scavenge items from the floor. Depending of the floor this could be anything; from herbs to rocks and ores. Those items will be useful in a way (seelling or crafting?)

    - Choice blocks, which are mini challenges inside the dungeon. They will present the player with a situation and prompt them with a choice. Depending on the choice they make, the situation will develop a certain way. Benefits as well as detriments can come out of exploring a choice block. But choice blocks outcomes are predetermined thus the experienced adventurer will remember how to get out as harmlesly as possible.
    
    There will be one special block which will be predetermined: the stairs block at the end of each floor. It is important because by clearing it, the player will get the right to leave the dungeon and get back to town (there's a town next to the dungeon for convenience) and access to the next floor (they can resume exploration from next floor right away). There is two kinds of stairs blocks:
    - Regular stairs block will be the most common. They will just be a warp back to town for the players thus just a walkthrough block with no special event.

    - Boss stairs blocks, as their name indicates, host a boss. A boss is a superior and more dangerous denizen of a stratum. They lurk as guardian of their stratum thus occur on the last level of the stratum. Fighting them will always be way harder than everything else but defeating them will be necessary for completing the exploration of said floor. Players will be prompted with the choice of avoiding the boss fight and just warping back to the city in which case the floor will not be marked as completed. But if they choose to confront the boss there are two possible outcomes. Either they manage to defeat the boss and clear the floor or they lose and in the process lose all results of the current exploration. Thus players need to be sure they're ready for that fight.

- Detailed Description of time:
    Time in the game world isn't real time. The game will record the number of elapsed days but it is as a way to record how long the player has been playing. A day is divided in the following time period:
    - Dawn: for those who rise right before sun does.
    - Morning: for those who went to a little late.
    - Noon: the sun is right above the town and the dungeon.
    - Afternoon: best for digesting lunch with a nap.
    - Evening: as the sun goes down, so does most of the city.
    - Midnight: late night times when robers like to do their business.
    
    Time passes (or at least time periods will change) as certain events are met by the player (the player decides the time of day). Exploring ten blocks of the dungeon changes the current time period to the next one - disregarding what events took place in the blocks. Sleeping at the inn will always change the time period twice (sleeping in the evening will allow you to wake up at dawn). Resting at the inn will move only to the next time period. Going to more than 3 facilities in a row in town will move the time period to the next one.

    Incidentaly the time period will regulate town activity and dungeon danger. Some facilities will not be available during certain time periods so the player must come back later. Also the dungeon will get more dangerous when it is dark so the beginner adventurer should avoid crawling at night time.

- Detailed Description of Town:
    The dungeon is full of floors and because of all that can be scavenged from it, a town has developed next to it and contains facilities that support dungeon explorers (including the player). The objective is to give the town the minimum amount of facilities that a player needs to be able to crawl the dungeon. This includes:
    - the Inn: classic rpg town facility. Allows the player to sleep or rest to recover from injuries. This facility will always open; well because an Inn is a welcoming place for travelers so obviously the inkeeper won't be chasing his clients away.

    - the Shop: another classic rpg town facility. The shop will offer items that the player can buy. Since this is a crawler with an auto battle system, this means mostly equipment pieces (but still thinking of adding consumables). The shop will feature more items as the player explores higher levels. The shop will open in the morning and close in with the evening.

    - The adventurer guild: This is less frequent in a crawler but this town will feature one as it is a way for the town to keep the explorers under control. They will keep records on the player (maybe also issue missions but not sure yet). The guild will open in the morning and close in the evening.

- Detailed Description of Units:
    This game bases itself on rpg thus features a number of different units. Units are living organisms that appear in the game world. This includes anything that lives (except for maybe special denizen in choice blocks). A unit is a living organism with life characteristics (hp and base stats). Players will have access to playable units they can imperson (thus making this an rpg). Monsters of the dungeon are also units - hostile units - that will always try to defeat the playable units.

- Detailed Description of Jobs:
    units will have jobs. Jobs give to units more characteristics and improve a few of their stats. Jobs will go from explorer classes to monster classes. For example explorer classes will allow use of skills. A boss job will greatly enhance stats and allow pattern changes thus making a monster dangerous. At playable unit creation a player will decide on a job. They can change those later on.

- Detailed Description of Body:
    A body is an additional addon that a explorers jobs will have, allowing them to equip some items on to raise their stats. A body provides 4 slots: 2 hands slots for weapons and shields, an armor slot for armor (obviously) and an accessory slot for a special equipment item that will raise a specific stat too. A player will be given the choice of what equipment to put on his body. Monsters will not have equipment slots but their stats will compensate for the lack of it.

- Detailed Description of items:
    They are collectibles that can be found in various ways. The player will have ways of collecting them as they are his main source of income (monster drops, dungeon scavenging rewards). 
    Objects can also be equipment pieces. These will be used by the player to strengthen his body.
    For the moment All objects will be non-consumables as the dungeon crawling is semi automatic thus gives little possibilities for item consumption. We could eventually add consumables for the purpose of quickly restoring stats in town.

- Detailed Description of Bag:
    units will have a "drop" slot that allows them to store an item on them that falls when they die (only monsters will actually use that).