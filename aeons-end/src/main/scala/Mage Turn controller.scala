import scala.io.StdIn.readLine
import util.control.Breaks._

def mageTurn(gameState: GameState): Unit = 
	val nemesis = gameState.nemesis
	val mage = gameState.mage
	val shop = gameState.shop
	
	// Casting Phase
	def castingPhase(): Unit =
		println("--- Start of Casting Phase ---")
		
		def castSpells(): Unit = 
			val breach1 = mage.firstBreach.currentSpell
			val breach2 = mage.secondBreach.currentSpell
			val breach3 = mage.thirdBreach.currentSpell
			val breach4 = mage.fourthBreach.currentSpell
			var numberPreppedSpells: Int = breach1.length + breach2.length + breach3.length + breach4.length
		
			numberPreppedSpells match
			case 0 => println("   No Spells prepped to cast.")
			case x => askToCastASpell()

		def askToCastASpell(): Unit = 
			var skip: String = readLine("If you want to cast one more spell please enter \"cast\"\n"
			+ "or if you want to stop casting enter \"skip\":")
			
			skip match
			case "skip" => skipCasting()
			case "cast" => castASpell()
			case str => println(str + "is neither \"skip\" or \"cast\", please try again.")
				askToCastASpell()

			def skipCasting(): Unit = 
				val canSkipfirst: Boolean = mage.firstBreach.focusedThisTurn && mage.firstBreach.currentSpell.isEmpty
				val canSkipsecond: Boolean = mage.secondBreach.focusedThisTurn && mage.secondBreach.currentSpell.isEmpty
				val canSkipthird: Boolean = mage.thirdBreach.focusedThisTurn && mage.thirdBreach.currentSpell.isEmpty
				val canSkipfourth: Boolean = mage.fourthBreach.focusedThisTurn && mage.fourthBreach.currentSpell.isEmpty
				val canSkip: Boolean = canSkipfirst && canSkipsecond && canSkipthird && canSkipfourth
				if canSkip then println("---End of Casting Phase---") else
					println("Spells in unopened breaches have to be cast. Cannot skip.")
					askToCastASpell()
			
			def castASpell():  Unit =
				var spellToCast: String = readLine("Please enter the name of the Spell you want to cast:")
				var spell: SpellCard = SpellCard("", "")
				
				val breach1 = mage.firstBreach.currentSpell
				val breach2 = mage.secondBreach.currentSpell
				val breach3 = mage.thirdBreach.currentSpell
				val breach4 = mage.fourthBreach.currentSpell

				def findSpellInBreach(breach: List[SpellCard]): Boolean =
					def findSpellToCast(x: SpellCard, y: SpellCard): SpellCard = 
						if y.title == spellToCast then y else x
					spell = breach.fold(spell)(findSpellToCast)
					spell.title == spellToCast
				
				if findSpellInBreach(breach1) then
					castSpell(mage.firstBreach, spell)
				else if findSpellInBreach(breach2) then
					castSpell(mage.secondBreach, spell)
				else if findSpellInBreach(breach3) then
					castSpell(mage.thirdBreach, spell)
				else if findSpellInBreach(breach4) then
					castSpell(mage.fourthBreach, spell)
				else
					println("Spell with that name not found, please try again.")
					castASpell()

				// Maybe there are more spells to cast after this one
				castSpells()

		def castSpell(breach: Breach, spell: SpellCard): Unit =
			//put card from breach into discard pile
			var breachSpells: List[SpellCard] = breach.currentSpell
			var found: Boolean = false
			breachSpells = breachSpells.filter(x => found || x.title != spell.title || {found = true; false})
			mage.discardPile = mage.discardPile ::: spell::Nil

			activateSpell(spell)

		def activateSpell(spell: SpellCard): Unit =
			println("   " + spell.title + " get's cast!")
			println("   -" + spell.effect)
			if spell.doesDmg then
				gameState.mageDoesDmg(spell.dmgAmount)
			if spell.doesGainAether then
				gameState.mageGainsAether(spell.aetherAmount)
			if spell.doesGainCharge then
				gameState.mageGainsCharge(spell.chargeAmount)

		castSpells()
		println("---End of Casting Phase---")


	// Main Phase
	def mainPhase(): Unit =
		println("---Start of Main Phase---")
		var turnOngoing: Boolean = true
		
		def takeAction(): Unit = 
			println("As a Mage you can do the following multiple times in any order:")
			println(" - [play] a card")
			println(" - buy a card from the [shop]")
			println(" - buy a [charge]")
			println(" - activate your Mages [ability]")
			println(" - [end] your turn")
			println(" - [focus] a breach")
			println(" - [open] a breach")
			println(" - pay aether to discard a [power]")
			val action: String = readLine("Enter the word in [quare brakets] corresponding to what you want to do: ")		

			def playACard(): Unit = 
				var cardToPlay: String = readLine(" Enter the name of the card you want to play: ")
				var card: MageCard = SpellCard("", "")
				var hand: List[MageCard] = mage.hand
				card = hand.fold(card)((x: MageCard, y: MageCard) => if y.title == cardToPlay then y else x)
				if card.title != "" then 
					var found = false
					hand = hand.filter(x => found || x.title != card.title || {found = true; false})
					
					card match
					case spell: SpellCard => playSpell(spell)
					case gem: GemCard => playGem(gem)
					case relic: RelicCard => playRelic(relic)
					case choice: ChoiceCard => playChoice(choice)
				else println("Card with that name wasn't in your hand.")

				def playSpell(spell: SpellCard): Unit = 
					val firstAccepts: Boolean = mage.firstBreach.focusedThisTurn && mage.firstBreach.currentSpell.isEmpty
					val secondAccepts: Boolean = mage.secondBreach.focusedThisTurn && mage.secondBreach.currentSpell.isEmpty
					val thirdAccepts: Boolean = mage.thirdBreach.focusedThisTurn && mage.thirdBreach.currentSpell.isEmpty
					val fourthAccepts: Boolean = mage.fourthBreach.focusedThisTurn && mage.fourthBreach.currentSpell.isEmpty

					val canPrepSpell: Boolean = firstAccepts || secondAccepts || thirdAccepts || fourthAccepts

					if !canPrepSpell then
						println("All breaches are either full, not open or weren't focused this turn. Cannot prep Spells.")
						mage.hand = spell :: mage.hand
					else prepSpell()

					def prepSpell(): Unit = ()
						val whichBreach: String = readLine("In which breach do you want to prepare this spell in? [1], [2], [3] or [4]?")
						whichBreach match
						case "[1]" => if firstAccepts then mage.firstBreach.currentSpell = spell::Nil else
							println("This Breach is full, not open or wasn't focused this turn. Cannot prep Spell.")
							mage.hand = spell :: mage.hand
						case "[2]" => if secondAccepts then mage.secondBreach.currentSpell = spell::Nil else
							println("This Breach is full, not open or wasn't focused this turn. Cannot prep Spell.")
							mage.hand = spell :: mage.hand
						case "[3]" => if thirdAccepts then mage.thirdBreach.currentSpell = spell::Nil else
							println("This Breach is full, not open or wasn't focused this turn. Cannot prep Spell.")
							mage.hand = spell :: mage.hand
						case "[4]" => if fourthAccepts then mage.fourthBreach.currentSpell = spell::Nil else
							println("This Breach is full, not open or wasn't focused this turn. Cannot prep Spell.")
							mage.hand = spell :: mage.hand
						case _ => println("That breach does not exist. (Don't forget the [brakets])")
							mage.hand = spell :: mage.hand
	
				def playGem(gem: GemCard): Unit = 
					mage.discardPile = mage.discardPile ::: gem::Nil
					activateGem(gem)

				def playRelic(relic: RelicCard): Unit = 
					mage.discardPile = mage.discardPile ::: relic::Nil
					activateRelic(relic)

				def playChoice(choice: ChoiceCard): Unit =
					mage.discardPile = mage.discardPile ::: choice::Nil
					activateChoice(choice)
					

				def activateGem(gem: GemCard): Unit = 
					println("   " + gem.title + " get's played!")
					println("   -" + gem.effect)
					if gem.doesGainAether then
						gameState.mageGainsAether(gem.aetherAmount)
					if gem.doesSilenceMonster then
						gameState.mageSilencesMonster()
					if gem.doesDiscardHand then
						gameState.mageDiscardsHand(gem.discardHandAmount)
					if gem.doesGainCharge then
						gameState.mageGainsCharge(gem.chargeAmount)

				def activateRelic(relic: RelicCard): Unit = 
					println("   " + relic.title + " get's played!")
					println("   -" + relic.effect)
					if relic.doesGainAether then
						gameState.mageGainsAether(relic.aetherAmount)
					if relic.doesDestroyHand then
						gameState.mageDestroysHand(relic.destroyHandAmount)
					if relic.doesDraw then
						gameState.mageDraws(relic.drawAmount)

				def activateChoice(choice: ChoiceCard): Unit = 
					val top: MageCard = choice.top
					val bottom: MageCard = choice.bottom

					println("With this card you have a choice:")
					println(" - [top] for:" + top.effect)
					println(" - [bottom] for:" + bottom.effect)
					val choose: String = readLine("Enter your choice: ")

					choose match
					case "[top]" => top match
						case gem: GemCard => activateGem(gem)
						case relic: RelicCard => activateRelic(relic)
					case "[bottom]" => bottom match
						case gem: GemCard => activateGem(gem)
						case relic: RelicCard => activateRelic(relic)
					case _ => println("Only [top] or [bottom], please try again.")
						activateChoice(choice)

			def buyACard(): Unit = 
				val cards = shop.cardArray
				val cardAmounts = shop.amountArray
				val cardToBuy: String = readLine("Enter the name of the card you want to buy: ")
				var found: Boolean = false
				breakable {
				for i <- 0 to cards.length-1 do
					found = cardToBuy == cards(i).title
					if found then
						if cardAmounts(i) > 0 then
							val cardPrice: Int = cards(i).cardCost
							if mage.currentAether >= cardPrice then
								mage.currentAether -= cardPrice
								cardAmounts.update(i, cardAmounts(i) - 1)
								mage.discardPile = mage.discardPile ::: cards(i)::Nil
								println(cardToBuy + " was bought.")
							else println("You don't have enough aether to buy the card.")
						else println("This Card is sold out!")
						break
				println("That card is not in the shop.")
				}
				

			def buyACharge(): Unit = 
				if mage.ability.currentCharges >= mage.ability.chargeAmount then
					println("You already have enough charges. Cannot gain more.")
				else if mage.currentAether >= mage.chargeCost then
					mage.currentAether -= mage.chargeCost
					gameState.mageGainsCharge(1)
					println("You gained a charge.")
				else println("Not enough aether to buy a charge.")

			def activateAbility(): Unit = 
				val ability: MageAbility = mage.ability
				if ability.currentCharges >= ability.chargeAmount then
					println("   " + ability.name + " get's activated!")
					println("   " + ability.description)
					if ability.doesHealing then
						gameState.mageHeals(ability.healingAmount)
				else println("Not enough Charges to activate.")

			def focusABreach(): Unit = 
				val whichBreach: String = readLine("Which breach do you want to focus? [1], [2], [3] or [4]?")
				whichBreach match
				case "[1]" => mage.focus(mage.firstBreach)
				case "[2]" => mage.focus(mage.secondBreach)
				case "[3]" => mage.focus(mage.thirdBreach)
				case "[4]" => mage.focus(mage.fourthBreach)
				case _ => println("That breach does not exist. (Don't forget the [brakets])")
	

			def openABreach(): Unit = 
				val whichBreach: String = readLine("Which breach do you want to open? [1], [2], [3] or [4]?")
				whichBreach match
				case "[1]" => mage.open(mage.firstBreach)
				case "[2]" => mage.open(mage.secondBreach)
				case "[3]" => mage.open(mage.thirdBreach)
				case "[4]" => mage.open(mage.fourthBreach)
				case _ => println("That breach does not exist. (Don't forget the [brakets])")

			def discardPower(): Unit =
				val powerToDiscard: String = readLine("Enter name of power you want to pay of: ")
				var power: PowerCard = PowerCard("", "")
				power = nemesis.persistentPowers.fold(power)((x: PowerCard, y: PowerCard) => if y.title == powerToDiscard then y else x)
				if power.title != "" then
					if power.doesToDiscard then
						if mage.currentAether >= power.toDiscardCost then
							mage.currentAether -= power.toDiscardCost
							nemesis.persistentPowers = nemesis.persistentPowers.filter(x  => x.title != power.title)
							println("Discarded that power.")
						else println("Not enough aether to discard this power.")
					else println("This Power cannot be discarded.")
				else println("Power with that name not found.")

			def endMainPhase(): Unit = 
				mage.firstBreach.focusedThisTurn = mage.firstBreach.currentPosition != 0
				mage.secondBreach.focusedThisTurn = mage.secondBreach.currentPosition != 0
				mage.thirdBreach.focusedThisTurn = mage.thirdBreach.currentPosition != 0
				mage.fourthBreach.focusedThisTurn = mage.fourthBreach.currentPosition != 0

				mage.currentAether = 0
				turnOngoing = false

				println("---End of Main Phase---")

			action match
				case "[play]" => playACard()
				case "[shop]" => buyACard()
				case "[charge]" => buyACharge()
				case "[ability]" => activateAbility()
				case "[focus]" => focusABreach()
				case "[open]" => openABreach()
				case "[power]" => discardPower()
				case "[end]" => endMainPhase()
				case _ => println("Not a valid action (Don't forget the [brakets]).")

		def takeActions(): Unit = 
			while turnOngoing do takeAction()
		
		takeActions()

		
	def drawPhase(): Unit = 
		mage.drawUpTo5()
		var spellList = mage.firstBreach.currentSpell ::: mage.secondBreach.currentSpell ::: mage.thirdBreach.currentSpell ::: mage.fourthBreach.currentSpell
		spellList = spellList.filter(x => x.doesDrawWhilePrepped)
		gameState.mageDraws(spellList.length)

	println("---Start of Mage Turn---")
	castingPhase()
	mainPhase()
	drawPhase()
	println("---End of Mage Turn---")




