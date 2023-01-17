// Nemesis Cards
abstract class NemesisCard extends Card:
	var nemesisTier: Int = 1
	var dmgTarget: String = "Mage"	

	var doesUnleash: Boolean = false
	var unleashAmount: Int = 0
end NemesisCard

class PowerCard(var title: String, var effect: String) extends NemesisCard:	
	var powerTokens: Int = 0
	
	var doesToDiscard: Boolean = false
	var toDiscardText: String = ""
	var toDiscasrdCost: Int = 0
end PowerCard

class MonsterCard(var title: String, var effect: String) extends NemesisCard:
	
end MonsterCard

class AttackCard(var title: String, var effect: String) extends NemesisCard:

end AttackCard








