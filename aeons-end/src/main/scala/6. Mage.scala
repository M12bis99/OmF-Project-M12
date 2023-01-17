class Mage(val mageName:String, val maxHealth: Int, val breachStartPositions: Array[Int], var deck: List[Card], var ability: MageAbility) :
	var currentHealth: Int = maxHealth
	var currentAether: Int = 0
	val chargeCost: Int = 2

	var discardPile: List[Card] = Nil
	var hand: List[Card] = Nil

	//Breaches	
	var firstBreach = Breach(1, breachStartPositions(0))
	var secondBreach = Breach(2, breachStartPositions(1))
	var thirdBreach = Breach(3, breachStartPositions(2))
	var fourthBreach = Breach(4, breachStartPositions(3))
	
	def draw(): Unit = deck match
		case Nil => 
			println("Deck ist empty. Discard pile becomes deck.")
			deck = discardPile
			discardPile = Nil
			deck match
			case Nil => println("Deck and Discard are empty. Cannot draw cards")
			case x::xs =>
				deck = xs
				hand = x :: hand
		case x::xs => 
			deck = xs
			hand = x :: hand
	

	def drawUpTo5(): Unit = 
		if (hand.length < 5) then
			draw()
			drawUpTo5()
		else ()

	def gainCharge(): Unit =
		if (ability.currentCharges >= ability.chargeAmount) then
			println("You already have enough charges. Cannot gain more.")
		else
			if (currentAether <= chargeCost) then
				currentAether = currentAether - chargeCost
				ability.currentCharges = ability.currentCharges + 1
			else println("Not enough Aether to by a charge.")

	def focus(breach: Breach): Unit = breach.currentPosition match
		case 0 => println("Cannot focus an open Breach.")
		case 1 => 
			if (breach.focusCost <= currentAether) then
				breach.currentPosition = 2
				breach.openCost = breach.openCost - breach.openCostReduction
				currentAether = currentAether - breach.focusCost
				println("Breach number "+breach.number+" focused.")
			else
				println("You do not have enough Aether to afford focusing this breach.")
		case 2 =>
			if (breach.focusCost <= currentAether) then
				breach.currentPosition = 3
				breach.openCost = breach.openCost - breach.openCostReduction
				currentAether = currentAether - breach.focusCost
				println("Breach number "+breach.number+" focused.")
			else
				println("You do not have enough Aether to afford focusing this breach.")
		case 3 =>
			if (breach.focusCost <= currentAether) then
				breach.currentPosition = 4
				breach.openCost = breach.openCost - breach.openCostReduction
				currentAether = currentAether - breach.focusCost
				println("Breach number "+breach.number+" focused.")
			else
				println("You do not have enough Aether to afford focusing this breach.")
		case 4 =>
			if (breach.focusCost <= currentAether) then
				breach.currentPosition = 0
				currentAether = currentAether - breach.focusCost
				println("Breach number "+breach.number+" opened.")
			else
				println("You do not have enough Aether to afford focusing this breach.")	

	def open(breach: Breach): Unit = 
		if (breach.currentPosition == 0) then
			println("This Breach is already open.")
		else
			if (breach.openCost <= currentAether) then
				currentAether = currentAether - breach.openCost
				breach.currentPosition = 0
			else
				println("You do not have enough Aether to afford opening this breach")	

	// placeholder
	def castSpell(spell: SpellCard): Unit = ()

	//placeholder
	def playCard(card: Card): Unit = ()

end Mage

class Breach(val number: Int, var currentPosition: Int):
	val focusCost: Int = number
	val openCostReduction: Int = number - 1
	var openCost: Int = number + (currentPosition - 1) * openCostReduction
	
	var currentSpell: List[Card] = Nil
	
	def addSpell(card: SpellCard): Unit = 
		if (currentPosition != 0) then println("This Breach is closed.")
		else
			if (currentSpell.isEmpty)
				currentSpell = card::Nil
			else	println("Breach is already full.")

end Breach

class MageAbility(val name: String, val description: String, val chargeAmount: Int):
	var currentCharges:Int = 0
	
	var doesHealing: Boolean = false
	var healingAmount: Int = 0

	//placeholder
	def activateAbility(): Unit =
		if (currentCharges == chargeAmount) then
			()
		else println("Not enough Charges to activate.")

end MageAbility