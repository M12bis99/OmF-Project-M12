class Nemesis(val nemesisName: String, val maxHealth: Int, val unleash: Unleash, var deck: List[NemesisCard]):
	var currentHealth: Int = maxHealth

	var persistentMonsters: List[NemesisCard] = Nil
	var persistentPowers: List[NemesisCard] = Nil
	
	var discardPile: List[NemesisCard] = Nil
	
	val emptyNemesisDeck: AttackCard = 
		AttackCard("Empty Nemesis Deck", "Bladius excapes and the players lose")

	// Draw from top of Nemesis Deck
	def draw(): Card = deck match
		case Nil => emptyNemesisDeck
		case x::xs => deck = xs
			x
	//placeholder
	def playCard(card: NemesisCard): Unit = ()

	//placeholder
	def activatePersistentCards(): Unit = ()
end Nemesis

abstract class Unleash:
	def unleash(): Unit
end Unleash

class BladiusUnleash extends Unleash:
	def unleash(): Unit = ()
end BladiusUnleash