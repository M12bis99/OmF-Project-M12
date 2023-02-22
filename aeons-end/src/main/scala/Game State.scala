import util.control.Breaks._
import scala.io.StdIn.readLine
import scala.util.Random
import scala.math._

class GameState(val nemesis: Nemesis, val mage: Mage, val shop: Shop):
	var gameEnd: Boolean = false
	var gameWon: Boolean = false

	def gameEnded(): Unit = 
		if gameWon then
			println("Congratulations "+mage.name+", you defeated "+nemesis.name+"!")
		else
			println("Tough luck, "+mage.name+", you couldn't stop "+nemesis.name+".")
	
	// true: Mage turn, false: Nemesis Turn
	val turnOrderList: List[Boolean] = Random.shuffle(List(true, true, true, false, false))
	var currentTurnOrder: List[Boolean] = turnOrderList
	var currentTurn: Boolean = true
	
	def nextTurn(): Boolean = currentTurnOrder match
		case Nil => currentTurnOrder = Random.shuffle(turnOrderList)
			nextTurn()
		case x::xs => currentTurnOrder = xs
			x
			

	// defining functions affecting the Mage
	def mageTakesDmg(dmg: Int): Unit = 
		mage.currentHealth -= dmg
		if mage.currentHealth <= 0 then 
			gameWon = false
			gameEnd = true

	def mageHeals(heal: Int): Unit =
		mage.currentHealth = min(mage.maxHealth, mage.currentHealth + heal)

	def nemesisTakesDmg(dmg: Int): Unit = 
		nemesis.currentHealth -= dmg
		if nemesis.currentHealth <= 0 then
			gameWon = true
			gameEnd = true
	
	def monsterTakesDmg(monsterName: String, dmg: Int): Unit = 
		var monster: MonsterCard = MonsterCard("", "")
		monster = nemesis.persistentMonsters.fold(monster)((x: MonsterCard, y: MonsterCard) => if y.title == monsterName then y else x)
		monster.health -= dmg
		if monster.health <= 0 then
			println("You killed $monster.title.")
			nemesis.persistentMonsters = nemesis.persistentMonsters.filter(x => x.title != monsterName)
	
	def silenceMonster(monsterName: String): Unit = 
		var monster: MonsterCard = MonsterCard("", "")
		monster = nemesis.persistentMonsters.fold(monster)((x: MonsterCard, y: MonsterCard) => if y.title == monsterName then y else x)
		monster.silenced = true
		println("You silenced $monster.title.")


	def mageDestroysSpells(amountToDestroy: Int): Unit = mageDiscardsOrDestroysSpells(amountToDestroy, false)

	def mageDiscardsSpells(amountToDiscard: Int): Unit = mageDiscardsOrDestroysSpells(amountToDiscard, true)
	
	// discarding or destroying a spell are the same, execpt that when you discard you put it into the discard pile
	def mageDiscardsOrDestroysSpells(amountToDiscardOrDestroy: Int, discard: Boolean): Unit = 
		val breach1 = mage.firstBreach.currentSpell
		val breach2 = mage.secondBreach.currentSpell
		val breach3 = mage.thirdBreach.currentSpell
		val breach4 = mage.fourthBreach.currentSpell
		var numberPreppedSpells: Int = breach1.length + breach2.length + breach3.length + breach4.length
		var amount = amountToDiscardOrDestroy
		var discardOrDestroyText = "detroy"
		if discard then discardOrDestroyText = "discard"
		
		
		// The Player can choose which to discard or destroy
		def askToDiscardSpells(): Unit = 
			var spellToDiscard: String = ""
			var spell: SpellCard = SpellCard("", "")
			
			println("   You have to $discardOrDestroyText $amount Spell(s). Which spell do you want to $discardOrDestroyText?")
			for i <- 1 to amount do
				var notFound: Boolean = true
				while notFound do
				breakable {
					notFound = false
					spellToDiscard = readLine("Please enter the Spells name: ")
					if breach1.length == 1 then
						spell = breach1.head
						if spell.title == spellToDiscard then
							mage.firstBreach.currentSpell = breach1.drop(1)
							if discard then mage.discardPile = mage.discardPile ::: spell::Nil
							break	
					if breach2.length == 1 then
						spell = breach2.head
						if spell.title == spellToDiscard then
							mage.firstBreach.currentSpell = breach2.drop(1)
							if discard then mage.discardPile = mage.discardPile ::: spell::Nil
							break
					if breach3.length == 1 then
						spell = breach3.head
						if spell.title == spellToDiscard then
							mage.firstBreach.currentSpell = breach3.drop(1)
							if discard then mage.discardPile = mage.discardPile ::: spell::Nil
							break
					if breach4.length == 1 then
						spell = breach4.head
						if spell.title == spellToDiscard then
							mage.firstBreach.currentSpell = breach4.drop(1)
							if discard then mage.discardPile = mage.discardPile ::: spell::Nil
							break
					println("Spell with that name not found, please try again.")
					notFound = true
				}

		numberPreppedSpells match
		case 0 => println("   No Spells prepped to $discardOrDestroyText.")
		case x => if amount > x then amount = x
			askToDiscardSpells()

	def mageDestroysHand(amountToDestroy: Int): Unit = mageDiscardsOrDestroysHand(amountToDestroy, false)

	def mageDiscardsHand(amountToDiscard: Int): Unit = mageDiscardsOrDestroysHand(amountToDiscard, true)

	def mageDiscardsOrDestroysHand(amountToDiscardOrDestroy: Int, discard: Boolean): Unit =
		var hand = mage.hand
		var amount = amountToDiscardOrDestroy
		var discardOrDestroyText = "detroy"
		if discard then discardOrDestroyText = "discard"
		
		
		// The Player can choose which to discard or destoy
		def askToDiscardHand(): Unit = 
			var cardToDiscard: String = ""
			var card: MageCard = SpellCard("", "")
			
			println("   You have to $discardOrDestroyText $amount card(s). Which card do you want to $discardOrDestroyText?")
			for i <- 1 to amount do
				var notFound: Boolean = true
				while notFound do
				breakable {
					notFound = false
					cardToDiscard = readLine("Please enter the cards name: ")
					card = hand.fold(card)((x: MageCard, y: MageCard) => if y.title == cardToDiscard then y else x)
					if card.title != "" then 
						var found = false
						hand = hand.filter(x => found || x.title != cardToDiscard || {found = true; false})
						if discard then mage.discardPile = mage.discardPile ::: card::Nil
						break
					println("Card with that name not found, please try again.")
					notFound = true
				}
			mage.hand = hand

		hand.length match
		case 0 => println("   No Card in hand to $discardOrDestroyText.")
		case x => if amount > x then amount = x
			askToDiscardHand()

	def mageLosesCharges(): Unit = mage.ability.currentCharges = 0

	def mageDraws(n: Int): Unit = n match
		case 0 => ()
		case x => mage.draw()
			mageDraws(x - 1)
	
	def mageGainsAether(n: Int): Unit =
		mage.currentAether += n

	def mageGainsCharge(n: Int): Unit = 
		mage.ability.currentCharges = min(mage.ability.currentCharges + n, mage.ability.chargeAmount)

	def mageDoesDmg(n: Int): Unit = 
		var target: String = ""
		if nemesis.persistentMonsters.isEmpty then
			println("Only available target is $nemesis.name.")
			target = nemesis.name
		else target = askForTarget(true)

		target match
		case nemesis.name => nemesisTakesDmg(n)
		case monster => monsterTakesDmg(monster, n)

	def mageSilencesMonster(): Unit = 
		var target: String = ""
		if nemesis.persistentMonsters.isEmpty then
			println("No available target for Silence.")
		else 
			target = askForTarget(false)
			silenceMonster(target)

	def askForTarget(nemesisIsAllowed: Boolean): String =
		val t: String = readLine("Please enter the name of the creature you want to target.")
		if nemesisIsAllowed && t == nemesis.name then nemesis.name
		else
			var monster: MonsterCard = MonsterCard("", "")
			monster = nemesis.persistentMonsters.fold(monster)((x: MonsterCard, y: MonsterCard) => if y.title == t then y else x)
			if monster.title != "" then monster.title
			else
				println("Creature with that name not found. Please try again.")
				askForTarget(nemesisIsAllowed)

	// Nemesis Card "Gather Darkness" has a unique and complex effect
	def activateGatherDarkness(): Unit = 
		mage.deck = mage.discardPile ::: mage.deck
		mage.discardPile = Nil
		mage.deck = Random.shuffle(mage.deck)
		mage.deck = mage.deck.drop(4)

	// Bladius empty-Deck-Attack has a unique effect of making the Mage loose
	def activateBladiusDeckIsEmpty(): Unit = 
		println("Bladius nemesis deck is empty! He manages to escape and wreak havok on the world!")
		gameWon = false
		gameEnd = true	

	// Loops until Mage either Won or lost:
	def gameStart(): Unit =
		drawGameState(this)
		while !gameEnd do
			currentTurn = nextTurn()
			if currentTurn then mageTurn(this) else nemesisTurn(this)
			drawGameState(this)
		gameEnded()
		drawGameState(this)

end GameState

	