import scala.util.Random

def initializeNemesisCards(): List[NemesisCard] = 
	var tier1: List[NemesisCard] = Nil
	var tier2: List[NemesisCard] = Nil
	var tier3: List[NemesisCard] = Nil

	// Attacks
	// Tier 1
	val skewer: AttackCard = AttackCard("Skewer", "Unleash./n/n The Mage suffers 3 damage/nand draws a card.")
	skewer.nemesisTier = 1
	skewer.doesUnleash = true
	skewer.unleashAmount = 1
	skewer.doesDmg = true
	skewer.dmgAmount = 3
	skewer.doesDraw = true
	skewer.drawAmount = 1
	tier1 = skewer::tier1

	val lunge: AttackCard = AttackCard("Lunge", "The Mage suffers 4 damage/nand discards a card in hand.")
	lunge.nemesisTier = 1
	lunge.doesDmg = true
	lunge.dmgAmount = 4
	lunge.doesDiscardHand = true
	lunge.discardHandAmount = 1
	tier1 = lunge::tier1	

	val parry: AttackCard = AttackCard("Parry", "The Mage suffers 2 damage and discards a prepped spell")
	parry.nemesisTier = 1
	parry.doesDmg = true
	parry.dmgAmount = 4
	parry.doesDiscardSpell = true
	parry.discardSpellAmount = 1
	tier1 = parry::tier1

	// Tier 2
	val riposte: AttackCard = AttackCard("Riposte", "Unleash./n The Mage discards 2 cards in hand.")
	riposte.nemesisTier = 2
	riposte.doesUnleash = true
	riposte.unleashAmount = 1
	riposte.doesDiscardHand = true
	riposte.discardHandAmount = 2
	tier2 = riposte::tier2

	val dispel: AttackCard = AttackCard("Dispel", "Unleash twice./n/nThe Mage discards two/nprepped spell.")
	dispel.nemesisTier = 2
	dispel.doesUnleash = true
	dispel.unleashAmount = 2
	dispel.doesDiscardSpell = true
	dispel.discardSpellAmount = 2
	tier2 = dispel::tier2

	// Tier 3
	val topple: AttackCard = AttackCard("Topple", "Unleash twice./nMage suffers 4 damage.")
	topple.nemesisTier = 3
	topple.doesUnleash = true
	topple.unleashAmount = 2
	topple.doesDmg = true
	topple.dmgAmount = 4
	tier3 = topple::tier3

	val gatherDarkness: AttackCard = AttackCard("Gather Darkness", "The Mage places their discard /n"+
									"pile on top of their deck,/n"+
									"shuffles it, and then destroys/n"+
									"the top four cards of their deck./n/n"+
									"Then Unleash twice.")
	gatherDarkness.nemesisTier = 3
	gatherDarkness.doesUnleash = true
	gatherDarkness.unleashAmount = 2
	gatherDarkness.doesGatherDarkness = true
	tier3 = gatherDarkness::tier3

	// Powers
	// Tier 2
	val morbidGyre: PowerCard = PowerCard("Morbid Gyre", "POWER 1: Unleash twice./n"+
								"The mage discards two/n"+
								"cards in hand.")
	morbidGyre.nemesisTier = 2
	morbidGyre.powerTokens = 1
	morbidGyre.doesToDiscard = true
	morbidGyre.toDiscardText = "TO DISCARD: Spend 7 Aether"
	morbidGyre.toDiscardCost = 7
	morbidGyre.doesUnleash = true
	morbidGyre.unleashAmount = 2
	morbidGyre.doesDiscardHand = true
	morbidGyre.discardHandAmount = 2
	tier2 = morbidGyre::tier2

	//Tier 3
	val realityRupture: PowerCard = PowerCard("Reality Ruptrue", "POWER 1: Unleash three times.")
	realityRupture.nemesisTier = 3
	realityRupture.powerTokens = 1
	realityRupture.doesToDiscard = true
	realityRupture.toDiscardText = "TO DISCARD: Spend 8 Aether"
	realityRupture.toDiscardCost = 8
	realityRupture.doesUnleash = true
	realityRupture.unleashAmount = 2
	tier3 = realityRupture::tier3

	val fleche: PowerCard = PowerCard("Fleche", "POWER 2: Unleash twice. The/n"+
							"Mage discards 2 prepped spells.")
	fleche.nemesisTier = 3
	fleche.powerTokens = 2
	fleche.doesUnleash = true
	fleche.unleashAmount = 2
	fleche.doesDiscardSpell = true
	fleche.discardSpellAmount = 2
	tier3 = fleche::tier3

	val flyingGuillotine: PowerCard = PowerCard("Flying Guillotine", "POWER 2: The Mage/nsuffers 6 damage.")
	flyingGuillotine.nemesisTier = 3
	flyingGuillotine.powerTokens = 2
	flyingGuillotine.doesToDiscard = true
	flyingGuillotine.toDiscardText = "TO DISCARD: Spend 8 Aether"
	flyingGuillotine.toDiscardCost = 8
	flyingGuillotine.doesDmg = true
	flyingGuillotine.dmgAmount = 6
	tier3 = flyingGuillotine::tier3

	val doomAegis: PowerCard = PowerCard("Doom Aegis", "POWER 1: Unleash. The Mage/n"+
								"suffers 4 damage and loses/n"+
								"all of their charges.")
	doomAegis.nemesisTier = 3
	doomAegis.powerTokens = 1
	doomAegis.doesToDiscard = true
	doomAegis.toDiscardText = "TO DISCARD: Spend 7 Aether"
	doomAegis.toDiscardCost = 7
	doomAegis.doesDmg = true
	doomAegis.dmgAmount = 4
	doomAegis.doesLoseCharge = true
	tier3 = doomAegis::tier3
	

	// Monsters
	// Tier 1
	val vileImpaler: MonsterCard = MonsterCard("Vile Impaler", "PERSISTENT: Unleash.")
	vileImpaler.nemesisTier = 1
	vileImpaler.health = 5
	vileImpaler.doesUnleash = true
	vileImpaler.unleashAmount = 1
	tier1 = vileImpaler::tier1	

	// Tier 2
	val hateHound: MonsterCard = MonsterCard("Hate Hound", "PERSISTENT: The Mage suffers 3 damage.")
	hateHound.nemesisTier = 2
	hateHound.health = 7
	hateHound.doesDmg = true
	hateHound.dmgAmount = 3
	tier2 = hateHound::tier2

	val voraciousCorroders: MonsterCard = MonsterCard("Voracious Corroders", "PERSISTENT: The Mage suffers 2 damage.")
	voraciousCorroders.nemesisTier = 2
	voraciousCorroders.health = 9
	voraciousCorroders.doesDmg = true
	voraciousCorroders.dmgAmount = 2
	tier2 = voraciousCorroders::tier2

	val venomite: MonsterCard = MonsterCard("Venomite", "PERSISTENT: The Mage discards a prepped spell.")
	venomite.nemesisTier = 2
	venomite.health = 9
	venomite.doesDiscardSpell = true
	venomite.discardSpellAmount = 1
	tier2 = venomite::tier2

	// Tier 3
	val jaggedOne: MonsterCard = MonsterCard("Jagged One", "PERSISTENT: Unleash twice.")
	jaggedOne.nemesisTier = 3
	jaggedOne.health = 14
	jaggedOne.doesUnleash = true
	jaggedOne.unleashAmount = 2
	tier3 = jaggedOne::tier3
	
	val ironCharger: MonsterCard = MonsterCard("ironCharger", "PERSISTENT: The Mage/n"+
									"destroys a prepped spell and/n"+
									"discards a card in hand.")
	ironCharger.nemesisTier = 3
	ironCharger.health = 13
	ironCharger.doesDiscardHand = true
	ironCharger.discardHandAmount = 1
	ironCharger.doesDestroySpell = true
	ironCharger.destroySpellAmount = 1
	tier3 = ironCharger::tier3

	// assembling the Nemesis Deck
	tier1 = Random.shuffle(tier1)
	tier2 = Random.shuffle(tier2)
	tier3 = Random.shuffle(tier3)
	var nemesisDeck: List[NemesisCard] = tier1:::tier2
	//return nemesisDeck
	nemesisDeck:::tier3