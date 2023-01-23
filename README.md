# Skat-Game-Id

This library should give a handy way to identify any [Skat](https://en.wikipedia.org/wiki/Skat_(card_game)) game or game run by a
simple id. It also comes with a way to read and write games, i.e. the card distributions and a certain game run.

Moreover, it defines two ways to store and display either a distribution of 32 Skat cards
and a complete Skat game which includes the card distribution but also all other information
to uniquely identify a single Skat game play. The two ways are: human-readable and by giving an
integer (either coded decimal or by a Base64 string)

## Definitions

Skat is a three-player trick-taking card game. There are 32 cards. Each has a value and a suit.

The eight values are:

| 7 | 8 | 9 | 10 | Jack | Queen | King | Ace |
|---|---|---|----|---|---|---|---|
| 7 | 8 | 9 | X  | U | O | K | A |

The lower row denote the single character used to denote it within this library. It's coming from the German names:
Unter / Ober / König.)

The four suits are:

| Diamonds ♦ | Hearts ♥ | Spades ♠ | Clubs ♣  |
|---|---|---|----------|
| ️ S | R | G | E        |

Again the latter column denotes the character used in this library.
It comes from their German names: **S**chellen, **R**ot,
**G**rün, **E**icheln.

## Classes

### Game

`de.jmizv.skatgameid.Game`

Should only indicate which player had which card. And what two cards were in the Skat before starting the game. This will
be the situation right before the bidding starts.

We introduce the following human-readable notation:

```
S7 S8 S9 SX SU SO SK SA R7 R8
R9 RX RU RO RK RA G7 G8 G9 GX
GU GO GK GA E7 E8 E9 EX EU EO
EK EA
```

The first line shows the cards that the player in front had, the second line the cards of the player in the middle and
the third line the cards for the player in rear. The last line shows the two cards of the Skat.

Cards are denoted by two characters, e.g. `S7` is a 7 of Diamonds (=_Schell Sieben_ German)
while `GU` is the Jack of Clubs (= _Grün Unter_ in German).

It should be possible to add additional white space but not between the two characters of one card. One could even put
all in one line without problems. Capitalization should also be neglected but upper-case is preferred.

Note that we don't demand that the cards are ordered from low to high for each stack. Although a normalization is offered.

The above schema converts to the following ID via the method `computeId`: `AABQVVWqqvo=`. Note that it is simply all cards
ordered from lowest to highest. To convert this to a decimal number, the method `computeIdAsBigInteger` helps.
So the real decimal ID would be then: `88327439690490`. 

If we reverse this order, the ID becomes `r6qqVVUF` or `-88327439690491`.


### Game Run

`de.jmizv.skatgameid.GameRun`

Should indicate when which player had played which card.
And what game for what value were played and how the bidding went.

We extend the previous human-readable notation (note that `--` are comments and get
discarded while reading):

```
S7S8S9SXSUSOSKSAR7R8 -- front
R9RXRURORKRAG7G8G9GX -- middle
GUGOGKGAE7E8E9EXEUEO -- rear
EKEA -- Skat before game
R7R8 -- Skat on announced game
07 -- bid of front
05 -- bid of middle
04 -- bid of rear
41111 -- spades
012 -- Denotes the index of the players cards from above, e.g.
123 --   forehand played their first card etc.
234
345
456
567
678
789
890
901
```

There first four lines are equal to the `Game` notation. That means they could be collapsed into one single line, too.
Also, the played cards are not validated, e.g. it is not checked whether a player has correctly *bedient*.

If the three values for bidding are all `0`, the game is considered to be *eingepasst*.

The above game definition would result in the following Base64 encoded ID:

```
AABQVVWqqvpwKETIPCAhMbGwqKipuXl4ZCQkIA==
```

The same id as `BigInteger` would be:
```
129090697728898356188161029675937060363232303187716560461767712
```