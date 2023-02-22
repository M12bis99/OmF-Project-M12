// Mage Card Definitions
def initializeMageCards(): Vector[List[MageCard]] = 
	var handList: List[MageCard] = Nil
	var deckList: List[MageCard] = Nil

	// Gems
	val crystal: GemCard = GemCard("Crystal", "Gain 1 Aether.")
	crystal.doesGainAether = true
	crystal.aetherAmount = 1

	//Spells
	val spark: SpellCard = SpellCard("Spark", "Cast: Deal 1 damage")
	spark.doesDmg = true
	spark.dmgAmount = 1

	val buriedLight: SpellCard = SpellCard("Buried Light", "Cast: Deal 1 damage. \nGain 1 Aether")
	buriedLight.doesDmg = true
	buriedLight.dmgAmount = 1
	buriedLight.doesGainAether = true
	buriedLight.aetherAmount = 1

	handList = crystal :: crystal :: crystal :: crystal :: buriedLight :: handList
	deckList = crystal :: crystal :: crystal :: spark :: spark :: deckList

	var handAndDeck: Vector[List[MageCard]] = Vector(handList, deckList)
	handAndDeck
	