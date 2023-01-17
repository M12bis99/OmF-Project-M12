// Mage Card Definitions
def initializeMageCards(): Unit = 

	// Gems
	val crystal: GemCard = GemCard("Crystal", "Gain 1 Aether.")
	crystal.doesGainAether = true
	crystal.aetherAmount = 1

	val allayingShell: GemCard = GemCard("Allaying Shell", "Gain 2 Aether./n Silence a Minion.")
	allayingShell.cardCost = 5
	allayingShell.doesGainAether = true
	allayingShell.aetherAmount = 1
	allayingShell.doesSilenceMonster = true


	val hauntedBeryliteTop: GemCard = GemCard("Haunted Berylite", "Gain 2 Aether")
	hauntedBeryliteTop.doesGainAether = true
	hauntedBeryliteTop.aetherAmount = 2

	val hauntedBeryliteBottom: GemCard = GemCard("Haunted Berylite", "Discard a card in hand. If you do, gain 2 charges.")
	hauntedBeryliteBottom.doesDiscardHand = true
	hauntedBeryliteBottom.discardHandAmount = 1

	val hauntedBerylite: ChoiceCard = 
	ChoiceCard("Haunted Berylite", "Gain 2 Aether/n OR /n Discard a card in hand. If you do, gain 2 charges.")
	hauntedBerylite.cardCost = 3
	hauntedBerylite.top = hauntedBeryliteTop
	hauntedBerylite.bottom = hauntedBeryliteBottom

	// Relics
	val geophageTop: RelicCard = RelicCard("Geophage", "Gain 1 Aether")
	geophageTop.doesGainAether = true
	geophageTop.aetherAmount = 1

	val geophageBottom: RelicCard = RelicCard("Geophage", "Gain 1 Aether. /n You destroy a card in hand.")
	geophageBottom.doesGainAether = true
	geophageBottom.aetherAmount = 1
	geophageBottom.doesDestroyHand = true
	geophageBottom.destroyHandAmount = 1

	val geophage: ChoiceCard = ChoiceCard("Geophage", "Gain 1 Aether./n You may destroy a card in hand.")
	geophage.cardCost = 3
	geophage.top = geophageTop
	geophage.bottom = geophageBottom

	val etherealHand: RelicCard = RelicCard("Ethereal Hand", "You draw two cards.")
	etherealHand.cardCost = 6
	etherealHand.doesDraw = true
	etherealHand.drawAmount = 2


	//Spells
	val spark: SpellCard = SpellCard("Spark", "Cast: Deal 1 damage")
	spark.doesDmg = true
	spark.dmgAmount = 1

	val buriedLight: SpellCard = SpellCard("Buried Light", "Cast: Deal 1 damage. Gain 1 Aether")
	buriedLight.doesDmg = true
	buriedLight.dmgAmount = 1
	buriedLight.doesGainAether = true
	buriedLight.aetherAmount = 1

	val ignite: SpellCard = SpellCard("Ignite", "Cast: Deal 2 damage./n You gain 1 charge.")
	ignite.doesDmg = true
	ignite.dmgAmount = 2
	ignite.doesGainCharge = true
	ignite.chargeAmount = 1

	val sagesBrand: SpellCard = SpellCard("Sage's Brand", "While Prepped, draw an additional/n card during your draw phase./n Cast: Deal 6 damage.")
	sagesBrand.doesDmg = true
	sagesBrand.dmgAmount = 6
	sagesBrand.doesDrawWhilePrepped = true
	sagesBrand.drawWhilePreppedAmount = 1
