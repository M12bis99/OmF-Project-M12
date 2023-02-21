// Nemesis Cards
abstract class NemesisCard extends Card:
	var nemesisTier: Int = 1
	var dmgTarget: String = "Mage"	

	var doesUnleash: Boolean = false
	var unleashAmount: Int = 0

	var doesLoseCharge: Boolean = false
end NemesisCard

class PowerCard(var title: String, var effect: String) extends NemesisCard:	
	var powerTokens: Int = 0
	
	var doesToDiscard: Boolean = false
	var toDiscardText: String = ""
	var toDiscardCost: Int = 0
end PowerCard

class MonsterCard(var title: String, var effect: String) extends NemesisCard:
	var health: Int = 0
	var silenced: Boolean = false
end MonsterCard

class AttackCard(var title: String, var effect: String) extends NemesisCard:
	var doesGatherDarkness: Boolean = false
	var NemesisDeckIsEmpty: Boolean = false
end AttackCard








