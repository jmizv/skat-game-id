# Skat-Game-Id

This library should give a handy way to identify any Skat game by a simple number.
It also comes with a way to read and write games, i.e. the card distributions.

## Definitions
Skat is a three-player trick-taking card game. There are 32 cards.
Each has a value and a suit.

The eight values are: 

| 7 | 8 | 9 | 10 | Jack | Queen | King | Ace |
|---|---|---|----|---|---|---|---|
| 7 | 8 | 9 | X  | U | O | K | A |

The lower row denote the single character used to denote it within this
library. It's coming from the German names: Unter / Ober / König.)

The four suits are:

|  |  |
|---|---|
| Diamonds ♦️ | S |
| Hearts ♥️ | H |
| Spades ♠️ | G |
| Clubs ♣️ | K |

Again the latter column denotes the character used in this library. It comes from their
German names: Schellen, Herz, Grün, Kreuz.  

## Classes


### Game
Should only indicate which player had which card. And what two cards were
in the Skat before starting the game.

We introduce the following human-readable notation:

```
S7 S8 S9 SX SU SO SK SA R7 R8
R9 RX RU RO RK RA G7 G8 G9 GX
GU GO GK GA E7 E8 E9 EX EU EO
EK EA
```

The first line shows the cards that the player in front had, the second line
the cards of the player in the middle and the third line the cards for the
player in rear. The last line shows the two cards of the Skat.

Cards are denoted by two characters, e.g. `S7` is a 7 of Diamonds
while `GU` is the Jack of Clubs.

It should be possible to add additional white space but not between the two characters of
one card. One could even put all in one line without problems. Capitalization should
also be neglected but upper-case is preferred.

Note also that the cards are ordered from low to high for each stack. This is some kind of
normalization. While it is not illegal to have it unsorted this library will always perform
a sorting when printing a game out.

### Game Run
Should indicate when which player had played which card. And what game for what value
were played.

We extend the previous human-readable notation:

```
TODO
```

