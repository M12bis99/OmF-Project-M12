//Mage Cards
abstract class MageCard extends Card:
	var doesGainAether: Boolean = false
	var aetherAmount: Int = 0

	var doesGainCharge: Boolean = false
	var chargeAmount: Int = 0
end MageCard

class SpellCard(var title: String, var effect: String) extends MageCard:
	var doesDrawWhilePrepped: Boolean = false
	var drawWhilePreppedAmount: Int = 0
end SpellCard

class GemCard(var title: String, var effect: String) extends MageCard:
	var doesSilenceMonster: Boolean = false
end GemCard

class RelicCard(var title: String, var effect: String) extends MageCard:

end RelicCard







