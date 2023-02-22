import java.awt.image.BufferedImage
import java.io._
import javax.imageio.ImageIO
import java.awt.Color
import java.awt.Graphics2D
	
def drawGameState(gameState: GameState): Unit = 
	var background: BufferedImage = ImageIO.read(new File("D:\\2. Uni\\Winter 2022 2023\\Object meets Funkiton\\Workspace\\Project\\OmF-Project-M12\\aeons-end\\src\\main\\scala\\BoardBackground.jpg"))
	var gBackground = background.createGraphics()

	//make Font bigger
	val currentFont = gBackground.getFont()
	val currentColor = gBackground.getColor()
	var newFont = currentFont.deriveFont(currentFont.getSize() * 5F)
	
	gBackground.setFont(newFont)

	val nemesis: Nemesis = gameState.nemesis
	val mage: Mage = gameState.mage
	val shop: Shop = gameState.shop

	def drawStrings(g: Graphics2D)(text: String, x: Int, y: Int): Unit = 
		var yWithOffset = y
		val splitLines: List[String] = text.split("\n").toList

		def drawStr(lines: List[String], xLine: Int, yLine: Int): Unit = lines match
			case Nil => ()
			case line::xs => g.drawString(line, xLine, yLine)
				drawStr(xs, xLine, yLine + g.getFontMetrics().getHeight())

		drawStr(splitLines, x, y)
	
	val drawString = drawStrings(gBackground)

	def drawCards(size: Float, cards: List[Card], x_title: List[Int], y_title: List[Int], x_effect: List[Int], y_effect: List[Int]): Unit = 
		def drawCard(card: Card, x_title: Int, y_title: Int, x_effect: Int, y_effect: Int ): Unit =
			//resize Font
			newFont = currentFont.deriveFont(currentFont.getSize() * size)
			gBackground.setFont(newFont)
			drawString(card.title, x_title, y_title)
			newFont = currentFont.deriveFont(currentFont.getSize() * size)
			gBackground.setFont(newFont)
			gBackground.setColor(Color.BLACK)
			drawString(card.effect, x_effect, y_effect)
			gBackground.setColor(currentColor)

		cards match
		case Nil => ()
		case card::xs => drawCard(card, x_title.head, y_title.head, x_effect.head, y_effect.head)
			drawCards(size, xs, x_title.tail, y_title.tail, x_effect.tail, y_effect.tail)

	def drawNemesis(): Unit = 
		val monsters: List[MonsterCard] = nemesis.persistentMonsters
		val powers: List[PowerCard] = nemesis.persistentPowers
		
		newFont = currentFont.deriveFont(currentFont.getSize() * 5F)
		gBackground.setFont(newFont)
		drawString(nemesis.currentHealth + "/" + nemesis.maxHealth, 450, 370)
		
		// Draw Unleash
		//make Font bigger
		newFont = currentFont.deriveFont(currentFont.getSize() * 3F)
		gBackground.setFont(newFont)
		drawString(nemesis.unleash.name, 990, 380)
		drawString(nemesis.unleash.description, 1000, 450)		

		def drawMonsters(monsters: List[MonsterCard], x_title: List[Int], y_title: List[Int], x_effect: List[Int], y_effect: List[Int], x_health: List[Int], y_health: List[Int]): Unit =
			def drawMonster(monster: MonsterCard, x_health: Int, y_health: Int): Unit =
				//resize Font
				newFont = currentFont.deriveFont(currentFont.getSize() * 2F)
				gBackground.setFont(newFont)
				drawString(monster.health.toString, x_health, y_health)	

			drawCards(1.3F, monsters, x_title, y_title, x_effect, y_effect)

			def drawMonstersWorker(monsters: List[MonsterCard], x_health: List[Int], y_health: List[Int]): Unit =
				monsters match
				case Nil => ()
				case monster::xs => drawMonster(monster, x_health.head, y_health.head)
					drawMonstersWorker(xs, x_health.tail, y_health.tail)
			
			drawMonstersWorker(monsters, x_health, y_health)

		def drawPowers(powers: List[PowerCard], x_title: List[Int], y_title: List[Int], x_effect: List[Int], y_effect: List[Int], x_Tokens: List[Int], y_Tokens: List[Int], x_discardText: List[Int], y_discardText: List[Int]): Unit =
			def drawPower(power: PowerCard, x_Tokens: Int, y_Tokens: Int, x_discardText: Int, y_discardText: Int): Unit = 
				//resize Font
				newFont = currentFont.deriveFont(currentFont.getSize() * 2F)
				gBackground.setFont(newFont)
				drawString(power.powerTokens.toString, x_Tokens, y_Tokens)
				
				if power.doesToDiscard then
					newFont = currentFont.deriveFont(currentFont.getSize() * 1.3F)
					gBackground.setFont(newFont)
					gBackground.setColor(Color.BLACK)
					drawString(power.toDiscardText.toString, x_discardText, y_discardText)
					gBackground.setColor(currentColor)


			drawCards(1.3F, powers, x_title, y_title, x_effect, y_effect)

			def drawPowersWorker(powers: List[PowerCard], x_Tokens: List[Int], y_Tokens: List[Int], x_discardText: List[Int], y_discardText: List[Int]): Unit =
				powers match
				case Nil => ()
				case power::xs => drawPower(power, x_Tokens.head, y_Tokens.head, x_discardText.head, y_discardText.head)
					drawPowersWorker(xs, x_Tokens.tail, y_Tokens.tail, x_discardText.tail, y_discardText.tail)

			drawPowersWorker(powers, x_Tokens, y_Tokens, x_discardText, y_discardText)
			

		//For testing Only:
		//val testMonster: MonsterCard = MonsterCard("TEST MONSTER", "This hould Show up\n in a textbox")
		//testMonster.health = 7
		//monsters = List(testMonster, testMonster, testMonster, testMonster, testMonster)

		//val testPower: PowerCard = PowerCard("TEST POWER", "POWER 1: This should be \n below the discard Text")
		//testPower.doesToDiscard = true
		//testPower.toDiscardText = "TO DISCARD: Spend \n7 Aether"
		//powers = List(testPower, testPower, testPower)

		val monsterTitleX: List[Int] = List(40, 255, 475, 690, 910)
		val monsterTitleY: List[Int] = List(100, 100, 100, 100, 100)
		val monsterEffectX: List[Int] = List(45, 260, 483, 703, 920)
		val monsterEffectY: List[Int] = List(160, 160, 160, 160, 160)
		val monsterHealthX: List[Int] = List(165, 389, 608, 827, 1044)
		val monsterHealthY: List[Int] = List(87, 87, 87, 87, 87)
		drawMonsters(monsters, monsterTitleX, monsterTitleY, monsterEffectX, monsterEffectY, monsterHealthX, monsterHealthY)

		val powerTitleX: List[Int] = List(1130, 1357, 1573)
		val powerTitleY: List[Int] = List(100, 100, 100)
		val powerEffectX: List[Int] = List(1135, 1362, 1578)
		val powerEffectY: List[Int] = List(222, 222, 222)
		val powerTokensX: List[Int] = List(1266, 1485, 1702)
		val powerTokensY: List[Int] = List(87, 87, 87)
		val powerDiscardTextX: List[Int] = List(1135, 1362, 1578)
		val powerDiscardTextY: List[Int] = List(160, 160, 160)
		drawPowers(powers, powerTitleX, powerTitleY, powerEffectX, powerEffectY, powerTokensX, powerTokensY, powerDiscardTextX, powerDiscardTextY)

	def drawSomeCards(size: Float, cards: List[MageCard], x_title: List[Int], y_title: List[Int], x_effect: List[Int], y_effect: List[Int], x_Cost: List[Int], y_Cost: List[Int], x_Type: List[Int], y_Type: List[Int]): Unit =
		def drawCard(card: MageCard, x_Cost: Int, y_Cost: Int, x_Type: Int, y_Type: Int): Unit =
			//resize Font
			newFont = currentFont.deriveFont(currentFont.getSize() * size * (2F/1.4F))
			gBackground.setFont(newFont)
			drawString(card.cardCost.toString, x_Cost, y_Cost)
			
			var cardType: String = ""
			card match
			case spell: SpellCard => cardType = "Spell"
			case gem: GemCard => cardType = "Gem"
			case relic: RelicCard => cardType = "Relic"
			case choice: ChoiceCard => choice.top match
				case spl: SpellCard => cardType = "Spell"
				case gm: GemCard => cardType = "Gem"
				case rlc: RelicCard => cardType = "Relic"

			drawString(cardType, x_Type, y_Type)

			drawCards(size, cards, x_title, y_title, x_effect, y_effect)

		def drawCardsWorker(hand: List[MageCard], x_Cost: List[Int], y_Cost: List[Int], x_Type: List[Int], y_Type: List[Int]): Unit =
			hand match
			case Nil => ()
			case card::xs => drawCard(card, x_Cost.head, y_Cost.head, x_Type.head, y_Type.head)
				drawCardsWorker(xs, x_Cost.tail, y_Cost.tail, x_Type.tail, y_Type.tail)
			
		drawCardsWorker(cards, x_Cost, y_Cost, x_Type, y_Type)

	def drawMage(): Unit = 
		val hand: List[MageCard] = mage.hand
		val breachList: List[Breach] = mage.firstBreach::mage.secondBreach::mage.thirdBreach::mage.fourthBreach::Nil
		
		newFont = currentFont.deriveFont(currentFont.getSize() * 5F)
		gBackground.setFont(newFont)
		drawString(mage.currentHealth + "/" + mage.maxHealth, 450, 470)
		newFont = currentFont.deriveFont(currentFont.getSize() * 4F)
		gBackground.setFont(newFont)
		drawString(mage.currentAether.toString, 1435, 763)

		def drawBreaches(breachList: List[Breach], x_title: List[Int], y_title: List[Int], x_effect: List[Int], y_effect: List[Int], x_Status: List[Int], y_Status: List[Int]): Unit = 
			var spellList: List[SpellCard] = Nil			

			def drawBreach(breach: Breach, x_Status: Int, y_Status: Int): Unit =
				if breach.currentSpell.isEmpty then spellList = spellList ::: SpellCard("", "")::Nil
				else spellList = spellList ::: breach.currentSpell
				
				var status: String = ""
				if breach.currentPosition == 0 then status = "open"
				else if breach.focusedThisTurn then status = "focused"
				else status = "closed"

				val offSet: Int = 48
				val radius: Int = 30

				gBackground.setColor(Color.YELLOW)
				breach.currentPosition match
				case 1 => ()
				case 2 => gBackground.fillOval(x_Status, y_Status, radius, radius)
				case 3 => gBackground.fillOval(x_Status, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet, y_Status, radius, radius)
				case 4 => gBackground.fillOval(x_Status, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet * 2, y_Status, radius, radius)
				case 0 => gBackground.fillOval(x_Status, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet * 2, y_Status, radius, radius)
					gBackground.fillOval(x_Status + offSet * 3, y_Status, radius, radius)

				drawString(status, x_Status + offSet * 4, y_Status + 10)

				gBackground.setColor(currentColor)

			def drawBreachesWorker(breachList: List[Breach], x_Status: List[Int], y_Status: List[Int]): Unit =
				breachList match
				case Nil => ()
				case breach::xs => drawBreach(breach, x_Status.head, y_Status.head)
					drawBreachesWorker(xs, x_Status.tail, y_Status.tail)

			drawBreachesWorker(breachList, x_Status, y_Status)
			drawCards(1.3F, spellList, x_title, y_title, x_effect, y_effect)

		//For Testing Only
		val testSpell = SpellCard("Pow", "Cast: Big Boom.")
		val testGem = GemCard("Big Chunky", "Wow, shiny!")
		val testRelic = RelicCard("A Clock", "It's time to fight.")

		//hand = List(testSpell, testRelic, testGem, testSpell, testSpell, testGem, testRelic, testGem)
		//mage.secondBreach.currentSpell = testSpell::Nil

		val handTitleX: List[Int] = List(40, 258, 471, 682, 901, 1110, 1332, 1546)
		val handTitleY: List[Int] = List(887, 887, 887, 887, 887, 887, 887, 887)
		val handEffectX: List[Int] = List(45, 263, 476, 687, 906, 1115, 1337, 1551)
		val handEffectY: List[Int] = List(940, 940, 940, 940, 940, 940, 940, 940)
		val handCostX: List[Int] = List(186, 400, 616, 834, 1048, 1264, 1483, 1703)
		val handCostY: List[Int] = List(844, 844, 844, 844, 844, 844, 844, 844)
		val handTypeX: List[Int] = List(40, 258, 471, 682, 901, 1110, 1332, 1546)
		val handTypeY: List[Int] = List(844, 844, 844, 844, 844, 844, 844, 844)
		drawSomeCards(1.3F, hand, handTitleX, handTitleY, handEffectX, handEffectY, handCostX, handCostY, handTypeX, handTypeY)

		val breachTitleX: List[Int] = List(65, 385, 695, 1015)
		val breachTitleY: List[Int] = List(573, 573, 573, 573)
		val breachEffectX: List[Int] = List(65, 385, 695, 1015)
		val breachEffectY: List[Int] = List(640, 640, 640, 640)
		val breachStatusX: List[Int] = List(52, 365, 682, 1000)
		val breachStatusY: List[Int] = List(733, 733, 733, 733)
		drawBreaches(breachList, breachTitleX, breachTitleY, breachEffectX, breachEffectY, breachStatusX, breachStatusY)

	def drawShop(): Unit = 

		val shopTitleX: List[Int] = List(1515, 1515, 1655, 1655, 1785, 1785)
		val shopTitleY: List[Int] = List(420, 575, 420, 575, 420, 575)
		val shopEffectX: List[Int] = List(1518, 1518, 1658, 1658, 1788, 1788)
		val shopEffectY: List[Int] = List(450, 605, 450, 605, 450, 605)
		val shopCostX: List[Int] = List(1596, 1596, 1737, 1737, 1870, 1870)
		val shopCostY: List[Int] = List(400, 555, 400, 555, 400, 555)
		val shopTypeX: List[Int] = List(1515, 1515, 1655, 1655, 1785, 1785)
		val shopTypeY: List[Int] = List(395, 550, 395, 550, 395, 550)
		val shopStockX: List[Int] = List(1568, 1568, 1708, 1708, 1838, 1838)
		val shopStockY: List[Int] = List(405, 560, 405, 560, 405, 560)
		drawSomeCards(1F, shop.cardArray.toList, shopTitleX, shopTitleY, shopEffectX, shopEffectY, shopCostX, shopCostY, shopTypeX, shopTypeY)
		
		newFont = currentFont.deriveFont(currentFont.getSize() * 0.8F)
		gBackground.setFont(newFont)
		gBackground.setColor(Color.YELLOW)
		def drawStock(stock: List[Int], x_stock: List[Int], y_stock: List[Int]): Unit = stock match
			case Nil =>
			case x::xs => drawString(x+ " left", x_stock.head, y_stock.head)
				drawStock(xs, x_stock.tail, y_stock.tail)

		drawStock(shop.amountArray.toList, shopStockX, shopStockY)
		gBackground.setColor(currentColor)

	drawNemesis()
	drawMage()
	drawShop()
	ViewImage.viewImage(background, "Board State")