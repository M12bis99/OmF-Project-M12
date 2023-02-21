def nemesisTurn(gameState: GameState): Unit = 
	val nemesis = gameState.nemesis
	val mage = gameState.mage
	
	//Resolve Unleash (used in a lot of different cards)
	def activateUnleash(unleashEffect: Unleash, unleashAmount: Int): Unit = 
		println("   " + unleashEffect.name + " activated " + unleashAmount + " time(s)!")
		println("   - " + unleashEffect.description)
		
		for i <- 1 to unleashAmount do 
		// for future Nemesis expand check List of effects below (since bladius only does Dmg only one if needed)
			if unleashEffect.doesDmg then
				gameState.mageTakesDmg(unleashEffect.dmgAmount)
	
	
	// Activate all Persistent effects of Monsters currently on the field
	def activatePersistentMonsters(monsterList: List[MonsterCard]): Unit = 
		println(" Activating all Monsters Persistent effects:")
		
		//Activating a single Monster
		def activateMonster(monster: MonsterCard): Unit = 
			if monster.silenced then 
				println("  - " + monster.title + " was silenced and doesn't act!")
				monster.silenced = false
			else
				println("  - " + monster.title + " get's activated!")
				println("    " + monster.effect)
				if monster.doesUnleash then
					activateUnleash(nemesis.unleash, monster.unleashAmount)
				if monster.doesDmg then
					gameState.mageTakesDmg(monster.dmgAmount)
				if monster.doesDiscardSpell then
					gameState.mageDiscardsSpells(monster.discardSpellAmount)
				if monster.doesDestroySpell then
					gameState.mageDestroysSpells(monster.destroySpellAmount)
				if monster.doesDiscardHand then
					gameState.mageDiscardsHand(monster.discardHandAmount)
			
		
		monsterList match
			case Nil => println("  - No more Monsters to activate")
			case monster::xs => activateMonster(monster)
				if gameState.gameEnd then
					println(monster.title + "brought you low!")
					gameState.gameEnded()
				else
					activatePersistentMonsters(xs)

	def activatePersistentPowers(powerList: List[PowerCard]): Unit = 
		println(" Activating all Powers:")
		
		//Activating a single Power
		def activatePower(power: PowerCard): Unit = 
			if power.powerTokens > 1 then 
				power.powerTokens -= 1
				println("  - " + power.title + " looses a Power Token!")
			else
				println("  - " + power.title + " looses a Power Token and get's activated!")
				println("    " + power.effect)
				if power.doesUnleash then
					activateUnleash(nemesis.unleash, power.unleashAmount)
				if power.doesDmg then
					gameState.mageTakesDmg(power.dmgAmount)
				if power.doesDiscardSpell then
					gameState.mageDiscardsSpells(power.discardSpellAmount)
				if power.doesDestroySpell then
					gameState.mageDestroysSpells(power.destroySpellAmount)
				if power.doesDiscardHand then
					gameState.mageDiscardsHand(power.discardHandAmount)
				if power.doesLoseCharge then
					gameState.mageLosesCharges()
			
		
		powerList match
			case Nil => println("  - No more Powers to activate")
			case power::xs => activatePower(power)
				if gameState.gameEnd then
					println(power.title + "brought you low!")
					gameState.gameEnded()
				else
					activatePersistentPowers(xs)

	def activateAttack(attack: AttackCard): Unit = 
		println("   " + attack.title + " get's played!")
		println("   -" + attack.effect)
		if attack.NemesisDeckIsEmpty then
			gameState.activateBladiusDeckIsEmpty()
		if attack.doesGatherDarkness then
			gameState.activateGatherDarkness()
		if attack.doesUnleash then
			activateUnleash(nemesis.unleash, attack.unleashAmount)
		if attack.doesDmg then
			gameState.mageTakesDmg(attack.dmgAmount)
		if attack.doesDraw then
			gameState.mageDraws(attack.drawAmount)
		if attack.doesDiscardSpell then
			gameState.mageDiscardsSpells(attack.discardSpellAmount)
		if attack.doesDestroySpell then
			gameState.mageDestroysSpells(attack.destroySpellAmount)
		if attack.doesDiscardHand then
			gameState.mageDiscardsHand(attack.discardHandAmount)

	def drawNemesisCard(): Unit = 
		val card: NemesisCard = nemesis.draw()
		card match
		case attack: AttackCard => activateAttack(attack)
			nemesis.lastPlayedAttack = attack
			if gameState.gameEnd then
				println(attack.title + " brought you low!")
				gameState.gameEnded()
		case monster: MonsterCard => println(monster.title + " was drawn and put onto the field!")
			nemesis.persistentMonsters = nemesis.persistentMonsters ::: monster::Nil
		case power: PowerCard => println(power.title + "was drawn and put onto the field!")
			nemesis.persistentPowers = nemesis.persistentPowers ::: power::Nil

	println("---Start of Nemesis Turn---")
	activatePersistentMonsters(nemesis.persistentMonsters)
	activatePersistentPowers(nemesis.persistentPowers)
	drawNemesisCard()
	println("---End of Nemesis Turn---")