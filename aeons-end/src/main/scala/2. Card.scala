abstract class Card :
	var title: String 
	var effect: String
	
	var cardCost: Int = 0	

	var doesDmg: Boolean = false
	var dmgAmount: Int = 0

	var doesDiscardHand: Boolean = false
	var discardHandAmount: Int = 0

	var doesDiscardSpell: Boolean = false
	var discardSpellAmount: Int = 0

	var doesDestroyHand: Boolean = false
	var destroyHandAmount: Int = 0

	var doesDestroySpell: Boolean = false
	var destroySpellAmount: Int = 0

	var doesDraw: Boolean = false
	var drawAmount: Int = 0
end Card


