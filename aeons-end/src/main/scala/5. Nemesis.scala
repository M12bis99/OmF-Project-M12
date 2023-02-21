class Nemesis(val name: String, val maxHealth: Int, val unleash: Unleash, var deck: List[NemesisCard], val emptyNemesisDeck: AttackCard):
	var currentHealth: Int = maxHealth

	var persistentMonsters: List[MonsterCard] = Nil
	var persistentPowers: List[PowerCard] = Nil
	var lastPlayedAttack: AttackCard = AttackCard("", "")
	
	// currently Not used
	var discardPile: List[NemesisCard] = Nil
	
	// Draw from top of Nemesis Deck
	def draw(): NemesisCard = deck match
		case Nil => emptyNemesisDeck
		case x::xs => deck = xs
			x
	//placeholder
	def playCard(card: NemesisCard): Unit = ()

	//placeholder
	def activatePersistentCards(): Unit = ()
end Nemesis

class Unleash(val name: String, val description: String):
	var doesDmg: Boolean = false
	var dmgAmount: Int = 0
end Unleash

def initializeBladius(): Nemesis =
	// Nemesis Deck with randomized Tier 1, 2 and 3
	val deck: List[NemesisCard] = initializeNemesisCards()
	
	// Unleash definition
	val bladiusUnleash: Unleash = Unleash("Bladius' Unleash", "The Mage suffers 2 damage.")
	bladiusUnleash.doesDmg = true
	bladiusUnleash.dmgAmount = 2
	
	val bladiusEmptyDeck: AttackCard  = AttackCard("Empty Nemesis Deck", "Bladius escapes and the players lose!")
	bladiusEmptyDeck.NemesisDeckIsEmpty = true


	val bladius: Nemesis = Nemesis("Bladius", 40, bladiusUnleash, deck, bladiusEmptyDeck)
	bladius