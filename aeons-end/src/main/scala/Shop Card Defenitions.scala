// Shop Card Definitions
def initializeShopCards(): List[MageCard] = 
	var shopList: List[MageCard] = Nil

	// Gems

	val allayingShell: GemCard = GemCard("Allaying Shell", "Gain 2 Aether.\nSilence a Minion.")
	allayingShell.cardCost = 5
	allayingShell.doesGainAether = true
	allayingShell.aetherAmount = 1
	allayingShell.doesSilenceMonster = true


	val hauntedBeryliteTop: GemCard = GemCard("Haunted Berylite", "Gain 2 Aether")
	hauntedBeryliteTop.doesGainAether = true
	hauntedBeryliteTop.aetherAmount = 2

	val hauntedBeryliteBottom: GemCard = GemCard("Haunted Berylite", "Discard a card in hand and gain 2 charges.")
	hauntedBeryliteBottom.doesDiscardHand = true
	hauntedBeryliteBottom.discardHandAmount = 1
	hauntedBeryliteBottom.doesGainCharge = true
	hauntedBeryliteBottom.chargeAmount = 2

	val hauntedBerylite: ChoiceCard = 
	ChoiceCard("Haunted Berylite", "Gain 2 Aether\n OR \n Discard a card \n in hand and gain \n 2 charges.")
	hauntedBerylite.cardCost = 3
	hauntedBerylite.top = hauntedBeryliteTop
	hauntedBerylite.bottom = hauntedBeryliteBottom

	// Relics
	val geophageTop: RelicCard = RelicCard("Geophage", "Gain 1 Aether")
	geophageTop.doesGainAether = true
	geophageTop.aetherAmount = 1

	val geophageBottom: RelicCard = RelicCard("Geophage", "Gain 1 Aether. \n You destroy a card in hand.")
	geophageBottom.doesGainAether = true
	geophageBottom.aetherAmount = 1
	geophageBottom.doesDestroyHand = true
	geophageBottom.destroyHandAmount = 1

	val geophage: ChoiceCard = ChoiceCard("Geophage", "Gain 1 Aether.\nYou may destroy \na card in hand.")
	geophage.cardCost = 3
	geophage.top = geophageTop
	geophage.bottom = geophageBottom

	val etherealHand: RelicCard = RelicCard("Ethereal Hand", "You draw two \ncards.")
	etherealHand.cardCost = 6
	etherealHand.doesDraw = true
	etherealHand.drawAmount = 2

	//Spells

	val ignite: SpellCard = SpellCard("Ignite", "Cast: Deal 2 damage.\n You gain 1 charge.")
	ignite.doesDmg = true
	ignite.dmgAmount = 2
	ignite.doesGainCharge = true
	ignite.chargeAmount = 1

	val sagesBrand: SpellCard = SpellCard("Sage's Brand", "While Prepped, \ndraw an additional\n card during your \ndraw phase.\n Cast: Deal 6 damage.")
	sagesBrand.doesDmg = true
	sagesBrand.dmgAmount = 6
	sagesBrand.doesDrawWhilePrepped = true
	sagesBrand.drawWhilePreppedAmount = 1

	shopList = ignite :: sagesBrand :: shopList
	shopList = geophage :: etherealHand :: shopList
	shopList = hauntedBerylite::allayingShell::shopList

	shopList
