[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/KprAwj1n)
# APCS - Stuyrim

## Features

Make a clear list of features that work/don't work

:white_check_mark: This feature works.

:question: This feature works partially.

:ballot_box_with_check: This extra (beyond the things that the lab was supposed to do) feature works.

:x: This required feature does not work.

:beetle: This is a bug that affects the game.

* Can play with a group of 3 different types of adventurers :x:
* Can play against 1-3 randomly chosen adventurer opponents :x:
* Can use attack/special operations on your opponents :x:
* Can use support operations on your team :x:
* The program ends when the user chose to quit, or all enemies is defeated, or the entire party is defeated :x:
* Can display the results of the attack/special/support inside your border :x:

## Adventurer Subclasses

--

### Crossbow Warrior

*Can occasionally get a really good shot, but also misses the target completely sometimes*

**Attack**: Bowshot - deals 0 damage (10% chance) or 4-7 damage (90% chance). Also restores 2 special to self.

**SpecialAttack**: Headshot - 0 damage (20% chance) or 12-18 damage (80% chance) (5 focus)

**Special**: Focus

**Initial Special**: 2

**Max Special**: 8

**Support**: Eat a carrot - heals 1-3 HP and 3-4 special

**Support Other**: Give a carrot to someone else - heals 1-2 HP and 3-4 special

**Max HP**: 20

--

### Sword Warrior

*Trips on a rock fairly often; not the most trained of fighters*

**Attack**: Slice - deals 4-5 or 8-9 damage (20% critical hit rate)

**Special Attack**: Monster Slice - deals 0-1 or 20-21 damage (50% crit rate) (5 energy)

**Special Resource**: Energy

**Initial Special**: 3

**Max Special**: 7

**Support**: Eat Pie - gains 2-3 health, 2-3 energy

**Support Other**: Throw Pie - gives -1 or 2-3 health, -1 or 2-3 energy (77% crit rate)

**Max HP**: 30

--

### Boss

*Always works alone; pretty strong*

**Attack**: Shoots lightning - deals 50% of opponent's health or 5 HP depending on which is higher

**Special Attack**: Huge lightning bolt - opponent goes to 1 HP automatically (5 power)

**Initial Special**: 0

**Special Resource**: Power

**Max Special**: 10

**Support**: Take a nap - gains 10 health and 10 power

**Max HP**: 100
